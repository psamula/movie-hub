package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieRoleDto {
    String id; //imdb actor id
    String image;
    String name;
    String asCharacter;
}
