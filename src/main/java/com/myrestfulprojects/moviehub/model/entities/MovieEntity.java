package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.model.entities.castMemberShorts.DirectorShortEntity;
import com.myrestfulprojects.moviehub.model.entities.castMemberShorts.StarShortEntity;
import com.myrestfulprojects.moviehub.model.entities.castMemberShorts.WriterShortEntity;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.CastMemberShortDto;
import com.myrestfulprojects.moviehub.webclient.imdbApi.dto.MovieRoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
public class MovieEntity {

    @Id
    private Long id;
    @NotNull
    private String imdbId;
    @NotNull
    private String title;
    private String fullTitle;
    @NotNull
    private String genres;
    @NotNull
    private int year;
    private String plot;
    private double imDbRating;
    private int imDbRatingVotes;
    private LocalDate releaseDate;
    private String image;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movieId")
    private List<DirectorShortEntity> directorList;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movieId")
    private List<WriterShortEntity> writerList;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movieId")
    private List<StarShortEntity> starList;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movieId")
    private List<MovieRoleEntity> actorList;
}
