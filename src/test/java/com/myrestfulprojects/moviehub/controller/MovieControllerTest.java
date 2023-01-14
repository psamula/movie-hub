package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.model.movies.MovieFull;
import com.myrestfulprojects.moviehub.model.movies.MovieShort;
import com.myrestfulprojects.moviehub.model.rating.dto.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.repository.MovieRatingRepository;
import com.myrestfulprojects.moviehub.repository.MovieRepository;
import com.myrestfulprojects.moviehub.repository.UserRepository;
import com.myrestfulprojects.moviehub.utils.ImdbApiTestUtils;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDtoWrapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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
    private ImdbApiTestUtils imdbApiTestUtils;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieRatingRepository movieRatingRepository;
    @Autowired
    private UserRepository userRepository;
    private final String TESTER_USERNAME = "mymockinguser";
    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldGetUserRatedMovies() throws Exception {
        //given
        UserEntity mockingUser = new UserEntity();
        mockingUser.setUsername(TESTER_USERNAME);
        mockingUser.setPassword("testpassword");
        userRepository.save(mockingUser);


        MovieEntity ratedMovie = imdbApiTestUtils.generateTestMovieEntity();
        movieRepository.save(ratedMovie);

        Rating testyRating = Rating.TWO;
        MovieRatingEntity testyMovieRating = new MovieRatingEntity();
        testyMovieRating.setMovie(ratedMovie);
        testyMovieRating.setUser(mockingUser);
        testyMovieRating.setRating(testyRating.getValue());
        movieRatingRepository.save(testyMovieRating);

        MovieWithRatingDTO expectedMovieWithRatingDto = new MovieWithRatingDTO(ratedMovie, testyRating.getValue());

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
    void shouldSuccesfullyRateAMovie() throws Exception {
        //given
        UserEntity mockingUser = new UserEntity();
        mockingUser.setUsername(TESTER_USERNAME);
        mockingUser.setPassword("testpassword");
        userRepository.save(mockingUser);

        Rating myRating = Rating.THREE;

        var ratedMovie = imdbApiTestUtils.generateTestMovieEntity();
        movieRepository.save(ratedMovie);

        //when
        mockMvc.perform(post("/movies/" + ratedMovie.getImdbId() + "/rate")
                        .param("rating", myRating.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        var resultMovieRatingRecordOpt = movieRatingRepository.findByUserAndMovie(mockingUser, ratedMovie);

        assertThat(resultMovieRatingRecordOpt).isPresent();
        assertThat(resultMovieRatingRecordOpt.get().getMovie()).usingRecursiveComparison().isEqualTo(ratedMovie);
        assertThat(resultMovieRatingRecordOpt.get().getRating()).usingRecursiveComparison().isEqualTo(myRating.getValue());
        assertThat(resultMovieRatingRecordOpt.get().getUser()).usingRecursiveComparison().isEqualTo(mockingUser);

    }

}