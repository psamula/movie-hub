package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;


import java.util.UUID;
@RequiredArgsConstructor
public class AuthorizedUserFacade {
    private final UserRepository userRepository;
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        var userEntity = userRepository.findByUsername(user.getUsername()).orElseThrow(IllegalStateException::new);
        return userEntity.getId();

    }
}
