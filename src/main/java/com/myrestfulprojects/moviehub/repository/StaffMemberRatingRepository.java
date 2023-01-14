package com.myrestfulprojects.moviehub.repository;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import com.myrestfulprojects.moviehub.model.rating.StaffMemberRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffMemberRatingRepository extends JpaRepository<StaffMemberRatingEntity, Long> {
    Optional<StaffMemberRatingEntity> findByUserAndStaffmember(UserEntity currentUser, StaffMemberEntity memberEntity);
}
