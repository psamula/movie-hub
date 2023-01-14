package com.myrestfulprojects.moviehub.config.registration;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.config.registration.dto.RegistrationRequest;
import com.myrestfulprojects.moviehub.config.registration.dto.RegistrationResponse;
import com.myrestfulprojects.moviehub.exceptions.UserAlreadyExistsException;
import com.myrestfulprojects.moviehub.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegistrationFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrationResponse registerNewUser(final RegistrationRequest registrationRequest) {

        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new UserAlreadyExistsException(String.format("User of username %s already exists",
                    registrationRequest.getUsername()));
        }
        UserEntity user = new UserEntity();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        UserEntity savedUser = userRepository.save(user);

        return RegistrationResponse.ofUserEntity(savedUser);

    }

}
