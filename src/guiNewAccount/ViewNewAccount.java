package guiNewAccount;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewNewAccount {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Create New Account");
    private static Label label_Instructions = new Label("Fill in your account details");
    protected static Label label_InvitationCode = new Label("");

    protected static TextField text_Username = new TextField();
    protected static PasswordField text_Password = new PasswordField();
    protected static PasswordField text_ConfirmPassword = new PasswordField();
    protected static TextField text_FirstName = new TextField();
    protected static TextField text_MiddleName = new TextField();
    protected static TextField text_LastName = new TextField();
    protected static TextField text_PreferredName = new TextField();
    protected static TextField text_Email = new TextField();
    
    protected static Alert alertError = new Alert(AlertType.INFORMATION);
    
    private static Button button_Create = new Button("Create Account");
    private static Button button_Cancel = new Button("Cancel");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theNewAccountScene = null;

    private static ViewNewAccount theView = null;
    private static String invitationCode = "";

    public static void displayNewAccount(Stage ps, String code) {
        theStage = ps;
        invitationCode = code;
        if (theView == null) theView = new ViewNewAccount();
        text_Username.setText("");
        text_Password.setText("");
        text_ConfirmPassword.setText("");
        text_FirstName.setText("");
        text_MiddleName.setText("");
        text_LastName.setText("");
        text_PreferredName.setText("");
        text_Email.setText("");
        label_InvitationCode.setText("Invitation Code: " + code);
        theStage.setTitle("CSE 360 Foundation Code: New Account");
        theStage.setScene(theNewAccountScene);
        theStage.show();
    }

    private ViewNewAccount() {
        theRootPane = new Pane();
        theNewAccountScene = new Scene(theRootPane, width, height);
        
        setupLabelUI(label_ApplicationTitle, "Arial", 28, width, Pos.CENTER, 0, 20);
        setupLabelUI(label_Instructions, "Arial", 16, width, Pos.CENTER, 0, 60);
        setupLabelUI(label_InvitationCode, "Arial", 14, width, Pos.CENTER, 0, 90);
        
        setupTextUI(text_Username, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 120, true);
        text_Username.setPromptText("Username");
        setupTextUI(text_Password, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 160, true);
        text_Password.setPromptText("Password");
        setupTextUI(text_ConfirmPassword, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 200, true);
        text_ConfirmPassword.setPromptText("Confirm Password");
        setupTextUI(text_FirstName, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 240, true);
        text_FirstName.setPromptText("First Name");
        setupTextUI(text_MiddleName, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 280, true);
        text_MiddleName.setPromptText("Middle Name (optional)");
        setupTextUI(text_LastName, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 320, true);
        text_LastName.setPromptText("Last Name");
        setupTextUI(text_PreferredName, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 360, true);
        text_PreferredName.setPromptText("Preferred First Name (optional)");
        setupTextUI(text_Email, "Arial", 14, 300, Pos.BASELINE_LEFT, 250, 400, true);
        text_Email.setPromptText("Email Address");
        
        setupButtonUI(button_Create, "Dialog", 16, 200, Pos.CENTER, 220, 450);
        button_Create.setOnAction((e) -> {ControllerNewAccount.doCreateUser(theStage, invitationCode); });
        setupButtonUI(button_Cancel, "Dialog", 16, 200, Pos.CENTER, 440, 450);
        button_Cancel.setOnAction((e) -> {guiUserLogin.ViewUserLogin.displayUserLogin(theStage); });
        
        alertError.setTitle("Account Creation Error");
        alertError.setHeaderText(null);
        
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                label_Instructions,
                label_InvitationCode,
                text_Username,
                text_Password,
                text_ConfirmPassword,
                text_FirstName,
                text_MiddleName,
                text_LastName,
                text_PreferredName,
                text_Email,
                button_Create,
                button_Cancel);
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
