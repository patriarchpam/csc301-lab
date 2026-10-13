// Import Stack class from java.util for operators
import java.util.Stack;

/**
 * Exercise 5: Infix to Postfix with Exponentiation (^) and Right-Associativity
 * 
 * Right-Associativity Explanation:
 * -----------------------------------------------------------------------------------------
 * 1. For left-associative operators (+, -, *, /):
 *    When the incoming operator has EQUAL precedence to the operator on top of the stack,
 *    the top of stack must be POPPED first because it was encountered earlier (left-to-right)
 *    and should evaluate first.
 *    Condition: precedence(opStack.peek()) >= precedence(ch)
 * 
 * 2. For right-associative operators (^):
 *    When the incoming operator has EQUAL precedence to the operator on top of the stack
 *    (e.g., encountering '^' when '^' is already on top of the stack), the existing '^'
 *    must NOT be popped. Instead, the new '^' must be PUSHED onto the stack so that it
 *    evaluates first when popped (right-to-left evaluation order).
 *    Condition: precedence(opStack.peek()) > precedence(ch) (strictly greater)
 */
public class Exercise5 {

    // Returns numeric precedence of each operator
    static int precedence(char op) {
        switch (op) {
            case '+': 
            case '-': 
                return 1; // Lowest precedence
            case '*': 
            case '/': 
                return 2; // Medium precedence
            case '^': 
                return 3; // Highest precedence for exponentiation
            default:  
                return -1; // Non-operator
        }
    }

    // Identifies whether character is an arithmetic operator
    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // Returns true if the operator is right-associative (i.e., '^')
    static boolean isRightAssociative(char op) {
        return op == '^';
    }

    // Converts infix string to postfix with right-associative support
    static String convert(String infix) {
        // Accumulator for building the postfix expression
        StringBuilder output = new StringBuilder();

        // Operator stack to hold operators and parentheses
        Stack<Character> opStack = new Stack<>();

        // Loop through each character of the input expression
        for (char ch : infix.toCharArray()) {
            // Ignore spaces in expressions (e.g. "a ^ b")
            if (Character.isWhitespace(ch)) {
                continue;
            }

            // Case 1: Operands (letters, digits) go directly to output
            if (Character.isLetterOrDigit(ch)) {
                output.append(ch);
            // Case 2: Opening parenthesis '(' pushed to stack
            } else if (ch == '(') {
                opStack.push(ch);
            // Case 3: Closing parenthesis ')' pops until '(' is found
            } else if (ch == ')') {
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    output.append(opStack.pop());
                }
                opStack.pop(); // Discard '(' from stack
            // Case 4: Operator (+, -, *, /, ^)
            } else if (isOperator(ch)) {
                // Modified popping condition to handle both left- and right-associativity:
                // - Left-associative: pop while precedence(top) >= precedence(ch)
                // - Right-associative: pop while precedence(top) > precedence(ch)
                while (!opStack.isEmpty() && opStack.peek() != '(' &&
                       shouldPop(opStack.peek(), ch)) {
                    output.append(opStack.pop());
                }
                // Push the incoming operator onto the stack
                opStack.push(ch);
            }
        }

        // Flush all remaining operators from the stack to the output
        while (!opStack.isEmpty()) {
            output.append(opStack.pop());
        }

        // Return completed postfix string
        return output.toString();
    }

    /**
     * Determines whether the operator on top of the stack should be popped before pushing 'incoming'.
     */
    static boolean shouldPop(char top, char incoming) {
        int precTop = precedence(top);
        int precIncoming = precedence(incoming);

        // If incoming operator is right-associative (like '^'):
        if (isRightAssociative(incoming)) {
            // Only pop if the top of stack has STRICTLY GREATER precedence.
            // If precedence is EQUAL (e.g., '^' on top, '^' incoming), do NOT pop!
            // This ensures incoming '^' is pushed on top and evaluates first (right-to-left).
            return precTop > precIncoming;
        } else {
            // For left-associative operators (+, -, *, /):
            // Pop if top has GREATER OR EQUAL precedence (evaluates left-to-right).
            return precTop >= precIncoming;
        }
    }

    // Main method testing right-associativity and standard operations
    public static void main(String[] args) {
        System.out.println("=== Testing Exercise 5: Exponentiation (^) and Right-Associativity ===\n");

        // Primary test from prompt: a^b^c -> evaluates as a^(b^c) -> postfix: abc^^
        String test1 = "a^b^c";
        System.out.println("Infix:   " + test1);
        System.out.println("Postfix: " + convert(test1)); // Expected: abc^^

        // Mixed precedence test: c^d evaluates first, then b*(c^d), then a + ...
        String test2 = "a+b*c^d";
        System.out.println("\nInfix:   " + test2);
        System.out.println("Postfix: " + convert(test2)); // Expected: abcd^*+

        // Parentheses explicitly forcing left-associativity: (a^b)^c -> ab^c^
        String test3 = "(a^b)^c";
        System.out.println("\nInfix:   " + test3);
        System.out.println("Postfix: " + convert(test3)); // Expected: ab^c^

        // Numerical verification: 2^3^2 = 2^(3^2) = 2^9 = 512
        String test4 = "2^3^2";
        System.out.println("\nInfix:   " + test4 + " (2^(3^2) = 2^9 = 512)");
        System.out.println("Postfix: " + convert(test4)); // Expected: 232^^
    }
}
