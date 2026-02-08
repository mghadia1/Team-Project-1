package passwordPopUpWindow;

import javafx.scene.paint.Color;

/*******
 * <p> Title: Model Class - establishes the required GUI data and the computations.
 * </p>
 *
 * <p> Description: This Model class is a major component of a Model View Controller (MVC)
 * application design that provides the user with Graphical User Interface using JavaFX
 * widgets as opposed to a command line interface.
 * 
 * In this case the Model deals with an input from the user and checks to see if it conforms to
 * the requirements specified by a graphical representation of a finite state machine.
 *
 * This is a purely static component of the MVC implementation. There is no need to instantiate
 * the class.
 *
 * <p> Copyright: Lynn Robert Carter © 2025 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 2.10 2026-02-05 Updated for TP1: enforce max length (16) and restricted special chars (_@#$!)
 */

public class Model {

    /*-********************************************************************************************
     *
     * Password policy constants for TP1 integration
     *
     */

    private static final int PASSWORD_MIN_LEN = 8;
    private static final int PASSWORD_MAX_LEN = 16;

    // TP1 simplified allowed special characters (as specified by the team Input Validation rules)
    private static final String ALLOWED_SPECIAL = "_@#$!";

    /*******
     * <p> Title: updatePassword - Protected Method </p>
     *
     * <p> Description: This method is called every time the user changes the password (e.g., with
     * every key pressed) using the GUI from the PasswordEvaluationGUITestbed. It resets the
     * messages associated with each of the requirements and then evaluates the current password
     * with respect to those requirements. The results of that evaluation are display via the View
     * to the user and via the console.</p>
     */

    protected static void updatePassword() {
        View.resetAssessments();                      // Reset the assessment flags
        View.label_errPassword.setText("");           // Clear any previous error summary
        View.validPassword.setText("");               // Clear previous success/fail message

        String password = View.text_Password.getText(); // Fetch the input
        if (password == null) password = "";

        // If the input is empty, clear the aspects of the user interface having to do with the
        // user input and tell the user that the input is empty.
        if (password.isEmpty()) {
            View.errPasswordPart1.setText("");
            View.errPasswordPart2.setText("");
            View.errPasswordPart3.setText("");
            View.noInputFound.setText("No input text found!");
            View.noInputFound.setTextFill(Color.RED);
            View.button_Finish.setDisable(true);
            return;
        }

        View.noInputFound.setText(""); // There was input, so hide the "no input" message

        // Evaluate the current password
        String errMessage = evaluatePassword(password);

        // Update UI flags (green/red requirement labels)
        updateFlags();

        // If error message not empty => invalid
        if (!errMessage.isEmpty()) {
            System.out.println(errMessage);

            // Provide a short summary on the GUI
            View.label_errPassword.setText(errMessage);

            // Protect substring bounds
            int ndx = passwordIndexofError;
            if (ndx < 0) ndx = 0;
            if (ndx > password.length()) ndx = password.length();

            // Extract the input up to the point of the error and place it in Part 1
            View.errPasswordPart1.setText(password.substring(0, ndx));

            // Place the red up arrow into Part 2
            View.errPasswordPart2.setText("\u21EB");

            // Tell the user about the meaning of the red up arrow
            View.errPasswordPart3.setText("The red arrow points at the character causing the error!");

            // Tell the user that the password is not valid with a red message
            View.validPassword.setTextFill(Color.RED);
            View.validPassword.setText("Failure! The password is not valid.");

            // Ensure the button is disabled
            View.button_Finish.setDisable(true);
        } else {
            System.out.println("Success! The password satisfies the requirements.");

            // Hide all of the error messages elements
            View.label_errPassword.setText("");
            View.errPasswordPart1.setText("");
            View.errPasswordPart2.setText("");
            View.errPasswordPart3.setText("");

            // Tell the user that the password is valid with a green message
            View.validPassword.setTextFill(Color.GREEN);
            View.validPassword.setText("Success! The password satisfies the requirements.");

            // Enable the button so the user can accept this password
            View.button_Finish.setDisable(false);
        }
    }

    /*-********************************************************************************************
     *
     * Attributes used by the Finite State Machine to inform the user about what was and was not
     * valid and point to the character of the error.
     *
     */

    public static String passwordErrorMessage = "";    // The error message text
    public static String passwordInput = "";           // The input being processed
    public static int passwordIndexofError = -1;       // The index where the error was located

    public static boolean foundUpperCase = false;
    public static boolean foundLowerCase = false;
    public static boolean foundNumericDigit = false;
    public static boolean foundSpecialChar = false;
    public static boolean foundLongEnough = false;

    private static String inputLine = "";              // The input line
    private static char currentChar;                   // The current character in the line
    private static int currentCharNdx;                 // The index of the current character
    private static boolean running;                    // The flag that specifies if the FSM is running

