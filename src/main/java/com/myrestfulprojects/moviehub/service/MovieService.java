package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.exceptions.AuthorizationException;
import com.myrestfulprojects.moviehub.exceptions.DuplicateEntityException;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.model.rating.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.repository.MovieRatingRepository;
import com.myrestfulprojects.moviehub.repository.UserRepository;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRatingRepository movieRatingRepository;
    private final UserRepository userRepository;
    private final ImdbClient imdbClient;
    private final MovieImportService movieImportService;
    private final AuthorizedUserFacade authorizedUserFacade;

    public MovieFull getApiMovie(String id) {
        return imdbClient.getMovie(id);
    }
    public List<MovieShort> getApiTrendyMovies() {
        return imdbClient.getTrendyMovies();
    }


    @Transactional
    public void rateMovie(String imdbId, Rating rating) {

        UserEntity currentUser = userRepository.findById(authorizedUserFacade.getCurrentUserId())
                .orElseThrow(() -> new AuthorizationException("Authorized user not found"));
        MovieEntity movieEntity = movieImportService.getOrCreateMovieFromApi(imdbId);
        var existingMovieRating = movieRatingRepository.findByUserAndMovie(currentUser, movieEntity);
        if (existingMovieRating.isPresent()) {
            if (existingMovieRating.get().getRating() == rating.getValue()) {
                throw new DuplicateEntityException("Already rated!");
            }
            existingMovieRating.get().setRating(rating.getValue());
            movieRatingRepository.save(existingMovieRating.get());
            return;
        }
        MovieRatingEntity movieRatingEntity = new MovieRatingEntity();
        movieRatingEntity.setRating(rating.getValue());
        movieRatingEntity.setMovie(movieEntity);
        movieRatingEntity.setUser(currentUser);
        movieRatingRepository.save(movieRatingEntity);
    }
    public List<MovieWithRatingDTO> getRatedMovies() {
        var userId = authorizedUserFacade.getCurrentUserId();
        return movieRatingRepository.findAll().stream()
                .filter(m -> m.getUser().getId() == userId)
                .map(m -> new MovieWithRatingDTO(m.getMovie(), m.getRating()))
                .collect(Collectors.toList());
    }
}
