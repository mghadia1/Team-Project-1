package userNameRecognizerTestbed;

/**
 * <p> Title: FSM-translated UserNameRecognizer. </p>
 * 
 * <p> Description: A demonstration of the mechanical translation of Finite State Machine 
 * diagram into an executable Java program using the UserName Recognizer. The code 
 * detailed design is based on a while loop with a select list</p>
 * 
 * <p> Copyright: Lynn Robert Carter © 2024 </p>
 * 
 * @author Lynn Robert Carter (modified for HW1)
 * @version 1.02 2026-01-27 Modified to require first character to be a letter and enforce
 *                       stricter special-character placement rules.
 */
public class UserNameRecognizer {

	/* Result attributes to be used for GUI applications where a detailed error message and
	 * a pointer to the character of the error will enhance the user experience.
	 */
	public static String userNameRecognizerErrorMessage = "";	// The error message text
	public static String userNameRecognizerInput = "";			// The input being processed
	public static int userNameRecognizerIndexofError = -1;		// The index of error location

	/* Internal FSM working variables */
	private static int state = 0;						// The current state value
	private static int nextState = 0;					// The next state value
	private static boolean finalState = false;			// Is this state a final state?
	private static String inputLine = "";				// The input line
	private static char currentChar;					// The current character in the line
	private static int currentCharNdx;					// The index of the current character
	private static boolean running;						// The flag that specifies if the FSM is running
	private static int userNameSize = 0;				// The username size counter

	/* Length bounds (assignment requires min 4, max 32) */
	private static final int MIN_LENGTH = 4;
	private static final int MAX_LENGTH = 32;

	/**
	 * Display debugging info (kept in original style).
	 */
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

	/**
	 * Move to the next character in the input. If there is no next character,
	 * set currentChar to a blank and stop the FSM running (EOF).
	 */
	private static void moveToNextCharacter() {
		currentCharNdx++;
		if (currentCharNdx < inputLine.length())
			currentChar = inputLine.charAt(currentCharNdx);
		else {
			currentChar = ' ';
			running = false;   // EOF will be handled after loop
		}
	}

	/**
	 * Check the given username against the FSM rules described in the assignment.
	 * Returns an empty string on success; otherwise returns a helpful error message.
	 *
	 * @param input the username to check
	 * @return "" if valid; otherwise an error message describing the rejection
	 */
	public static String checkForValidUserName(String input) {
		// Check for empty input (immediate error)
		if (input == null || input.length() <= 0) {
			userNameRecognizerIndexofError = 0;
			return "\n*** ERROR *** The input is empty";
		}

		// Initialize FSM variables
		state = 0;							// Start state
		inputLine = input;
		currentCharNdx = 0;
		currentChar = input.charAt(0);
		userNameRecognizerInput = input;
		running = true;
		nextState = -1;
		userNameSize = 0;
		finalState = false;
		userNameRecognizerErrorMessage = "";
		userNameRecognizerIndexofError = -1;

		// Print header for debug trace (keeps original format)
		System.out.println("\nCurrent Final Input  Next\nState   State Char  State  Size");

		/* 
		 * State numbering for clarity:
		 * 0 = Start (no characters processed yet)
		 * 1 = LetterSeen (last was a letter)  <-- final state
		 * 2 = DigitSeen  (last was a digit)   <-- final state
		 * 3 = SpecialSeen (last was special)  <-- NOT final
		 */

		while (running) {
			switch (state) {
			case 0:
				// Start state: only a LETTER is acceptable as the first character
				// LETTER -> state 1 (LetterSeen)
				if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
					nextState = 1;
					userNameSize++;
				}
				else {
					// Any other first character is an immediate error
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "First character must be a letter (A-Z or a-z).";
					return userNameRecognizerErrorMessage;
				}
				// check max length guard (redundant here but matches original pattern)
				if (userNameSize > MAX_LENGTH) {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Username too long (maximum " + MAX_LENGTH + " characters).";
					return userNameRecognizerErrorMessage;
				}
				break;

			case 1:
				// LetterSeen:
				// LETTER -> stay in 1
				if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
					nextState = 1;
					userNameSize++;
				}
				// DIGIT -> go to 2
				else if (currentChar >= '0' && currentChar <= '9') {
					nextState = 2;
					userNameSize++;
				}
				// SPECIAL (-, _, .) -> go to 3
				else if (currentChar == '-' || currentChar == '_' || currentChar == '.') {
					nextState = 3;
					userNameSize++;
				}
				// Any other character -> error
				else {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Invalid character '" + currentChar + "' at position " + (currentCharNdx + 1) + ". Allowed: letters, digits, '-', '_', '.'.";
					return userNameRecognizerErrorMessage;
				}
				// length guard
				if (userNameSize > MAX_LENGTH) {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Username too long (maximum " + MAX_LENGTH + " characters).";
					return userNameRecognizerErrorMessage;
				}
				break;

			case 2:
				// DigitSeen:
				// DIGIT -> stay in 2
				if (currentChar >= '0' && currentChar <= '9') {
					nextState = 2;
					userNameSize++;
				}
				// LETTER -> go to 1
				else if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
					nextState = 1;
					userNameSize++;
				}
				// SPECIAL -> NOT allowed here (we decided specials must be between letters only)
				else if (currentChar == '-' || currentChar == '_' || currentChar == '.') {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Special characters ('-','_','.') may only appear between letters (not adjacent to digits). Error at position " + (currentCharNdx + 1) + ".";
					return userNameRecognizerErrorMessage;
				}
				else {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Invalid character '" + currentChar + "' at position " + (currentCharNdx + 1) + ".";
					return userNameRecognizerErrorMessage;
				}
				// length guard
				if (userNameSize > MAX_LENGTH) {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Username too long (maximum " + MAX_LENGTH + " characters).";
					return userNameRecognizerErrorMessage;
				}
				break;

