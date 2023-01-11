package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.UUID;
@RequiredArgsConstructor
public class AuthorizedUserFacade {
    private final UserRepository userRepository;
    public UUID getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String)authentication.getPrincipal();
        var userEntity = userRepository.findByUsername(username).orElseThrow(IllegalStateException::new);
        return userEntity.getId();

    }
}
