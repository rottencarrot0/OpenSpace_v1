package openspace.page.dto.reservation;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ReservationRegister {

    private Long spaceId;

    @NotNull(message = "시작 일시는 필수 입력 항목입니다.")
    @Future(message = "시작 일시는 현재 시간 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;

    @NotNull(message = "종료 일시는 필수 입력 항목입니다.")
    @Future(message = "종료 일시는 현재 시간 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
}
