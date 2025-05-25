package by.era.blog_project.security;


import by.era.blog_project.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Берём роль из домена и конвертируем в GrantedAuthority
        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();  // Хеш пароля из БД
    }

    @Override
    public String getUsername() {
        return user.getEmail();  // Логин пользователя
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Нет логики истечения аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Нет блокировок
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Пароль не истекает
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(user.getIsActive());  // Активность из поля isActive
    }
}
