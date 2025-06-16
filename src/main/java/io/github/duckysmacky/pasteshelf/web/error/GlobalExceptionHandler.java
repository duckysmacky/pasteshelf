package io.github.duckysmacky.pasteshelf.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    public record ErrorResponseBody(String message) {}
}
