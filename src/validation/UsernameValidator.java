package validation;

public class UsernameValidator {
    
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 32;
    private static final String ALLOWED_SPECIAL_CHARS = "_#$!";
    
    /**
     * Validates a username according to the rules:
     * - Required (not null or empty)
     * - Must be 6-32 characters
     * - Must start with a letter
     * - No spaces allowed
     * - Allowed characters: A-Z a-z 0-9 _ # $ !
     * 
     * @param username The username to validate (will be trimmed)
     * @return Empty string if valid, error message if invalid
     */
    public static String validate(String username) {
        // Check if null or empty
        if (username == null || username.trim().isEmpty()) {
            return "Username is required.";
        }
        
        // Trim whitespace
        username = username.trim();
        
        // Check for spaces
        if (username.contains(" ")) {
            return "Username cannot contain spaces.";
        }
        
        // Check minimum length
        if (username.length() < MIN_LENGTH) {
            return "Username must be at least 6 characters.";
        }
        
        // Check maximum length
        if (username.length() > MAX_LENGTH) {
            return "Username is too long (max 32 characters).";
        }
        
        // Check if starts with a letter
        char firstChar = username.charAt(0);
        if (!Character.isLetter(firstChar)) {
            return "Username must start with a letter.";
        }
        
        // Check for valid characters only
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && ALLOWED_SPECIAL_CHARS.indexOf(c) == -1) {
                return "Username can only contain letters, numbers, and special characters.";
            }
        }
        
        // All validations passed
        return "";
    }
}
