package guiFirstAdmin;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewFirstAdmin {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Foundation Application Setup - First Admin");
    private static Label label_Instructions = new Label("Enter admin username and password to set up the system");
    protected static Label label_PasswordsDoNotMatch = new Label("");

    protected static TextField text_Username = new TextField();
    protected static PasswordField text_Password = new PasswordField();
    protected static PasswordField text_ConfirmPassword = new PasswordField();
    private static Button button_Setup = new Button("Setup Admin");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theFirstAdminScene = null;

    private static ViewFirstAdmin theView = null;

    public static void displayFirstAdmin(Stage ps) {
        theStage = ps;
        if (theView == null) theView = new ViewFirstAdmin();
        text_Username.setText("");
        text_Password.setText("");
        text_ConfirmPassword.setText("");
        label_PasswordsDoNotMatch.setText("");
        theStage.setTitle("CSE 360 Foundation Code: First Admin Setup");
        theStage.setScene(theFirstAdminScene);
        theStage.show();
    }

    private ViewFirstAdmin() {
        theRootPane = new Pane();
        theFirstAdminScene = new Scene(theRootPane, width, height);
        setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 20);
        setupLabelUI(label_Instructions, "Arial", 18, width, Pos.CENTER, 0, 80);
        setupTextUI(text_Username, "Arial", 18, 300, Pos.BASELINE_LEFT, 250, 140, true);
        text_Username.setPromptText("Enter Username");
        setupTextUI(text_Password, "Arial", 18, 300, Pos.BASELINE_LEFT, 250, 190, true);
        text_Password.setPromptText("Enter Password");
        setupTextUI(text_ConfirmPassword, "Arial", 18, 300, Pos.BASELINE_LEFT, 250, 240, true);
        text_ConfirmPassword.setPromptText("Confirm Password");
        setupButtonUI(button_Setup, "Dialog", 18, 200, Pos.CENTER, 300, 290);
        button_Setup.setOnAction((e) -> {ControllerFirstAdmin.doSetupAdmin(theStage); });
        setupLabelUI(label_PasswordsDoNotMatch, "Arial", 14, width, Pos.CENTER, 0, 340);
        label_PasswordsDoNotMatch.setStyle("-fx-text-fill: red;");
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                label_Instructions,
                text_Username,
                text_Password,
                text_ConfirmPassword,
                button_Setup,
                label_PasswordsDoNotMatch);
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
