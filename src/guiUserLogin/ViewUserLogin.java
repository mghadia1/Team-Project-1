package guiUserLogin;

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

public class ViewUserLogin {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Foundation Application Startup Page");
    private static Label label_OperationalStartTitle = new Label("Log In or Invited User Account Setup ");
    private static Label label_LogInInsrtuctions = new Label("Enter your user name and password and "+    
            "then click on the LogIn button");
    protected static Alert alertUsernamePasswordError = new Alert(AlertType.INFORMATION);

    protected static TextField text_Username = new TextField();
    protected static PasswordField text_Password = new PasswordField();
    private static Button button_Login = new Button("Log In");    

    private static Label label_AccountSetupInsrtuctions = new Label("No account? "+    
            "Enter your invitation code and click on the Account Setup button");
    private static TextField text_Invitation = new TextField();
    private static Button button_SetupAccount = new Button("Setup Account");

    private static Button button_Quit = new Button("Quit");

    private static Stage theStage;    
    private static Pane theRootPane;
    public static Scene theUserLoginScene = null;    

    private static ViewUserLogin theView = null;

    public static void displayUserLogin(Stage ps) {
        theStage = ps;
        if (theView == null) theView = new ViewUserLogin();
        text_Username.setText("");
        text_Password.setText("");
        text_Invitation.setText("");
        theStage.setTitle("CSE 360 Foundation Code: User Login Page");        
        theStage.setScene(theUserLoginScene);
        theStage.show();
    }

    private ViewUserLogin() {
        theRootPane = new Pane();
        theUserLoginScene = new Scene(theRootPane, width, height);
        setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 10);
        setupLabelUI(label_OperationalStartTitle, "Arial", 24, width, Pos.CENTER, 0, 60);
        setupLabelUI(label_LogInInsrtuctions, "Arial", 18, width, Pos.BASELINE_LEFT, 20, 120);
        setupTextUI(text_Username, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 160, true);
        text_Username.setPromptText("Enter Username");
        setupTextUI(text_Password, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 210, true);
        text_Password.setPromptText("Enter Password");
        setupButtonUI(button_Login, "Dialog", 18, 200, Pos.CENTER, 475, 180);
        button_Login.setOnAction((_) -> {ControllerUserLogin.doLogin(theStage); });
        alertUsernamePasswordError.setTitle("Invalid username/password!");
        alertUsernamePasswordError.setHeaderText(null);
        setupLabelUI(label_AccountSetupInsrtuctions, "Arial", 18, width, Pos.BASELINE_LEFT, 20, 300);
        setupTextUI(text_Invitation, "Arial", 18, 300, Pos.BASELINE_LEFT, 50, 340, true);
        text_Invitation.setPromptText("Enter Invitation Code");
        setupButtonUI(button_SetupAccount, "Dialog", 18, 200, Pos.CENTER, 475, 340);
        button_SetupAccount.setOnAction((_) -> {
            System.out.println("**** Calling doSetupAccount");
            ControllerUserLogin.doSetupAccount(theStage, text_Invitation.getText());
        });
        setupButtonUI(button_Quit, "Dialog", 18, 250, Pos.CENTER, 300, 520);
        button_Quit.setOnAction((_) -> {ControllerUserLogin.performQuit(); });
        theRootPane.getChildren().addAll(
                label_ApplicationTitle, 
                label_OperationalStartTitle,
                label_LogInInsrtuctions, label_AccountSetupInsrtuctions, text_Username,
                button_Login, text_Password, text_Invitation, button_SetupAccount,
                button_Quit);
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
