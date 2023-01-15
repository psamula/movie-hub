package com.myrestfulprojects.moviehub.model.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Setter
@Getter
@Table(name = "staffmember")
public class StaffMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "movieid")
    private Long movieId;
    @NotNull
    @Column(name = "imdbid")
    private String imdbId;
    @NotNull
    @Column(name = "name")
    private String name;
    @Column(name = "department")
    private String department;
}
