package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.SessionConst;
import openspace.page.domain.User;
import openspace.page.dto.reservation.ReservationRegister;
import openspace.page.dto.space.SpaceDetail;
import openspace.page.exception.BusinessException;
import openspace.page.service.ReservationService;
import openspace.page.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/new/{id}")
    public String newReservation(@PathVariable Long id, Model model, HttpSession session) {
        if(null == session.getAttribute(SessionConst.LOGIN_USER)) {
            return "redirect:/user/login";
        }
        SpaceDetail space = spaceService.getSpaceDetail(id);
        model.addAttribute("space", space);
        return "reservation/form";
    }

    @PostMapping
    public String newReservation(@Valid ReservationRegister register, BindingResult bindingResult, Model model, HttpSession session) {
        if(bindingResult.hasErrors()) {
            SpaceDetail spaceDetail = spaceService.getSpaceDetail(register.getSpaceId());
            model.addAttribute("space", spaceDetail);
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            model.addAttribute("errorMessage", defaultMessage);
            return "reservation/form";
        }

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        SpaceDetail spaceDetail = spaceService.getSpaceDetail(register.getSpaceId());

        try {
            reservationService.createReservation(register, loginUser.getId(), spaceDetail.getPricePerHour());
            return "redirect:/reservation/my";
        } catch(BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("space", spaceDetail);
            return "reservation/form";
        }
    }

    @GetMapping("/my")
    public String my(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        return "reservation/my-reservation";
    }

}
