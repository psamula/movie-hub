package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MovieShortDtoWrapper {
    private List<MovieShortDto> items;
}
