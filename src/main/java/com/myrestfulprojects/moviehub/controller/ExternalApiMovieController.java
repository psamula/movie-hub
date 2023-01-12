package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.service.ExternalApiMovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/imdb-api/movies")
@Api("Display movies from external imdb-api")
public class ExternalApiMovieController {
    private final ExternalApiMovieService externalApiMovieService;

    @ApiOperation("Display movie details")
    @GetMapping("/{imdbId}")
    public MovieFull getApiMovie(@PathVariable String imdbId) {
        return externalApiMovieService.getApiMovie(imdbId);
    }
    @ApiOperation("Display recently trending movies")
    @GetMapping("/trending")
    public List<MovieShort> getApiTrendingMovies(@RequestParam(required = false) Integer page) {
        int pageNumber = page != null && page >= 1 ? page : 1;
        return externalApiMovieService.getApiTrendingMovies(pageNumber - 1);
    }
}
