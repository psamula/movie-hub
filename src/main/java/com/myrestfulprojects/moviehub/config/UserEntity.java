package com.myrestfulprojects.moviehub.config;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.util.UUID;
@Entity
@Getter
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;

}
