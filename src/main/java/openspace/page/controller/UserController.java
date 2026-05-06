package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.SessionConst;
import openspace.page.domain.User;
import openspace.page.dto.user.UserLogin;
import openspace.page.dto.user.UserRegister;
import openspace.page.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static openspace.page.domain.SessionConst.LOGIN_USER;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@Valid final UserLogin user, final BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            log.info("UserLogin = {}", user);
        }
        log.info("UserLogin = {}", user);
        User loginUser = userService.login(user);
        session.setAttribute(LOGIN_USER, loginUser);

        return "redirect:/";
    }



    @GetMapping("/register")
    public String registerForm() {
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegister register, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "user/register";
        }

        log.info("register = {}", register);
        userService.register(register);
        return "redirect:/user/login";

    }
}
