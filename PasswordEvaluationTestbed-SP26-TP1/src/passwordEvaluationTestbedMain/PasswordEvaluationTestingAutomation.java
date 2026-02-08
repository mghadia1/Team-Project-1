package passwordEvaluationTestbedMain;

/*******
 * <p> Title: PasswordEvaluationTestingAutomation Class. </p>
 *
 * <p> Description: A Java demonstration for semi-automated tests </p>
 *
 * <p> Copyright: Lynn Robert Carter © 2022 </p>
 *
 * @author Lynn Robert Carter
 *
 * @version 2.10 2026-02-05 Updated test cases to align with TP1 password rules:
 *              Min length 8, Max length 16, requires upper/lower/digit/special from _@#$!
 *
 */
public class PasswordEvaluationTestingAutomation {

    static int numPassed = 0;
    static int numFailed = 0;

    public static void main(String[] args) {

        System.out.println("______________________________________");
        System.out.println("\nTesting Automation (TP1 Password Rules)");

        // Positive (valid)
        performTestCase(1, "Aa1!aaaa", true);

        // Too short
        performTestCase(2, "Aa1!aaa", false);

        // Missing uppercase
        performTestCase(3, "aa1!aaaa", false);

        // Missing lowercase
        performTestCase(4, "AA1!AAAA", false);

        // Missing digit
        performTestCase(5, "Aa!aaaaa", false);

        // Missing special
        performTestCase(6, "Aa1aaaaa", false);

        // Invalid special character (not allowed)
        performTestCase(7, "Aa1%aaaa", false);

        // Too long (17 chars) -> should fail
        performTestCase(8, "Aa1!aaaaaaaaaaaaa", false);

        // Max length boundary (16 chars) -> should pass if it meets rules
        performTestCase(9, "Aa1!aaaaaaaaaaaa", true);

        System.out.println("____________________________________________________________________________");
        System.out.println();
        System.out.println("Number of tests passed: " + numPassed);
        System.out.println("Number of tests failed: " + numFailed);
    }

    private static void performTestCase(int testCase, String inputText, boolean expectedPass) {

        System.out.println("____________________________________________________________________________\n\nTest case: " + testCase);
        System.out.println("Input: \"" + inputText + "\"");
        System.out.println("______________");
        System.out.println("\nFinite state machine execution trace:");

        String resultText = passwordPopUpWindow.Model.evaluatePassword(inputText);

        System.out.println();

        if (!resultText.isEmpty()) {
            if (expectedPass) {
                System.out.println("***Failure*** The password <" + inputText + "> is invalid."
                        + "\nBut it was supposed to be valid, so this is a failure!\n");
                System.out.println("Error message: " + resultText);
                numFailed++;
            } else {
                System.out.println("***Success*** The password <" + inputText + "> is invalid."
                        + "\nBut it was supposed to be invalid, so this is a pass!\n");
                System.out.println("Error message: " + resultText);
                numPassed++;
            }
        } else {
            if (expectedPass) {
                System.out.println("***Success*** The password <" + inputText + "> is valid, so this is a pass!");
                numPassed++;
            } else {
                System.out.println("***Failure*** The password <" + inputText + "> was judged as valid"
                        + "\nBut it was supposed to be invalid, so this is a failure!");
                numFailed++;
            }
        }
        displayEvaluation();
    }

    private static void displayEvaluation() {

        if (passwordPopUpWindow.Model.foundUpperCase)
            System.out.println("At least one upper case letter - Satisfied");
        else
            System.out.println("At least one upper case letter - Not Satisfied");

        if (passwordPopUpWindow.Model.foundLowerCase)
            System.out.println("At least one lower case letter - Satisfied");
        else
            System.out.println("At least one lower case letter - Not Satisfied");

        if (passwordPopUpWindow.Model.foundNumericDigit)
            System.out.println("At least one digit - Satisfied");
        else
            System.out.println("At least one digit - Not Satisfied");

        if (passwordPopUpWindow.Model.foundSpecialChar)
            System.out.println("At least one allowed special (_ @ # $ !) - Satisfied");
        else
            System.out.println("At least one allowed special (_ @ # $ !) - Not Satisfied");

        if (passwordPopUpWindow.Model.foundLongEnough)
            System.out.println("At least 8 characters - Satisfied");
        else
            System.out.println("At least 8 characters - Not Satisfied");
    }
}
