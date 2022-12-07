package com.myrestfulprojects.moviehub.webclient.imdbApi.dto;

import com.myrestfulprojects.moviehub.model.CastMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class MovieDto {
    String id;
    String title;
    String fullTitle;
    int year;
    float imDbRating;
    long imDbRatingCount;
    String image;
    String[] crew;
    /*@ManyToMany(cascade = CascadeType.ALL)
    List<CastMember> castMembers;*/
}
