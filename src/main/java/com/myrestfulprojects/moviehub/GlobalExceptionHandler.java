package com.myrestfulprojects.moviehub;

import com.myrestfulprojects.moviehub.exceptions.UserAlreadyExistsException;
import com.myrestfulprojects.moviehub.exceptions.*;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEntityException(DuplicateEntityException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.CONFLICT;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.NOT_FOUND;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.BAD_REQUEST;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(InvalidJsonFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJsonFormatException(InvalidJsonFormatException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.BAD_GATEWAY;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.CONFLICT;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.UNAUTHORIZED;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.NOT_FOUND;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handlePSQLException(PSQLException ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
    @ExceptionHandler(ObjectWithImdbIdNotFound.class)
    public ResponseEntity<ErrorResponse> handleObjectWithImdbIdNotFound(ObjectWithImdbIdNotFound ex) {
        ErrorResponse error = new ErrorResponse();
        var httpStatus = HttpStatus.NO_CONTENT;
        error.setErrorCode(httpStatus.value());
        error.setMessage(ex.getMessage());
        return new ResponseEntity<>(error, httpStatus);
    }
}