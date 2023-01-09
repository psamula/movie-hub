package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.model.Department;
import com.myrestfulprojects.moviehub.model.rating.CharacterWithRatingDTO;
import com.myrestfulprojects.moviehub.model.rating.Rating;
import com.myrestfulprojects.moviehub.model.rating.StaffMemberWithRatingDTO;
import com.myrestfulprojects.moviehub.service.CastMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CastMemberController {
    private final CastMemberService castMemberService;

    @GetMapping("/cast-member/{id}")
    public CastMemberFull getApiCastMember(@PathVariable String id) {
        return castMemberService.getApiCastMember(id);
    }
    @PostMapping("cast-member/actors/rate-character")
    public void rateCharacter(@RequestParam(required = false) String movieId,
                              @RequestParam(required = false) String actorId,
                              @RequestParam(required = true) Rating rating,
                              @RequestParam(required = false) Long movieRoleId) {

        this.castMemberService.rateCharacter(movieId, actorId, rating, movieRoleId);
    }
    @PostMapping("cast-member/staff-member/rate-staff-member")
    public void rateStaffMember(@RequestParam(required = false) String movieId,
                                @RequestParam(required = false) String memberImdbId,
                                @RequestParam(required = true) Rating rating,
                                @RequestParam(required = false) Long staffMemberId,
                                @RequestParam(required = false) Department department) {
        this.castMemberService.rateStaffMember(movieId, memberImdbId, rating, staffMemberId, department);
    }
    @GetMapping("cast-member/actors/rated")
    public List<CharacterWithRatingDTO> getRatedCharacters() {
        return this.castMemberService.getRatedCharacters();
    }
    @GetMapping("cast-member/staff-member/rated")
    public List<StaffMemberWithRatingDTO> getRatedStaffMembers() {
        return this.castMemberService.getRatedStaffMembers();
    }
}
