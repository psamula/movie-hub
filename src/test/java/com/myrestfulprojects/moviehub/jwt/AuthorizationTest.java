package com.myrestfulprojects.moviehub.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.config.auth.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSuccessfullyAccessSecuredEndpoint_whenAuthorizedUser() throws Exception {
        //given
        String validLogin = "admin";
        String validPassword = "test";

        AuthenticationRequest validAuthRequest = new AuthenticationRequest();
        validAuthRequest.setUsername(validLogin);
        validAuthRequest.setPassword(validPassword);

        //when
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuthRequest)))
        //then
                .andExpect(status().isOk())
                .andReturn();
        String token = login.getResponse().getHeader("Authorization");
        mockMvc.perform(get("/some/umapped/secured/endpoint")
                        .header("Authorization", token)
                )
                .andExpect(status().is(404));
    }
    @Test
    void shouldThrow403ForbiddenException_whenUnauthorizedUser() throws Exception {
        //given
        String validLogin = "admin";
        String validPassword = "test";

        AuthenticationRequest validAuthRequest = new AuthenticationRequest();
        validAuthRequest.setUsername(validLogin);
        validAuthRequest.setPassword(validPassword);

        //when
        MvcResult login = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validAuthRequest)))
                .andExpect(status().isOk())
                .andReturn();
        //then

        mockMvc.perform(get("/some/umapped/secured/endpoint"))
                .andExpect(status().is(403));   // does not contain valid Authorization header
    }
}
