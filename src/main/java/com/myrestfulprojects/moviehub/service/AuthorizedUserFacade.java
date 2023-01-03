package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.CustomUser;
import com.myrestfulprojects.moviehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

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
