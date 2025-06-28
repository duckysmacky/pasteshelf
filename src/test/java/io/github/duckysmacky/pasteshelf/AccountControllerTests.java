package io.github.duckysmacky.pasteshelf;

import io.github.duckysmacky.pasteshelf.application.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AccountControllerTests {
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

    private String getUserJSON(String username, String email, String password) {
        StringBuilder json = new StringBuilder("{");

        if (username != null) {
            json.append(String.format("\"username\": \"%s\"", username));
            json.append(',');
        }

        if (email != null) {
            json.append(String.format("\"email\": \"%s\"", email));
            json.append(',');
        }

        if (password != null) {
            json.append(String.format("\"password\": \"%s\"", password));
            json.append(',');
        }
        // remove the ',' symbol
        json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }

    private void createNewUser(String username, String email) throws Exception {
        String userJSON = getUserJSON(username, email, "testpassword");

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON)
            )
            .andExpect(status().isCreated());
    }

    @Test
    public void testRegisterAccountSuccessful() throws Exception {
        String username = "testuser";
        String email = "testmail@email.com";
        String password = "testpassword";
        String userJSON = getUserJSON(username, email, password);

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON)
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value(username))
            .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void testRegisterAccountInvalidDetails() throws Exception {
        String username = "testuser";
        String email = "testmail@email.com";
        String password = "testpassword";

        String invalidUsername = "a";
        String invalidEmail = "aaa@a@n.asvn.";
        String invalidPassword = "b";

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getUserJSON(invalidUsername, email, password))
            )
            .andExpect(status().isBadRequest());

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getUserJSON(username, invalidEmail, password))
            )
            .andExpect(status().isBadRequest());

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getUserJSON(username, email, invalidPassword))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegisterAccountUsernameAlreadyTaken() throws Exception {
        createNewUser("testuser", "othermail@email.com");
        String userJSON = getUserJSON("testuser", "testmail@email.com", "testpassword");

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON)
            )
            .andExpect(status().isConflict());
    }

    @Test
    public void testRegisterAccountEmailAlreadyExists() throws Exception {
        createNewUser("otheruser", "testmail@email.com");
        String userJSON = getUserJSON("testuser", "testmail@email.com", "testpassword");

        mvc.perform(post("/api/account/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON)
            )
            .andExpect(status().isConflict());
    }

    @Test
    public void testGetAccountSuccessful() throws Exception {
        String username = "testuser";
        String email = "testmail@email.com";
        createNewUser(username, email);

        mvc.perform(get("/api/account")
                .with(basicAuth(username, "testpassword"))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(username))
            .andExpect(jsonPath("$.email").value(email));
    }

    @Test
    public void testGetAccountUnauthorized() throws Exception {
        mvc.perform(get("/api/account"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    public void testUpdateAccountSuccessful() throws Exception {
        String oldUsername = "testuser";
        String oldEmail = "testmail@email.com";
        String oldPassword = "testpassword";
        createNewUser(oldUsername, oldEmail);

        String newUsername = "updateduser";
        String newEmail = "updatedmail@email.com";
        String newPassword = "updatedpassword";
        String newUserJSON = getUserJSON(newUsername, newEmail, newPassword);

        mvc.perform(patch("/api/account")
                .with(basicAuth(oldUsername, oldPassword))
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserJSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(newUsername))
            .andExpect(jsonPath("$.email").value(newEmail));

        mvc.perform(get("/api/account")
                .with(basicAuth(newUsername, newPassword))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(newUsername))
            .andExpect(jsonPath("$.email").value(newEmail));
    }

    @Test
    public void testDeleteAccountSuccessful() throws Exception {
        String username = "testuser";
        String email = "testmail@email.com";
        String password = "testpassword";
        createNewUser(username, email);

        mvc.perform(delete("/api/account")
                .with(basicAuth(username, password))
            )
            .andExpect(status().isOk());

        mvc.perform(get("/api/account")
                .with(basicAuth(username, password))
            )
            .andExpect(status().isUnauthorized());
    }
}
