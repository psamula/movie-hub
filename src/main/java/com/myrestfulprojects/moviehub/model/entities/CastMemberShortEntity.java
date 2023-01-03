package com.myrestfulprojects.moviehub.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Setter
@Getter
public abstract class CastMemberShortEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "movieid")
    private Long movieId;
    @NotNull
    @Column(name = "imdbid")
    private String imdbId;
    @NotNull
    @Column(name = "name")
    private String name;
}
