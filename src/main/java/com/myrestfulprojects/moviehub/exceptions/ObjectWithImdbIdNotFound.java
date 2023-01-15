package com.myrestfulprojects.moviehub.exceptions;

import lombok.Getter;

public class ObjectWithImdbIdNotFound extends RuntimeException {
    public ObjectWithImdbIdNotFound(String message) {
        super(message);
    }
    public String getMessage() {
        return super.getMessage();
    }
}
