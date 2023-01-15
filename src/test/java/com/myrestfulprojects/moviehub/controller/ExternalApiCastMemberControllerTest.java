package com.myrestfulprojects.moviehub.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.model.rating.castmembers.CastMemberFull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExternalApiCastMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @Transactional
    @WithMockUser
    void shouldGetApiCastMember() throws Exception {
        //given
        CastMemberFull expectedCastMember = CastMemberFull.builder()
                .id("nm0000204")
                .name("Natalie Portman")
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/imdb-api/cast-members/" + expectedCastMember.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        CastMemberFull returnedCastMember = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CastMemberFull.class);

        assertThat(returnedCastMember).isNotNull();
        assertThat(returnedCastMember.getId()).isEqualTo(expectedCastMember.getId());
        assertThat(returnedCastMember.getName()).isEqualTo(expectedCastMember.getName());

    }

}