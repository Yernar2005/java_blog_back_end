package by.era.blog_project.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;



/**
 * DTO layer: ответ с access и refresh токенами.
 */

@Data
@AllArgsConstructor
public class JwtResponseDto {


    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn;
}
