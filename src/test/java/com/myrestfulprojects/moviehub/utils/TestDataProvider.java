package com.myrestfulprojects.moviehub.utils;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import com.myrestfulprojects.moviehub.model.enums.Department;
import com.myrestfulprojects.moviehub.model.enums.Rating;
import com.myrestfulprojects.moviehub.model.rating.StaffMemberRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.dto.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.dto.StaffMemberWithRatingDTO;
import com.myrestfulprojects.moviehub.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        var  movieRatingEntity = new MovieRatingEntity();
        movieRatingEntity.setMovie(movieEntity);
        movieRatingEntity.setUser(testUser);
        movieRatingEntity.setRating(rating.getValue());
        movieRatingRepository.save(movieRatingEntity);

        return new MovieWithRatingDTO(movieEntity, rating.getValue());
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
    public StaffMemberWithRatingDTO getAndSaveStaffMemberWithRatingDTO(StaffMemberEntity staffMemberEntity, UserEntity testUser, Rating rating) {
        var staffMemberRatingEntity = new StaffMemberRatingEntity();
        staffMemberRatingEntity.setUser(testUser);
        staffMemberRatingEntity.setStaffmember(staffMemberEntity);
        staffMemberRatingEntity.setRating(rating.getValue());
        staffMemberRatingRepository.save(staffMemberRatingEntity);

        return new StaffMemberWithRatingDTO(staffMemberEntity, rating.getValue());
    }
}
