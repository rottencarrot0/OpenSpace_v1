package openspace.page.dto.space;

import lombok.Data;
import openspace.page.domain.SpaceImage;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpaceDetail {

    private Long id;
    private Long hostId;
    private String title;
    private String description;
    private String address;
    private int pricePerHour;
    private int capacity;
    private LocalDateTime createdAt;
    private List<SpaceImage> images;

}
