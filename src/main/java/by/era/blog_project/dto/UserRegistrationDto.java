package by.era.blog_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto {

    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,30}$";
    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9_-]{3,30}$";

    @NotBlank(message = "Email cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Incorrect email")
    private String email;


    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = USERNAME_PATTERN, message = "Incorrect username")
    private String username;

    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Incorrect password")
    private String password;

    @NotBlank
    @Pattern(regexp = PASSWORD_PATTERN, message = "Incorrect password")
    private String confirmPassword;


}
