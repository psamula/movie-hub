package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.Department;
import com.myrestfulprojects.moviehub.model.MovieFull;
import com.myrestfulprojects.moviehub.model.entities.CastMemberShortEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import com.myrestfulprojects.moviehub.model.entities.staffMemberShorts.DirectorShortEntity;
import com.myrestfulprojects.moviehub.model.entities.staffMemberShorts.StarShortEntity;
import com.myrestfulprojects.moviehub.model.entities.staffMemberShorts.WriterShortEntity;
import com.myrestfulprojects.moviehub.repository.CastMemberShortRepository;
import com.myrestfulprojects.moviehub.repository.MovieRepository;
import com.myrestfulprojects.moviehub.repository.MovieRoleRepository;
import com.myrestfulprojects.moviehub.repository.StaffMemberRepository;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.CastMemberShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieImportService {
    private final MovieRepository movieRepository;
    private final CastMemberShortRepository castMemberShortRepository;
    private final ImdbClient imdbClient;
    private final MovieRoleRepository movieRoleRepository;
    private final StaffMemberRepository staffMemberRepository;

    public MovieEntity getOrCreateMovieFromApi(String imdbId) {
        if (movieRepository.existsByImdbId(imdbId)) {
            return movieRepository.findByImdbId(imdbId).get();
        }

        var apiMovie = imdbClient.getMovie(imdbId);
        MovieEntity movieEntity = createMovieEntity(apiMovie);
        movieRepository.save(movieEntity);

        var convertedEntityDirectors = getCastMemberDirectors(apiMovie);
        var convertedEntityWriters = getCastMemberWriters(apiMovie);
        var convertedEntityStars = getCastMemberStars(apiMovie);

        convertedEntityDirectors.forEach(ent -> {
            ent.setMovieId(movieEntity.getId());
            castMemberShortRepository.save(ent);
            ent.setStaff_member_id(insertStaffMemberRecord(ent.getName(), ent.getImdbId(), ent.getMovieId(), Department.DIRECTOR.getDepartment()));
        });
        convertedEntityWriters.forEach(ent -> {
            ent.setMovieId(movieEntity.getId());
            castMemberShortRepository.save(ent);
            ent.setStaff_member_id(insertStaffMemberRecord(ent.getName(), ent.getImdbId(), ent.getMovieId(), Department.WRITER.getDepartment()));
        });
        convertedEntityStars.forEach(ent -> {
            ent.setMovieId(movieEntity.getId());
            castMemberShortRepository.save(ent);
            ent.setStaff_member_id(insertStaffMemberRecord(ent.getName(), ent.getImdbId(), ent.getMovieId(), Department.STAR.getDepartment()));
        });

        List<MovieRoleEntity> movieRoleEntities = apiMovie.getCharacters().stream()
                .map(this::getMovieRoleEntity)
                .collect(Collectors.toList());
        movieRoleEntities.forEach(movieRoleEntity -> {
            movieRoleEntity.setMovieId(movieEntity.getId());
            if (!movieRoleRepository.existsByAsCharacterAndMovieId(movieRoleEntity.getAsCharacter(), movieRoleEntity.getMovieId())) {
                movieRoleRepository.save(movieRoleEntity);
            }
        });
        movieEntity.setActorList(movieRoleEntities);
        movieRepository.save(movieEntity);
        return movieEntity;
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
        movieRoleEntity.setAsCharacter(movieRoleDto.getAsCharacter());
        return movieRoleEntity;
    }
    private Long insertStaffMemberRecord(String name, String imdbId, Long movieId, String department) {
        Optional<StaffMemberEntity> optionalMember = staffMemberRepository.findByMovieIdAndImdbIdAndDepartment(movieId, imdbId, department);
        if (optionalMember.isPresent()) {
            return optionalMember.get().getId();
        }
        StaffMemberEntity staffMember = new StaffMemberEntity();
        staffMember.setMovieId(movieId);
        staffMember.setImdbId(imdbId);
        staffMember.setName(name);
        staffMember.setDepartment(department);
        staffMemberRepository.save(staffMember);
        var idOfThisStaffMember = staffMember.getId();
        return idOfThisStaffMember;
    }

}
