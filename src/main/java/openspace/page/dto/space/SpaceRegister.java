package openspace.page.dto.space;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SpaceRegister {

    @NotEmpty(message = "제목은 필수 입력 항목입니다.")
    private String title;

    @NotEmpty(message = "설명은 필수 입력 항목입니다.")
    private String description;

    @NotEmpty(message = "주소는 필수 입력 항목입니다.")
    private String address;

    // 가격은 0 원 일 수도 있다..
    private String pricePerHour;

    @Min(value = 1, message = "수용 인원은 최소 1명 이상이어야 합니다.")
    @Max(value = 1000, message = "수용 인원은 최대 1000명까지 가능합니다.")
    private String capacity;
}
