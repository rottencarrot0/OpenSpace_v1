package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @GetMapping("/new/{id}")
    public String createReservation(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("id", id);
        return "ok";
    }
}
