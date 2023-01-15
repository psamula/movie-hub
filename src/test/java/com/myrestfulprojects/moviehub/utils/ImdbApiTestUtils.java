package com.myrestfulprojects.moviehub.utils;

import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class ImdbApiTestUtils {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<MovieShortDto> getTrendingMoviesFromImdbApi() {
        return null;
    }
    public MovieEntity generateTestMovieEntity() {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setImdbId("TestyImdbId");
        movieEntity.setTitle("TestyTitle");
        movieEntity.setGenres("Drama");
        movieEntity.setYear(2022);

        return movieEntity;

    }
    public MovieRoleEntity generateTestMovieCharacter() {
        MovieRoleEntity movieRoleEntity = new MovieRoleEntity();
        movieRoleEntity.setImdbId("dummyActorId");
        movieRoleEntity.setActorName("Dummy Actor");
        movieRoleEntity.setAsCharacter("Dummy character");
        return movieRoleEntity;

    }
}
