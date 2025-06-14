package io.github.duckysmacky.pasteshelf.web.dto;

public class PasteCreationRequest {
    private String username;
    private String content;

    public PasteCreationRequest(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
