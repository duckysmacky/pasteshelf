package io.github.duckysmacky.pasteshelf;

import com.jayway.jsonpath.JsonPath;
import io.github.duckysmacky.pasteshelf.application.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class PasteControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private RequestPostProcessor basicAuth(String username, String password) {
        return request -> {
            String credentials = username + ":" + password;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());
            request.addHeader("Authorization", "Basic " + encodedCredentials);
            return request;
        };
    }

    private String getContentJSON(String content) {
        return "{\"content\": \"" + content + "\"}";
    }

    /// Creates a new Paste as the 'testuser' user
    private String createNewPaste(String content) throws Exception {
        MvcResult result = mvc.perform(post("/api/pastes")
                .with(basicAuth("testuser", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentJSON(content))
            )
            .andExpect(status().isCreated())
            .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        return JsonPath.read(responseJson, "$.hash");
    }

    @BeforeEach
    public void setupTestUser() {
        userService.registerUser("testuser", "password", "test@email.com");
    }

    @Test
    public void testGetPasteSuccessful() throws Exception {
        String content = "This is a test paste";
        String hash = createNewPaste(content);

        mvc.perform(get("/api/pastes/{hash}", hash))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    public void testGetPasteNotFound() throws Exception {
        mvc.perform(get("/api/pastes/noneexistenthash"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testGetPasteInvalidHash() throws Exception {
        mvc.perform(get("/api/pastes/tooshort"))
            .andExpect(status().isBadRequest());

        mvc.perform(get("/api/pastes/tooooooooooooooooooooooooolong"))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePasteSuccessful() throws Exception {
        String content = "This is a test paste";

        mvc.perform(post("/api/pastes")
                .with(basicAuth("testuser", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentJSON(content))
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    public void testCreatePasteUnauthorized() throws Exception {
        String content = "This is a test paste";

        mvc.perform(post("/api/pastes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentJSON(content))
            )
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdatePasteSuccessful() throws Exception {
        String hash = createNewPaste("This is the old paste content");
        String updatedContent = "This is some new paste content";

        mvc.perform(patch("/api/pastes/{hash}", hash)
                .with(basicAuth("testuser", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentJSON(updatedContent))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value(updatedContent));
    }

    @Test
    public void testUpdatePasteForbidden() throws Exception {
        String hash = createNewPaste("This is the old paste content");
        String updatedContent = "This is some new paste content";

        userService.registerUser("otheruser", "password", "other@email.com");

        mvc.perform(patch("/api/pastes/{hash}", hash)
                .with(basicAuth("otheruser", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentJSON(updatedContent))
            )
            .andExpect(status().isForbidden());
    }

    @Test
    public void testUpdatePasteNotFound() throws Exception {
        String hash = createNewPaste("This is the old paste content");
        String updatedContent = "This is some new paste content";

        mvc.perform(patch("/api/pastes/noneexistenthash")
                .with(basicAuth("testuser", "password"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentJSON(updatedContent))
            )
            .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePasteSuccessful() throws Exception {
        String hash = createNewPaste("This paste will be deleted");

        mvc.perform(delete("/api/pastes/{hash}", hash)
                .with(basicAuth("testuser", "password"))
            )
            .andExpect(status().isOk());

        mvc.perform(get("/api/pastes/{hash}", hash))
            .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePasteForbidden() throws Exception {
        String hash = createNewPaste("This paste will be deleted");

        userService.registerUser("otheruser", "password", "other@email.com");

        mvc.perform(delete("/api/pastes/{hash}", hash)
                .with(basicAuth("otheruser", "password"))
            )
            .andExpect(status().isForbidden());
    }

    @Test
    public void testDeletePasteNotFound() throws Exception {
        String hash = createNewPaste("This paste will be deleted");

        mvc.perform(delete("/api/pastes/noneexistenthash")
                .with(basicAuth("testuser", "password"))
            )
            .andExpect(status().isNotFound());
    }
}
