package com.myrestfulprojects.moviehub.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long movie_id;
    String name;
    LocalDateTime releaseDate;
    int rating;
    @ManyToMany(cascade = CascadeType.ALL)
    List<CastMember> castMembers;

}
