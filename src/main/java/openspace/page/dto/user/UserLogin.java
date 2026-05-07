package openspace.page.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLogin {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
