package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "MovieRating")
public class MovieRatingEntity {
    @Id
    private Long id;

    @ManyToOne
    private UserEntity userId;

    @ManyToOne
    private MovieEntity movieId;

    @Enumerated(EnumType.ORDINAL)
    private Rating rating;
}