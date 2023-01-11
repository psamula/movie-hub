package com.myrestfulprojects.moviehub.exceptions;

public class InvalidJsonFormatException extends RuntimeException {
    public InvalidJsonFormatException(String message) {
        super(message);
    }
}
