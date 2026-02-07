package guiFirstAdmin;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;
import validation.UsernameValidator;
import validation.PasswordValidator;

public class ControllerFirstAdmin {

    private static Database theDatabase = applicationMain.FoundationsMain.database;
    private static Stage theStage;

    protected static void doSetupAdmin(Stage ts) {
        theStage = ts;
        String username = ViewFirstAdmin.text_Username.getText();
        String password = ViewFirstAdmin.text_Password.getText();
        String confirmPassword = ViewFirstAdmin.text_ConfirmPassword.getText();

        // Validate username
        String usernameError = UsernameValidator.validate(username);
        if (!usernameError.isEmpty()) {
            ViewFirstAdmin.label_PasswordsDoNotMatch.setText(usernameError);
            return;
        }

        // Validate password
        String passwordError = PasswordValidator.validate(password);
        if (!passwordError.isEmpty()) {
            ViewFirstAdmin.label_PasswordsDoNotMatch.setText(passwordError);
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            ViewFirstAdmin.label_PasswordsDoNotMatch.setText("Passwords do not match.");
            return;
        }

        try {
            // Create first admin user
            User adminUser = new User(username, password, "", "", "", "", "", true, false, false);
            theDatabase.register(adminUser);
            
            // Navigate to login page
            guiUserLogin.ViewUserLogin.displayUserLogin(theStage);
        } catch (Exception e) {
            ViewFirstAdmin.label_PasswordsDoNotMatch.setText("Error creating admin account: " + e.getMessage());
        }
    }
}
