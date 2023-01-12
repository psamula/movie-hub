package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.model.Department;
import com.myrestfulprojects.moviehub.model.rating.CharacterWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.model.rating.StaffMemberWithRatingDTO;
import com.myrestfulprojects.moviehub.service.CastMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cast-members")
@Api("Browse and rate characters and cast members - directors, stars and writers")
public class CastMemberController {
    private final CastMemberService castMemberService;

    @GetMapping("/user/characters")
    @ApiOperation(value = "Display all of your rated movie characters and rating")
    public List<CharacterWithRatingDTO> getRatedCharacters() {
        return this.castMemberService.getRatedCharacters();
    }
    @ApiOperation(value = "Display all of your rated staff members and rating")
    @GetMapping("/user/staff-members")
    public List<StaffMemberWithRatingDTO> getRatedStaffMembers() {
        return this.castMemberService.getRatedStaffMembers();
    }
    @ApiOperation(value = "Rate a movie character")
    @PostMapping("/characters/rate")
    public void rateCharacter(@RequestParam(required = false) String movieId,
                              @RequestParam(required = false) String actorId,
                              @RequestParam(required = true) Rating rating,
                              @RequestParam(required = false) Long movieRoleId) {

        this.castMemberService.rateCharacter(movieId, actorId, rating, movieRoleId);
    }
    @ApiOperation(value = "Rate a movie staff member")
    @PostMapping("/staff-members/rate")
    public void rateStaffMember(@RequestParam(required = false) String movieId,
                                @RequestParam(required = false) String memberImdbId,
                                @RequestParam(required = true) Rating rating,
                                @RequestParam(required = false) Long staffMemberId,
                                @RequestParam(required = false) Department department) {
        this.castMemberService.rateStaffMember(movieId, memberImdbId, rating, staffMemberId, department);
    }

}
