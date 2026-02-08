package passwordPopUpWindow;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import passwordEvaluationTestbedMain.PasswordEvaluationGUITestbed;

/*******
 * <p> Title: View Class - establishes the Graphics User interface, presents information to the
 * user, and accept information from the user.</p>
 *
 * <p> Description: This View class is a major component of a Model View Controller (MVC)
 * application design that provides the user with Graphical User Interface with JavaFX
 * widgets as opposed to a command line interface.
 *
 * In this case the GUI consists of numerous widgets to show the user where to enter the password,
 * where any errors are located, and a set of requirements for a valid password and whether or not
 * they have been satisfied.
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 2.10 2026-02-05 Updated labels to reflect TP1 allowed special set and improved messaging
 */

public class View {

    static private Label label_Password = new Label("Enter the password here");
    static protected TextField text_Password = new TextField();

    static protected Label label_errPassword = new Label();
    static protected Label noInputFound = new Label();
    static private TextFlow errPassword;
    static protected Text errPasswordPart1 = new Text();
    static protected Text errPasswordPart2 = new Text();
    static protected Label errPasswordPart3 = new Label();

    static protected Label validPassword = new Label();
    static protected Label label_Requirements =
            new Label("A valid password must satisfy the following requirements:");
    static protected Label label_UpperCase = new Label();
    static protected Label label_LowerCase = new Label();
    static protected Label label_NumericDigit = new Label();
    static protected Label label_SpecialChar = new Label();
    static protected Label label_LongEnough = new Label();

    static protected Button button_Finish = new Button();

    public static void view(Pane theRoot) {
        double windowWidth = PasswordEvaluationGUITestbed.WINDOW_WIDTH;

        setupLabelWidget(label_Password, 10, 10, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);

        setupTextWidget(text_Password, 10, 30, "Arial", 18, windowWidth - 20, Pos.BASELINE_LEFT, true);
        text_Password.textProperty().addListener((observable, oldValue, newValue) -> { Model.updatePassword(); });

        setupLabelWidget(noInputFound, 10, 80, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);
        noInputFound.setText("No input text found!");
        noInputFound.setTextFill(Color.RED);

        label_errPassword.setTextFill(Color.RED);
        label_errPassword.setAlignment(Pos.BASELINE_LEFT);
        setupLabelWidget(label_errPassword, 22, 96, "Arial", 14, windowWidth - 40, Pos.BASELINE_LEFT);

        errPasswordPart1.setFill(Color.BLACK);
        errPasswordPart1.setFont(Font.font("Arial", FontPosture.REGULAR, 18));

        errPasswordPart2.setFill(Color.RED);
        errPasswordPart2.setFont(Font.font("Arial", FontPosture.REGULAR, 24));

        errPassword = new TextFlow(errPasswordPart1, errPasswordPart2);
        errPassword.setMinWidth(windowWidth - 10);
        errPassword.setLayoutX(22);
        errPassword.setLayoutY(70);

        setupLabelWidget(errPasswordPart3, 20, 110, "Arial", 14, windowWidth - 20, Pos.BASELINE_LEFT);

        setupLabelWidget(label_Requirements, 10, 140, "Arial", 16, windowWidth - 10, Pos.BASELINE_LEFT);

        setupLabelWidget(label_UpperCase, 30, 180, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);
        setupLabelWidget(label_LowerCase, 30, 210, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);
        setupLabelWidget(label_NumericDigit, 30, 240, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);
        setupLabelWidget(label_SpecialChar, 30, 270, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);
        setupLabelWidget(label_LongEnough, 30, 300, "Arial", 14, windowWidth - 10, Pos.BASELINE_LEFT);

        resetAssessments();

        validPassword.setTextFill(Color.GREEN);
        validPassword.setAlignment(Pos.BASELINE_LEFT);
        setupLabelWidget(validPassword, 10, 340, "Arial", 18, windowWidth - 20, Pos.BASELINE_LEFT);

        setupButtonWidget(button_Finish, "Finish and Save The Password", (windowWidth - 250) / 2,
                380, "Arial", 14, 250, Pos.BASELINE_CENTER);
        button_Finish.setDisable(true);
        button_Finish.setOnAction(new EventHandler<>() {
            public void handle(ActionEvent event) {
                Controller.handleButtonPress();
            }
        });

        theRoot.getChildren().addAll(label_Password, text_Password, noInputFound,
                label_errPassword, errPassword, errPasswordPart3, validPassword,
                label_Requirements, label_UpperCase, label_LowerCase, label_NumericDigit,
                label_SpecialChar, label_LongEnough, button_Finish);
    }

    static protected void resetAssessments() {
        label_UpperCase.setText("At least one upper case letter - Not yet satisfied");
        label_UpperCase.setTextFill(Color.RED);

        label_LowerCase.setText("At least one lower case letter - Not yet satisfied");
        label_LowerCase.setTextFill(Color.RED);

        label_NumericDigit.setText("At least one numeric digit - Not yet satisfied");
        label_NumericDigit.setTextFill(Color.RED);

        label_SpecialChar.setText("At least one special character (_ @ # $ !) - Not yet satisfied");
        label_SpecialChar.setTextFill(Color.RED);

        label_LongEnough.setText("At least eight characters - Not yet satisfied");
        label_LongEnough.setTextFill(Color.RED);
    }

    static private void setupLabelWidget(Label l, double x, double y, String ff, double f, double w, Pos p) {
        l.setLayoutX(x);
        l.setLayoutY(y);
        l.setFont(Font.font(ff, f));
        l.setMinWidth(w);
        l.setAlignment(p);
    }

    static private void setupTextWidget(TextField t, double x, double y, String ff, double f, double w, Pos p, boolean e) {
        t.setFont(Font.font(ff, f));
        t.setMinWidth(w);
        t.setMaxWidth(w);
        t.setAlignment(p);
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setEditable(e);
    }

    static private void setupButtonWidget(Button b, String s, double x, double y, String ff, double f, double w, Pos p) {
        b.setText(s);
        b.setFont(Font.font(ff, f));
        b.setMinWidth(w);
        b.setMaxWidth(w);
        b.setAlignment(p);
        b.setLayoutX(x);
        b.setLayoutY(y);
    }
}
