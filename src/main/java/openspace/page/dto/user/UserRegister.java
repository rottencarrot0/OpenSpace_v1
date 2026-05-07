package openspace.page.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import openspace.page.domain.UserRole;

@Data
public class UserRegister {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank
    @Size(min = 3, message = "3자 이상의 비밀번호를 입력해주세요")
    private String password;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private UserRole role;
}
