package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.config.auth.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldSuccessfullyLogin_whenValidCredentials() throws Exception {
        //given
        String validLogin = "admin";
        String validPassword = "test";

        AuthenticationRequest validAuthRequest = new AuthenticationRequest();
        validAuthRequest.setUsername(validLogin);
        validAuthRequest.setPassword(validPassword);

        //when
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuthRequest)))
        //then
                .andExpect(status().isOk());
    }
    @Test
    public void shouldThrowUnauthorized_whenInvalidCredentials() throws Exception {
        //given
        String invalidLogin = "invalidadmin";
        String invalidPassword = "invalidtest";

        AuthenticationRequest invalidAuthRequest = new AuthenticationRequest();
        invalidAuthRequest.setUsername(invalidLogin);
        invalidAuthRequest.setPassword(invalidPassword);

        //when
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAuthRequest)))
        //then
                .andExpect(status().isUnauthorized());
    }
}
