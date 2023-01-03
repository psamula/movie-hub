package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieFullDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieParticipationDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class CastMemberEntity {
    @Id
    private long id;
    private String imdbId;
    private String name;
    private String knownFor;
    private String image;
    private String summary;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private String awards;
    private double height;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "castMemberId", updatable = false, insertable = false)
    List<MovieParticipationEntity> castMovies;

}
