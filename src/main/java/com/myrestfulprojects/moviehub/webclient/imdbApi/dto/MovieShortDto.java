package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieShortDto {
    String id;
    int rank;
    String title;
    int year;
    String image;
    String crew;
    double imDbRating;
    int imDbRatingCount;
}
