package io.github.duckysmacky.pasteshelf.service;

import io.github.duckysmacky.pasteshelf.model.Paste;
import io.github.duckysmacky.pasteshelf.repository.PasteRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasteService {
    private final PasteRepository repository;

    public PasteService(PasteRepository repository) {
        this.repository = repository;
    }

    public Paste createPaste(String username, String content) {
        String hash = generateHash(username, content);
        Paste paste = new Paste(hash, username, content);
        return repository.save(paste);
    }

    public Optional<Paste> getPasteByHash(String hash) {
        return repository.findByHash(hash);
    }

    private String generateHash(String username, String content) {
        MessageDigest digest;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm is unavailable!");
        }

        String input = username + content;
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash).toUpperCase().substring(0, 7);
    }
}
