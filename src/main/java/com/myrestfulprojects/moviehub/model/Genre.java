package com.myrestfulprojects.moviehub.model;

import lombok.Getter;

@Getter
public enum Genre {
    DRAMA("Drama"),
    COMEDY("Comedy"),
    ACTION("Action"),
    HORROR("Horror"),
    ROMANCE("Romance"),
    SCI_FI("Sci-Fi"),
    ANIMATION("Animation"),
    FANTASY("Fantasy"),
    THRILLER("Thriller"),
    CRIME("Crime"),
    MYSTERY("Mystery"),
    DOCUMENTARY("Documentary"),
    ADVENTURE("Adventure"),
    FAMILY("Family"),
    HISTORY("History"),
    WAR("War"),
    WESTERN("Western"),
    MUSICAL("Musical");

    private String genre;
    Genre(String genre) {
        this.genre = genre;
    }
}
