package io.github.duckysmacky.pasteshelf.application.services;

import io.github.duckysmacky.pasteshelf.infrastructure.models.Paste;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.infrastructure.repositories.PasteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasteService {
    private final PasteRepository repository;

    public PasteService(PasteRepository repository) {
        this.repository = repository;
    }

    public Paste createPaste(String content, User creator) {
        Paste paste = new Paste(creator, content);

        return repository.save(paste);
    }

    public Optional<Paste> getPasteByHash(String hash) {
        return repository.findByHash(hash);
    }

}
