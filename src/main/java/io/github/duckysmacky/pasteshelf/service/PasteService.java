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

    public Paste createPaste(String content) {
        Paste paste = new Paste(content);
        return repository.save(paste);
    }

    public Optional<Paste> getPasteByHash(String hash) {
        return repository.findByHash(hash);
    }
}
