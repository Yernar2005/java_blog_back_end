package by.era.blog_project.service;


import by.era.blog_project.dto.request.LoginRequestDto;
import by.era.blog_project.dto.request.RefreshTokenRequestDto;
import by.era.blog_project.dto.response.JwtResponseDto;
/**
 * Service layer: бизнес-логика аутентификации и обновления токенов.
 */

public interface AuthService {

    JwtResponseDto login(LoginRequestDto loginRequest);
    JwtResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);

}
