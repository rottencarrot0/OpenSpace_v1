package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.config.dto.space.SpaceRegister;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/space")
public class SpaceController {

    @GetMapping("/register")
    public String newSpace() {
        return "space/form";
    }

    // 등록 / 수정 시 같은 controller 사용한다.
    @PostMapping
    public String createSpace(
            @Valid SpaceRegister register,
            BindingResult bindingResult,
            HttpSession session,
            Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "space/form";
        }
        // PRG (post redirect get)
        return "redirect:/space/my_space";
    }

    @GetMapping("/my_space")
    public String mySpace(HttpSession session, Model model) {

        return "space/my_space";
    }
}
