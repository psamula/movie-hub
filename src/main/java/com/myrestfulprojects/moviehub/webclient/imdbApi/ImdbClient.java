package com.myrestfulprojects.moviehub.webclient.imdbApi;

import com.myrestfulprojects.moviehub.exceptions.InvalidJsonFormatException;
import com.myrestfulprojects.moviehub.exceptions.ObjectWithImdbIdNotFound;
import com.myrestfulprojects.moviehub.model.rating.castmembers.CastMemberFull;
import com.myrestfulprojects.moviehub.model.movies.MovieFull;
import com.myrestfulprojects.moviehub.model.movies.MovieShort;
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
    private static final String API_KEY2 = "k_zbbq3lxc";
    private static final String API_KEY = "k_dag49j41";
    private final RestTemplate restTemplate = new RestTemplate();
    public CastMemberFull getCastMember (String id) {
        String memberId = null;
        try {
            CastMemberFullDto castMemberFullDto = callGetRequest("/Name/{apiKey}/{id}", CastMemberFullDto.class, API_KEY, id);
            memberId = castMemberFullDto.getId();
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
            if (memberId == null) {
                throw new ObjectWithImdbIdNotFound("CastMember with such imdbId could not be found");
            }
            throw new InvalidJsonFormatException("Json cast member from external api could not be mapped");
        }
    }


    public MovieFull getMovie(String id) {
        String movieId = null;
        try {
            MovieFullDto movieFullDto = callGetRequest("Title/{apiKey}/{id}/FullCast,",
                    MovieFullDto.class, API_KEY, id);
            movieId = movieFullDto.getId();
            MovieFull movieFull = MovieFull.builder()
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
                    .characters(shortenedMovieRoleList(movieFullDto.getActorList(), 15)) //shortened list
                    .build();
            return movieFull;
        }
        catch (RuntimeException re) {
            if (movieId == null) {
                throw new ObjectWithImdbIdNotFound("Could not find Movie with such id");
            }
            throw new InvalidJsonFormatException("Json full movie from external api could not be mapped");
        }
    }

    public <T> T callGetRequest (String url, Class<T> responseType, Object... objects) {
        return restTemplate.getForObject(IMDB_URL + url, responseType, objects);
    }
    public List<MovieShort> getTrendingMovies() {
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
                        throw new InvalidJsonFormatException("Json trending movies from external api could not be mapped");
                    }
                });
        return trendyMoviesList;
    }
    public List<MovieRoleDto> shortenedMovieRoleList(List<MovieRoleDto> movieRoleDtoList, long limit) {
        return movieRoleDtoList.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
}
