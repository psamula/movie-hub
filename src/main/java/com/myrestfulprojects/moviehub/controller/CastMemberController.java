package com.myrestfulprojects.moviehub.controller;

import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.service.CastMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class CastMemberController {
    private final CastMemberService castMemberService;

//    @GetMapping("/actors/{id}")
//    public CastMemberFull getApiCastMember(@PathVariable String id) {
//        return castMemberService.getApiCastMember(id);
//    }
}
