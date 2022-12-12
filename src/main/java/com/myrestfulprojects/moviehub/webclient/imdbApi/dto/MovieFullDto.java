package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import com.myrestfulprojects.moviehub.model.CastMember;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
// a class which intercepts MovieFullDto json

public class MovieDto {
    String id;
    String title;
    String fullTitle;
    int year;
    String directors;
    String writers;
    String plot;
    double imDbRating;
    int imDbRatingVotes;
    LocalDate releaseDate;
    String image;
    List<MovieRoleDto> actorList;
}
