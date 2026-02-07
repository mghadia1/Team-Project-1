package guiMultipleRoleDispatch;

import entityClasses.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewMultipleRoleDispatch {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Select Role");
    private static Label label_Instructions = new Label("You have multiple roles. Please select one:");

    private static Button button_Admin = new Button("Login as Admin");
    private static Button button_Role1 = new Button("Login as Role1");
    private static Button button_Role2 = new Button("Login as Role2");
    private static Button button_Logout = new Button("Logout");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theMultipleRoleDispatchScene = null;

    private static ViewMultipleRoleDispatch theView = null;
    private static User currentUser = null;

    public static void displayMultipleRoleDispatch(Stage ps, User user) {
        theStage = ps;
        currentUser = user;
        if (theView == null) theView = new ViewMultipleRoleDispatch();
        
        // Show only available roles
        button_Admin.setVisible(user.getAdminRole());
        button_Role1.setVisible(user.getNewRole1());
        button_Role2.setVisible(user.getNewRole2());
        
        theStage.setTitle("CSE 360 Foundation Code: Select Role");
        theStage.setScene(theMultipleRoleDispatchScene);
        theStage.show();
    }

    private ViewMultipleRoleDispatch() {
        theRootPane = new Pane();
        theMultipleRoleDispatchScene = new Scene(theRootPane, width, height);
        
        setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 50);
        setupLabelUI(label_Instructions, "Arial", 18, width, Pos.CENTER, 0, 110);
        
        setupButtonUI(button_Admin, "Dialog", 18, 250, Pos.CENTER, 275, 180);
        button_Admin.setOnAction((e) -> {ControllerMultipleRoleDispatch.selectAdminRole(theStage, currentUser); });
        
        setupButtonUI(button_Role1, "Dialog", 18, 250, Pos.CENTER, 275, 240);
        button_Role1.setOnAction((e) -> {ControllerMultipleRoleDispatch.selectRole1(theStage, currentUser); });
        
        setupButtonUI(button_Role2, "Dialog", 18, 250, Pos.CENTER, 275, 300);
        button_Role2.setOnAction((e) -> {ControllerMultipleRoleDispatch.selectRole2(theStage, currentUser); });
        
        setupButtonUI(button_Logout, "Dialog", 18, 250, Pos.CENTER, 275, 400);
        button_Logout.setOnAction((e) -> {guiUserLogin.ViewUserLogin.displayUserLogin(theStage); });
        
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                label_Instructions,
                button_Admin,
                button_Role1,
                button_Role2,
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