    private static void displayInputState() {
        System.out.println(inputLine);
        int ndx = currentCharNdx;
        if (ndx < 0) ndx = 0;
        if (ndx > inputLine.length()) ndx = inputLine.length();
        System.out.println(inputLine.substring(0, ndx) + "?");
        System.out.println("The password size: " + inputLine.length()
                + "  |  The currentCharNdx: " + currentCharNdx
                + "  |  The currentChar: \"" + currentChar + "\"");
    }

    private static void updateFlags() {

        if (foundUpperCase) {
            View.label_UpperCase.setText("At least one upper case letter - Satisfied");
            View.label_UpperCase.setTextFill(Color.GREEN);
        }

        if (foundLowerCase) {
            View.label_LowerCase.setText("At least one lower case letter - Satisfied");
            View.label_LowerCase.setTextFill(Color.GREEN);
        }

        if (foundNumericDigit) {
            View.label_NumericDigit.setText("At least one numeric digit - Satisfied");
            View.label_NumericDigit.setTextFill(Color.GREEN);
        }

        if (foundSpecialChar) {
            View.label_SpecialChar.setText("At least one special character (_ @ # $ !) - Satisfied");
            View.label_SpecialChar.setTextFill(Color.GREEN);
        }

        if (foundLongEnough) {
            View.label_LongEnough.setText("At least eight characters - Satisfied");
            View.label_LongEnough.setTextFill(Color.GREEN);
        }
    }

    /**********
     * <p> Title: evaluatePassword - Public Method </p>
     *
     * <p> Description: This method evaluates the password using the directed graph processing.
     *
     * TP1 updates:
     * - Reject password length > 16 with "Password is too long."
     * - Restrict special characters to: _ @ # $ !
     *
     * @param input The input string evaluated by the directed graph processing
     * @return Empty string if valid; otherwise a helpful error message
     */
    public static String evaluatePassword(String input) {

        passwordErrorMessage = "";
        passwordIndexofError = 0;
        inputLine = (input == null) ? "" : input;
        currentCharNdx = 0;

        // Reset requirement flags
        foundUpperCase = false;
        foundLowerCase = false;
        foundNumericDigit = false;
        foundSpecialChar = false;
        foundLongEnough = false;

        // Empty check
        if (inputLine.length() == 0) {
            passwordIndexofError = 0;
            return "*** Error *** The password is empty!";
        }

        // TP1: max length protection
        if (inputLine.length() > PASSWORD_MAX_LEN) {
            passwordIndexofError = PASSWORD_MAX_LEN; // point at first character beyond the limit
            return "*** Error *** Password is too long. Maximum length is " + PASSWORD_MAX_LEN + ".";
        }

        // Setup for scan
        passwordInput = inputLine;
        currentChar = inputLine.charAt(0);
        running = true;

        while (running) {
            displayInputState();

            if (currentChar >= 'A' && currentChar <= 'Z') {
                System.out.println("Upper case letter found");
                foundUpperCase = true;

            } else if (currentChar >= 'a' && currentChar <= 'z') {
                System.out.println("Lower case letter found");
                foundLowerCase = true;

            } else if (currentChar >= '0' && currentChar <= '9') {
                System.out.println("Digit found");
                foundNumericDigit = true;

            } else if (ALLOWED_SPECIAL.indexOf(currentChar) >= 0) {
                System.out.println("Allowed special character found");
                foundSpecialChar = true;

            } else {
                passwordIndexofError = currentCharNdx;
                return "*** Error *** An invalid character has been found! Allowed specials: " + ALLOWED_SPECIAL;
            }

            if (currentCharNdx >= (PASSWORD_MIN_LEN - 1)) {
                System.out.println("At least 8 characters found");
                foundLongEnough = true;
            }

            // Advance
            currentCharNdx++;
            if (currentCharNdx >= inputLine.length()) {
                running = false;
            } else {
                currentChar = inputLine.charAt(currentCharNdx);
            }
            System.out.println();
        }

        // Build missing-requirements message
        String errMessage = "";

        if (!foundUpperCase) errMessage += "Upper case; ";
        if (!foundLowerCase) errMessage += "Lower case; ";
        if (!foundNumericDigit) errMessage += "Numeric digits; ";
        if (!foundSpecialChar) errMessage += "Special character (_ @ # $ !); ";
        if (!foundLongEnough) errMessage += "Long enough (>= " + PASSWORD_MIN_LEN + "); ";

        if (errMessage.isEmpty()) {
            passwordIndexofError = inputLine.length();
            return "";
        }

        passwordIndexofError = inputLine.length();
        return "*** Error *** " + errMessage + "conditions were not satisfied";
    }
}
