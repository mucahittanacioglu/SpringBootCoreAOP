package com.ts.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleAuthenticationException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("Wrong info");
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleAuthenticationException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed for this resource.");
    }
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleAuthenticationException(ValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}