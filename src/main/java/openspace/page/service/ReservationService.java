package openspace.page.service;

import openspace.page.domain.Reservation;
import openspace.page.domain.ReservationStatus;
import openspace.page.dto.reservation.ReservationRegister;
import openspace.page.exception.BusinessException;
import openspace.page.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ReservationService {

    @Autowired
    ReservationMapper reservationMapper;


    public void createReservation(ReservationRegister register, Long guestId, int pricePerHour) {
        // post 이기 때문에 한번더 확인한다.
        if(!register.getEndDate().isAfter(register.getStartDate())) {
            throw new BusinessException("종료 시간은 시작 시간 이후여야 합니다.");
        }

        long hours = Duration.between(register.getStartDate(), register.getEndDate()).toHours();
        if(hours <= 0) {
            throw new BusinessException("예약 시간은 최소 1시간 이상이어야 합니다.");
        }

        int totalPrice = (int)(hours * pricePerHour);

        Reservation reservation = new Reservation();
        reservation.setSpaceId(register.getSpaceId());
        reservation.setGuestId(guestId);
        reservation.setStartDate(register.getStartDate());
        reservation.setEndDate(register.getEndDate());
        reservation.setTotalPrice(totalPrice);
        reservation.setStatus(ReservationStatus.PENDING);

        reservationMapper.insertReservation(reservation);

    }

}
