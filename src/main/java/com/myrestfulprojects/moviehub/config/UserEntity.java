package com.myrestfulprojects.moviehub.config;

import javax.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;
@Entity
@Getter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;

}
