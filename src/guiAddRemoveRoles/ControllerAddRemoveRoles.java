package guiAddRemoveRoles;

import database.Database;
import entityClasses.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.List;

public class ControllerAddRemoveRoles {

    private static Database theDatabase = applicationMain.FoundationsMain.database;

    protected static void loadUsers() {
        List<String> userList = theDatabase.getUserList();
        ViewAddRemoveRoles.combo_Users.getItems().clear();
        ViewAddRemoveRoles.combo_Users.getItems().addAll(userList);
        if (userList.size() > 1) {
            ViewAddRemoveRoles.combo_Users.setValue(userList.get(0));
        }
    }

    protected static void onUserSelected() {
        String username = ViewAddRemoveRoles.combo_Users.getValue();
        if (username == null || username.equals("<Select a User>")) {
            ViewAddRemoveRoles.check_Admin.setSelected(false);
            ViewAddRemoveRoles.check_Role1.setSelected(false);
            ViewAddRemoveRoles.check_Role2.setSelected(false);
            return;
        }
        
        if (theDatabase.getUserAccountDetails(username)) {
            ViewAddRemoveRoles.check_Admin.setSelected(theDatabase.getCurrentAdminRole());
            ViewAddRemoveRoles.check_Role1.setSelected(theDatabase.getCurrentNewRole1());
            ViewAddRemoveRoles.check_Role2.setSelected(theDatabase.getCurrentNewRole2());
        }
    }

    protected static void performSave(Stage theStage, User currentUser) {
        String username = ViewAddRemoveRoles.combo_Users.getValue();
        if (username == null || username.equals("<Select a User>")) {
            showAlert("No User Selected", "Please select a user to modify roles.");
            return;
        }
        
        boolean adminRole = ViewAddRemoveRoles.check_Admin.isSelected();
        boolean role1 = ViewAddRemoveRoles.check_Role1.isSelected();
        boolean role2 = ViewAddRemoveRoles.check_Role2.isSelected();
        
        // Check if trying to remove all roles
        if (!adminRole && !role1 && !role2) {
            showAlert("Invalid Operation", "User must have at least one role.");
            return;
        }
        
        // Check if trying to remove Admin role when this is the last admin
        if (theDatabase.getUserAccountDetails(username)) {
            boolean wasAdmin = theDatabase.getCurrentAdminRole();
            if (wasAdmin && !adminRole && theDatabase.countAdmins() <= 1) {
                showAlert("Cannot Remove Role", "Cannot remove Admin role from the last admin user.");
                return;
            }
        }
        
        // Update roles
        theDatabase.updateUserRole(username, "Admin", String.valueOf(adminRole));
        theDatabase.updateUserRole(username, "Role1", String.valueOf(role1));
        theDatabase.updateUserRole(username, "Role2", String.valueOf(role2));
        
        showAlert("Success", "User roles updated successfully!");
    }

    protected static void performRemoveRole(String username, String role) {
        // Check if this is the last admin before removing admin role
        if (role.equals("Admin") && theDatabase.countAdmins() <= 1) {
            showAlert("Cannot Remove Role", "Cannot remove Admin role from the last admin user.");
            return;
        }
        
        theDatabase.updateUserRole(username, role, "false");
    }

    private static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
