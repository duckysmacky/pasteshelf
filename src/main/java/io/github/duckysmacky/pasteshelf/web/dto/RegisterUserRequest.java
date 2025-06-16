package io.github.duckysmacky.pasteshelf.web.dto;

import io.github.duckysmacky.pasteshelf.web.error.RequestValidationFailedException;

import java.util.regex.Pattern;

public class RegisterUserRequest {
    private String username;
    private String password;
    private String email;

    public RegisterUserRequest(String username, String password, String email) {
        if (username == null || username.isBlank()) {
            throw new RequestValidationFailedException("Username must not be blank");
        } else if (!(username.length() >= 4 && username.length() <= 20)) {
            throw new RequestValidationFailedException("Username must be between 4 and 20 characters");
        }

        if (password == null || password.isBlank()) {
            throw new RequestValidationFailedException("Password must not be blank");
        }

        if (email == null || email.isBlank()) {
            throw new RequestValidationFailedException("Email must not be blank");
        } else if (!validateEmail(email)) {
            throw new RequestValidationFailedException("Invalid email provided");
        }

        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    private static boolean validateEmail(String email) {
        // I know, that verifying email using a regex is generally not the best practice, but for now this will do.
        // Also, I am aware that this regex sucks, will replace in the future
        return Pattern.compile("^[a-zA-Z0-9_.+-]+@([a-z]+)\\.[a-z]{2,3}$")
            .matcher(email)
            .matches();
    }
}
