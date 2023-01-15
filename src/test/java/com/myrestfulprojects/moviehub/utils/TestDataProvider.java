package com.myrestfulprojects.moviehub.utils;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import com.myrestfulprojects.moviehub.model.enums.Department;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.model.rating.CharacterRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.StaffMemberRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.dto.CharacterWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.dto.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.dto.StaffMemberWithRatingDTO;
import com.myrestfulprojects.moviehub.repository.*;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDtoWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class TestDataProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieRoleRepository movieRoleRepository;
    @Autowired
    private MovieRatingRepository movieRatingRepository;
    @Autowired
    private StaffMemberRepository staffMemberRepository;
    @Autowired
    private StaffMemberRatingRepository staffMemberRatingRepository;
    @Autowired
    private CharacterRatingRepository characterRatingRepository;

    public UserEntity getAndSaveTestUserEntityOfUsername(String username) {
        UserEntity mockingUser = new UserEntity();
        mockingUser.setUsername(username);
        mockingUser.setPassword("testpassword");
        return userRepository.save(mockingUser);
    }
    public MovieEntity getAndSaveTestMovieEntity() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setImdbId("TestyImdbId");
        movieEntity.setTitle("TestyTitle");
        movieEntity.setGenres("Drama");
        movieEntity.setYear(2022);
        return movieRepository.save(movieEntity);
    }

    public MovieWithRatingDTO getAndSaveTestMovieWithRatingDTO(MovieEntity movieEntity, UserEntity testUser, Rating rating) {
        var  movieRatingEntity = getAndSaveTestMovieRatingEntity(movieEntity, testUser, rating);
        return new MovieWithRatingDTO(movieEntity, rating.getValue());
    }
    public MovieRatingEntity getAndSaveTestMovieRatingEntity(MovieEntity movieEntity, UserEntity testUser, Rating rating) {
        var  movieRatingEntity = new MovieRatingEntity();
        movieRatingEntity.setMovie(movieEntity);
        movieRatingEntity.setUser(testUser);
        movieRatingEntity.setRating(rating.getValue());
        return movieRatingRepository.save(movieRatingEntity);
    }
    public MovieRoleEntity getAndSaveTestMovieCharacter() {
        MovieRoleEntity movieRoleEntity = new MovieRoleEntity();
        movieRoleEntity.setImdbId("dummyActorId");
        movieRoleEntity.setActorName("Dummy Actor");
        movieRoleEntity.setAsCharacter("Dummy character");
        movieRoleRepository.save(movieRoleEntity);
        return movieRoleEntity;

    }
    public StaffMemberEntity getAndSaveTestStaffMemberEntity() {
        var staffMemberEntity = new StaffMemberEntity();
        staffMemberEntity.setImdbId("dummyStaffMemberId");
        staffMemberEntity.setDepartment(Department.DIRECTOR.getDepartment());
        staffMemberEntity.setName("Dummy Staffmember");
        return staffMemberRepository.save(staffMemberEntity);
    }
    public CharacterRatingEntity getAndSaveCharacterRatingEntity(MovieRoleEntity movieRoleEntity, UserEntity testUser, Rating rating) {
        CharacterRatingEntity expectedCharacterRating = new CharacterRatingEntity();
        expectedCharacterRating.setUser(testUser);
        expectedCharacterRating.setMovierole(movieRoleEntity);
        expectedCharacterRating.setRating(rating.getValue());
        return characterRatingRepository.save(expectedCharacterRating);
    }
    public CharacterWithRatingDTO getAndSaveCharacterWithRatingDTO(MovieRoleEntity movieRoleEntity, UserEntity testUser, Rating rating) {
        CharacterRatingEntity characterRatingEntity = getAndSaveCharacterRatingEntity(movieRoleEntity, testUser, rating);
        return new CharacterWithRatingDTO(movieRoleEntity, rating.getValue());
    }
    public StaffMemberWithRatingDTO getAndSaveStaffMemberWithRatingDTO(StaffMemberEntity staffMemberEntity, UserEntity testUser, Rating rating) {
        var staffMemberRatingEntity = new StaffMemberRatingEntity();
        staffMemberRatingEntity.setUser(testUser);
        staffMemberRatingEntity.setStaffmember(staffMemberEntity);
        staffMemberRatingEntity.setRating(rating.getValue());
        staffMemberRatingRepository.save(staffMemberRatingEntity);

        return new StaffMemberWithRatingDTO(staffMemberEntity, rating.getValue());
    }

    public List<MovieShortDto> getTrendyMovies() {
        RestTemplate restTemplate = new RestTemplate();
        var shortDtoWrapper = restTemplate.getForObject(
                "https://imdb-api.com/en/API/MostPopularMovies/k_zbbq3lxc", MovieShortDtoWrapper.class);
        return shortDtoWrapper.getItems();
    }
}
