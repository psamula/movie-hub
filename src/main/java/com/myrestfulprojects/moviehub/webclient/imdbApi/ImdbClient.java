package com.myrestfulprojects.moviehub.webclient.imdbApi;

import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieFullDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieRoleDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieShortDtoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class ImdbClient {
    private static final String IMDB_URL = "https://imdb-api.com/en/API/";
    private static final String API_KEY = "k_dag49j41";
    private final RestTemplate restTemplate = new RestTemplate();

    public MovieFull getMovie(String id) {
        MovieFullDto movieFullDto =  callGetRequest("Title/{apiKey}/{id}/FullCast,",
                MovieFullDto.class, API_KEY, id);
        return MovieFull.builder()
                .imdbId(movieFullDto.getId())
                .title(movieFullDto.getTitle())
                .fullTitle(movieFullDto.getFullTitle())
                .genres(movieFullDto.getGenres())
                .releaseYear(movieFullDto.getYear())
                .directors(movieFullDto.getDirectors())
                .writers(movieFullDto.getWriters())
                .stars(movieFullDto.getStars())
                .plot(movieFullDto.getPlot())
                .rating(movieFullDto.getImDbRating())
                .votesCount(movieFullDto.getImDbRatingVotes())
                .releaseDate(movieFullDto.getReleaseDate())
                .imageUrl(movieFullDto.getImage())
                .directorList(movieFullDto.getDirectorList())
                .writerList(movieFullDto.getWriterList())
                .starList(movieFullDto.getStarList())
                .characters(shortenMovieRoleList(movieFullDto.getActorList(), 15)) //shortened list
                .build();

    }

    public <T> T callGetRequest (String url, Class<T> responseType, Object... objects) {
        var x = objects;
        var s1 = "k_dag49j41";
        var s2 = IMDB_URL + url;
        return restTemplate.getForObject(IMDB_URL + url, responseType, objects);
    }
    public List<MovieShort> getTrendyMovies() {
        var trendyMoviesDto = callGetRequest("MostPopularMovies/{apiKey}", MovieShortDtoWrapper.class, API_KEY);
        var trendyMoviesList = new ArrayList<MovieShort>();
        trendyMoviesDto.getItems().stream()
                .forEach(movieDto -> {
                    trendyMoviesList.add(MovieShort.builder()
                            .title(movieDto.getTitle())
                            .imdbId(movieDto.getId())
                            .rank(movieDto.getRank())
                            .releaseYear(movieDto.getYear())
                            .image(movieDto.getImage())
                            .cast(movieDto.getCrew())
                            .rating(movieDto.getImDbRating())
                            .votesCount(movieDto.getImDbRatingCount())
                            .build()
                    );});
        return trendyMoviesList;
    }
    public List<MovieRoleDto> shortenMovieRoleList(List<MovieRoleDto> movieRoleDtoList, long limit) {
        return movieRoleDtoList.stream().limit(limit).collect(Collectors.toList());
    }
}
