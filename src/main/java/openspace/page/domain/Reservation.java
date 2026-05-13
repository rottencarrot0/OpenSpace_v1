package openspace.page.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reservation {

    private Long id;
    private Long spaceId;
    private Long guestId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int totalPrice;
    private ReservationStatus status;
    private String spaceName;
    private String guestName;
}
