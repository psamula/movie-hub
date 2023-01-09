package com.myrestfulprojects.moviehub.model.entities.castMemberShorts;

import com.myrestfulprojects.moviehub.model.entities.CastMemberShortEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "directorshort")
public class DirectorShortEntity extends CastMemberShortEntity {
}
