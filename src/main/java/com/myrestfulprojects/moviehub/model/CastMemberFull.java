package com.myrestfulprojects.moviehub.model;

import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieParticipationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class CastMemberFull {
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
