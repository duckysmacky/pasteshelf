package io.github.duckysmacky.pasteshelf.web.error;

public class DataValidationFailedException extends RuntimeException {
    public DataValidationFailedException(String message) {
        super(message);
    }
}