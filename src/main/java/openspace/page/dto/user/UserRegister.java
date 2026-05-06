package openspace.page.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import openspace.page.domain.UserRole;

@Data
public class UserRegister {

    @NotEmpty(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotEmpty
    @Size(min = 3, message = "3자 이상의 비밀번호를 입력해주세요")
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private UserRole role;
}
