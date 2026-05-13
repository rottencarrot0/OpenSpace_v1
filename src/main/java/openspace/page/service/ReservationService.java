package openspace.page.service;

import openspace.page.domain.Reservation;
import openspace.page.domain.ReservationStatus;
import openspace.page.domain.Space;
import openspace.page.dto.reservation.ReservationList;
import openspace.page.dto.reservation.ReservationRegister;
import openspace.page.exception.BusinessException;
import openspace.page.exception.ResourceNotFoundException;
import openspace.page.mapper.ReservationMapper;
import openspace.page.mapper.SpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    ReservationMapper reservationMapper;

    @Autowired
    SpaceMapper spaceMapper;


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

    public List<ReservationList> getReservationListByGuestId(Long guestId) {
        // 예약 리스트 객체로
        return reservationMapper.findReservationListByGuestId(guestId).stream().map(this::toReservationList).toList();
    }

    public ReservationList toReservationList(Reservation reservation) {
        ReservationList list = new ReservationList();
        list.setId(reservation.getId());
        list.setSpaceId(reservation.getSpaceId());
        list.setSpaceName(reservation.getSpaceName());
        list.setStartDate(reservation.getStartDate());
        list.setEndDate(reservation.getEndDate());
        list.setTotalPrice(reservation.getTotalPrice());
        list.setStatus(reservation.getStatus());
        list.setGuestName(reservation.getGuestName());
        return list;
    }

    @Transactional
    public void cancelReservation(Long id, Long guestId) {
        // 예약 여부 확인
        Reservation reservation = reservationMapper.findReservationById(id);
        if(reservation == null) {
            throw new ResourceNotFoundException("예약을 찾을 수 없습니다.");
        }

        // 본인 예약인지 확인
        if(!reservation.getGuestId().equals(guestId)) {
            throw new BusinessException("예약 취소 권한이 없습니다.");
        }

        // 예약이 Pending 상태인지 확인
        if(reservation.getStatus() != ReservationStatus.PENDING) {
            throw new BusinessException("대기 중인 예약만 취소할 수 있습니다.");
        }

        reservationMapper.updateReservationStatus(id, ReservationStatus.CANCELED);
    }

    public List<ReservationList> getReservationListByHostId(Long id) {
        List<Reservation> reservation = reservationMapper.findReservationByHostId(id);
        return reservation.stream().map(this::toReservationList).toList();
    }

    @Transactional
    public void approveReservation(Long id, Long hostId) {
        // 예약 여부 확인
        Reservation reservation = reservationMapper.findReservationById(id);
        if(reservation == null) {
            throw new ResourceNotFoundException("예약을 찾을 수 없습니다.");
        }

        // 예약 공간에 대한 정보 먼저 찾고
        // 공간의 주인인지 확인한다.
        Space space = spaceMapper.findSpaceById(reservation.getSpaceId());
        if(!space.getHostId().equals(hostId)) {
            throw new BusinessException("승인 권한이 없습니다.");
        }

        // 예약이 Pending 상태인지 확인
        if(reservation.getStatus() != ReservationStatus.PENDING) {
            throw new BusinessException("대기 중인 예약만 승인할 수 있습니다.");
        }

        reservationMapper.updateReservationStatus(id, ReservationStatus.APPROVED);
    }

    public void rejectReservation(Long id, Long hostId) {
        // 예약 여부 확인
        Reservation reservation = reservationMapper.findReservationById(id);
        if(reservation == null) {
            throw new ResourceNotFoundException("예약을 찾을 수 없습니다.");
        }

        // 예약 공간에 대한 정보 먼저 찾고
        // 공간의 주인인지 확인한다.
        Space space = spaceMapper.findSpaceById(reservation.getSpaceId());
        if(!space.getHostId().equals(hostId)) {
            throw new BusinessException("거절 권한이 없습니다.");
        }

        // 예약이 Pending 상태인지 확인
        if(reservation.getStatus() != ReservationStatus.PENDING) {
            throw new BusinessException("대기 중인 예약만 거절할 수 있습니다.");
        }

        reservationMapper.updateReservationStatus(id, ReservationStatus.REJECTED);
    }
}
