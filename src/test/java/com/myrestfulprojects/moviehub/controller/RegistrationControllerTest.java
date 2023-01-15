package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.config.registration.dto.RegistrationRequest;
import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void shouldSuccessfullyRegisterNewUser_whenUniqueUsername() throws Exception {
        //given
        String validUniqueLogin = userRepository.findAll().stream().findFirst().get().getUsername() + "uniqueuniqueuniqueunique";
        String validPassword = "test";

        RegistrationRequest validRegistrationRequest = new RegistrationRequest();
        validRegistrationRequest.setUsername(validUniqueLogin);
        validRegistrationRequest.setPassword(validPassword);

        //when
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegistrationRequest)))
        //then
                .andExpect(status().isOk());
        Optional<UserEntity> registeredUser = userRepository.findByUsername(validUniqueLogin);

        assertThat(registeredUser).isPresent();
    }
    @Test
    @Transactional
    public void shouldThrowUserAlreadyExistsException_whenExistingUsername() throws Exception {
        //given

        String existingUsername = userRepository.findAll().stream().findFirst().get().getUsername();
        String validPassword = "test";

        RegistrationRequest invalidRegistrationRequest = new RegistrationRequest();
        invalidRegistrationRequest.setUsername(existingUsername);
        invalidRegistrationRequest.setPassword(validPassword);

        //when
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRegistrationRequest)))
        //then
                .andExpect(status().isConflict());
    }
}
