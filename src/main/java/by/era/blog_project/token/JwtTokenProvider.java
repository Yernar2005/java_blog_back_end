package by.era.blog_project.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Getter
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.expiration}")
    private Long accessExpirationInMinutes;

    @Value("${jwt.refresh.expiration}")
    private Long refreshExpirationInDays;

    private Key signingKey;


    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes();
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Authentication auth) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpirationInMinutes * 60);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles", user.getAuthorities())
                .claim("tokenType", "access")
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .id(UUID.randomUUID().toString())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public String generateRefreshToken(Authentication auth) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(refreshExpirationInDays * 24 * 3600);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim("tokenType", "refresh")
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .id(UUID.randomUUID().toString())
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractTokenId(String token) {
        return extractAllClaims(token).getId();
    }

    public boolean validateAccessToken(String token) {
        try {
            Claims c = extractAllClaims(token);
            return "access".equals(c.get("tokenType")) && !c.getExpiration().before(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims c = extractAllClaims(token);
            return "refresh".equals(c.get("tokenType")) && !c.getExpiration().before(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
