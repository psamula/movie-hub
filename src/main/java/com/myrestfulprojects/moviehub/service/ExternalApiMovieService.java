package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.movies.MovieFull;
import com.myrestfulprojects.moviehub.model.movies.MovieShort;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExternalApiMovieService {
    private final ImdbClient imdbClient;
    private final PaginationProvider paginationProvider;
    private final int PAGE_SIZE = 10;
    public MovieFull getApiMovie(String id) {
        return imdbClient.getMovie(id);
    }
    @Cacheable(cacheNames = "WeeklyTrendingMovies")
    public List<MovieShort> getApiTrendingMovies(int page) {
        var movieShorts = imdbClient.getTrendingMovies();
        return paginationProvider.getElementsByPage(movieShorts, page, PAGE_SIZE);
    }
}
