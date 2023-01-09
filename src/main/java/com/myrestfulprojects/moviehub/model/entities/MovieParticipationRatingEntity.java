package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "movie_participation_rating")
public class MovieParticipationRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @ManyToOne
    MovieParticipationEntity movieParticipation;
    @ManyToOne
    private UserEntity user;
    Rating rating;
}
