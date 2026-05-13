package openspace.page.mapper;

import openspace.page.domain.Reservation;
import openspace.page.domain.ReservationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReservationMapper {

    void insertReservation(Reservation reservation);
    List<Reservation> findReservationListByGuestId(Long guestId);

    Reservation findReservationById(Long id);

    void updateReservationStatus(@Param("id") Long id, @Param("status") ReservationStatus reservationStatus);
}
