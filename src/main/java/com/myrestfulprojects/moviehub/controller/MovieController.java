package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.Genre;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.model.rating.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
@Api("Display and rate movies from database")
public class MovieController {
    private final MovieService movieService;
    @ApiOperation("Rate a movie")
    @PostMapping("/{imdbId}/rate")
    public void rateMovie(@PathVariable String imdbId, @RequestParam Rating rating) {
        movieService.rateMovie(imdbId, rating);
    }

    @ApiOperation("Display all of your rated movies and rating")
    @GetMapping("/user")
    public List<MovieWithRatingDTO> getRatedMovies(@RequestParam(required = false) Integer page, Genre genre) {
        int pageNumber = page != null && page >= 1 ? page : 1;
        return movieService.getRatedMovies(pageNumber - 1, genre);
    }

}
