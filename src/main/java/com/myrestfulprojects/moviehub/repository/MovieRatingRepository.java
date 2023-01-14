package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRatingEntity, Long> {
    Optional<MovieRatingEntity> findByUserAndMovie(UserEntity currentUser, MovieEntity movieEntity);
    @Query("UPDATE MovieRatingEntity mr SET mr.rating = :rating WHERE mr.id = :id")
    MovieRatingEntity updateRating(@Param("id") long id, @Param("rating") int rating);
}
