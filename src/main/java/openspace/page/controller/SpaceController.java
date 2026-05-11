package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.SessionConst;
import openspace.page.domain.User;
import openspace.page.dto.CommonResponse;
import openspace.page.dto.space.SpaceDetail;
import openspace.page.dto.space.SpaceList;
import openspace.page.dto.space.SpaceRegister;
import openspace.page.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/space")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/register")
    public String newSpace() {
        return "space/form";
    }


    // 등록 / 수정 시 같은 controller 사용한다.
    @PostMapping
    public String createSpace(
            @Valid SpaceRegister register,
            BindingResult bindingResult,
            @RequestParam(required = false) List<MultipartFile> images,
            HttpSession session,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "space/form";
        }
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        try {
            spaceService.createSpace(register, images, loginUser.getId());
            // PRG (post redirect get)
            return "redirect:/space/my";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "파일 업로드 중 오류 발생했습니다.");
            return "space/form";
        }
    }

    @GetMapping
    public String spaceList(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "1") int page,
            Model model) {
        Map<String, Object> spaces = spaceService.showSpaceList(keyword, page, 9);
        String string = spaces.toString();
        log.info("map {}", string);
        model.addAllAttributes(spaces);
        return "space/list";
    }

    /**
     * 내 공간 페이지로 호출
     *
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String mySpace(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        List<SpaceList> spaceListByHostId = spaceService.getSpaceListByHostId(loginUser.getId());
        model.addAttribute("spaces", spaceListByHostId);
        return "space/my_space";
    }
    // 공간 상세
    @GetMapping("/{id}")
    public String spaceDetail(@PathVariable int id, Model model) {
        log.info("id = {}" , id);

        return "space/detail";
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> spaceDelete(@PathVariable Long id, HttpSession session) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null) {
            return ResponseEntity.status(400).body(CommonResponse.error("로그인이 필요합니다."));
        }
        log.info("삭제 서비스 실행 {}", id);
        spaceService.deleteSpace(id, loginUser.getId());
        return ResponseEntity.ok().body(CommonResponse.success(null));
    }
    // 공간 수정
    @GetMapping("/{id}/edit")
    public String spaceEdit(@PathVariable Long id, HttpSession session, Model model) {
        SpaceDetail spaceDetail = spaceService.getSpaceDetail(id);

        User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);
        if(!loginUser.getId().equals(spaceDetail.getHostId())) {
            return "error/404";
        }
        model.addAttribute("spaceDetail", spaceDetail);
        return "space/edit";
    }
    // 공간 수정 등록
    @PostMapping("/{id}")
    public String spaceUpdate(@PathVariable Long id,
                              @Valid SpaceRegister register, BindingResult bindingResult,
                              HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            SpaceDetail spaceDetail = spaceService.getSpaceDetail(id);
            model.addAttribute("spaceDetail", spaceDetail);
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "space/edit";
        }
        try {
            User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);
            spaceService.updateSpace(id, register, loginUser.getId());
            return "redirect:/space/" + id;
        } catch (RuntimeException e) {
            SpaceDetail spaceDetail = spaceService.getSpaceDetail(id);
            model.addAttribute("spaceDetail", spaceDetail);
            return "space/edit";
        }
    }
}
