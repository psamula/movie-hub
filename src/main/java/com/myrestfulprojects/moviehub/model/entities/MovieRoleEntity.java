package com.myrestfulprojects.moviehub.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class MovieRoleEntity {
    @Id
    private Long id;
    @NotNull
    private Long movieId;
    @NotNull
    private String imdbId;
    private String image;
    @NotNull
    private String actorName;
    @NotNull
    private String asCharacter;

}
