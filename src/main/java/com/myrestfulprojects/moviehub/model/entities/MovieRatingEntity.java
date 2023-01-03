package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "movierating")
public class MovieRatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private MovieEntity movie;

    @Column(name = "rating")
    private int rating;
}