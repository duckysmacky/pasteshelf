package io.github.duckysmacky.pasteshelf.web.dto;

import io.github.duckysmacky.pasteshelf.infrastructure.models.User;

import java.time.format.DateTimeFormatter;

public record UserResponse(
    String createdAt,
    String updatedAt,
    String username,
    String email
) {
    public static UserResponse from(User user, DateTimeFormatter formatter) {
        return new UserResponse(
            formatter.format(user.getCreatedAt()),
            formatter.format(user.getUpdatedAt()),
            user.getUsername(),
            user.getEmail()
        );
    }
}
