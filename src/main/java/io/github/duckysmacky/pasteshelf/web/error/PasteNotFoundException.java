package io.github.duckysmacky.pasteshelf.web.error;

public class PasteNotFoundException extends RuntimeException {
    public PasteNotFoundException() {
        super("Paste not found");
    }

    public PasteNotFoundException(String message) {
        super(message);
    }
}
