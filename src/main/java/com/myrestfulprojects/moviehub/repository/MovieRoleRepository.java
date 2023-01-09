package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRoleRepository extends JpaRepository<MovieRoleEntity, Long> {
    @Query(value = "SELECT mr.* FROM movierole mr INNER JOIN movie m ON mr.movieid = m.id WHERE m.imdbId = :imdbId", nativeQuery = true)
    List<MovieRoleEntity> findAllByMovieImdbId(@Param("imdbId") String imdbId);
    boolean existsByAsCharacterAndMovieId(String asCharacter, Long movieId);
}
