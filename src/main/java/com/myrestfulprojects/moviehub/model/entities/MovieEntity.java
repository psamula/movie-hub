package com.myrestfulprojects.moviehub.model.entities;

import com.myrestfulprojects.moviehub.model.entities.staffMemberShorts.DirectorShortEntity;
import com.myrestfulprojects.moviehub.model.entities.staffMemberShorts.StarShortEntity;
import com.myrestfulprojects.moviehub.model.entities.staffMemberShorts.WriterShortEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "imdbid")
    private String imdbId;
    @NotNull
    @Column(name = "title")
    private String title;
    @Column(name = "fulltitle")
    private String fullTitle;
    @NotNull
    @Column(name = "genres")
    private String genres;
    @NotNull
    @Column(name = "year")
    private int year;
    @Column(name = "plot")
    private String plot;
    @Column(name = "imdbrating")
    private double imDbRating;
    @Column(name = "imdbratingvotes")
    private int imDbRatingVotes;
    @Column(name = "releasedate")
    private LocalDate releaseDate;
    @Column(name = "image")
    private String image;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movieid")
    private List<DirectorShortEntity> directorList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movieid")
    private List<WriterShortEntity> writerList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movieid")
    private List<StarShortEntity> starList;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "movieid")
    private List<MovieRoleEntity> actorList;
}
