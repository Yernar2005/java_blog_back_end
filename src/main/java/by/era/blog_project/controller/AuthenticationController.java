package by.era.blog_project.controller;


import by.era.blog_project.dto.UserDto;
import by.era.blog_project.dto.UserRegistrationDto;
import by.era.blog_project.dto.request.LoginRequestDto;
import by.era.blog_project.dto.request.RefreshTokenRequestDto;
import by.era.blog_project.dto.response.JwtResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import by.era.blog_project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import by.era.blog_project.service.RegistrationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;
    private final RegistrationService registrationService;  // регистрация новых пользователей

    /**
     * Регистрация нового пользователя
     */
    @PostMapping("/registration")
    public ResponseEntity<UserDto> register(
            @Valid @RequestBody UserRegistrationDto dto) {
        UserDto created = registrationService.register(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /**
     * Аутентификация: генерация access + refresh токенов
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto) {
        JwtResponseDto tokens = authService.login(dto);
        return ResponseEntity.ok(tokens);
    }

    /**
     * Обновление access-токена по refresh-токену
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refresh(
            @Valid @RequestBody RefreshTokenRequestDto dto) {
        JwtResponseDto tokens = authService.refreshToken(dto);
        return ResponseEntity.ok(tokens);
    }

}

