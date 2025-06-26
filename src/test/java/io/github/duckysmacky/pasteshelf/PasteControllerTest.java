package io.github.duckysmacky.pasteshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PasteControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testInvalidHashFormat() throws Exception {
        mvc.perform(get("/api/pastes/tooshort"))
            .andExpect(status().isBadRequest());

        mvc.perform(get("/api/pastes/tooooooooooooooooooooooooolong"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testPasteNotFound() throws Exception {
        mvc.perform(get("/api/pastes/noneexistenthash"))
            .andExpect(status().isNotFound());
    }
}
