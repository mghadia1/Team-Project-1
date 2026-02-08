package passwordPopUpWindow;

/*******
 * <p> Title: Controller Class - Based on the current state and user input invoke the right action.
 * </p>
 *
 * <p> Description: This Controller class is a major component of a Model View Controller (MVC)
 * application design that provides the user with Graphical User Interface with JavaFX
 * widgets as opposed to a command line interface.
 *
 * This class contains no state of it's own, so there is no need to instantiate it.
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 2.10 2026-02-05 TP1 aligned - no functional changes required
 */

public class Controller {

    /*******
     * <p> Title: handleButtonPress - Handle the user action of clicking on the GUI's button</p>
     *
     * <p> Description: The Finish button is enabled only when the password is valid. Pressing the
     * button hides the password window and returns the validated password.</p>
     */
    static protected void handleButtonPress() {
        passwordEvaluationTestbedMain.PasswordEvaluationGUITestbed.theStage.hide();
        String thePassword = View.text_Password.getText();
        System.out.println("The password returned was: " + thePassword);
    }
}
