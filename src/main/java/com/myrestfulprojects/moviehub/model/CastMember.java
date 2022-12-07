package com.myrestfulprojects.moviehub.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CastMember {
    @Id
    long member_id;
    String name;
    int age;
    LocalDateTime dob;
    @ManyToMany(cascade = CascadeType.ALL)
    List<Movie> associatedMovies;

}
