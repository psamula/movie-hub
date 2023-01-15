package com.myrestfulprojects.moviehub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import com.myrestfulprojects.moviehub.model.enums.Department;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.model.rating.dto.CharacterWithRatingDTO;
import com.myrestfulprojects.moviehub.repository.*;
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
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class CastMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String TESTER_USERNAME = "mymockinguser";
    @Autowired
    private CharacterRatingRepository characterRatingRepository;
    @Autowired
    private TestDataProvider testDataProvider;
    @Autowired
    private StaffMemberRatingRepository staffMemberRatingRepository;

    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldGetUserRatedCharacters() throws Exception {
        //given
        UserEntity mockingUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        MovieRoleEntity expectedMovieRole = testDataProvider.getAndSaveTestMovieCharacter();
        Rating testyRating = Rating.TWO;


        CharacterWithRatingDTO expectedCharacterWithRatingDto = testDataProvider
                .getAndSaveCharacterWithRatingDTO(expectedMovieRole, mockingUser, testyRating);

        //when
        MvcResult mvcResult = mockMvc.perform(get("/cast-members/user/characters"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        List<CharacterWithRatingDTO> resultRatedCharacters = Arrays.asList(
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(), CharacterWithRatingDTO[].class));


        assertThat(resultRatedCharacters).isNotNull();
        assertThat(resultRatedCharacters).isNotEmpty();
        assertThat(resultRatedCharacters.get(0)).usingRecursiveComparison().isEqualTo(expectedCharacterWithRatingDto);
    }

    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldSuccessfullyRateACharacterByMovieroleId() throws Exception {
        //given
        UserEntity mockingUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        Rating expectedRating = Rating.ONE;
        MovieRoleEntity expectedRatedCharacter = testDataProvider.getAndSaveTestMovieCharacter();

        //when
        mockMvc.perform(post("/cast-members/characters/rate")
                        .param("rating", expectedRating.name())
                        .param("movieRoleId", String.valueOf(expectedRatedCharacter.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        var resultCharacterRatingEntityOpt = characterRatingRepository.findByUserAndMovierole(mockingUser, expectedRatedCharacter);

        assertThat(resultCharacterRatingEntityOpt).isPresent();
        assertThat(resultCharacterRatingEntityOpt.get().getMovierole()).usingRecursiveComparison().isEqualTo(expectedRatedCharacter);
        assertThat(resultCharacterRatingEntityOpt.get().getRating()).usingRecursiveComparison().isEqualTo(expectedRating.getValue());
        assertThat(resultCharacterRatingEntityOpt.get().getUser()).usingRecursiveComparison().isEqualTo(mockingUser);
    }
    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldSuccessfullyRateACharacterByMovieIdAndActorId() throws Exception {
        //given
        UserEntity mockingUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        Rating expectedRating = Rating.ONE;
        MovieEntity expectedMovie = testDataProvider.getAndSaveTestMovieEntity();
        MovieRoleEntity expectedRatedCharacter = testDataProvider.getAndSaveTestMovieCharacter();

        expectedMovie.setActorList(Collections.singletonList(expectedRatedCharacter));
        expectedRatedCharacter.setMovieId(expectedMovie.getId());

        //when
        mockMvc.perform(post("/cast-members/characters/rate")
                        .param("movieId", expectedMovie.getImdbId())
                        .param("actorId", expectedRatedCharacter.getImdbId())
                        .param("rating", expectedRating.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        var resultCharacterRatingEntityOpt = characterRatingRepository.findByUserAndMovierole(mockingUser, expectedRatedCharacter);

        assertThat(resultCharacterRatingEntityOpt).isPresent();
        assertThat(resultCharacterRatingEntityOpt.get().getMovierole()).usingRecursiveComparison().isEqualTo(expectedRatedCharacter);
        assertThat(resultCharacterRatingEntityOpt.get().getRating()).usingRecursiveComparison().isEqualTo(expectedRating.getValue());
        assertThat(resultCharacterRatingEntityOpt.get().getUser()).usingRecursiveComparison().isEqualTo(mockingUser);

    }
    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldSuccessfullyRateAStaffMemberByStaffMemberId() throws Exception {
        //given
        UserEntity mockingUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        Rating expectedRating = Rating.ONE;
        StaffMemberEntity testStaffMemberEntity = testDataProvider.getAndSaveTestStaffMemberEntity();

        //when
        mockMvc.perform(post("/cast-members/staff-members/rate")
                        .param("rating", expectedRating.name())
                        .param("staffMemberId", String.valueOf(testStaffMemberEntity.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        var resultStaffMemberRatingOpt = staffMemberRatingRepository.findByUserAndStaffmember(mockingUser, testStaffMemberEntity);

        assertThat(resultStaffMemberRatingOpt).isPresent();
        assertThat(resultStaffMemberRatingOpt.get().getStaffmember()).usingRecursiveComparison().isEqualTo(testStaffMemberEntity);
        assertThat(resultStaffMemberRatingOpt.get().getRating()).usingRecursiveComparison().isEqualTo(expectedRating.getValue());
        assertThat(resultStaffMemberRatingOpt.get().getUser()).usingRecursiveComparison().isEqualTo(mockingUser);
    }
    @Test
    @Transactional
    @WithMockUser(username = TESTER_USERNAME)
    void shouldSuccessfullyRateAStaffMemberByMovieIdAndStaffMemberId() throws Exception {
        //given
        UserEntity mockingUser = testDataProvider.getAndSaveTestUserEntityOfUsername(TESTER_USERNAME);
        Rating expectedRating = Rating.ONE;
        MovieEntity expectedMovie = testDataProvider.getAndSaveTestMovieEntity();
        StaffMemberEntity expectedStaffMemberEntity = testDataProvider.getAndSaveTestStaffMemberEntity();
        expectedStaffMemberEntity.setMovieId(expectedMovie.getId());

        //when
        mockMvc.perform(post("/cast-members/staff-members/rate")
                        .param("movieId", expectedMovie.getImdbId())
                        .param("memberImdbId", expectedStaffMemberEntity.getImdbId())
                        .param("department", Department.DIRECTOR.name())
                        .param("rating", expectedRating.name())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        //then
        var resultStaffMemberRatingOpt = staffMemberRatingRepository.findByUserAndStaffmember(mockingUser, expectedStaffMemberEntity);

        assertThat(resultStaffMemberRatingOpt).isPresent();
        assertThat(resultStaffMemberRatingOpt.get().getStaffmember()).usingRecursiveComparison().isEqualTo(expectedStaffMemberEntity);
        assertThat(resultStaffMemberRatingOpt.get().getRating()).usingRecursiveComparison().isEqualTo(expectedRating.getValue());
        assertThat(resultStaffMemberRatingOpt.get().getUser()).usingRecursiveComparison().isEqualTo(mockingUser);

    }
}