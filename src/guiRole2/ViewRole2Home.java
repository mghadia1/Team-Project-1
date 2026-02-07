package guiRole2;

import entityClasses.User;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ViewRole2Home {

    private static double width = applicationMain.FoundationsMain.WINDOW_WIDTH;
    private static double height = applicationMain.FoundationsMain.WINDOW_HEIGHT;

    private static Label label_ApplicationTitle = new Label("Role 2 Home Page");
    private static Label label_Welcome = new Label("");

    private static Button button_UpdateAccount = new Button("Update My Account");
    private static Button button_Logout = new Button("Logout");

    private static Stage theStage;
    private static Pane theRootPane;
    public static Scene theRole2HomeScene = null;

    private static ViewRole2Home theView = null;
    private static User currentUser = null;

    public static void displayRole2Home(Stage ps, User user) {
        theStage = ps;
        currentUser = user;
        if (theView == null) theView = new ViewRole2Home();
        label_Welcome.setText("Welcome, " + user.getUserName() + " (Role 2)");
        theStage.setTitle("CSE 360 Foundation Code: Role 2 Home");
        theStage.setScene(theRole2HomeScene);
        theStage.show();
    }

    private ViewRole2Home() {
        theRootPane = new Pane();
        theRole2HomeScene = new Scene(theRootPane, width, height);
        
        setupLabelUI(label_ApplicationTitle, "Arial", 32, width, Pos.CENTER, 0, 50);
        setupLabelUI(label_Welcome, "Arial", 20, width, Pos.CENTER, 0, 110);
        
        setupButtonUI(button_UpdateAccount, "Dialog", 16, 250, Pos.CENTER, 275, 200);
        button_UpdateAccount.setOnAction((e) -> {guiUserUpdate.ViewUserUpdate.displayUserUpdate(theStage, currentUser); });
        
        setupButtonUI(button_Logout, "Dialog", 16, 250, Pos.CENTER, 275, 300);
        button_Logout.setOnAction((e) -> {guiUserLogin.ViewUserLogin.displayUserLogin(theStage); });
        
        theRootPane.getChildren().addAll(
                label_ApplicationTitle,
                label_Welcome,
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
