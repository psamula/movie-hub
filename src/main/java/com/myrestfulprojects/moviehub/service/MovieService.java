package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.Movie;
import com.myrestfulprojects.moviehub.service.repository.MovieRepository;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class MovieService {
    //private final MovieRepository movieRepository;
    private final ImdbClient imdbClient;
    public List<Movie> getMovies() {
        //return movieRepository.findAll();
        throw new IllegalStateException("Not implemented yet!");
    }

    public MovieDto getApiMovie(String id) {
        return imdbClient.getMovie(id);
    }

    public Movie getMovie(long id) {
        //return movieRepository.findById(id).orElseThrow();
        throw new IllegalStateException("Not implemented yet!");

    }

}
