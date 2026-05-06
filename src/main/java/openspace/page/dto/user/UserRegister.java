package openspace.page.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import openspace.page.domain.UserRole;

@Data
public class UserRegister {

    @NotEmpty
    private String name;

    @NotEmpty
    @Size(min = 3)
    private String password;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private UserRole role;
}
