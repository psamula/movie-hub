package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.model.Department;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffMemberRepository extends JpaRepository<StaffMemberEntity, Long> {
    Optional<StaffMemberEntity> findByMovieIdAndDepartment(Long movieId, String department);
}

