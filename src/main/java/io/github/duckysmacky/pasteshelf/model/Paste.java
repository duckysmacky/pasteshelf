package io.github.duckysmacky.pasteshelf.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pastes")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String hash;
    @Column(nullable = false)
    private String content;

    public Paste () {}

    public Paste(String hash, String content) {
        this.hash = hash;
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public String getHash() {
        return hash;
    }

    public String getContent() {
        return content;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
