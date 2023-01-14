package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.rating.castmembers.CastMemberFull;
import com.myrestfulprojects.moviehub.service.ExternalApiCastMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/imdb-api/cast-members")
@Api("Display cast members from external imdb-api")
public class ExternalApiCastMemberController {
    private final ExternalApiCastMemberService externalApiCastMemberService;

    @ApiOperation(value = "Display details of chosen cast member")
    @GetMapping("/{imdbId}")
    public CastMemberFull getApiCastMember(@PathVariable String imdbId) {
        return externalApiCastMemberService.getCastMemberFromApi(imdbId);
    }

}
