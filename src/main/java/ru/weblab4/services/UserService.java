package ru.weblab4.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.weblab4.domain.User;
import ru.weblab4.dto.UserRegDto;
import ru.weblab4.exceptions.LoginAlreadyExistsException;
import ru.weblab4.reposotories.UserRepository;
import ru.weblab4.security.JwtAuthentication;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public UUID registration(UserRegDto userRegDto) {
        if (userRepository.existsByLogin(userRegDto.getLogin())) {
            throw new LoginAlreadyExistsException(userRegDto.getLogin());
        }

        String password = passwordEncoder.encode(userRegDto.getPassword());
        userRegDto.setPassword(password);
        User user = userRepository.save(modelMapper.map(userRegDto, User.class));
        return user.getId();
    }

    public User getCurrentUser(){
        JwtAuthentication contextHolder = authService.getAuthInfo();
        return Optional.ofNullable(userRepository.findUserByLogin(contextHolder.getLogin()))
                .orElseThrow(() -> new EntityNotFoundException("User doesn't exist!"));
    }

}
