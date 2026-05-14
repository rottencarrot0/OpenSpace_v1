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
import org.springframework.web.bind.annotation.RequestParam;

import static openspace.page.domain.SessionConst.LOGIN_USER;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    // 로그인 폼
    @GetMapping("/login")
    public String login(@RequestParam(name = "redirectUrl", required = false) String redirectURL, Model model) {
        model.addAttribute("redirectUrl", redirectURL);
        return "user/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@Valid UserLogin user, BindingResult bindingResult,
                        @RequestParam(name = "redirectUrl", defaultValue = "/") String redirectURL,
                        HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "user/login";
        }
        log.info("UserLogin = {}", user);
        User loginUser = userService.login(user);
        if(loginUser == null) {
            model.addAttribute("errorMessage", "이메일 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }

        session.setAttribute(LOGIN_USER, loginUser);

        return "redirect:" + redirectURL;
    }

    // 회워 가입 폼
    @GetMapping("/register")
    public String registerForm() {
        return "user/register";
    }

    // 회원 가입
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

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute(LOGIN_USER);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }
}
