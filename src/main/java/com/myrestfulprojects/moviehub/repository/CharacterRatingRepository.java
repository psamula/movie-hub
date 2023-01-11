package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.model.rating.CharacterRatingEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRatingRepository extends JpaRepository<CharacterRatingEntity, Long> {

    Optional<CharacterRatingEntity> findByUserAndMovierole(UserEntity currentUser, MovieRoleEntity role);
}
