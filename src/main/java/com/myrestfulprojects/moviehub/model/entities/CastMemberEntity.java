package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieFullDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieParticipationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class CastMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "imdbid")
    private String imdbId;
    @Column(name = "name")
    private String name;
    @Column(name = "knownfor")
    private String knownFor;
    @Column(name = "image")
    private String image;
    @Column(name = "summary")
    private String summary;
    @Column(name = "birthdate")
    private LocalDate birthDate;
    @Column(name = "deathdate")
    private LocalDate deathDate;
    @Column(name = "awards")
    private String awards;
    @Column(name = "height")
    private double height;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "castmemberid", updatable = false, insertable = false)
    List<MovieParticipationEntity> castMovies;

}
