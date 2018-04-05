import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Object;
public class function extends runnable
{
    private String title = "empty";
    
    public function(String name)
    {
        title = name;
    }
    
    private static Object pop(ArrayList<Object> stack)
    {
        java.lang.Object item = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        return item;
    }
    
    private void print(pilly terp)
    {
        if (terp.stack.isEmpty()) System.out.println("Not enough items on stack");
        else {
            System.out.println(pop(terp.stack));
        }
    }
    
    private void pstack(pilly terp)
    {
        System.out.println(terp.stack);
    }
    
    private void plus(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b+a);
        }
    }
    
    private void minus(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b-a);
        }
    }
    
    private void times(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b*a);
        }
    }
    
    private void divide(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b/a);
        }
    }
    
    private void sqrt(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            terp.stack.add(Math.sqrt(a));
        }
    }
    
    private void dup(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            terp.stack.add(a);
            terp.stack.add(a);
        }
    }
    
    private void drop(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
        }
    }
    
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
    
    private void var(pilly terp)
    {
        String varName = terp.lexer.nextWord();
        if (varName == null) System.out.println("Unexpected end of input");
        terp.define(varName, new variable());
    }
    
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
    
    private void fetch(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            variable ref = (variable) pop(terp.stack);
            terp.stack.add(ref.value);
        }
    }
    
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
    
    public void quote(pilly terp)
    {
        terp.stack.add(terp.lexer.nextCharsUpTo('\"'));
    }
    
    public void string(pilly terp)
    {
        terp.stack.add(terp.lexer.nextCharsUpTo("END"));
    }
    
    public void commLine(pilly terp)
    {
        terp.lexer.nextCharsUpTo('\n');
    }
    
    public void commParen(pilly terp)
    {
        terp.lexer.nextCharsUpTo(')');
    }
    
    public void commBlock(pilly terp)
    {
        terp.lexer.nextCharsUpTo("*/");
    }
    
    private void halt(pilly terp)
    {
        terp.running = false;
    }
    
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
    
    public static void main(String[] args)
    {
        
    }
}
