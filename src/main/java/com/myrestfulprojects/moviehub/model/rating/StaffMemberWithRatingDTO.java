package com.myrestfulprojects.moviehub.model.rating;

import com.myrestfulprojects.moviehub.model.entities.MovieEntity;
import com.myrestfulprojects.moviehub.model.entities.StaffMemberEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StaffMemberWithRatingDTO {
    private StaffMemberEntity staffMemberEntity;
    private int rating;
}