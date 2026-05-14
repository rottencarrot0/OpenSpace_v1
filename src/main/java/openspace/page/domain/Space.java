package openspace.page.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
// id,
// host_id,
// title,
// description,
// address,
// price_per_hour,
// capacity,
// created_at,
// updated_at,
// is_deleted
@Data
public class Space {

    private Long id;
    private Long hostId;
    private String title;
    private String description;
    private String address;
    private int pricePerHour;
    private int capacity;
    private List<SpaceImage> images;
}
