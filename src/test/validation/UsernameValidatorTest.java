package test.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import validation.UsernameValidator;

/**
 * JUnit tests for UsernameValidator based on README test cases.
 * Tests cover all 12 username validation scenarios from the README.
 */
public class UsernameValidatorTest {
    
    @Test
    public void testU01_ValidUsername() {
        // U-01: Valid username "Meet12"
        String result = UsernameValidator.validate("Meet12");
        assertEquals("", result, "Valid username should be accepted");
    }
    
    @Test
    public void testU02_ValidUsernameWithSpecialChars() {
        // Valid username with special characters (@ removed)
        String result = UsernameValidator.validate("User_#1");
        assertEquals("", result, "Valid username with special chars should be accepted");
    }
    
    @Test
    public void testU03_ValidUsernameAllSpecialChars() {
        // Valid username with all allowed special characters (without @)
        String result = UsernameValidator.validate("User_#$!");
        assertEquals("", result, "Valid username with all special chars should be accepted");
    }
    
    @Test
    public void testU04_StartsWithDigit() {
        // U-04: Starts with digit "1Meet12"
        String result = UsernameValidator.validate("1Meet12");
        assertEquals("Username must start with a letter.", result);
    }
    
    @Test
    public void testU05_ContainsSpace() {
        // U-05: Contains space "Meet 12"
        String result = UsernameValidator.validate("Meet 12");
        assertEquals("Username cannot contain spaces.", result);
    }
    
    @Test
    public void testU06_EmptyUsername() {
        // Empty username
        String result = UsernameValidator.validate("");
        assertEquals("Username is required.", result);
    }
    
    @Test
    public void testU07_TooShort() {
        // U-07: Too short (5 chars) "Meet1"
        String result = UsernameValidator.validate("Meet1");
        assertEquals("Username must be at least 6 characters.", result);
    }
    
    @Test
    public void testU08_MinimumLength() {
        // Minimum valid length (6 chars)
        String result = UsernameValidator.validate("User12");
        assertEquals("", result, "Username with minimum length should be accepted");
    }
    
    @Test
    public void testU09_MaximumLength() {
        // Maximum valid length (32 chars)
        String username = "A" + "1".repeat(31); // A followed by 31 ones
        String result = UsernameValidator.validate(username);
        assertEquals("", result, "Username with maximum length should be accepted");
    }
    
    @Test
    public void testU10_TooLong() {
        // U-10: Too long (33 chars)
        String username = "A" + "1".repeat(32); // A followed by 32 ones
        String result = UsernameValidator.validate(username);
        assertEquals("Username is too long (max 32 characters).", result);
    }
    
    @Test
    public void testU11_InvalidSpecialChar() {
        // Invalid special character (%)
        String result = UsernameValidator.validate("User%12");
        assertEquals("Username can only contain letters, numbers, and special characters.", result);
    }
    
    @Test
    public void testU12_WhitespaceTrimming() {
        // Whitespace should be trimmed before validation
        String result = UsernameValidator.validate("  Meet12  ");
        assertEquals("", result, "Username with leading/trailing whitespace should be trimmed and accepted");
    }
    
    @Test
    public void testU13_AtSignRejected() {
        // @ character should now be rejected
        String result = UsernameValidator.validate("User@123");
        assertEquals("Username can only contain letters, numbers, and special characters.", result);
    }
}
