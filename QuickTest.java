import org.mindrot.jbcrypt.BCrypt;
import validation.PasswordValidator;
import validation.UsernameValidator;

public class QuickTest {
    public static void main(String[] args) {
        System.out.println("=== Testing BCrypt ===");
        String password = "TestPass1!";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println("Generated hash: " + hash);
        System.out.println("Hash length: " + hash.length());
        
        boolean match = BCrypt.checkpw(password, hash);
        System.out.println("Password verification: " + (match ? "PASS" : "FAIL"));
        
        boolean wrongMatch = BCrypt.checkpw("WrongPass1!", hash);
        System.out.println("Wrong password rejection: " + (!wrongMatch ? "PASS" : "FAIL"));
        
        System.out.println("\n=== Testing Password Validator ===");
        String validPass = "Aa1!" + "a".repeat(60);  // 64 chars
        String result1 = PasswordValidator.validate(validPass);
        System.out.println("64-char password validation result: '" + result1 + "'");
        System.out.println("64-char password accepted: " + result1.isEmpty());
        
        String invalidPass = "Aa1!" + "a".repeat(61);  // 65 chars
        String result2 = PasswordValidator.validate(invalidPass);
        System.out.println("65-char password validation result: '" + result2 + "'");
        System.out.println("65-char password rejected: " + !result2.isEmpty());
        
        System.out.println("\n=== Testing Username Validator ===");
        String validUsername = "User_#1";
        String result3 = UsernameValidator.validate(validUsername);
        System.out.println("Valid username (without @) result: '" + result3 + "'");
        System.out.println("Valid username accepted: " + result3.isEmpty());
        
        String invalidUsername = "User@123";
        String result4 = UsernameValidator.validate(invalidUsername);
        System.out.println("Username with @ validation result: '" + result4 + "'");
        System.out.println("Username with @ rejected: " + !result4.isEmpty());
        
        System.out.println("\n=== All Tests Complete ===");
    }
}
