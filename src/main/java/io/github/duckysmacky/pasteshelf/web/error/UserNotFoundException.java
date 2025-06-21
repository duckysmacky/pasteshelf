package io.github.duckysmacky.pasteshelf.web.error;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
