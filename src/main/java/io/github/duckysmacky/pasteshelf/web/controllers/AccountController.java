package io.github.duckysmacky.pasteshelf.web.controllers;

import io.github.duckysmacky.pasteshelf.application.services.UserService;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.web.dto.RegisterUserRequest;
import io.github.duckysmacky.pasteshelf.web.error.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAccountDetails(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername())
            .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(user.asResponseBody());
    }

    @PatchMapping
    public ResponseEntity<?> updateAccountDetails(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Map<String, String> request) {
        String newUsername = request.get("username");
        String newPassword = request.get("password");
        String newEmail = request.get("email");

        User updatedUser = userService.updateUser(userDetails.getUsername(), newUsername, newPassword, newEmail);

        return ResponseEntity.ok(updatedUser.asResponseBody());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteUser(userDetails.getUsername());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterUserRequest request) {
        User user = userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());

        return ResponseEntity.ok(Map.of(
            "username", user.getUsername(),
            "email", user.getEmail()
        ));
    }

}
