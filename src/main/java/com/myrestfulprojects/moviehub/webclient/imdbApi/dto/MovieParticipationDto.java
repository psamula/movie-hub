package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieParticipationDto {
    String id;
    String role;
    String title;
    String year;
    String description;
}
