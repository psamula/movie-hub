package com.myrestfulprojects.moviehub.model.rating;

import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieWithRatingDTO {
    private MovieEntity movie;
    private int rating;
}