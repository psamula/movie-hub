package com.myrestfulprojects.moviehub.model.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MovieParticipationEntity {
    @Id
    private Long id;
    private Long castMemberId;
    private String imdbId;
    private String role;
    private String movieTitle;
    private int year;
    private String description;
}
