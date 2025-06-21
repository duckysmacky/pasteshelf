package io.github.duckysmacky.pasteshelf.web.controllers;

import io.github.duckysmacky.pasteshelf.application.services.UserService;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.web.dto.CreatePasteRequest;
import io.github.duckysmacky.pasteshelf.infrastructure.models.Paste;
import io.github.duckysmacky.pasteshelf.application.services.PasteService;
import io.github.duckysmacky.pasteshelf.web.error.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pastes")
public class PasteController {
    private final PasteService pasteService;
    private final UserService userService;

    public PasteController(PasteService pasteService, UserService userService) {
        this.pasteService = pasteService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createPaste(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreatePasteRequest request) {
        User creator = userService.getUserByUsername(userDetails.getUsername())
            .orElseThrow(() -> new UserNotFoundException(String.format("User with username '%s' was not found", userDetails.getUsername())));

        Paste paste = pasteService.createPaste(request.content(), creator);

        return ResponseEntity.ok(paste.asResponseBody());
    }

    @GetMapping("/{hash}")
    public ResponseEntity<?> getPaste(@PathVariable String hash) {
        if (hash.length() != 16) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Invalid hash format. Expected 16 characters, got " + hash.length()));
        }

        Optional<Paste> paste = pasteService.getPasteByHash(hash);

        return paste
            .map(p -> ResponseEntity.ok(p.asResponseBody()))
            .orElse(ResponseEntity.notFound().build());
    }

}
