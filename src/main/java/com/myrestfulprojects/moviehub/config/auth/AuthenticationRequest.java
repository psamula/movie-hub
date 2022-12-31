package com.myrestfulprojects.moviehub.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
public class AuthenticationRequest {
    private String username;
    private String password;
}
