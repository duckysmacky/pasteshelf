package io.github.duckysmacky.pasteshelf.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException exception) {
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<?> handePasteNotFound(PasteNotFoundException exception) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(RequestValidationFailedException.class)
    public ResponseEntity<?> handleRequestValidationFailed(RequestValidationFailedException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    @ExceptionHandler(InvalidHashFormatException.class)
    public ResponseEntity<?> handleInvalidHashFormat(InvalidHashFormatException exception) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponseBody(exception.getMessage()));
    }

    public record ErrorResponseBody(String message) {}
}
