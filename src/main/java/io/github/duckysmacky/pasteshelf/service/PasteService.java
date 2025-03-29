package io.github.duckysmacky.pasteshelf.service;

import io.github.duckysmacky.pasteshelf.model.Paste;
import io.github.duckysmacky.pasteshelf.repository.PasteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasteService {
    private final PasteRepository repository;

    public PasteService(PasteRepository repository) {
        this.repository = repository;
    }

    public Optional<Paste> getPasteByHash(String hash) {
        return repository.findByHash(hash);
    }
}
