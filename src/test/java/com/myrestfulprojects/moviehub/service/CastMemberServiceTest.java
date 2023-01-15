package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.exceptions.DuplicateEntityException;
import com.myrestfulprojects.moviehub.exceptions.InvalidInputException;
import com.myrestfulprojects.moviehub.exceptions.ObjectWithImdbIdNotFound;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.utils.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class CastMemberServiceTest {
    @Autowired
    private CastMemberService castMemberService;
    @Autowired
    private TestDataProvider testDataProvider;

    @WithMockUser(username = "mymockinguser")
    @Transactional
    @Test
    void shouldThrowDuplicateEntityException_whenInsertingDuplicateMovieRoleRating() throws Exception {
        //given
        UserEntity testUser = testDataProvider.getAndSaveTestUserEntityOfUsername("mymockinguser");
        MovieEntity movieEntity = testDataProvider.getAndSaveTestMovieEntity();

        MovieRoleEntity movieRoleEntity = testDataProvider.getAndSaveTestMovieCharacter();
        Rating testRating = Rating.ONE;
        movieEntity.setActorList(Collections.singletonList(movieRoleEntity));
        movieRoleEntity.setMovieId(movieEntity.getId());

        //when
        castMemberService.rateCharacter(movieEntity.getImdbId(), movieRoleEntity.getImdbId(), testRating, null);

        //then
        assertThrows(DuplicateEntityException.class,
                () -> castMemberService.rateCharacter(movieEntity.getImdbId(),movieRoleEntity.getImdbId(), testRating, null));
    }
    @WithMockUser(username = "mymockinguser")
    @Transactional
    @Test
    void shouldThrowInvalidInputException_whenClientInsertsNullValues() throws Exception {
        //given
        UserEntity testUser = testDataProvider.getAndSaveTestUserEntityOfUsername("mymockinguser");
        String movieId = null;
        String actorId = null;
        Rating testRating = Rating.ONE;
        //when
        //then
        assertThrows(InvalidInputException.class,
                () -> castMemberService.rateCharacter(movieId, actorId, testRating, null));
    }
    @WithMockUser(username = "mymockinguser")
    @Transactional
    @Test
    void shouldThrowObjectNotFound_whenClientInsertsNonExistingIds() throws Exception {
        //given
        UserEntity testUser = testDataProvider.getAndSaveTestUserEntityOfUsername("mymockinguser");
        String movieId = "definitely_non_existing";
        String actorId = "definitely_not_less_non_existing";
        Rating testRating = Rating.ONE;
        //when
        //then
        assertThrows(ObjectWithImdbIdNotFound.class,
                () -> castMemberService.rateCharacter(movieId, actorId, testRating, null));
    }
}
