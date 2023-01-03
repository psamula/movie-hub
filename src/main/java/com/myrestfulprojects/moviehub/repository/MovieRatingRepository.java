package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatingRepository extends JpaRepository<MovieRatingEntity, Long> {
}
