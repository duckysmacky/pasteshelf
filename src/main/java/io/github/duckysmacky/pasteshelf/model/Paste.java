package io.github.duckysmacky.pasteshelf.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pastes")
public class Paste {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true, length = 32)
    private String hash;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String content;

    public Paste () {}

    public Paste(String hash, String username, String content) {
        this.hash = hash;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
