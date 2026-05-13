package openspace.page.dto.reservation;

import lombok.Data;
import openspace.page.domain.ReservationStatus;

import java.time.LocalDateTime;

@Data
public class ReservationList {

    private Long id;
    private Long spaceId;
    private String spaceName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int totalPrice;
    private ReservationStatus status;
    private String guestName;

}
