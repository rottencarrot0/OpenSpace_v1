package openspace.page.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLogin {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;
}
