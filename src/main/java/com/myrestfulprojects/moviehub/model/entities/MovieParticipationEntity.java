package com.myrestfulprojects.moviehub.model.entities;

import javax.persistence.*;

@Entity
public class MovieParticipationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "castmemberid")
    private Long castMemberId;
    @Column(name = "imdbid")
    private String imdbId;
    @Column(name = "role")
    private String role;
    @Column(name = "movietitle")
    private String movieTitle;
    @Column(name = "year")
    private int year;
    @Column(name = "description")
    private String description;
}
