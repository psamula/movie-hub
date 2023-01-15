package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.exceptions.DuplicateEntityException;
import com.myrestfulprojects.moviehub.exceptions.ObjectWithImdbIdNotFound;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceTest {
    @Autowired
    private MovieService movieService;
    @Autowired
    private TestDataProvider testDataProvider;

    @WithMockUser(username = "mymockinguser")
    @Transactional
    @Test
    void shouldThrowDuplicateEntityException_whenInsertingDuplicateMovieRating() throws Exception {
        //given
        UserEntity testUser = testDataProvider.getAndSaveTestUserEntityOfUsername("mymockinguser");
        MovieEntity movieEntity = testDataProvider.getAndSaveTestMovieEntity();
        Rating testRating = Rating.ONE;

        //when
        movieService.rateMovie(movieEntity.getImdbId(), testRating);

        //then
        assertThrows(DuplicateEntityException.class,
                () -> movieService.rateMovie(movieEntity.getImdbId(), testRating));
    }

    @WithMockUser(username = "mymockinguser")
    @Transactional
    @Test
    void shouldThrowObjectNotFound_whenClientInsertsNonExistingId() throws Exception {
        //given
        UserEntity testUser = testDataProvider.getAndSaveTestUserEntityOfUsername("mymockinguser");
        String movieId = "definitely_non_existing";

        Rating testRating = Rating.ONE;
        //when
        //then
        assertThrows(ObjectWithImdbIdNotFound.class,
                () -> movieService.rateMovie(movieId, testRating));
    }
}
