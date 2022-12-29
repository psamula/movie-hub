package com.myrestfulprojects.moviehub.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
@Setter

public class AuthenticationRequest {
    private final String username;
    private final String password;
}
