package validation;

public class PasswordValidator {
    
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 64;
    private static final String ALLOWED_SPECIAL_CHARS = "_@#$!";
    
    /**
     * Validates a password according to the rules:
     * - Required (not null or empty)
     * - Must be 8-64 characters
     * - Must include at least one uppercase letter
     * - Must include at least one lowercase letter
     * - Must include at least one digit
     * - Must include at least one special character (_@#$!)
     * - Allowed characters: A-Z a-z 0-9 _ @ # $ !
     * 
     * @param password The password to validate (will be trimmed)
     * @return Empty string if valid, error message if invalid
     */
    public static String validate(String password) {
        // Check if null or empty
        if (password == null || password.trim().isEmpty()) {
            return "Password is required.";
        }
        
        // Trim whitespace
        password = password.trim();
        
        // Check minimum length
        if (password.length() < MIN_LENGTH) {
            return "Password must be at least 8 characters.";
        }
        
        // Check maximum length
        if (password.length() > MAX_LENGTH) {
            return "Password is too long (max 64 characters).";
        }
        
        // Flags for password requirements
        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        
        // Check each character
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (ALLOWED_SPECIAL_CHARS.indexOf(c) != -1) {
                hasSpecialChar = true;
            }
        }
        
        // Return specific error messages for missing requirements
        if (!hasUppercase) {
            return "Password must include at least one uppercase letter.";
        }
        if (!hasLowercase) {
            return "Password must include at least one lowercase letter.";
        }
        if (!hasDigit) {
            return "Password must include at least one digit.";
        }
        if (!hasSpecialChar) {
            return "Password must include at least one special character (_@#$!).";
        }
        
        // All validations passed
        return "";
    }
}
