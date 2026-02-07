package guiNewAccount;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;
import validation.UsernameValidator;
import validation.PasswordValidator;

public class ControllerNewAccount {

    private static Database theDatabase = applicationMain.FoundationsMain.database;

    protected static void doCreateUser(Stage theStage, String invitationCode) {
        String username = ViewNewAccount.text_Username.getText();
        String password = ViewNewAccount.text_Password.getText();
        String confirmPassword = ViewNewAccount.text_ConfirmPassword.getText();
        String firstName = ViewNewAccount.text_FirstName.getText();
        String middleName = ViewNewAccount.text_MiddleName.getText();
        String lastName = ViewNewAccount.text_LastName.getText();
        String preferredName = ViewNewAccount.text_PreferredName.getText();
        String email = ViewNewAccount.text_Email.getText();

        // Validate username
        String usernameError = UsernameValidator.validate(username);
        if (!usernameError.isEmpty()) {
            ViewNewAccount.alertError.setContentText(usernameError);
            ViewNewAccount.alertError.showAndWait();
            return;
        }

        // Validate password
        String passwordError = PasswordValidator.validate(password);
        if (!passwordError.isEmpty()) {
            ViewNewAccount.alertError.setContentText(passwordError);
            ViewNewAccount.alertError.showAndWait();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            ViewNewAccount.alertError.setContentText("Passwords do not match.");
            ViewNewAccount.alertError.showAndWait();
            return;
        }

        // Verify invitation code
        String role = theDatabase.getRoleGivenAnInvitationCode(invitationCode);
        if (role.isEmpty()) {
            ViewNewAccount.alertError.setContentText("Invalid invitation code.");
            ViewNewAccount.alertError.showAndWait();
            return;
        }

        try {
            // Create user with appropriate role
            boolean adminRole = role.equals("Admin");
            boolean role1 = role.equals("Role1");
            boolean role2 = role.equals("Role2");
            
            User newUser = new User(username, password, firstName, middleName, lastName, 
                                   preferredName, email, adminRole, role1, role2);
            theDatabase.register(newUser);
            theDatabase.removeInvitationAfterUse(invitationCode);
            
            // Navigate to login
            guiUserLogin.ViewUserLogin.displayUserLogin(theStage);
        } catch (Exception e) {
            ViewNewAccount.alertError.setContentText("Error creating account: " + e.getMessage());
            ViewNewAccount.alertError.showAndWait();
        }
    }
}
