package by.era.blog_project.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;



/**
 * DTO layer: payload запроса для входа (login).
 */

@Data
public class LoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
