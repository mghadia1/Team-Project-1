package guiUserUpdate;

import database.Database;
import entityClasses.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import validation.NameValidator;
import validation.EmailValidator;

public class ControllerUserUpdate {

    private static Database theDatabase = applicationMain.FoundationsMain.database;

    protected static void doSave(Stage theStage, User currentUser) {
        String firstName = ViewUserUpdate.text_FirstName.getText();
        String middleName = ViewUserUpdate.text_MiddleName.getText();
        String lastName = ViewUserUpdate.text_LastName.getText();
        String preferredName = ViewUserUpdate.text_PreferredName.getText();
        String email = ViewUserUpdate.text_Email.getText();
        
        // Validate first name
        String firstNameError = NameValidator.validate(firstName);
        if (!firstNameError.isEmpty()) {
            showAlert("Invalid First Name", firstNameError);
            return;
        }
        
        // Validate middle name (if provided)
        if (!middleName.trim().isEmpty()) {
            String middleNameError = NameValidator.validate(middleName);
            if (!middleNameError.isEmpty()) {
                showAlert("Invalid Middle Name", middleNameError);
                return;
            }
        }
        
        // Validate last name
        String lastNameError = NameValidator.validate(lastName);
        if (!lastNameError.isEmpty()) {
            showAlert("Invalid Last Name", lastNameError);
            return;
        }
        
        // Validate preferred name (if provided)
        if (!preferredName.trim().isEmpty()) {
            String preferredNameError = NameValidator.validate(preferredName);
            if (!preferredNameError.isEmpty()) {
                showAlert("Invalid Preferred Name", preferredNameError);
                return;
            }
        }
        
        // Validate email
        String emailError = EmailValidator.validate(email);
        if (!emailError.isEmpty()) {
            showAlert("Invalid Email", emailError);
            return;
        }
        
        // Update database
        theDatabase.updateFirstName(currentUser.getUserName(), firstName);
        theDatabase.updateMiddleName(currentUser.getUserName(), middleName);
        theDatabase.updateLastName(currentUser.getUserName(), lastName);
        theDatabase.updatePreferredFirstName(currentUser.getUserName(), preferredName);
        theDatabase.updateEmailAddress(currentUser.getUserName(), email);
        
        // Update user object
        currentUser.setFirstName(firstName);
        currentUser.setMiddleName(middleName);
        currentUser.setLastName(lastName);
        currentUser.setPreferredFirstName(preferredName);
        currentUser.setEmailAddress(email);
        
        showAlert("Success", "Account information updated successfully!");
        doBack(theStage, currentUser);
    }

    protected static void doBack(Stage theStage, User currentUser) {
        if (currentUser.getAdminRole()) {
            guiAdminHome.ViewAdminHome.displayAdminHome(theStage, currentUser);
        } else if (currentUser.getNewRole1()) {
            guiRole1.ViewRole1Home.displayRole1Home(theStage, currentUser);
        } else if (currentUser.getNewRole2()) {
            guiRole2.ViewRole2Home.displayRole2Home(theStage, currentUser);
        }
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
