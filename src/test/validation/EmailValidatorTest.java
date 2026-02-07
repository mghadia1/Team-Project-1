package test.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import validation.EmailValidator;

/**
 * JUnit tests for EmailValidator.
 * Tests cover basic email validation scenarios.
 */
public class EmailValidatorTest {
    
    @Test
    public void testE01_ValidEmail() {
        // Valid email address
        String result = EmailValidator.validate("user@example.com");
        assertEquals("", result, "Valid email should be accepted");
    }
    
    @Test
    public void testE02_ValidEmailSimple() {
        // Valid simple email
        String result = EmailValidator.validate("a@b.c");
        assertEquals("", result, "Valid simple email should be accepted");
    }
    
    @Test
    public void testE03_EmptyEmail() {
        // Empty email
        String result = EmailValidator.validate("");
        assertEquals("Email is required.", result);
    }
    
    @Test
    public void testE04_NullEmail() {
        // Null email
        String result = EmailValidator.validate(null);
        assertEquals("Email is required.", result);
    }
    
    @Test
    public void testE05_MissingAtSign() {
        // Missing @ symbol
        String result = EmailValidator.validate("userexample.com");
        assertEquals("Invalid email address format.", result);
    }
    
    @Test
    public void testE06_MissingDomain() {
        // Missing domain after @
        String result = EmailValidator.validate("user@");
        assertEquals("Invalid email address format.", result);
    }
    
    @Test
    public void testE07_MissingLocal() {
        // Missing local part before @
        String result = EmailValidator.validate("@example.com");
        assertEquals("Invalid email address format.", result);
    }
    
    @Test
    public void testE08_ContainsSpace() {
        // Contains space
        String result = EmailValidator.validate("user name@example.com");
        assertEquals("Invalid email address format.", result);
    }
    
    @Test
    public void testE09_TooShort() {
        // Too short (3 chars)
        String result = EmailValidator.validate("a@b");
        assertEquals("Email is too short (min 4).", result);
    }
    
    @Test
    public void testE10_TooLong() {
        // Too long (33 chars)
        String result = EmailValidator.validate("verylongemailaddress@example.comm");
        assertEquals("Email address is too long.", result);
    }
    
    @Test
    public void testE11_MultipleAtSigns() {
        // Multiple @ symbols
        String result = EmailValidator.validate("user@@example.com");
        assertEquals("Invalid email address format.", result);
    }
    
    @Test
    public void testE12_NoDotInDomain() {
        // No dot in domain
        String result = EmailValidator.validate("user@examplecom");
        assertEquals("Invalid email address format.", result);
    }
    
    @Test
    public void testE13_WhitespaceTrimming() {
        // Whitespace should be trimmed before validation
        String result = EmailValidator.validate("  user@example.com  ");
        assertEquals("", result, "Email with leading/trailing whitespace should be trimmed and accepted");
    }
}
