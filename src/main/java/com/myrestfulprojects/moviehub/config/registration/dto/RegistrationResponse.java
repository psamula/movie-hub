package com.myrestfulprojects.moviehub.config.registration.dto;

import com.myrestfulprojects.moviehub.config.UserEntity;

import java.util.UUID;

public record RegistrationResponse(UUID uuid, String username) {
    public static RegistrationResponse ofUserEntity(UserEntity userEntity) {
        return new RegistrationResponse(userEntity.getId(), userEntity.getUsername());
    }
}
