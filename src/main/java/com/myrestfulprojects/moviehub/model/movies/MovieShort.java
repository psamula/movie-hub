package com.myrestfulprojects.moviehub.model.movies;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MovieShort {
    private String title;
    private String imdbId;
    private int rank;
    private int releaseYear;
    private String image;
    private String cast;
    private double rating;
    private int votesCount;
}
