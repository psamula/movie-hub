package com.myrestfulprojects.moviehub.model.rating;

import com.myrestfulprojects.moviehub.config.user.UserEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "staffmemberrating")
public class StaffMemberRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private StaffMemberEntity staffmember;

    @Column(name = "rating")
    private int rating;
}