package by.era.blog_project.service.impl;


import by.era.blog_project.dto.UserDto;
import by.era.blog_project.dto.UserRegistrationDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import by.era.blog_project.service.RegistrationService;
import by.era.blog_project.service.UserService;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {


    private final UserService userService;

    @Override
    @Transactional
    public UserDto register(UserRegistrationDto dto) {
        return userService.saveUser(dto);
    }
}
