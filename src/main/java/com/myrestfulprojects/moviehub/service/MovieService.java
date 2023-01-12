package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.exceptions.UnauthorizedException;
import com.myrestfulprojects.moviehub.exceptions.DuplicateEntityException;
import com.myrestfulprojects.moviehub.model.Genre;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.MovieShort;
import com.myrestfulprojects.moviehub.model.PaginationProvider;
import com.myrestfulprojects.moviehub.model.rating.MovieWithRatingDTO;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRatingEntity;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.repository.MovieRatingRepository;
import com.myrestfulprojects.moviehub.repository.UserRepository;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRatingRepository movieRatingRepository;
    private final UserRepository userRepository;
    private final MovieImportService movieImportService;
    private final AuthorizedUserFacade authorizedUserFacade;
    private final int PAGE_SIZE = 10;

    @Transactional
    public void rateMovie(String imdbId, Rating rating) {

        UserEntity currentUser = userRepository.findById(authorizedUserFacade.getCurrentUserId())
                .orElseThrow(() -> new UnauthorizedException("Authorized user not found"));
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
    public List<MovieWithRatingDTO> getRatedMovies(int page, Genre genre) {
        var userId = authorizedUserFacade.getCurrentUserId();
        String chosenGenre = genre != null ? genre.getGenre() : "";
        return movieRatingRepository.findAll(PageRequest.of(page, PAGE_SIZE)).stream()
                .filter(m -> m.getUser().getId() == userId)
                .filter(m -> m.getMovie().getGenres().contains(chosenGenre))
                .map(m -> new MovieWithRatingDTO(m.getMovie(), m.getRating()))
                .collect(Collectors.toList());
    }
}
