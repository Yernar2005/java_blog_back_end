package by.era.blog_project.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


/**
 * DTO layer: payload запроса для обновления access-токена.
 */
@Data
public class RefreshTokenRequestDto {
    @NotBlank
    private String refreshToken;
}
