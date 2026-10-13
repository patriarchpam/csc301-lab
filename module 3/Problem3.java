// Import Stack class to hold operands during postfix evaluation
import java.util.Stack;
 
/**
 * Problem 3: Postfix (Reverse Polish) Expression Evaluator.
 * Scans tokens left-to-right and uses a stack to evaluate expressions.
 */
public class Problem3 {
 
    // Evaluates a space-delimited postfix expression and returns the integer result
    static int evaluate(String expr) {
        // Stack to store integer operands awaiting operators
        Stack<Integer> stack = new Stack<>();

        // Split expression into tokens using one or more spaces ("\\s+") as delimiter
        String[] tokens = expr.trim().split("\\s+");
 
        // Iterate through each token from left to right
        for (String token : tokens) {
            // Check if current token is an arithmetic operator (+, -, *, /)
            if (isOperator(token)) {
                // Pop the second operand (it was pushed last onto the stack)
                int b = stack.pop();
                // Pop the first operand (it was pushed earlier)
                int a = stack.pop();
                // Compute result of (a op b) and push the result back onto the stack
                stack.push(applyOp(a, b, token.charAt(0)));
            } else {
                // Token is a numeric operand: parse string to int and push to stack
                stack.push(Integer.parseInt(token));
            }
        }
        // At the end of valid postfix expression, exactly one value remains: the final answer
        return stack.pop();
    }
 
    // Helper method to identify if a token string represents an operator
    static boolean isOperator(String token) {
        // Returns true if token is "+", "-", "*", or "/"
        return token.equals("+") || token.equals("-") ||
               token.equals("*") || token.equals("/");
    }
 
    // Performs the arithmetic operation 'op' with operands 'a' (left) and 'b' (right)
    static int applyOp(int a, int b, char op) {
        // Switch on the operator character
        switch (op) {
            case '+': return a + b; // Addition
            case '-': return a - b; // Subtraction: order matters (a - b)
            case '*': return a * b; // Multiplication
            case '/': return a / b; // Integer division: order matters (a / b)
        }
        // Throw exception if an unsupported operator is encountered
        throw new IllegalArgumentException("Invalid operator: " + op);
    }
 
    // Main method to test postfix evaluation
    public static void main(String[] args) {
        // Sample postfix string: "2 3 4 * +" represents 2 + (3 * 4) = 14
        String postfix = "2 3 4 * +";

        // Print the input expression
        System.out.println("Postfix expression: " + postfix);

        // Evaluate and print result (expected: 14)
        System.out.println("Result = " + evaluate(postfix));
    }
}
