package io.github.duckysmacky.pasteshelf.controller;

import io.github.duckysmacky.pasteshelf.model.Paste;
import io.github.duckysmacky.pasteshelf.service.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pastes")
public class PasteController {
    private final PasteService pasteService;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @GetMapping
    public String getStatus() {
        return "I am online!";
    }

    @PostMapping
    public ResponseEntity<Paste> createPaste(@RequestBody String content) {
        Paste paste = pasteService.createPaste(content);
        return ResponseEntity.ok(paste);
    }

    @GetMapping("/{hash}")
    public ResponseEntity<?> getPaste(@PathVariable String hash) {
        if (hash.length() < 7) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Invalid hash format. Expected at least 7 characters."));
        }

        Optional<Paste> paste = pasteService.getPasteByHash(hash);
        return paste
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