			case 3:
				// SpecialSeen:
				// Only a LETTER may follow a special in this stricter FSM
				if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
					nextState = 1; // move to LetterSeen
					userNameSize++;
				}
				// If next is digit, special, or invalid char -> error
				else if (currentChar >= '0' && currentChar <= '9') {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Special characters must be followed by a letter. Found a digit at position " + (currentCharNdx + 1) + ".";
					return userNameRecognizerErrorMessage;
				}
				else if (currentChar == '-' || currentChar == '_' || currentChar == '.') {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Consecutive special characters are not allowed. Error at position " + (currentCharNdx + 1) + ".";
					return userNameRecognizerErrorMessage;
				}
				else {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Invalid character '" + currentChar + "' at position " + (currentCharNdx + 1) + ".";
					return userNameRecognizerErrorMessage;
				}
				// length guard
				if (userNameSize > MAX_LENGTH) {
					userNameRecognizerIndexofError = currentCharNdx;
					userNameRecognizerErrorMessage = "Username too long (maximum " + MAX_LENGTH + " characters).";
					return userNameRecognizerErrorMessage;
				}
				break;

			default:
				// Should not happen
				userNameRecognizerErrorMessage = "Internal error: invalid FSM state.";
				return userNameRecognizerErrorMessage;
			}

			// Display trace line (keeps original debugging trace behavior)
			displayDebuggingInfo();

			// Move to next character
			moveToNextCharacter();

			// Advance to next state
			state = nextState;

			// Set finalState flag for states that are accepting
			finalState = (state == 1 || state == 2);

			// Reset nextState sentinel
			nextState = -1;
		} // end while running

		// We reached EOF: now check whether the current state is an accepting state
		// Remember: at EOF the currentChar has been set to ' ' and running=false
		// Acceptance if in LetterSeen (1) or DigitSeen (2) and length constraints satisfied.
		if (!(state == 1 || state == 2)) {
			// If we ended in SpecialSeen (3), that's an error: cannot end on special
			if (state == 3) {
				userNameRecognizerIndexofError = input.length() - 1;
				userNameRecognizerErrorMessage = "UserName cannot end with a special character ('-','_','.').";
				return userNameRecognizerErrorMessage;
			}
			// Any other unaccepted state
			userNameRecognizerErrorMessage = "Invalid end state for input.";
			return userNameRecognizerErrorMessage;
		}

		// Now check length constraints
		if (userNameSize < MIN_LENGTH) {
			userNameRecognizerIndexofError = input.length() - 1;
			userNameRecognizerErrorMessage = "A UserName must have at least " + MIN_LENGTH + " characters.";
			return userNameRecognizerErrorMessage;
		}
		if (userNameSize > MAX_LENGTH) {
			userNameRecognizerIndexofError = input.length() - 1;
			userNameRecognizerErrorMessage = "A UserName must have no more than " + MAX_LENGTH + " characters.";
			return userNameRecognizerErrorMessage;
		}

		// If we get here the username is valid
		userNameRecognizerIndexofError = -1;
		userNameRecognizerErrorMessage = "";
		return userNameRecognizerErrorMessage;
	}
}
