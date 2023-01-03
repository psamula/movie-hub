package com.myrestfulprojects.moviehub.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Setter
@Getter
public abstract class CastMemberShortEntity {
    @Id
    private Long id;
    @NotNull
    private Long movieId;
    @NotNull
    private String imdbId;
    @NotNull
    private String name;
}
