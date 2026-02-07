package guiUserUpdate;

import entityClasses.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewUserUpdate {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Update Account Information");
    protected static TextField text_FirstName = new TextField();
    protected static TextField text_MiddleName = new TextField();
    protected static TextField text_LastName = new TextField();
    protected static TextField text_PreferredName = new TextField();
    protected static TextField text_Email = new TextField();
    
    private static Button button_Save = new Button("Save Changes");
    private static Button button_Back = new Button("Back");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theUserUpdateScene = null;

    private static ViewUserUpdate theView = null;
    private static User currentUser = null;

    public static void displayUserUpdate(Stage ps, User user) {
        theStage = ps;
        currentUser = user;
        if (theView == null) theView = new ViewUserUpdate();
        
        // Load current values
        text_FirstName.setText(user.getFirstName());
        text_MiddleName.setText(user.getMiddleName());
        text_LastName.setText(user.getLastName());
        text_PreferredName.setText(user.getPreferredFirstName());
        text_Email.setText(user.getEmailAddress());
        
        theStage.setTitle("CSE 360 Foundation Code: Update Account");
        theStage.setScene(theUserUpdateScene);
        theStage.show();
    }

    private ViewUserUpdate() {
        theRootPane = new Pane();
        theUserUpdateScene = new Scene(theRootPane, width, height);
        
        setupLabelUI(label_ApplicationTitle, "Arial", 28, width, Pos.CENTER, 0, 20);
        
        setupTextUI(text_FirstName, "Arial", 16, 300, Pos.BASELINE_LEFT, 250, 100, true);
        text_FirstName.setPromptText("First Name");
        setupTextUI(text_MiddleName, "Arial", 16, 300, Pos.BASELINE_LEFT, 250, 150, true);
        text_MiddleName.setPromptText("Middle Name");
        setupTextUI(text_LastName, "Arial", 16, 300, Pos.BASELINE_LEFT, 250, 200, true);
        text_LastName.setPromptText("Last Name");
        setupTextUI(text_PreferredName, "Arial", 16, 300, Pos.BASELINE_LEFT, 250, 250, true);
        text_PreferredName.setPromptText("Preferred First Name");
        setupTextUI(text_Email, "Arial", 16, 300, Pos.BASELINE_LEFT, 250, 300, true);
        text_Email.setPromptText("Email Address");
        
        setupButtonUI(button_Save, "Dialog", 16, 200, Pos.CENTER, 220, 380);
        button_Save.setOnAction((e) -> {ControllerUserUpdate.doSave(theStage, currentUser); });
        setupButtonUI(button_Back, "Dialog", 16, 200, Pos.CENTER, 440, 380);
        button_Back.setOnAction((e) -> {ControllerUserUpdate.doBack(theStage, currentUser); });
        
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                text_FirstName,
                text_MiddleName,
                text_LastName,
                text_PreferredName,
                text_Email,
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

    private void setupTextUI(TextField t, String ff, double f, double w, Pos p, double x, double y, boolean e){
        t.setFont(Font.font(ff, f));
        t.setMinWidth(w);
        t.setMaxWidth(w);
        t.setAlignment(p);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setEditable(e);
    }
}
