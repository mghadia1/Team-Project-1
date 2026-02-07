package validation;

public class NameValidator {
    
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 32;
    
    /**
     * Validates a name (first, middle, last, preferred) according to the rules:
     * - Required (not null or empty)
     * - Must be 4-32 characters
     * - Allowed characters: A-Z a-z - ' (letters, hyphens, apostrophes)
     * - No digits or special symbols
     * 
     * @param name The name to validate (will be trimmed)
     * @return Empty string if valid, error message if invalid
     */
    public static String validate(String name) {
        // Check if null or empty
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }
        
        // Trim whitespace
        name = name.trim();
        
        // Check minimum length
        if (name.length() < MIN_LENGTH) {
            return "Name is too short (min 4).";
        }
        
        // Check maximum length
        if (name.length() > MAX_LENGTH) {
            return "Name is too long.";
        }
        
        // Check for valid characters only (letters, hyphen, apostrophe)
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && c != '-' && c != '\'') {
                return "Name must contain letters only.";
            }
        }
        
        // All validations passed
        return "";
    }
}
