package io.github.duckysmacky.pasteshelf.application.services;

import io.github.duckysmacky.pasteshelf.infrastructure.models.Paste;
import io.github.duckysmacky.pasteshelf.infrastructure.models.User;
import io.github.duckysmacky.pasteshelf.infrastructure.repositories.PasteRepository;
import io.github.duckysmacky.pasteshelf.web.error.AccessDeniedException;
import io.github.duckysmacky.pasteshelf.web.error.PasteNotFoundException;
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

    public Paste updatePaste(String hash, String newContent, User user) {
        Paste paste = repository.findByHash(hash)
            .orElseThrow(PasteNotFoundException::new);

        if (!paste.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the owner of this paste");
        }

        paste.updateContent(newContent);

        return repository.save(paste);
    }

    public void deletePaste(String hash, User user) {
        Paste paste = repository.findByHash(hash)
            .orElseThrow(PasteNotFoundException::new);

        if (!paste.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not the owner of this paste");
        }

        repository.delete(paste);
    }

    public Optional<Paste> getPasteByHash(String hash) {
        return repository.findByHash(hash);
    }

}
