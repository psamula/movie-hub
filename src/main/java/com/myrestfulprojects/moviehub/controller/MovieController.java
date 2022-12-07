package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.Movie;
import com.myrestfulprojects.moviehub.service.MovieService;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/myMovies")
    public List<Movie> getMovies() {
        return movieService.getMovies();
    }
    @GetMapping("/myMovie/{id}")
    public Movie getMovie(@PathVariable long id) {
        return movieService.getMovie(id);
    }
    @GetMapping("/movie/{id}")
    public MovieDto getMovieApi(@PathVariable String id) {
        return movieService.getApiMovie(id);
    }
}
