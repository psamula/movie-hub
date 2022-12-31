package com.myrestfulprojects.moviehub.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Movie {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    long id;
    String imdb_id;
    String name;
    LocalDateTime releaseDate;
    int rating;
//    @ManyToMany(cascade = CascadeType.ALL)
//    List<CastMember> castMembers;

}
