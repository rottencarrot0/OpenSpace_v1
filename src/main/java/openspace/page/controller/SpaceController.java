package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.SessionConst;
import openspace.page.domain.User;
import openspace.page.dto.space.SpaceRegister;
import openspace.page.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

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
    ) throws IOException {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "space/form";
        }
        User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);

        spaceService.createSpace(register, images, loginUser.getId());
        // PRG (post redirect get)
        return "redirect:/space/my_space";
    }

    /**
     * 내 공간 페이지로 호출
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/my_space")
    public String mySpace(HttpSession session, Model model) {

        return "space/my_space";
    }
}
