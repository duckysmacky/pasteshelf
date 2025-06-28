package io.github.duckysmacky.pasteshelf.web.util;

import io.github.duckysmacky.pasteshelf.web.error.DataValidationFailedException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserValidator {
    // I am aware that this regex sucks, will replace in the future
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_.+-]+@([a-z]+)\\.[a-z]{2,3}$");

    public void validateUsername(String username) {
        if (username == null || username.isBlank())
            throw new DataValidationFailedException("Username must not be blank");

        if (!(username.length() >= 4 && username.length() <= 20))
            throw new DataValidationFailedException("Username must be between 4 and 20 characters");
    }

    public void validatePassword(String password) {
        if (password == null || password.isBlank())
            throw new DataValidationFailedException("Password must not be blank");

        if (!(password.length() >= 4 && password.length() <= 20))
            throw new DataValidationFailedException("Password must be between 4 and 20 characters");
    }

    public void validateEmail(String email) {
        if (email == null || email.isBlank())
            throw new DataValidationFailedException("Email must not be blank");

        // I know that verifying email using a regex is generally not the best practice, but for now this will do
        if (!EMAIL_PATTERN.matcher(email).matches())
            throw new DataValidationFailedException("Invalid email provided");
    }

    public void validateUser(String username, String password, String email) {
        validateUsername(username);
        validatePassword(password);
        validateEmail(email);
    }
}
