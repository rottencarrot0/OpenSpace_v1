package openspace.page.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.SessionConst;
import openspace.page.domain.User;
import openspace.page.dto.CommonResponse;
import openspace.page.dto.reservation.ReservationList;
import openspace.page.dto.reservation.ReservationRegister;
import openspace.page.dto.space.SpaceDetail;
import openspace.page.exception.BusinessException;
import openspace.page.exception.ResourceNotFoundException;
import openspace.page.service.ReservationService;
import openspace.page.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // 서버에서 처리할 예외
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
        // 서버에서 처리할 예외
        SpaceDetail spaceDetail = spaceService.getSpaceDetail(register.getSpaceId());

        try {
            reservationService.createReservation(register, loginUser.getId(), spaceDetail.getPricePerHour());
            return "redirect:/reservation/my";
        } catch(BusinessException  e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("space", spaceDetail);
            return "reservation/form";
        }
    }

    @GetMapping("/my")
    public String my(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null) {
            return "redirect:/user/login?redirectUrl=/reservation/my";
        }

        List<ReservationList> reservations = reservationService.getReservationListByGuestId(loginUser.getId());
        model.addAttribute("reservations", reservations);

        return "reservation/my-reservation";
    }

    @ResponseBody
    @PostMapping("/{id}/cancel")
    public ResponseEntity<CommonResponse> cancelReservation(@PathVariable Long id, HttpSession session) {
        User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null) {
            return ResponseEntity.status(400).body(CommonResponse.error("로그인이 필요합니다."));
        }
        try {
            reservationService.cancelReservation(id, loginUser.getId());
            return ResponseEntity.ok(CommonResponse.success(null));
        } catch (BusinessException | ResourceNotFoundException e) {
           return ResponseEntity.status(400).body(CommonResponse.error(e.getMessage()));
        }
    }

    // 호스트의 예약 목록이다
    @GetMapping("/manage")
    public String manageReservation(Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null) {
            return "redirect:/user/login?redirectUrl=/reservation/manage";
        }
        List<ReservationList> reservationList = reservationService.getReservationListByHostId(loginUser.getId());
        model.addAttribute("reservations", reservationList);
        return "reservation/manage";
    }

    @ResponseBody
    @PostMapping("/approve/{id}")
    public ResponseEntity<CommonResponse> approveReservation(@PathVariable Long id, HttpSession session) {
        User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null) {
            return ResponseEntity.status(400).body(CommonResponse.error("로그인이 필요합니다."));
        }
        try {
            reservationService.approveReservation(id, loginUser.getId());
            return ResponseEntity.ok(CommonResponse.success(null));
        } catch (BusinessException | ResourceNotFoundException e) {
            return ResponseEntity.status(400).body(CommonResponse.error(e.getMessage()));
        }
    }

    @ResponseBody
    @PostMapping("/reject/{id}")
    public ResponseEntity<CommonResponse> rejectReservation(@PathVariable Long id, HttpSession session) {
        User loginUser = (User)session.getAttribute(SessionConst.LOGIN_USER);
        if(loginUser == null) {
            return ResponseEntity.status(400).body(CommonResponse.error("로그인이 필요합니다."));
        }
        try {
            reservationService.rejectReservation(id, loginUser.getId());
            return ResponseEntity.ok(CommonResponse.success(null));
        } catch (BusinessException | ResourceNotFoundException e) {
            return ResponseEntity.status(400).body(CommonResponse.error(e.getMessage()));
        }
    }
}
