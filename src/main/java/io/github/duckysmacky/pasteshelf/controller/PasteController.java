package io.github.duckysmacky.pasteshelf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pastes")
public class PasteController {
    @GetMapping
    public String getStatus() {
        return "I am online!";
    }
}
