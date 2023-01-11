package com.myrestfulprojects.moviehub.webclient.imdbApi;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.myrestfulprojects.moviehub.exceptions.InvalidJsonFormatException;
import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class ImdbClient {
    private static final String IMDB_URL = "https://imdb-api.com/en/API/";
    private static final String API_KEY = "k_dag49j41";
    private final RestTemplate restTemplate = new RestTemplate();
    public CastMemberFull getCastMember (String id) {
        try {
            CastMemberFullDto castMemberFullDto = callGetRequest("/Name/{apiKey}/{id}", CastMemberFullDto.class, API_KEY, id);
            return CastMemberFull.builder()
                    .id(castMemberFullDto.getId())
                    .name(castMemberFullDto.getName())
                    .role(castMemberFullDto.getRole())
                    .image(castMemberFullDto.getImage())
                    .summary(castMemberFullDto.getSummary())
                    .birthDate(castMemberFullDto.getBirthDate())
                    .deathDate(castMemberFullDto.getDeathDate())
                    .awards(castMemberFullDto.getAwards())
                    .height(castMemberFullDto.getHeight())
                    .castMovies(castMemberFullDto.getCastMovies())
                    .build();
        } catch (RuntimeException re) {
            throw new InvalidJsonFormatException("Json from external api could not be mapped");
        }
    }


    public MovieFull getMovie(String id) {
        try {
            MovieFullDto movieFullDto = callGetRequest("Title/{apiKey}/{id}/FullCast,",
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
        } catch (RuntimeException re) {
            throw new InvalidJsonFormatException("Json from external api could not be mapped");
        }


    }

    public <T> T callGetRequest (String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(IMDB_URL + url, responseType, objects);
    }
    public List<MovieShort> getTrendyMovies() {
        var trendyMoviesDto = callGetRequest("MostPopularMovies/{apiKey}", MovieShortDtoWrapper.class, API_KEY);
        var trendyMoviesList = new ArrayList<MovieShort>();
        trendyMoviesDto.getItems().stream()
                .forEach(movieDto -> {
                    try {
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
                        );
                    } catch (RuntimeException re) {
                        throw new InvalidJsonFormatException("Json from external api could not be mapped");
                    }
                });
        return trendyMoviesList;
    }
    public List<MovieRoleDto> shortenMovieRoleList(List<MovieRoleDto> movieRoleDtoList, long limit) {
        return movieRoleDtoList.stream().limit(limit).collect(Collectors.toList());
    }
}
