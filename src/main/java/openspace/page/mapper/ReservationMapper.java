package openspace.page.mapper;

import openspace.page.domain.Reservation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper {


    void insertReservation(Reservation reservation);

}
