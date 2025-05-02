package by.era.blog_project.service;


import by.era.blog_project.dto.UserDto;
import by.era.blog_project.dto.UserRegistrationDto;



/**
 * Service layer: отвечает за регистрацию новых пользователей.
 */

public interface RegistrationService {
    UserDto register(UserRegistrationDto dto);
}