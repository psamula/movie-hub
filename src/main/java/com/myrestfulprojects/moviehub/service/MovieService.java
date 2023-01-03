package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.Movie;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.model.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.repository.MovieRatingRepository;
import com.myrestfulprojects.moviehub.repository.MovieRepository;
import com.myrestfulprojects.moviehub.repository.UserRepository;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final UserRepository userRepository;
    private final ImdbClient imdbClient;
    private final MovieImportService movieImportService;
    private final AuthorizedUserFacade authorizedUserFacade;
    public List<Movie> getMovies() {
        //return movieRepository.findAll();
        throw new IllegalStateException("Not implemented yet!");
    }

    public MovieFull getApiMovie(String id) {
        return imdbClient.getMovie(id);
    }
    public List<MovieShort> getApiTrendyMovies() {
        return imdbClient.getTrendyMovies();
    }

    public Movie getMovie(long id) {
        //return movieRepository.findById(id).orElseThrow();
        throw new IllegalStateException("Not implemented yet!");
    }


    @Transactional
    public void rateMovie(String imdbId, Rating rating) {
        MovieRatingEntity movieRatingEntity = new MovieRatingEntity();
        UserEntity currentUser = userRepository.findById(authorizedUserFacade.getCurrentUserId()).orElseThrow(IllegalStateException::new);
        MovieEntity movieEntity = movieImportService.saveMovieFromApi(imdbId);
        movieRatingEntity.setRating(rating.getValue());
        movieRatingEntity.setMovie(movieEntity);
        movieRatingEntity.setUser(currentUser);
        movieRatingRepository.save(movieRatingEntity);
    }

    public List<MovieWithRatingDTO> getRatedMovies() {
        var userId = authorizedUserFacade.getCurrentUserId();
        List<MovieWithRatingDTO> moviesWithUserRatings = movieRatingRepository.findAll().stream()
                .filter(m -> m.getUser().getId() == userId)
                .map(m -> new MovieWithRatingDTO(m.getMovie(), m.getRating()))
                .collect(Collectors.toList());
        return moviesWithUserRatings;

    }
}
