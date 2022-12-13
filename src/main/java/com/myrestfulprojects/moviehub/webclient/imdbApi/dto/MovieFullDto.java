package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Setter
@Getter
// a class which intercepts MovieFullDto json

public class MovieFullDto {
    String id;
    String title;
    String fullTitle;
    String genres;
    int year;
    String directors;
    String writers;
    String stars;
    String plot;
    double imDbRating;
    int imDbRatingVotes;
    LocalDate releaseDate;
    List<CastMemberShortDto> directorList;
    List<CastMemberShortDto> writerList;
    List<CastMemberShortDto> starList;
    String image;
    List<MovieRoleDto> actorList;
}
