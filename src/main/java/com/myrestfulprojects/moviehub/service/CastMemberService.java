package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.config.UserEntity;
import com.myrestfulprojects.moviehub.exceptions.AuthorizationException;
import com.myrestfulprojects.moviehub.exceptions.DuplicateEntityException;
import com.myrestfulprojects.moviehub.exceptions.InvalidInputException;
import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.model.Department;
import com.myrestfulprojects.moviehub.model.entities.*;
import com.myrestfulprojects.moviehub.model.rating.*;
import com.myrestfulprojects.moviehub.repository.*;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CastMemberService {
    private final AuthorizedUserFacade authorizedUserFacade;
    private final ImdbClient imdbClient;
    private final UserRepository userRepository;
    private final MovieImportService movieImportService;
    private final MovieRepository movieRepository;
    private final MovieRoleRepository movieRoleRepository;
    private final CharacterRatingRepository characterRatingRepository;

    private final StaffMemberRepository staffMemberRepository;
    private final StaffMemberRatingRepository staffMemberRatingRepository;

    public CastMemberFull getApiCastMember(String id) {
        return imdbClient.getCastMember(id);
    }

    @Transactional
    public void rateStaffMember(String movieId, String memberImdbId, Rating rating, Long staffMemberId, Department department) {

        if (staffMemberId == null) {
            staffMemberId = (long) -1;
        }
        var staffMemberFromId = staffMemberRepository.findById(staffMemberId);
        UserEntity currentUser = userRepository.findById(authorizedUserFacade.getCurrentUserId())
                .orElseThrow(() -> new AuthorizationException("Cannot find authorized user"));
        StaffMemberEntity memberEntity;
        if (staffMemberFromId.isPresent()) {
            memberEntity = staffMemberFromId.get();
        }
        else {
            if (movieId == null || memberImdbId == null || department == null) {
                throw new InvalidInputException("Invalid member rating input");
            }
            memberEntity = getStaffMemberFromApi(movieId, memberImdbId, department);
        }
        var existingStaffMemberRating = staffMemberRatingRepository.findByUserAndStaffmember(currentUser, memberEntity);
        if (existingStaffMemberRating.isPresent()) {
            if (existingStaffMemberRating.get().getRating() == rating.getValue()) {
                throw new DuplicateEntityException("Staff member is already in database");
            }
            existingStaffMemberRating.get().setRating(rating.getValue());
            staffMemberRatingRepository.save(existingStaffMemberRating.get());
            return;
        }
        StaffMemberRatingEntity staffMemberRatingEntity = new StaffMemberRatingEntity();
        staffMemberRatingEntity.setStaffmember(memberEntity);
        staffMemberRatingEntity.setRating(rating.getValue());
        staffMemberRatingEntity.setUser(currentUser);
        staffMemberRatingRepository.save(staffMemberRatingEntity);
    }
    @Transactional
    public void rateCharacter(String movieId, String actorId, Rating rating, Long roleId) {
        if (roleId == null) {
            roleId = (long) -1;
        }
        var movieRole = movieRoleRepository.findById(roleId);
        UserEntity currentUser = userRepository.findById(authorizedUserFacade.getCurrentUserId())
                .orElseThrow(() -> new AuthorizationException("Authorized user could not be found"));
        MovieRoleEntity role;
        if (movieRole.isPresent()) {
            role = movieRole.get();
        }
        else {
            if (movieId == null || actorId == null) {
                throw new InvalidInputException("Invalid input");
            }
            role = getCharacterFromApi(movieId, actorId);
        }
        var existingCharacterRating = characterRatingRepository.findByUserAndMovierole(currentUser, role);
        if (existingCharacterRating.isPresent()) {
            if (existingCharacterRating.get().getRating() == rating.getValue()) {
                throw new DuplicateEntityException("character is already in database");
            }
            existingCharacterRating.get().setRating(rating.getValue());
            characterRatingRepository.save(existingCharacterRating.get());
            return;
        }
        CharacterRatingEntity characterRatingEntity = new CharacterRatingEntity();

        characterRatingEntity.setRating(rating.getValue());
        characterRatingEntity.setMovierole(role);
        characterRatingEntity.setUser(currentUser);

        characterRatingRepository.save(characterRatingEntity);
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

        var staffMember = new ArrayList<CastMemberShortEntity>();
        staffMember.addAll(movieEntity.getDirectorList());
        staffMember.addAll(movieEntity.getStarList());
        staffMember.addAll(movieEntity.getStarList());

        var extractedStaffMember = staffMember.stream()
                .map(mem -> staffMemberRepository.findById(mem.getStaff_member_id()).orElseThrow(IllegalStateException::new))
                .filter(mem -> mem.getImdbId().equals(memberImdbId))
                .filter(mem -> mem.getDepartment().equals(department.getDepartment()))
                .findFirst();
        if (extractedStaffMember.isEmpty()) {
            throw new EntityNotFoundException("No staff member with such imdbid and movieid could be found");
        }
        return extractedStaffMember.get();
    }
    public List<CharacterWithRatingDTO> getRatedCharacters() {
        var userId = authorizedUserFacade.getCurrentUserId();
        return characterRatingRepository.findAll().stream()
                .filter(m -> m.getUser().getId() == userId)
                .map(m -> new CharacterWithRatingDTO(m.getMovierole(), m.getRating()))
                .collect(Collectors.toList());
    }
    public List<StaffMemberWithRatingDTO> getRatedStaffMembers() {
        var userId = authorizedUserFacade.getCurrentUserId();
        return staffMemberRatingRepository.findAll().stream()
                .filter(sm -> sm.getUser().getId() == userId)
                .map(sm -> new StaffMemberWithRatingDTO(sm.getStaffmember(), sm.getRating()))
                .collect(Collectors.toList());
    }
}
