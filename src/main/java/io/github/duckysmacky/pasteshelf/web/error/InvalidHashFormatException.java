package io.github.duckysmacky.pasteshelf.web.error;

public class InvalidHashFormatException extends RuntimeException {
    public InvalidHashFormatException(int length) {
        super("Invalid hash format provided. Expected 16 characters, got " + length);
    }
}
