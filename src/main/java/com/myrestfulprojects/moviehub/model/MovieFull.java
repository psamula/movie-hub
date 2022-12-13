package com.myrestfulprojects.moviehub.model;

import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.CastMemberShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieRoleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Builder
public class MovieFull {
    String imdbId;
    String title;
    String fullTitle;
    String genres;
    int releaseYear;
    String directors;
    String writers;
    String stars;
    String plot;
    double rating;
    int votesCount;
    LocalDate releaseDate;
    String imageUrl;
    List<CastMemberShortDto> directorList;
    List<CastMemberShortDto> writerList;
    List<CastMemberShortDto> starList;
    List<MovieRoleDto> characters;

}
