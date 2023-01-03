package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.model.entities.CastMemberShortEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CastMemberShortRepository extends JpaRepository<CastMemberShortEntity, Long> {
}
