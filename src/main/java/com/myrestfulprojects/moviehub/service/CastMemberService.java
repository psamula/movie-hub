package com.myrestfulprojects.moviehub.service;

import com.myrestfulprojects.moviehub.model.CastMemberFull;
import com.myrestfulprojects.moviehub.webclient.imdbApi.ImdbClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CastMemberService {
    //private final CastMemberRepository castMemberRepository;
    private final ImdbClient imdbClient;

//    public CastMemberFull getApiCastMember(String id) {
//        //return imdbClient.getApiCastMember(id);
//    }
}
