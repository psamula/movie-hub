package com.myrestfulprojects.moviehub.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
