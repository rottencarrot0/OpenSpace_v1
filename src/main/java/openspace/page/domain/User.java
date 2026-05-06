package openspace.page.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String password;
    private String name;
    private String email;
    private UserRole role;
    private LocalDateTime createdAt;
    private int isDeleted;
}
