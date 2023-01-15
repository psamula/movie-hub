package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.model.movies.MovieFull;
import com.myrestfulprojects.moviehub.model.movies.MovieShort;
import com.myrestfulprojects.moviehub.utils.TestDataProvider;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private TestDataProvider testDataProvider;
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
        List<String> expectedTrendingMoviesTitles = testDataProvider.getTrendyMovies().stream()
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
        assertThat(returnedMoviesTitles).isEqualTo(expectedTrendingMoviesTitles);
    }
}
