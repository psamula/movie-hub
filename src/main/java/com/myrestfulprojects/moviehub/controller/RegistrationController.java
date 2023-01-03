package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.config.registration.RegistrationFacade;
import com.myrestfulprojects.moviehub.config.registration.dto.RegistrationRequest;
import com.myrestfulprojects.moviehub.config.registration.dto.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RequiredArgsConstructor
@RestController
public class RegistrationController {
    private final RegistrationFacade registrationFacade;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser (
            @RequestBody @Valid final RegistrationRequest registrationRequest) {

        RegistrationResponse response = registrationFacade.registerNewUser(registrationRequest);
        return ResponseEntity.ok(response);
    }
}
