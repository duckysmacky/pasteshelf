package io.github.duckysmacky.pasteshelf.web.controllers;

import io.github.duckysmacky.pasteshelf.application.services.UserService;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.web.dto.CreatePasteRequest;
import io.github.duckysmacky.pasteshelf.infrastructure.models.Paste;
import io.github.duckysmacky.pasteshelf.application.services.PasteService;
import io.github.duckysmacky.pasteshelf.web.dto.UpdatePasteRequest;
import io.github.duckysmacky.pasteshelf.web.error.InvalidHashFormatException;
import io.github.duckysmacky.pasteshelf.web.error.UserNotFoundException;
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
            .orElseThrow(UserNotFoundException::new);

        Paste paste = pasteService.createPaste(request.content(), creator);

        return ResponseEntity.ok(paste.asResponseBody());
    }

    @GetMapping("/{hash}")
    public ResponseEntity<?> getPaste(@PathVariable String hash) {
        if (hash.length() != 16) {
            throw new InvalidHashFormatException(hash.length());
        }

        Optional<Paste> paste = pasteService.getPasteByHash(hash);

        return paste
            .map(p -> ResponseEntity.ok(p.asResponseBody()))
            .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{hash}")
    public ResponseEntity<?> updatePaste(@PathVariable String hash, @AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdatePasteRequest request) {
        if (hash.length() != 16) {
            throw new InvalidHashFormatException(hash.length());
        }

        User user = userService.getUserByUsername(userDetails.getUsername())
            .orElseThrow(UserNotFoundException::new);

        Paste updatedPaste = pasteService.updatePaste(hash, request.content(), user);

        return ResponseEntity.ok(updatedPaste.asResponseBody());
    }

    @DeleteMapping("/{hash}")
    public ResponseEntity<?> deletePaste(@PathVariable String hash, @AuthenticationPrincipal UserDetails userDetails) {
        if (hash.length() != 16) {
            throw new InvalidHashFormatException(hash.length());
        }

        User user = userService.getUserByUsername(userDetails.getUsername())
                .orElseThrow(UserNotFoundException::new);

        pasteService.deletePaste(hash, user);

        return ResponseEntity.ok().build();
    }
}
