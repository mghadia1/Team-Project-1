package guiAdminHome;

import entityClasses.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewAdminHome {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Admin Home Page");
    private static Label label_Welcome = new Label("");

    private static Button button_InviteUser = new Button("Invite User");
    private static Button button_ResetPassword = new Button("Reset Password");
    private static Button button_DeleteUser = new Button("Delete User");
    private static Button button_ListUsers = new Button("List All Users");
    private static Button button_UpdateAccount = new Button("Update My Account");
    private static Button button_ManageRoles = new Button("Manage User Roles");
    private static Button button_Logout = new Button("Logout");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theAdminHomeScene = null;

    private static ViewAdminHome theView = null;
    private static User currentUser = null;

    public static void displayAdminHome(Stage ps, User user) {
        theStage = ps;
        currentUser = user;
        if (theView == null) theView = new ViewAdminHome();
        label_Welcome.setText("Welcome, " + user.getUserName());
        theStage.setTitle("CSE 360 Foundation Code: Admin Home");
        theStage.setScene(theAdminHomeScene);
        theStage.show();
    }

    private ViewAdminHome() {
        theRootPane = new Pane();
        theAdminHomeScene = new Scene(theRootPane, width, height);
        
        setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 20);
        setupLabelUI(label_Welcome, "Arial", 20, width, Pos.CENTER, 0, 70);
        
        setupButtonUI(button_InviteUser, "Dialog", 16, 250, Pos.CENTER, 275, 130);
        button_InviteUser.setOnAction((_) -> {ControllerAdminHome.performInvitation(theStage, currentUser); });
        
        setupButtonUI(button_ResetPassword, "Dialog", 16, 250, Pos.CENTER, 275, 180);
        button_ResetPassword.setOnAction((_) -> {ControllerAdminHome.setOnetimePassword(theStage, currentUser); });
        
        setupButtonUI(button_DeleteUser, "Dialog", 16, 250, Pos.CENTER, 275, 230);
        button_DeleteUser.setOnAction((_) -> {ControllerAdminHome.deleteUser(theStage, currentUser); });
        
        setupButtonUI(button_ListUsers, "Dialog", 16, 250, Pos.CENTER, 275, 280);
        button_ListUsers.setOnAction((_) -> {ControllerAdminHome.listUsers(theStage, currentUser); });
        
        setupButtonUI(button_ManageRoles, "Dialog", 16, 250, Pos.CENTER, 275, 330);
        button_ManageRoles.setOnAction((_) -> {guiAddRemoveRoles.ViewAddRemoveRoles.displayAddRemoveRoles(theStage, currentUser); });
        
        setupButtonUI(button_UpdateAccount, "Dialog", 16, 250, Pos.CENTER, 275, 380);
        button_UpdateAccount.setOnAction((_) -> {guiUserUpdate.ViewUserUpdate.displayUserUpdate(theStage, currentUser); });
        
        setupButtonUI(button_Logout, "Dialog", 16, 250, Pos.CENTER, 275, 480);
        button_Logout.setOnAction((_) -> {guiUserLogin.ViewUserLogin.displayUserLogin(theStage); });
        
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                label_Welcome,
                button_InviteUser,
                button_ResetPassword,
                button_DeleteUser,
                button_ListUsers,
                button_ManageRoles,
                button_UpdateAccount,
                button_Logout);
    }

    private void setupLabelUI(Label l, String ff, double f, double w, Pos p, double x, double y){
        l.setFont(Font.font(ff, f));
        l.setMinWidth(w);
        l.setAlignment(p);
        l.setLayoutX(x);
        l.setLayoutY(y);
    }

    private void setupButtonUI(Button b, String ff, double f, double w, Pos p, double x, double y){
        b.setFont(Font.font(ff, f));
        b.setMinWidth(w);
        b.setAlignment(p);
        b.setLayoutX(x);
        b.setLayoutY(y);
    }
}
