package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.model.rating.dto.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.repository.MovieRatingRepository;
import com.myrestfulprojects.moviehub.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired
    private TestDataProvider testDataProvider;
    private final String TESTER_USERNAME = "mymockinguser";
    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldGetUserRatedMovies() throws Exception {
        //given
        var testUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        var movieEntity = testDataProvider.getAndSaveTestMovieEntity();
        var rating = Rating.ONE;

        MovieWithRatingDTO expectedMovieWithRatingDto = testDataProvider.getAndSaveTestMovieWithRatingDTO(movieEntity, testUser, rating);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/movies/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List<MovieWithRatingDTO> ratedMovies = Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(), MovieWithRatingDTO[].class));


        assertThat(ratedMovies).isNotNull();
        assertThat(ratedMovies).isNotEmpty();
        assertThat(ratedMovies.get(0)).usingRecursiveComparison().isEqualTo(expectedMovieWithRatingDto);
    }
    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldSuccessfullyRateAMovie() throws Exception {
        //given
        var testUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        var expectedMovie = testDataProvider.getAndSaveTestMovieEntity();
        var expectedRating = Rating.ONE;

        //when
        mockMvc.perform(post("/movies/" + expectedMovie.getImdbId() + "/rate")
                        .param("rating", expectedRating.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        var resultMovieRatingRecordOpt = movieRatingRepository.findByUserAndMovie(testUser, expectedMovie);

        assertThat(resultMovieRatingRecordOpt).isPresent();
        assertThat(resultMovieRatingRecordOpt.get().getMovie()).usingRecursiveComparison().isEqualTo(expectedMovie);
        assertThat(resultMovieRatingRecordOpt.get().getRating()).usingRecursiveComparison().isEqualTo(expectedRating.getValue());
        assertThat(resultMovieRatingRecordOpt.get().getUser()).usingRecursiveComparison().isEqualTo(testUser);

    }

}