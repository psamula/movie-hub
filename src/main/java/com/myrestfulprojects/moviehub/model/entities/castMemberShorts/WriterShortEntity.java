package com.myrestfulprojects.moviehub.model.entities.castMemberShorts;

import com.myrestfulprojects.moviehub.model.entities.CastMemberShortEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "writershort")
public class WriterShortEntity extends CastMemberShortEntity {
}
