package guiUserLogin;

import database.Database;
import entityClasses.User;
import javafx.stage.Stage;
import validation.UsernameValidator;
import validation.PasswordValidator;

public class ControllerUserLogin {

    public ControllerUserLogin() {
    }

    private static Database theDatabase = applicationMain.FoundationsMain.database;
    private static Stage theStage;    

    protected static void doLogin(Stage ts) {
        theStage = ts;
        String username = ViewUserLogin.text_Username.getText();
        String password = ViewUserLogin.text_Password.getText();
        boolean loginResult = false;
        
        // Validate username
        String usernameError = UsernameValidator.validate(username);
        if (!usernameError.isEmpty()) {
            ViewUserLogin.alertUsernamePasswordError.setContentText(usernameError);
            ViewUserLogin.alertUsernamePasswordError.showAndWait();
            return;
        }
        
        // Validate password
        String passwordError = PasswordValidator.validate(password);
        if (!passwordError.isEmpty()) {
            ViewUserLogin.alertUsernamePasswordError.setContentText(passwordError);
            ViewUserLogin.alertUsernamePasswordError.showAndWait();
            return;
        }
        
        if (theDatabase.getUserAccountDetails(username) == false) {
            ViewUserLogin.alertUsernamePasswordError.setContentText(
                    "Incorrect username/password. Try again!");
            ViewUserLogin.alertUsernamePasswordError.showAndWait();
            return;
        }
        
        String actualPassword = theDatabase.getCurrentPassword();
        
        if (password.compareTo(actualPassword) != 0) {
            ViewUserLogin.alertUsernamePasswordError.setContentText(
                    "Incorrect username/password. Try again!");
            ViewUserLogin.alertUsernamePasswordError.showAndWait();
            return;
        }
        
        User user = new User(username, password, theDatabase.getCurrentFirstName(), 
                theDatabase.getCurrentMiddleName(), theDatabase.getCurrentLastName(), 
                theDatabase.getCurrentPreferredFirstName(), theDatabase.getCurrentEmailAddress(), 
                theDatabase.getCurrentAdminRole(), 
                theDatabase.getCurrentNewRole1(), theDatabase.getCurrentNewRole2());
        
        int numberOfRoles = theDatabase.getNumberOfRoles(user);        
        if (numberOfRoles == 1) {
            if (user.getAdminRole()) {
                loginResult = theDatabase.loginAdmin(user);
                if (loginResult) {
                    guiAdminHome.ViewAdminHome.displayAdminHome(theStage, user);
                }
            } else if (user.getNewRole1()) {
                loginResult = theDatabase.loginRole1(user);
                if (loginResult) {
                    guiRole1.ViewRole1Home.displayRole1Home(theStage, user);
                }
            } else if (user.getNewRole2()) {
                loginResult = theDatabase.loginRole2(user);
                if (loginResult) {
                    guiRole2.ViewRole2Home.displayRole2Home(theStage, user);
                }
            } else {
                System.out.println("***** UserLogin goToUserHome request has an invalid role");
            }
        } else if (numberOfRoles > 1) {
            guiMultipleRoleDispatch.ViewMultipleRoleDispatch.
                displayMultipleRoleDispatch(theStage, user);
        }
    }
    
    protected static void doSetupAccount(Stage theStage, String invitationCode) {
        guiNewAccount.ViewNewAccount.displayNewAccount(theStage, invitationCode);
    }

    protected static void performQuit() {
        System.out.println("Perform Quit");
        System.exit(0);
    }    
}
