package io.github.duckysmacky.pasteshelf.web.dto;

import io.github.duckysmacky.pasteshelf.infrastructure.models.Paste;

import java.time.format.DateTimeFormatter;

public record PasteResponse(
    String hash,
    String owner,
    String createdAt,
    String updatedAt,
    String content
) {
    public static PasteResponse from(Paste paste, DateTimeFormatter formatter) {
        return new PasteResponse(
            paste.getHash(),
            paste.getOwner().getUsername(),
            formatter.format(paste.getCreatedAt()),
            formatter.format(paste.getUpdatedAt()),
            paste.getContent()
        );
    }
}
