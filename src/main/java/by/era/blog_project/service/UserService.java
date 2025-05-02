package by.era.blog_project.service;

import by.era.blog_project.dto.UserChangePassword;
import by.era.blog_project.dto.UserDto;
import by.era.blog_project.dto.UserRegistrationDto;
import by.era.blog_project.dto.UserUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    /**
     * Регистрирует нового пользователя,
     * шифрует пароль, присваивает роль ROLE_USER.
     */
    UserDto saveUser(UserRegistrationDto dto);

    /**
     * Проверяет, существует ли email в БД.
     */
    boolean emailExists(String email);

    /**
     * Проверяет, существует ли username в БД.
     */
    boolean usernameExists(String username);

    /**
     * Возвращает DTO пользователя по email или бросает исключение.
     */
    UserDto getByEmail(String email);

    /**
     * Возвращает DTO пользователя по username или бросает исключение
     */
    UserDto getByUsername(String username);

    /**
     * Возвращает всех пользователей постранично.
     */
    Page<UserDto> findAll(Pageable pageable);

}
