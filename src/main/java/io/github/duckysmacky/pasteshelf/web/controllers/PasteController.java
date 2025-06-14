package io.github.duckysmacky.pasteshelf.web.controllers;

import io.github.duckysmacky.pasteshelf.web.dto.PasteCreationRequest;
import io.github.duckysmacky.pasteshelf.infrastructure.models.Paste;
import io.github.duckysmacky.pasteshelf.application.services.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pastes")
public class PasteController {
    private final PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @PostMapping
    public ResponseEntity<Paste> createPaste(@RequestBody PasteCreationRequest request) {
        Paste paste = pasteService.createPaste(request.getUsername(), request.getContent());
        return ResponseEntity.ok(paste);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<?> getPaste(@PathVariable String hash) {
        if (hash.length() != 32) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Invalid hash format. Expected 32 characters, got " + hash.length()));
        }

        Optional<Paste> paste = pasteService.getPasteByHash(hash);
        return paste
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

}
