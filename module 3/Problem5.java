// Import Stack class from java.util for operators
import java.util.Stack;
 
/**
 * Problem 5: Infix to Postfix Converter using Shunting-Yard Algorithm.
 * Converts expressions like "a+b*c" to postfix notation "abc*+".
 */
public class Problem5 {
 
    // Returns numeric precedence level of an operator (higher number = higher precedence)
    static int precedence(char op) {
        switch (op) {
            case '+': 
            case '-': 
                return 1; // Lowest precedence for addition and subtraction
            case '*': 
            case '/': 
                return 2; // Higher precedence for multiplication and division
            default: 
                return -1; // Non-operator or invalid character
        }
    }
 
    // Helper method to check if a character is one of the four supported operators
    static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
 
    // Converts an infix mathematical expression string into a postfix expression string
    static String convert(String infix) {
        // StringBuilder to accumulate output postfix characters
        StringBuilder output = new StringBuilder();

        // Stack to temporarily hold operator characters while respecting precedence rules
        Stack<Character> opStack = new Stack<>();
 
        // Iterate through each character in the input string
        for (char ch : infix.toCharArray()) {
            // Case 1: Operands (letters or digits like 'a', 'b', '1', etc.)
            if (Character.isLetterOrDigit(ch)) {
                // Operands are immediately appended to the postfix output
                output.append(ch);
            // Case 2: Opening parenthesis '('
            } else if (ch == '(') {
                // Push onto the operator stack to mark the beginning of a sub-expression
                opStack.push(ch);
            // Case 3: Closing parenthesis ')'
            } else if (ch == ')') {
                // Pop and append all operators until the matching '(' is encountered
                while (!opStack.isEmpty() && opStack.peek() != '(') {
                    output.append(opStack.pop());
                }
                // Discard the matching '(' from the stack
                opStack.pop();
            // Case 4: Operators (+, -, *, /)
            } else if (isOperator(ch)) {
                // Pop operators of greater or equal precedence from the stack to the output
                // Stop popping if the stack is empty, or if an opening '(' is reached
                while (!opStack.isEmpty() && opStack.peek() != '(' &&
                       precedence(opStack.peek()) >= precedence(ch)) {
                    output.append(opStack.pop());
                }
                // Push the incoming operator onto the stack
                opStack.push(ch);
            }
        }

        // Pop and append any remaining operators left in the stack to the output
        while (!opStack.isEmpty()) {
            output.append(opStack.pop());
        }

        // Return the final postfix string
        return output.toString();
    }
 
    // Main method to test sample conversions
    public static void main(String[] args) {
        // Test 1: Precedence test -> * has higher precedence than +, so b*c evaluates first
        String infix = "a+b*c";
        System.out.println("Infix:   " + infix);
        System.out.println("Postfix: " + convert(infix)); // Expected: abc*+
 
        // Test 2: Parentheses override -> (a+b) evaluates before *(c-d)
        String infix2 = "(a+b)*(c-d)";
        System.out.println("Infix:   " + infix2);
        System.out.println("Postfix: " + convert(infix2)); // Expected: ab+cd-*
    }
}
