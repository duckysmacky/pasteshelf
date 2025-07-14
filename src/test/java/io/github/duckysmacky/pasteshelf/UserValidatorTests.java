package io.github.duckysmacky.pasteshelf;

import io.github.duckysmacky.pasteshelf.web.error.DataValidationFailedException;
import io.github.duckysmacky.pasteshelf.web.util.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTests {
    UserValidator validator;

    @BeforeEach
    public void initializeUserValidator() {
        this.validator = new UserValidator();
    }

    @Test
    public void testUsernameValidation() {
        Executable validateBlankUsername = () -> validator.validateUsername("");
        assertThrows(DataValidationFailedException.class, validateBlankUsername);

        Executable validateShortUsername = () -> validator.validateUsername("usr");
        assertThrows(DataValidationFailedException.class, validateShortUsername);

        Executable validateLongUsername = () -> validator.validateUsername("thisusernameiswaytoolongtobeaccepted");
        assertThrows(DataValidationFailedException.class, validateLongUsername);

        Executable validateValidUsername = () -> validator.validateUsername("User123");
        assertDoesNotThrow(validateValidUsername);
    }

    @Test
    public void testPasswordValidation() {
        Executable validateBlankPassword = () -> validator.validatePassword("");
        assertThrows(DataValidationFailedException.class, validateBlankPassword);

        Executable validateShortPassword = () -> validator.validatePassword("123");
        assertThrows(DataValidationFailedException.class, validateShortPassword);

        Executable validateLongPassword = () -> validator.validatePassword("thisisawaytoolongofapasswordtobeaccepted");
        assertThrows(DataValidationFailedException.class, validateLongPassword);

        Executable validateValidPassword = () -> validator.validatePassword("Password123");
        assertDoesNotThrow(validateValidPassword);
    }

    @Test
    public void testEmailValidation() {
        Executable validateBlankEmail = () -> validator.validateEmail("");
        assertThrows(DataValidationFailedException.class, validateBlankEmail);

        assertThrows(DataValidationFailedException.class, () -> validator.validateEmail("user@email.com.com"));
        assertThrows(DataValidationFailedException.class, () -> validator.validateEmail("user@@email.com"));
        assertThrows(DataValidationFailedException.class, () -> validator.validateEmail("user^@email.com"));
        assertThrows(DataValidationFailedException.class, () -> validator.validateEmail("user@emailcom"));

        Executable validateValidEmail = () -> validator.validateEmail("user@email.com");
        assertDoesNotThrow(validateValidEmail);
    }
}
