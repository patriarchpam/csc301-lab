// Import the Stack collection from java.util to store opening brackets
import java.util.Stack;
 
/**
 * Problem 2: Balanced Brackets Checker using Java's built-in Stack.
 */
public class Problem2 {
 
    // Evaluates whether opening and closing brackets in an expression are properly matched
    static boolean isBalanced(String expr) {
        // Stack to keep track of opening bracket characters in LIFO order
        Stack<Character> stack = new Stack<>();
 
        // Convert the input string into a character array and iterate through each character
        for (char ch : expr.toCharArray()) {
            // Check if current character is an opening bracket: '(', '{', or '['
            if (ch == '(' || ch == '{' || ch == '[') {
                // Push opening bracket onto the stack to await its matching closing pair
                stack.push(ch);
            // Check if current character is a closing bracket: ')', '}', or ']'
            } else if (ch == ')' || ch == '}' || ch == ']') {
                // If a closing bracket appears but stack is empty, there is no opener to match it
                if (stack.isEmpty()) {
                    return false; // Immediately unbalanced (e.g., extra closing bracket)
                }
                // Pop the most recently seen opening bracket from the top of the stack
                char open = stack.pop();
                // Check if the popped opening bracket corresponds to the current closing bracket
                if (!matches(open, ch)) {
                    return false; // Mismatched types, e.g., '(' closed with '}'
                }
            }
            // All non-bracket characters (alphanumeric, spaces, operators like +, *, -) are skipped
        }
        // If stack is empty, every opening bracket was matched; if not, an unclosed opener remains
        return stack.isEmpty();
    }
 
    // Helper method that returns true if the open and close characters form a valid pair
    static boolean matches(char open, char close) {
        // Parentheses match: '(' with ')'
        return (open == '(' && close == ')') ||
               // Curly braces match: '{' with '}'
               (open == '{' && close == '}') ||
               // Square brackets match: '[' with ']'
               (open == '[' && close == ']');
    }
 
    // Main method to test balanced and unbalanced sample expressions
    public static void main(String[] args) {
        // Sample 1: Properly nested brackets -> {} contains (), and []
        String expr1 = "{(a+b)*[c-d]}";
        // Sample 2: Unbalanced -> outer '[' is never closed, inner '(' is never closed properly
        String expr2 = "[(a+b)*(c-d]";
 
        // Evaluates expr1 (expected: true)
        System.out.println(expr1 + "  -> Balanced: " + isBalanced(expr1));
        // Evaluates expr2 (expected: false)
        System.out.println(expr2 + "  -> Balanced: " + isBalanced(expr2));
    }
}
