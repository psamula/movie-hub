package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.model.movies.MovieFull;
import com.myrestfulprojects.moviehub.model.movies.MovieShort;
import com.myrestfulprojects.moviehub.model.rating.castmembers.CastMemberFull;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDtoWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExternalApiCastMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @Transactional
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