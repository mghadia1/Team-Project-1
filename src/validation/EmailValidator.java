package validation;

public class EmailValidator {
    
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 32;
    
    /**
     * Validates an email address according to the rules:
     * - Required (not null or empty)
     * - Must be 4-32 characters
     * - Must follow local@domain format
     * - No spaces allowed
     * 
     * @param email The email address to validate (will be trimmed)
     * @return Empty string if valid, error message if invalid
     */
    public static String validate(String email) {
        // Check if null or empty
        if (email == null || email.trim().isEmpty()) {
            return "Email is required.";
        }
        
        // Trim whitespace
        email = email.trim();
        
        // Check minimum length
        if (email.length() < MIN_LENGTH) {
            return "Email is too short (min 4).";
        }
        
        // Check maximum length
        if (email.length() > MAX_LENGTH) {
            return "Email address is too long.";
        }
        
        // Check for spaces
        if (email.contains(" ")) {
            return "Invalid email address format.";
        }
        
        // Check for @ symbol and basic format
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            return "Invalid email address format.";
        }
        
        // Check that there's only one @
        if (email.indexOf('@', atIndex + 1) != -1) {
            return "Invalid email address format.";
        }
        
        // Check that there's something after @
        String domain = email.substring(atIndex + 1);
        if (domain.isEmpty() || !domain.contains(".")) {
            return "Invalid email address format.";
        }
        
        // All validations passed
        return "";
    }
}
