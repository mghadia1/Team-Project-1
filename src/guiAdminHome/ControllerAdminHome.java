package guiAdminHome;

import database.Database;
import entityClasses.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import validation.EmailValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class ControllerAdminHome {

    private static Database theDatabase = applicationMain.FoundationsMain.database;

    protected static void performInvitation(Stage theStage, User currentUser) {
        // Get email address
        TextInputDialog emailDialog = new TextInputDialog();
        emailDialog.setTitle("Invite User");
        emailDialog.setHeaderText("Enter the email address to invite:");
        emailDialog.setContentText("Email:");
        
        Optional<String> emailResult = emailDialog.showAndWait();
        if (!emailResult.isPresent() || emailResult.get().trim().isEmpty()) {
            return;
        }
        
        String email = emailResult.get().trim();
        
        // Validate email
        String emailError = EmailValidator.validate(email);
        if (!emailError.isEmpty()) {
            showAlert("Invalid Email", emailError, AlertType.ERROR);
            return;
        }
        
        // Check if email already used
        if (theDatabase.emailaddressHasBeenUsed(email)) {
            showAlert("Email Already Used", "This email address has already been invited.", AlertType.ERROR);
            return;
        }
        
        // Get role selection
        List<String> roles = List.of("Admin", "Role1", "Role2");
        ChoiceDialog<String> roleDialog = new ChoiceDialog<>("Role1", roles);
        roleDialog.setTitle("Select Role");
        roleDialog.setHeaderText("Select the role for the new user:");
        roleDialog.setContentText("Role:");
        
        Optional<String> roleResult = roleDialog.showAndWait();
        if (!roleResult.isPresent()) {
            return;
        }
        
        String role = roleResult.get();
        
        // Set deadline to 24 hours from now
        LocalDateTime deadline = LocalDateTime.now().plusHours(24);
        
        // Generate invitation code with deadline
        String code = theDatabase.generateInvitationCode(email, role, deadline.toString());
        
        // Show invitation code
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invitation Created");
        alert.setHeaderText("Invitation code generated successfully!");
        alert.setContentText("Invitation Code: " + code + "\nEmail: " + email + "\nRole: " + role + "\nExpires: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        alert.showAndWait();
    }

    protected static void setOnetimePassword(Stage theStage, User currentUser) {
        // Get user list
        List<String> userList = theDatabase.getUserList();
        if (userList.size() <= 1) {
            showAlert("No Users", "No users available for password reset.", AlertType.INFORMATION);
            return;
        }
        
        // Select user
        ChoiceDialog<String> userDialog = new ChoiceDialog<>(userList.get(1), userList.subList(1, userList.size()));
        userDialog.setTitle("Reset Password");
        userDialog.setHeaderText("Select user to reset password:");
        userDialog.setContentText("User:");
        
        Optional<String> userResult = userDialog.showAndWait();
        if (!userResult.isPresent()) {
            return;
        }
        
        String username = userResult.get();
        
        // Generate random OTP
        String otp = generateOTP();
        
        // Store OTP in database
        theDatabase.setOneTimePassword(username, otp);
        
        // Show OTP to admin
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("One-Time Password Generated");
        alert.setHeaderText("Password reset for user: " + username);
        alert.setContentText("One-Time Password: " + otp + "\n\nProvide this to the user. They must change it on next login.");
        alert.showAndWait();
    }

    protected static void deleteUser(Stage theStage, User currentUser) {
        // Get user list
        List<String> userList = theDatabase.getUserList();
        if (userList.size() <= 1) {
            showAlert("No Users", "No users available to delete.", AlertType.INFORMATION);
            return;
        }
        
        // Select user
        ChoiceDialog<String> userDialog = new ChoiceDialog<>(userList.get(1), userList.subList(1, userList.size()));
        userDialog.setTitle("Delete User");
        userDialog.setHeaderText("Select user to delete:");
        userDialog.setContentText("User:");
        
        Optional<String> userResult = userDialog.showAndWait();
        if (!userResult.isPresent()) {
            return;
        }
        
        String username = userResult.get();
        
        // Check if trying to delete self
        if (username.equals(currentUser.getUserName())) {
            showAlert("Cannot Delete", "You cannot delete your own account.", AlertType.ERROR);
            return;
        }
        
        // Check if this is the last admin
        if (theDatabase.getUserAccountDetails(username)) {
            if (theDatabase.getCurrentAdminRole() && theDatabase.countAdmins() <= 1) {
                showAlert("Cannot Delete", "Cannot delete the last admin user.", AlertType.ERROR);
                return;
            }
        }
        
        // Confirmation dialog
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Delete user: " + username);
        confirmAlert.setContentText("Are you sure you want to delete this user? This action cannot be undone.");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            theDatabase.deleteUser(username);
            showAlert("User Deleted", "User " + username + " has been deleted successfully.", AlertType.INFORMATION);
        }
    }

    protected static void listUsers(Stage theStage, User currentUser) {
        List<User> users = theDatabase.getAllUsers();
        
        if (users.isEmpty()) {
            showAlert("No Users", "No users found in the system.", AlertType.INFORMATION);
            return;
        }
        
        StringBuilder userList = new StringBuilder();
        for (User user : users) {
            userList.append("Username: ").append(user.getUserName()).append("\n");
            userList.append("Email: ").append(user.getEmailAddress()).append("\n");
            userList.append("Roles: ");
            if (user.getAdminRole()) userList.append("Admin ");
            if (user.getNewRole1()) userList.append("Role1 ");
            if (user.getNewRole2()) userList.append("Role2 ");
            userList.append("\n\n");
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("User List");
        alert.setHeaderText("All Users in System");
        alert.setContentText(userList.toString());
        alert.showAndWait();
    }

    protected static boolean invalidEmailAddress(String email) {
        String error = EmailValidator.validate(email);
        return !error.isEmpty();
    }

    private static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private static void showAlert(String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
