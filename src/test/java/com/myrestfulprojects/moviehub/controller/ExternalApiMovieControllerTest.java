package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.model.movies.MovieFull;
import com.myrestfulprojects.moviehub.model.movies.MovieShort;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDtoWrapper;
import lombok.RequiredArgsConstructor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser

public class ExternalApiMovieControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @Transactional
    void shouldGetApiMovie() throws Exception {
        //given
        MovieFull expectedMovie = MovieFull.builder()
                .imdbId("tt1630029")
                .title("Avatar: The Way of Water")
                .build();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/imdb-api/movies/" + expectedMovie.getImdbId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        MovieFull returnedMovie = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieFull.class);

        assertThat(returnedMovie).isNotNull();
        assertThat(returnedMovie.getImdbId()).isEqualTo(expectedMovie.getImdbId());
        assertThat(returnedMovie.getTitle()).isEqualTo(expectedMovie.getTitle());

    }
    @Test
    @Transactional
    void shouldRGetApiTrendingMovies() throws Exception {
        //given
        RestTemplate restTemplate = new RestTemplate();
        var shortDtoWrapper = restTemplate.getForObject(
                "https://imdb-api.com/en/API/MostPopularMovies/k_zbbq3lxc", MovieShortDtoWrapper.class);
        var expectedMoviesTitles = shortDtoWrapper.getItems().stream()
                .limit(10)
                .map(MovieShortDto::getTitle)
                .collect(Collectors.toList());

        //when
        MvcResult mvcResult = mockMvc.perform(get("/imdb-api/movies/trending"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        List<MovieShort> returnedTrendyMovies = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>() {});
        var returnedMoviesTitles = returnedTrendyMovies.stream()
                .limit(10)
                .map(MovieShort::getTitle)
                .collect(Collectors.toList());

        assertThat(returnedTrendyMovies).isNotNull();
        assertThat(returnedTrendyMovies).isNotEmpty();
        assertThat(returnedMoviesTitles).isEqualTo(expectedMoviesTitles);

    }

}
