package com.myrestfulprojects.moviehub.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "movierole")
public class MovieRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
//    @NotNull
    @Column(name = "movieid")
    private Long movieId;
    @NotNull
    @Column(name = "imdbid")
    private String imdbId;
    @Column(name = "image")
    private String image;
    @NotNull
    @Column(name = "actorname")
    private String actorName;
    @Column(name = "ascharacter")
    private String asCharacter;
}
