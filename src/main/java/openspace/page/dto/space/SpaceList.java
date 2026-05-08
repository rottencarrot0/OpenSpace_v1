package openspace.page.dto.space;

import lombok.Data;

@Data
public class SpaceList {

    private Long id;
    private String title;
    private String address;
    private int pricePerHour;
    private int capacity;
    private String mainImageUrl;
}
