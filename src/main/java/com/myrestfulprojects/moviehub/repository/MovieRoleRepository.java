package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRoleRepository extends JpaRepository<MovieRoleEntity, Long> {
}
