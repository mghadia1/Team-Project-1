package test.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import validation.PasswordValidator;

/**
 * JUnit tests for PasswordValidator based on README test cases.
 * Tests cover all 12 password validation scenarios from the README.
 */
public class PasswordValidatorTest {
    
    @Test
    public void testP01_ValidPassword() {
        // P-01: Valid password "Aa1!aaaa"
        String result = PasswordValidator.validate("Aa1!aaaa");
        assertEquals("", result, "Valid password should be accepted");
    }
    
    @Test
    public void testP02_MissingUppercase() {
        // P-02: Missing uppercase "aa1!aaaa"
        String result = PasswordValidator.validate("aa1!aaaa");
        assertEquals("Password must include at least one uppercase letter.", result);
    }
    
    @Test
    public void testP03_MissingLowercase() {
        // P-03: Missing lowercase "AA1!AAAA"
        String result = PasswordValidator.validate("AA1!AAAA");
        assertEquals("Password must include at least one lowercase letter.", result);
    }
    
    @Test
    public void testP04_MissingDigit() {
        // P-04: Missing digit "Aa!aaaaa"
        String result = PasswordValidator.validate("Aa!aaaaa");
        assertEquals("Password must include at least one digit.", result);
    }
    
    @Test
    public void testP05_MissingSpecialChar() {
        // Missing special character
        String result = PasswordValidator.validate("Aa1aaaaa");
        assertEquals("Password must include at least one special character (_@#$!).", result);
    }
    
    @Test
    public void testP06_EmptyPassword() {
        // Empty password
        String result = PasswordValidator.validate("");
        assertEquals("Password is required.", result);
    }
    
    @Test
    public void testP07_TooShort() {
        // Too short (7 chars)
        String result = PasswordValidator.validate("Aa1!aaa");
        assertEquals("Password must be at least 8 characters.", result);
    }
    
    @Test
    public void testP08_MinimumLength() {
        // Minimum valid length (8 chars)
        String result = PasswordValidator.validate("Aa1!aaaa");
        assertEquals("", result, "Password with minimum length should be accepted");
    }
    
    @Test
    public void testP09_MaximumLength() {
        // Maximum valid length (64 chars)
        String password = "Aa1!" + "a".repeat(60);  // 64 total chars
        String result = PasswordValidator.validate(password);
        assertEquals("", result, "Password with maximum length should be accepted");
    }
    
    @Test
    public void testP10_AllSpecialChars() {
        // Valid password with all allowed special characters
        String result = PasswordValidator.validate("Aa1_@#$!aaaa");
        assertEquals("", result, "Password with all special chars should be accepted");
    }
    
    @Test
    public void testP11_TooLong() {
        // P-11: Too long (65 chars)
        String password = "Aa1!" + "a".repeat(61);  // 65 total chars
        String result = PasswordValidator.validate(password);
        assertEquals("Password is too long (max 64 characters).", result);
    }
    
    @Test
    public void testP12_WhitespaceTrimming() {
        // Whitespace should be trimmed before validation
        String result = PasswordValidator.validate("  Aa1!aaaa  ");
        assertEquals("", result, "Password with leading/trailing whitespace should be trimmed and accepted");
    }
}
