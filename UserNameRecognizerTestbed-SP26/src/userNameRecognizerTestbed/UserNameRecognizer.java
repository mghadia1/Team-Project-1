package userNameRecognizerTestbed;

public class UserNameRecognizer {
    /**
     * <p> Title: FSM-translated UserNameRecognizer. </p>
     * 
     * <p> Description: A demonstration of the mechanical translation of a Finite State Machine
     * diagram into an executable Java program using the UserName Recognizer. The code
     * detailed design is based on a while loop with a select list.</p>
     * 
     * <p> Copyright: Lynn Robert Carter © 2024 </p>
     * 
     * @author Lynn Robert Carter
     * 
     * @version 2.00 2026-01-27 Updated to match new FSM requirements:
     *              - must start with a letter
     *              - UNChar: A-Z, a-z, 0-9
     *              - special chars '-', '_', '.' only between two UNChars
     *              - length 4 to 32
     */

    /**********************************************************************************************
     * Result attributes to be used for GUI applications where a detailed error message and a
     * pointer to the character of the error will enhance the user experience.
     */
    public static String userNameRecognizerErrorMessage = ""; // The error message text
    public static String userNameRecognizerInput = "";        // The input being processed
    public static int userNameRecognizerIndexofError = -1;    // The index of error location

    private static int state = 0;                 // The current state value
    private static int nextState = 0;             // The next state value
    private static boolean finalState = false;    // Is this state a final state?
    private static String inputLine = "";         // The input line
    private static char currentChar;              // The current character in the line
    private static int currentCharNdx;            // The index of the current character
    private static boolean running;               // Is the FSM running
    private static int userNameSize = 0;          // The username size

    // Private method to display debugging data
    private static void displayDebuggingInfo() {
        if (currentCharNdx >= inputLine.length())
            System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state +
                    ((finalState) ? "       F   " : "           ") + "None");
        else
            System.out.println(((state > 99) ? " " : (state > 9) ? "  " : "   ") + state +
                    ((finalState) ? "       F   " : "           ") + "  " + currentChar + " " +
                    ((nextState > 99) ? "" : (nextState > 9) || (nextState == -1) ? "   " : "    ") +
                    nextState + "     " + userNameSize);
    }

    // Move to next character (or stop if done)
    private static void moveToNextCharacter() {
        currentCharNdx++;
        if (currentCharNdx < inputLine.length())
            currentChar = inputLine.charAt(currentCharNdx);
        else {
            currentChar = ' ';
            running = false;
        }
    }

    // UNChar is A-Z, a-z, 0-9
    private static boolean isUNChar(char c) {
        return (c >= 'A' && c <= 'Z') ||
               (c >= 'a' && c <= 'z') ||
               (c >= '0' && c <= '9');
    }

    // AlphaChar is A-Z, a-z
    private static boolean isAlphaChar(char c) {
        return (c >= 'A' && c <= 'Z') ||
               (c >= 'a' && c <= 'z');
    }

    // Special chars allowed only between UNChars
    private static boolean isSpecial(char c) {
        return (c == '-') || (c == '_') || (c == '.');
    }

    /**
     * Mechanical transformation of a Finite State Machine diagram into a Java method.
     * 
     * @param input The input string for the Finite State Machine
     * @return An output string that is empty if everything is okay, or an error message if not
     */
    public static String checkForValidUserName(String input) {
        // Check to ensure that there is input to process
        if (input == null || input.length() <= 0) {
            userNameRecognizerIndexofError = 0;
            return "\n*** ERROR *** The input is empty";
        }

        // Initialize FSM globals
        state = 0;
        inputLine = input;
        currentCharNdx = 0;
        currentChar = input.charAt(0);

        userNameRecognizerInput = input;
        running = true;
        nextState = -1;

        System.out.println("\nCurrent Final Input  Next");
        System.out.println("State   State Char  State  Size");

        // semantic action at start
        userNameSize = 0;

        // FSM loop
        while (running) {
            switch (state) {
            case 0:
                // State 0: first char must be a letter (AlphaChar)
                if (isAlphaChar(currentChar)) {
                    nextState = 1;
                    userNameSize++;
                } else {
                    running = false;
                }
                break;

            case 1:
                // State 1: UNChar stays in state 1
                //          special '-', '_', '.' goes to state 2
                if (isUNChar(currentChar)) {
                    nextState = 1;
                    userNameSize++;
                } else if (isSpecial(currentChar)) {
                    nextState = 2;
                    userNameSize++;
                } else {
                    running = false;
                }

                if (userNameSize > 32)
                    running = false;
                break;

            case 2:
                // State 2: after a special char, must see a UNChar, then go back to state 1
                if (isUNChar(currentChar)) {
                    nextState = 1;
                    userNameSize++;
                } else {
                    running = false;
                }

                if (userNameSize > 32)
                    running = false;
                break;

            default:
                running = false;
                break;
            }

            if (running) {
                displayDebuggingInfo();
                moveToNextCharacter();
                state = nextState;

                // final state is only state 1 (cannot end right after special char)
                finalState = (state == 1);

                nextState = -1;
            }
        }

        displayDebuggingInfo();
        System.out.println("The loop has ended.");

        // Decide error vs success
        userNameRecognizerIndexofError = currentCharNdx;
        userNameRecognizerErrorMessage = "\n*** ERROR *** ";

        // success only if we ended in final state and consumed all input
        if (state == 1 && currentCharNdx >= input.length()) {
            if (userNameSize < 4) {
                userNameRecognizerErrorMessage += "A UserName must have at least 4 characters.\n";
                return userNameRecognizerErrorMessage;
            }
            if (userNameSize > 32) {
                userNameRecognizerErrorMessage += "A UserName must have no more than 32 characters.\n";
                return userNameRecognizerErrorMessage;
            }

            userNameRecognizerIndexofError = -1;
            userNameRecognizerErrorMessage = "";
            return userNameRecognizerErrorMessage;
        }

        // Handle specific errors based on state
        switch (state) {
        case 0:
            userNameRecognizerErrorMessage += "A UserName must start with a letter A-Z or a-z.\n";
            return userNameRecognizerErrorMessage;

        case 1:
            if (userNameSize > 32) {
                userNameRecognizerErrorMessage += "A UserName must have no more than 32 characters.\n";
                return userNameRecognizerErrorMessage;
            }
            if (currentCharNdx < input.length()) {
                userNameRecognizerErrorMessage += "A UserName may only contain A-Z, a-z, 0-9, and '-', '_', '.' (special chars only between two letters/digits).\n";
                return userNameRecognizerErrorMessage;
            }
            userNameRecognizerErrorMessage += "A UserName is not valid.\n";
            return userNameRecognizerErrorMessage;

        case 2:
            userNameRecognizerErrorMessage += "A special character '-', '_', or '.' must be followed by A-Z, a-z, or 0-9.\n";
            return userNameRecognizerErrorMessage;

        default:
            userNameRecognizerErrorMessage += "An internal error occurred.\n";
            return userNameRecognizerErrorMessage;
        }
    }
}
