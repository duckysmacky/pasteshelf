package io.github.duckysmacky.pasteshelf.web.error;

public class RequestValidationFailedException extends RuntimeException {
    public RequestValidationFailedException(String message) {
        super(message);
    }
}