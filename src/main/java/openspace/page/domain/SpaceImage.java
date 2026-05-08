package openspace.page.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpaceImage {

    private Long id;
    private Long spaceId;
    private String imageUrl;
    private int isMain;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private int isDeleted;
}
