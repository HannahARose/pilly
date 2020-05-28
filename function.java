import java.util.ArrayList;
import java.lang.Object;

/**
 * A class to encapsulate functions in tokens
 */
public class function implements runnable
{
    private String title = "empty";
    
    /**
     * creates a function with the given name
     */
    public function(String name)
    {
        title = name;
    }
    
    /**
     * pop an item from the specified stack
     */
    private static Object pop(ArrayList<Object> stack)
    {
        java.lang.Object item = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        return item;
    }
   
    /**
     * Execute print command
     * <p>
     * pop an item from the stack and print it to output
     * @param terp
     */
    private void print(pilly terp)
    {
        if (terp.stack.isEmpty()) System.out.println("Not enough items on stack");
        else {
            System.out.println(pop(terp.stack));
        }
    }
   
    /**
     * Execute print stack command
     * <p>
     * print out the entire stack
     * @param terp
     */
    private void pstack(pilly terp)
    {
        System.out.println(terp.stack);
    }
   
    /**
     * Execute the add command
     * <p>
     * pop two items from the stack and push their sum onto the stack
     * @param terp
     */
    private void plus(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b+a);
        }
    }
   
    /**
     * Execute the subtract command
     * <p>
     * pop two items from the stack and push their difference onto the stack
     * @param terp
     */
    private void minus(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b-a);
        }
    }
   
    /**
     * Execute the multiply command
     * <p>
     * pop two items from the stack and push their product onto the stack
     * @param terp
     */
    private void times(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b*a);
        }
    }
   
    /**
     * Execute the divide command
     * <p>
     * pop two items from the stack and push their quotient onto the stack
     * @param terp
     */
    private void divide(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b/a);
        }
    }
   
    /**
     * Execute the square root command
     * <p>
     * pop one item from the stack and push its square root onto the stack
     * @param terp
     */
    private void sqrt(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            terp.stack.add(Math.sqrt(a));
        }
    }
   
    /**
     * Execute the duplicate command
     * <p>
     * duplicate the top item on the stack
     * @param terp
     */
    private void dup(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            terp.stack.add(a);
            terp.stack.add(a);
        }
    }
   
    /**
     * Execute the drop command
     * <p>
     * remove the top item from the stack
     * @param terp
     */
    private void drop(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            pop(terp.stack);
        }
    }
   
    /**
     * Execute the swap command
     * <p>
     * swap the top two items on the stack
     * @param terp
     */
    private void swap(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            Object b = pop(terp.stack);
            terp.stack.add(a);
            terp.stack.add(b);
        }
    }
   
    /**
     * Execute the over command
     * <p>
     * duplicate the value of the second item on the stack onto the top
     * @param terp
     */
    private void over(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            Object b = pop(terp.stack);
            terp.stack.add(b);
            terp.stack.add(a);
            terp.stack.add(b);
        }
    }

    /**
     * Execute the rot command
     * <p>
     * rotate the top three items on the stack (first->second, second->third, third->first)
     * @param terp
     */
    private void rot(pilly terp)
    {
        if (terp.stack.size() < 3) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            Object b = pop(terp.stack);
            Object c = pop(terp.stack);
            terp.stack.add(b);
            terp.stack.add(a);
            terp.stack.add(c);
        }
    }
   
    /**
     * Execute the make variable command
     * <p>
     * create a new variable named the next word in the line
     */
    private void var(pilly terp)
    {
        String varName = terp.lexer.nextWord();
        if (varName == null) System.out.println("Unexpected end of input");
        terp.define(varName, new variable());
    }
   
    /**
     * Execute the store variable command
     * <p>
     * pop the top two items off the stack and store the value in the second in the variable named in the first
     * @param terp
     */
    private void store(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else
        {
            variable ref = (variable) pop(terp.stack);
            Object newV = pop(terp.stack);
            ref.value = newV;
        }
    }
   
    /**
     * Execute the fetch variable command
     * <p>
     * replace the variable at the top of the stack with its value
     * @param terp
     */
    private void fetch(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            variable ref = (variable) pop(terp.stack);
            terp.stack.add(ref.value);
        }
    }
   
    /**
     * Execute the make constant command
     * <p>
     * pop the top value off the stack and define a constant named the next word in the line with that value
     * @param terp
     */
    public void constant(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            String constName = terp.lexer.nextWord();
            if (constName == null) System.out.println("Unexpected end of input");
            else
            {
                Object constVal = pop(terp.stack);
                terp.define(constName, new constant(constVal));
            }
        }
    }
   
    /**
     * Execute the escape string command with delimiter '\"'
     * <p>
     * push a string containing the characters upto the next occurence of '\"' to the stack
     * @param terp
     */
    public void quote(pilly terp)
    {
        terp.stack.add(terp.lexer.nextCharsUpTo('\"'));
    }
   
    /**
     * Execute the escape string command with delimeter "END"
     * <p>
     * push a string containing the characters upto the next occurence of "END" to the stack
     * @param terp
     */
    public void string(pilly terp)
    {
        terp.stack.add(terp.lexer.nextCharsUpTo("END"));
    }
   
    /**
     * Execute the comment command
     * <p>
     * ignore the remainder of the line
     * @param terp
     */
    public void commLine(pilly terp)
    {
        terp.lexer.nextCharsUpTo('\n');
    }
    
    /**
     * Execute the comment command with delimiter ")"
     * <p>
     * ignore the characters upto the next occurence of ')'
     * @param terp 
     */
    public void commParen(pilly terp)
    {
        terp.lexer.nextCharsUpTo(')');
    }
    
    /**
     * Execute the comment command with delimeter "*"+"/"
     * <p>
     * ignore the characters upto the next occurence of '*'+'/'
     * @param terp
     */
    public void commBlock(pilly terp)
    {
        terp.lexer.nextCharsUpTo("*/");
    }

    /**
     * Execute the halt command
     * <p>
     * stop the execution loop
     * @param terp
     */ 
    private void halt(pilly terp)
    {
        terp.running = false;
    }
   
    /**
     * Execute the correct command based on the title of the function
     */
    public void run(pilly terp)
    {
        switch(title)
        {
            case "PRINT":
                print(terp);
                break;
            case "PSTACK":
                pstack(terp);
                break;
            case "+":
                plus(terp);
                break;
            case "-":
                minus(terp);
                break;
            case "*":
                times(terp);
                break;
            case "/":
                divide(terp);
                break;
            case "SQRT":
                sqrt(terp);
                break;
            case "DUP":
                dup(terp);
                break;
            case "DROP":
                drop(terp);
                break;
            case "SWAP":
                swap(terp);
                break;
            case "OVER":
                over(terp);
                break;
            case "ROT":
                rot(terp);
                break;
            case "VAR":
                var(terp);
                break;
            case "STORE":
                store(terp);
                break;
            case "FETCH":
                fetch(terp);
                break;
            case "HALT":
                halt(terp);
                break;
            case "CONST":
                constant(terp);
                break;
            case "QUOTE":
                quote(terp);
                break;
            case "STRING":
                string(terp);
                break;
            case "//":
                commLine(terp);
                break;
            case "/*":
                commBlock(terp);
                break;
            case "(":
                commParen(terp);
                break;
        }
    }
}
