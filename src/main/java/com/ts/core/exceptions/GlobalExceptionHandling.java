package com.ts.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleAuthorizationException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed for this resource.");
    }
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseEntity<?> handleAuthenticationException(UnauthenticatedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authenticated for this resource.");
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleAuthenticationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}