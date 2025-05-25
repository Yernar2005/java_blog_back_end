package by.era.blog_project.service.impl;


import by.era.blog_project.domain.RefreshToken;
import by.era.blog_project.domain.User;
import by.era.blog_project.dto.request.LoginRequestDto;
import by.era.blog_project.dto.request.RefreshTokenRequestDto;
import by.era.blog_project.dto.response.JwtResponseDto;
import by.era.blog_project.repository.RefreshTokenRepository;
import by.era.blog_project.repository.UserRepository;
import by.era.blog_project.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import by.era.blog_project.service.AuthService;

import java.time.Instant;
import java.util.UUID;


/**
 * ServiceImpl layer: реализует login() и refreshToken(), взаимодействуя с JwtTokenProvider.
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    public JwtResponseDto login(LoginRequestDto dto) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        String access = tokenProvider.generateAccessToken(auth);
        String refresh = tokenProvider.generateRefreshToken(auth);


//        Setting for BaseData
        String saveToBd = tokenProvider.extractTokenId(refresh);
        String username = auth.getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(tokenProvider.getRefreshExpirationInDays() * 24 * 60 * 60);

        RefreshToken rt = RefreshToken.builder()
                .id(UUID.fromString(saveToBd))
                .user(user)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .revoked(false)
                .build();
        refreshTokenRepository.save(rt);

        long expiresIn = tokenProvider.getAccessExpirationInMinutes() * 60;
        return new JwtResponseDto(access, refresh, "Bearer", expiresIn);
    }



    @Override
    public JwtResponseDto refreshToken(RefreshTokenRequestDto dto) {
        String refreshToken = dto.getRefreshToken();
        if (!tokenProvider.validateRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        UUID saveToBD = UUID.fromString(tokenProvider.extractTokenId(refreshToken));
        String userEmail = tokenProvider.extractUsername(refreshToken);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

//        Проверяем наличие и статуса токена в БД
        RefreshToken stored = refreshTokenRepository.findByIdAndUser_Id(saveToBD, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not recognized"));


        if(stored.isRevoked() || stored.getExpiresAt().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Refresh token expired or revoked");
        }


        stored.setRevoked(true);
        refreshTokenRepository.save(stored);



        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetailsService.loadUserByUsername(userEmail),
                null,
                userDetailsService.loadUserByUsername(userEmail).getAuthorities()
        );


        String newAccessToken = tokenProvider.generateAccessToken(auth);
        String newRefreshToken = tokenProvider.generateRefreshToken(auth);

        String  newSaveToDB = tokenProvider.extractTokenId(newRefreshToken);
        Instant now = Instant.now();
        Instant newExpiresAt = now.plusSeconds(tokenProvider.getRefreshExpirationInDays() * 24 * 60 * 60);

        RefreshToken rt = RefreshToken.builder()
                .id(UUID.fromString(newSaveToDB))
                .user(user)
                .issuedAt(now)
                .expiresAt(newExpiresAt)
                .revoked(false)
                .build();

        refreshTokenRepository.save(rt);
        long expiresIn = tokenProvider.getAccessExpirationInMinutes() * 60;
        return new JwtResponseDto(newAccessToken, newRefreshToken, "Bearer", expiresIn);

    }

}
