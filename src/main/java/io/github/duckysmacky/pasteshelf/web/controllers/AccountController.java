package io.github.duckysmacky.pasteshelf.web.controllers;

import io.github.duckysmacky.pasteshelf.application.services.UserService;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.web.dto.RegisterUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterUserRequest request) {
        User user = userService.registerUser(request.getUsername(), request.getPassword(), request.getEmail());
        // TODO: make proper json response
        return ResponseEntity.ok("User " + user.getUsername() + " was successfully registered");
    }



}
