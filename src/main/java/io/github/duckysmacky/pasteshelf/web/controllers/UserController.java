package io.github.duckysmacky.pasteshelf.web.controllers;

import io.github.duckysmacky.pasteshelf.application.services.UserService;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.web.dto.PasteResponse;
import io.github.duckysmacky.pasteshelf.web.dto.UserResponse;
import io.github.duckysmacky.pasteshelf.web.error.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final DateTimeFormatter timeFormatter;

    public UserController(UserService userService, DateTimeFormatter timeFormatter) {
        this.userService = userService;
        this.timeFormatter = timeFormatter;
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}/pastes")
    public ResponseEntity<?> getUserPastes(@PathVariable String username) {
        User user = userService.getUserByUsername(username)
            .orElseThrow(UserNotFoundException::new);

        List<PasteResponse> pasteResponses = user.getPastes().stream()
            .map(paste -> PasteResponse.from(paste, timeFormatter))
            .toList();

        return ResponseEntity.ok(pasteResponses);
    }
}
