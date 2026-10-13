// Import Stack class to store operands during prefix evaluation
import java.util.Stack;

/**
 * Exercise 3: Prefix (Polish) Expression Evaluator.
 * Evaluates expressions where operators precede operands (e.g., "+ 2 * 3 4").
 * Requires scanning tokens from RIGHT to LEFT.
 */
public class Exercise3 {

    // Evaluates a space-delimited prefix expression and returns the integer result
    static int evaluatePrefix(String expr) {
        // Stack to store integer operands
        Stack<Integer> stack = new Stack<>();

        // Split the expression string by whitespace into an array of tokens
        String[] tokens = expr.trim().split("\\s+");

        // Scan tokens from RIGHT to LEFT (index tokens.length - 1 down to 0)
        // This is the fundamental difference from postfix evaluation
        for (int i = tokens.length - 1; i >= 0; i--) {
            // Get current token at index i
            String token = tokens[i];

            // If the token is an operator (+, -, *, /)
            if (isOperator(token)) {
                // When scanning right-to-left:
                // The top of the stack is the left operand (a)
                int a = stack.pop();
                // The next element popped is the right operand (b)
                int b = stack.pop();

                // Compute (a op b) and push the result back onto the stack
                stack.push(applyOp(a, b, token.charAt(0)));
            } else {
                // Token is a numeric operand: parse to int and push onto the stack
                stack.push(Integer.parseInt(token));
            }
        }
        // After scanning all tokens, the final calculated result is on top of the stack
        return stack.pop();
    }

    // Helper method to verify whether a string is one of the four basic operators
    static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") ||
               token.equals("*") || token.equals("/");
    }

    // Executes the operator on operands 'a' and 'b'
    static int applyOp(int a, int b, char op) {
        switch (op) {
            case '+': return a + b; // Performs addition
            case '-': return a - b; // Performs subtraction (a minus b)
            case '*': return a * b; // Performs multiplication
            case '/': return a / b; // Performs division (a divided by b)
        }
        // Throws error if an invalid operator character was passed
        throw new IllegalArgumentException("Invalid operator: " + op);
    }

    // Main method to test prefix expressions
    public static void main(String[] args) {
        // Test 1: Equivalent prefix form of Problem 3's example "2 3 4 * +" -> 2 + (3 * 4) = 14
        String prefix1 = "+ 2 * 3 4";
        System.out.println("Prefix expression: " + prefix1);
        System.out.println("Result = " + evaluatePrefix(prefix1)); // Prints 14

        // Test 2: Nested operations with subtraction -> ((2 + (3 * 4)) - 5) = 14 - 5 = 9
        String prefix2 = "- + 2 * 3 4 5";
        System.out.println("\nPrefix expression: " + prefix2);
        System.out.println("Result = " + evaluatePrefix(prefix2)); // Prints 9

        // Test 3: Division order verification -> (10 * 6) / 4 = 60 / 4 = 15
        String prefix3 = "/ * 10 6 4";
        System.out.println("\nPrefix expression: " + prefix3);
        System.out.println("Result = " + evaluatePrefix(prefix3)); // Prints 15
    }
}
