package openspace.page.domain;

import lombok.Data;

import java.time.LocalDateTime;
// id,
// space_id,
// image_url,
// is_main,
// created_at,
// updated_at,
// is_deleted
@Data
public class SpaceImage {

    private Long id;
    private Long spaceId;
    private String imageUrl;
    private int isMain;
}
