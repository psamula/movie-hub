package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.rating.castmembers.CastMemberFull;
import com.myrestfulprojects.moviehub.model.enums.Department;
import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import com.myrestfulprojects.moviehub.repository.*;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
@RequiredArgsConstructor
@Service
public class ExternalApiCastMemberService {
    private final MovieImportService movieImportService;
    private final MovieRepository movieRepository;

    private final StaffMemberRepository staffMemberRepository;
    private final ImdbClient imdbClient;

    public CastMemberFull getCastMemberFromApi(String id) {
        return imdbClient.getCastMember(id);
    }
    @Transactional
    public MovieRoleEntity getCharacterFromApi(String movieId, String actorId) {
        MovieEntity movieEntity;
        Optional<MovieEntity> optionalMovieEntity = movieRepository.findByImdbId(movieId);
        if (optionalMovieEntity.isEmpty()) {
            movieEntity = movieImportService.getOrCreateMovieFromApi(movieId);
        } else {
            movieEntity = optionalMovieEntity.get();
        }
        var movieRole = movieEntity.getActorList().stream()
                .filter(role -> role.getImdbId().equals(actorId))
                .findFirst();
        if (movieRole.isEmpty()) {
            throw new EntityNotFoundException("No character with such movieId and actorId could be found");
        }
        return movieRole.get();
    }
    @Transactional
    public StaffMemberEntity getStaffMemberFromApi(String movieId, String memberImdbId, Department department) {
        MovieEntity movieEntity;
        movieEntity = movieImportService.getOrCreateMovieFromApi(movieId);

       var movieEntityStaffMembers = staffMemberRepository.findAllByMovieId(movieEntity.getId());

        var extractedStaffMember = movieEntityStaffMembers.stream()
                .filter(mem -> mem.getImdbId().equals(memberImdbId))
                .filter(mem -> mem.getDepartment().equals(department.getDepartment()))
                .findFirst();
        if (extractedStaffMember.isEmpty()) {
            throw new EntityNotFoundException("No staff member with such imdbid, department and movieid could be found");
        }
        return extractedStaffMember.get();
    }
}
