package openspace.page.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Space {

    private Long id;
    private Long hostId;
    private String title;
    private String description;
    private String address;
    private int pricePerHour;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int isDeleted;

}
