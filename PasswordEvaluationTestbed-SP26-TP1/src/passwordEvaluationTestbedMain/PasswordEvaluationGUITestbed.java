package passwordEvaluationTestbedMain;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/*******
 * <p> Title: PasswordEvaluationGUITestbed Class. </p>
 *
 * <p> Description: A JavaFX demonstration application and baseline for a sequence of projects </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2022 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 4.10 2026-02-05 TP1 aligned - no functional changes required
 *
 */

public class PasswordEvaluationGUITestbed extends Application {

    private String string_ApplicationTitle = new String("Specify Your Password");

    public static Stage theStage;

    public final static double WINDOW_WIDTH = 500;
    public final static double WINDOW_HEIGHT = 430;

    @Override
    public void start(Stage stage) throws Exception {

        theStage = stage;

        theStage.setTitle(string_ApplicationTitle);

        Pane theRoot = new Pane();

        passwordPopUpWindow.View.view(theRoot);

        Scene theScene = new Scene(theRoot, WINDOW_WIDTH, WINDOW_HEIGHT);

        theStage.setScene(theScene);

        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
