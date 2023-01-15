package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.UUID;
@RequiredArgsConstructor
public class AuthorizedUserFacade {
    private final UserRepository userRepository;
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        }
        return username;
    }
    public UUID getCurrentUserId() {
        var authenticatedUserName = getCurrentUsername();
        var userEntity = userRepository.findByUsername(authenticatedUserName).orElseThrow(IllegalStateException::new);
        return userEntity.getId();
    }
}
