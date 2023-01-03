package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.Movie;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.model.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public MovieFull getApiMovie(@PathVariable String id) {
        return movieService.getApiMovie(id);
    }
    @GetMapping("/movies/trendy")
    public List<MovieShort> getApiTrendyMovies() {
        return movieService.getApiTrendyMovies();
    }
    @PostMapping("/rate/movies")
    public void rateMovie(@RequestParam(required = true) Rating rating, @RequestParam(required = true) String imdbId) {
        movieService.rateMovie(imdbId, rating);
    }
    @GetMapping("/user/movies")
    public List<MovieWithRatingDTO> getRatedMovies() {
        return movieService.getRatedMovies();
    }
}
