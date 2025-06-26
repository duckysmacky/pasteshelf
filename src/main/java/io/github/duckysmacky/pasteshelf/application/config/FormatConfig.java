package io.github.duckysmacky.pasteshelf.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class FormatConfig {
    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    }
}
