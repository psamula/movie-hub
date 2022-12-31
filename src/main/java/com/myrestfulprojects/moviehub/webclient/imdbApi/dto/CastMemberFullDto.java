package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class CastMemberFullDto {
    String id;
    String name;
    String role;
    String image;
    String summary;
    LocalDate birthDate;
    LocalDate deathDate;
    String awards;
    String height;
    List<MovieParticipationDto> castMovies;
}
