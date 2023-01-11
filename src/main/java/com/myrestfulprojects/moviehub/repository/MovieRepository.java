package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByImdbId(String imdbId);

    boolean existsByImdbId(String imdbId);
}
