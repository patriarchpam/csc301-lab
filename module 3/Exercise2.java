// Import the Stack collection from java.util
import java.util.Stack;

/**
 * Exercise 2: Syntax Error Reporting for Balanced Brackets.
 * Reports the exact index of unmatched or mismatched brackets and displays
 * a compiler-like visual arrow pointer (^) beneath the offending character.
 */
public class Exercise2 {

    // Helper data structure that bundles a bracket character with its 0-based string index
    static class Bracket {
        char ch;    // The bracket character itself ('(', '{', '[')
        int index;  // The position where this bracket appeared in the expression string

        // Constructor to initialize the character and its index
        Bracket(char ch, int index) {
            this.ch = ch;       // Assign bracket character
            this.index = index; // Assign index position
        }
    }

    // Returns true if open and close characters form a matching pair
    static boolean matches(char open, char close) {
        return (open == '(' && close == ')') || // Matching parentheses
               (open == '{' && close == '}') || // Matching braces
               (open == '[' && close == ']');   // Matching square brackets
    }

    // Inspects the expression string and pinpoints syntax errors by exact character index
    static void checkBalance(String expr) {
        // Print the original expression being analyzed
        System.out.println("Expression: " + expr);

        // Stack to store Bracket objects (containing both character and index)
        Stack<Bracket> stack = new Stack<>();

        // Loop through each index 'i' in the string from 0 to length - 1
        for (int i = 0; i < expr.length(); i++) {
            // Get character at index position i
            char ch = expr.charAt(i);

            // Check if current character is an opening bracket
            if (ch == '(' || ch == '{' || ch == '[') {
                // Push both the bracket and its index onto the stack
                stack.push(new Bracket(ch, i));
            // Check if current character is a closing bracket
            } else if (ch == ')' || ch == '}' || ch == ']') {
                // If closing bracket occurs but stack is empty, an extra closing bracket was found
                if (stack.isEmpty()) {
                    // Report error pointing directly to index 'i'
                    printCompilerError(expr, i, "Unmatched closing bracket '" + ch + "' (no matching opening bracket)");
                    return; // Stop evaluation upon finding first syntax error
                }

                // Pop the most recent opening bracket from the stack
                Bracket open = stack.pop();

                // If bracket types do not match (e.g., '{' closed by ']')
                if (!matches(open.ch, ch)) {
                    // Report mismatch error pointing to the closing bracket index 'i'
                    printCompilerError(expr, i, "Mismatched bracket: expected closing for '" + open.ch + 
                                       "' (opened at index " + open.index + "), but found '" + ch + "'");
                    return; // Stop evaluation upon finding first syntax error
                }
            }
            // Skip non-bracket characters (letters, numbers, spaces, operators)
        }

        // After scanning the entire string, check if any opening brackets were never closed
        if (!stack.isEmpty()) {
            // Pop the unclosed opening bracket to report its location
            Bracket unmatchedOpen = stack.pop();
            // Point the error indicator to the opening bracket's original index
            printCompilerError(expr, unmatchedOpen.index, "Unmatched opening bracket '" + unmatchedOpen.ch + "' was never closed");
            return;
        }

        // If the stack is empty and loop completed without errors, the expression is balanced
        System.out.println("Result: BALANCED (No syntax errors detected)\n");
    }

    // Formats and prints a compiler-style syntax error message with a pointer (^)
    static void printCompilerError(String expr, int index, String message) {
        // Print the offending expression line
        System.out.println(expr);

        // Construct spaces to align the pointer under the exact error index
        StringBuilder pointer = new StringBuilder();
        for (int i = 0; i < index; i++) {
            pointer.append(' '); // Append spaces up to the error index
        }
        pointer.append('^'); // Place caret pointing directly at the offending character

        // Print the visual pointer line
        System.out.println(pointer.toString());

        // Print clear diagnostic message explaining what went wrong and at which index
        System.out.println("Syntax Error at index " + index + ": " + message + "\n");
    }

    // Main method to test various syntax error scenarios
    public static void main(String[] args) {
        // Print test suite header
        System.out.println("=== Testing Exercise 2: Bracket Balance with Index Reporting ===\n");

        // Case 1: Perfectly balanced expression (all pairs match)
        checkBalance("{(a+b)*[c-d]}");

        // Case 2: Mismatched bracket type (opening '[' at 0 closed by '}' at 12)
        checkBalance("[(a+b)*(c-d)}");

        // Case 3: Extra closing bracket (closing ']' at index 9 with no opener)
        checkBalance("(a+b)*c-d]");

        // Case 4: Mismatched bracket (opening '(' at index 7 closed by ']' at index 11)
        checkBalance("[(a+b)*(c-d]");

        // Case 5: Unclosed opening bracket '{' at index 0 never closed
        checkBalance("{a + b");
    }
}
