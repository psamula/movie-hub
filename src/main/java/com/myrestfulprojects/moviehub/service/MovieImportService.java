package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.entities.CastMemberShortEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.entities.castMemberShorts.DirectorShortEntity;
import com.myrestfulprojects.moviehub.model.entities.castMemberShorts.StarShortEntity;
import com.myrestfulprojects.moviehub.model.entities.castMemberShorts.WriterShortEntity;
import com.myrestfulprojects.moviehub.repository.CastMemberShortRepository;
import com.myrestfulprojects.moviehub.repository.MovieRepository;
import com.myrestfulprojects.moviehub.repository.MovieRoleRepository;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.CastMemberShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieImportService {
    private final MovieRepository movieRepository;
    private final CastMemberShortRepository castMemberShortRepository;
    private final ImdbClient imdbClient;
    private final MovieRoleRepository movieRoleRepository;

    @Transactional
    public void SaveMovieFromApi(String imdbId) {
        var apiMovie =  imdbClient.getMovie(imdbId);
        MovieEntity movieEntity = createMovieEntity(apiMovie);
        movieRepository.save(movieEntity);

        var convertedEntityDirectors = getCastMemberDirectors(apiMovie);
        var convertedEntityWriters = getCastMemberWriters(apiMovie);
        var convertedEntityStars = getCastMemberStars(apiMovie);

        convertedEntityDirectors.forEach(ent -> {
            ent.setMovieId(movieEntity.getId());
            castMemberShortRepository.save(ent);
        });
        convertedEntityWriters.forEach(ent -> {
            ent.setMovieId(movieEntity.getId());
            castMemberShortRepository.save(ent);
        });
        convertedEntityStars.forEach(ent -> {
            ent.setMovieId(movieEntity.getId());
            castMemberShortRepository.save(ent);
        });

        List<MovieRoleEntity> movieRoleEntities = apiMovie.getCharacters().stream()
                .map(this::getMovieRoleEntity)
                .collect(Collectors.toList());
        movieRoleEntities.forEach(movieRoleEntity -> movieRoleEntity.setMovieId(movieEntity.getId()));
        movieRoleRepository.saveAll(movieRoleEntities);
        movieEntity.setActorList(movieRoleEntities);
        movieRepository.save(movieEntity);


    }
    private MovieEntity createMovieEntity (MovieFull apiMovie) {
        MovieEntity movieEntity = new MovieEntity();
        movieEntity.setImdbId(apiMovie.getImdbId());
        movieEntity.setTitle(apiMovie.getTitle());
        movieEntity.setFullTitle(apiMovie.getFullTitle());
        movieEntity.setGenres(apiMovie.getGenres());
        movieEntity.setYear(apiMovie.getReleaseYear());
        movieEntity.setPlot(apiMovie.getPlot());
        movieEntity.setImDbRating(apiMovie.getRating());
        movieEntity.setImDbRatingVotes(apiMovie.getVotesCount());
        movieEntity.setReleaseDate(apiMovie.getReleaseDate());
        movieEntity.setImage(apiMovie.getImageUrl());
        movieEntity.setDirectorList(new ArrayList<>());
        movieEntity.setWriterList(new ArrayList<>());
        movieEntity.setStarList(new ArrayList<>());
        movieEntity.setActorList(new ArrayList<>());
        return movieEntity;
    }
    public List<CastMemberShortEntity> getCastMemberDirectors (MovieFull apiMovie) {
        var apiDirectors = apiMovie.getDirectorList();
        List<CastMemberShortEntity> entityDirectors = new ArrayList<>();
        apiDirectors.stream()
                .forEach(dir -> {
                    var entityDirector = new DirectorShortEntity();
                    fillCastMemberShortEntity(dir, entityDirector);
                    entityDirectors.add(entityDirector);
                });
        return entityDirectors;
    }
    public List<CastMemberShortEntity> getCastMemberWriters (MovieFull apiMovie) {
        var apiWriters = apiMovie.getWriterList();
        List<CastMemberShortEntity> entityWriters = new ArrayList<>();
        apiWriters.stream()
                .forEach(wri -> {
                    var entityWriter = new WriterShortEntity();
                    fillCastMemberShortEntity(wri, entityWriter);
                    entityWriters.add(entityWriter);
                });
        return entityWriters;
    }
    public List<CastMemberShortEntity> getCastMemberStars (MovieFull apiMovie) {
        var apiStars = apiMovie.getStarList();
        List<CastMemberShortEntity> entityStars = new ArrayList<>();
        apiStars.stream()
                .forEach(star -> {
                    var entityStar = new StarShortEntity();
                    fillCastMemberShortEntity(star, entityStar);
                    entityStars.add(entityStar);
                });
        return entityStars;
    }
    public <T extends CastMemberShortEntity> void fillCastMemberShortEntity (CastMemberShortDto memberShortDto, T entityType) {
        entityType.setImdbId(memberShortDto.getId());
        entityType.setName(memberShortDto.getName());
    }
    public MovieRoleEntity getMovieRoleEntity (MovieRoleDto movieRoleDto) {
        MovieRoleEntity movieRoleEntity = new MovieRoleEntity();
        movieRoleEntity.setImdbId(movieRoleDto.getId());
        movieRoleEntity.setImage(movieRoleDto.getImage());
        movieRoleEntity.setActorName(movieRoleDto.getName());
        movieRoleEntity.setAsCharacter(movieRoleEntity.getAsCharacter());
        return movieRoleEntity;
    }

}
