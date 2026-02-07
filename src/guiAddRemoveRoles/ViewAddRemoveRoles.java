package guiAddRemoveRoles;

import entityClasses.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewAddRemoveRoles {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Manage User Roles");
    private static Label label_SelectUser = new Label("Select User:");
    protected static ComboBox<String> combo_Users = new ComboBox<>();
    protected static CheckBox check_Admin = new CheckBox("Admin Role");
    protected static CheckBox check_Role1 = new CheckBox("Role 1");
    protected static CheckBox check_Role2 = new CheckBox("Role 2");
    
    private static Button button_Save = new Button("Save Changes");
    private static Button button_Back = new Button("Back");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theAddRemoveRolesScene = null;

    private static ViewAddRemoveRoles theView = null;
    private static User currentUser = null;

    public static void displayAddRemoveRoles(Stage ps, User user) {
        theStage = ps;
        currentUser = user;
        if (theView == null) theView = new ViewAddRemoveRoles();
        
        // Load user list
        ControllerAddRemoveRoles.loadUsers();
        
        theStage.setTitle("CSE 360 Foundation Code: Manage Roles");
        theStage.setScene(theAddRemoveRolesScene);
        theStage.show();
    }

    private ViewAddRemoveRoles() {
        theRootPane = new Pane();
        theAddRemoveRolesScene = new Scene(theRootPane, width, height);
        
        setupLabelUI(label_ApplicationTitle, "Arial", 28, width, Pos.CENTER, 0, 20);
        setupLabelUI(label_SelectUser, "Arial", 16, 200, Pos.BASELINE_LEFT, 250, 100);
        
        combo_Users.setLayoutX(250);
        combo_Users.setLayoutY(130);
        combo_Users.setMinWidth(300);
        combo_Users.setOnAction((_) -> {ControllerAddRemoveRoles.onUserSelected(); });
        
        check_Admin.setLayoutX(280);
        check_Admin.setLayoutY(180);
        check_Admin.setFont(Font.font("Arial", 16));
        
        check_Role1.setLayoutX(280);
        check_Role1.setLayoutY(220);
        check_Role1.setFont(Font.font("Arial", 16));
        
        check_Role2.setLayoutX(280);
        check_Role2.setLayoutY(260);
        check_Role2.setFont(Font.font("Arial", 16));
        
        setupButtonUI(button_Save, "Dialog", 16, 200, Pos.CENTER, 220, 340);
        button_Save.setOnAction((_) -> {ControllerAddRemoveRoles.performSave(theStage, currentUser); });
        setupButtonUI(button_Back, "Dialog", 16, 200, Pos.CENTER, 440, 340);
        button_Back.setOnAction((_) -> {guiAdminHome.ViewAdminHome.displayAdminHome(theStage, currentUser); });
        
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                label_SelectUser,
                combo_Users,
                check_Admin,
                check_Role1,
                check_Role2,
                button_Save,
                button_Back);
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
