package com.myrestfulprojects.moviehub.model.rating.dto;

import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.MovieRoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CharacterWithRatingDTO {
    private MovieRoleEntity movieRoleEntity;
    private int rating;
}