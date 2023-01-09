package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.model.rating.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movie/{id}")
    public MovieFull getApiMovie(@PathVariable String id) {
        return movieService.getApiMovie(id);
    }
    @GetMapping("/movies/trending")
    public List<MovieShort> getApiTrendingMovies() {
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
