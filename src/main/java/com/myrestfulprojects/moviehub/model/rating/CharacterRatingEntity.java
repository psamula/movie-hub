package com.myrestfulprojects.moviehub.model.rating;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "characterrating")
public class CharacterRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private MovieRoleEntity movierole;

    @Column(name = "rating")
    private int rating;
}
