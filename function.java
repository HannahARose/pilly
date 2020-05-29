import java.util.ArrayList;
import java.lang.Object;
import java.util.Scanner;
import java.io.File;

/**
 * A class to encapsulate functions in tokens
 */
public class function implements runnable
{
    private String title = "empty";
    private final String immediates = " VAR CONST QUOTE STRING /* ( // DEF END [ .\" IMMEDIATE ";
    
    /**
     * creates a function with the given name
     */
    public function(String name)
    {
        title = name;
    }

    /**
     * checks if the function needs to be evaluated immediately
     */
    public boolean isImmediate() {
        return immediates.contains(" "+title+" ");
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
            System.out.print(pop(terp.stack)+" ");
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
     * Execute the print quote command
     * <p>
     * prints a string upto the next occurence of '\"'
     */
    public void pquote(pilly terp)
    {
        System.out.print(terp.lexer.nextCharsUpTo('\"')+" ");
    }
    
    /**
     * Execute the newline command
     * <p>
     * prints a line terminator
     */
    private void cr(pilly terp)
    {
        System.out.println();
    }
    
    /**
     * Execute the spaces command
     * <p>
     * pops a number from the stack and prints that many spaces
     */
    private void spaces(pilly terp)
    {
        if (terp.stack.isEmpty()) System.out.println("Not enough items on stack");
        else {
            int num = (int) Math.round((double) pop(terp.stack));
            String message = "";
            for (int i = 0; i < num; i++)
                message = message + " ";
            System.out.print(message);
        }
    }
    
    /**
     * Execute the emit command
     * <p>
     * pops a number from the stack and prints that ASCII character
     */
    private void emit(pilly terp)
    {
        if (terp.stack.isEmpty()) System.out.println("Not enough items on stack");
        else {
            System.out.print((char) Math.round((double) pop(terp.stack))+" ");
        }
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
    private void mult(pilly terp)
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
     * Execute the modulus command
     * <p>
     * pop two items from the stack and push their remainder onto the stack
     */
    private void mod(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b%a);
        }
    }
    
    /**
     * Execute the divide with remainder command
     * <p>
     * pop two items off the stack and push first their remainder then their quotient onto the stack
     * @param terp
     */
    private void divmod(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b%a);
            terp.stack.add(Math.floor(b/a));
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
     * Execute the rotate command
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
     * Execute the duplicate pair command
     * <p>
     * duplicate the top pair on the stack
     */
    private void dup2(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            Object b = pop(terp.stack);
            terp.stack.add(b);
            terp.stack.add(a);
            terp.stack.add(b);
            terp.stack.add(a);
        }
    }
    
    /**
     * Execute the drop pair command
     * <p>
     * removes the top pair from the stack
     * @param terp
     */
    private void drop2(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            pop(terp.stack);
            pop(terp.stack);
        }
    }
    
    /**
     * Executes the swap pair command
     * <p>
     * swaps the top two pairs on the stack
     * @param terp
     */
    private void swap2(pilly terp)
    {
        if (terp.stack.size() < 4) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            Object b = pop(terp.stack);
            Object c = pop(terp.stack);
            Object d = pop(terp.stack);
            terp.stack.add(b);
            terp.stack.add(a);
            terp.stack.add(d);
            terp.stack.add(c);
        }
    }
    
    /**
     * Executes the over pair command
     * <p>
     * duplicates the second pair on the stack onto the top
     */
    private void over2(pilly terp)
    {
        if (terp.stack.size() < 4) System.out.println("Not enough items on stack");
        else {
            Object a = pop(terp.stack);
            Object b = pop(terp.stack);
            Object c = pop(terp.stack);
            Object d = pop(terp.stack);
            terp.stack.add(d);
            terp.stack.add(c);
            terp.stack.add(b);
            terp.stack.add(a);
            terp.stack.add(d);
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
     * Execute the include command
     * <p>
     * Executes the file named the next word before continuing execution
     * @param terp
     */
    private void include(pilly terp)
    {
        String name = terp.lexer.nextWord();
        if (name == null) System.out.println("Unexpected end of input");
        String textLeft = terp.lexer.textRemaining();
        try {
            Scanner in = new Scanner(new File(name));
            String file = "";
            while (in.hasNext()) {
                file = file + in.nextLine() + "\n";
            }
            terp.lexer.setText(file);
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println(e);
        }
        terp.lexer.addText(textLeft);
    }
    
    /**
     * Execute the define function command
     * <p>
     * flags the interpreter to start compiling a function named the next word
     */
    private void def(pilly terp)
    {
        String name = terp.lexer.nextWord();
        if (name == null) System.out.println("Unexpected end of input");
        else
        {
            terp.method = name;
            terp.define(name, new method());
            terp.startCompiling();
        }
    }
   
    /**
     * Execute the complie command
     * <p>
     * compile the next word and add the result to the stack
     * @param terp
     */
    private void compile(pilly terp)
    {
        String nextWord = terp.lexer.nextWord();
        if (nextWord == null) System.out.println("Unexpected end of input");
        else
        {
            terp.stack.add(terp.compile(nextWord));
        }
    }
    
    /**
     * Execute the make immediate command
     * <p>
     * make the current command immediate
     * @param terp
     */
    private void immediate(pilly terp)
    {
        ((method) terp.dictionary.get(terp.method)).makeImmediate();
    }
    
    /**
     * Execute the end function command
     * <p>
     * Signal the interpreter to end function compilation and set the function code
     */
    private void end(pilly terp)
    {
        ArrayList<Object> list = new ArrayList<Object>(0);
        for(int i = 0; i<terp.stack.size(); i++)
        {
            list.add(terp.stack.get(i));
        }
        terp.stack.clear();
        ((method) terp.dictionary.get(terp.method)).setCode(list);
        terp.stopCompiling();
    }
    
    /**
     * Execute the forget command
     * <p>
     * tell the interpreter to forget the definition of the next word
     */
    private void forget(pilly terp)
    {
        String wordName = terp.lexer.nextWord();
        if (wordName == null) System.out.println("Unexpected end of input");
        terp.dictionary.remove(wordName);
    }
    
    /**
     * Execute the make array command
     * <p>
     * parse the next words up to the next occurence of "]" as an array
     */
    private void array(pilly terp)
    {
        ArrayList<Object> list = new ArrayList<Object>();
        ArrayList<Object> oldStack = new ArrayList<Object>();
        for(int i = 0; i<terp.stack.size(); i++)
        {
            oldStack.add(terp.stack.get(i));
        }
        terp.stack = list;
        
        do {
            String nextWord = terp.lexer.nextWord();
            if (nextWord == null) System.out.println("Unexpected end of input");
            if (nextWord.contains("]")) break;
            
            Object nextObj = terp.compile(nextWord);
            if (terp.immediate)
                terp.interpret(nextObj);
            else
                terp.stack.add(nextObj);
        } while (true);
        
        terp.stack = oldStack;
        terp.stack.add(list);
    }
    
    /**
     * Execute the length command
     * <p>
     * pop an array from the stack and push its length onto the stack
     */
    private void length(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            ArrayList<Object> temp = (ArrayList<Object>) pop(terp.stack);
            terp.stack.add(temp.size());
        }
    }
    
    /**
     * Execute the get item command
     * <p>
     * Pop a number then an array from the stack and push that position of the array to the stack
     * @param terp
     */
    private void item(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else
        {
            int key = (int) Math.round((double) pop(terp.stack));
            ArrayList<Object> temp = (ArrayList<Object>) pop(terp.stack);
            terp.stack.add(temp.get(key));
        }
    }
    
    /**
     * Execute the use command
     * <p>
     * set the blocks to the file specified in the next word
     * @param terp
     */
    private void use(pilly terp)
    {
        
        String fileName = terp.lexer.nextWord();
        if (fileName == null) System.out.println("Unexpected end of input");
        else
        {
            try {
                terp.block = new File(fileName);
            }
            catch (NullPointerException e)
            {
                System.out.println(e);
            }
        }
    }
    
    /**
     * Execute the list command
     * <p>
     * pop a number from the stack and print out that block
     * @param terp
     */
    private void list(pilly terp)
    {
        int block = (int) Math.round((double) pop(terp.stack));
        try {
            Scanner in = new Scanner(terp.block);
            String file = "";
            String nextLine = "";
            int line = 0;
            int lineNumber = 0;
            while (line < block*16 && in.hasNext()) {
                in.nextLine();
                line ++;
            }
            while (lineNumber < 16 && in.hasNext()) {
                nextLine = in.nextLine();
                if (nextLine.length() > 64) nextLine = nextLine.substring(0,64);
                if (lineNumber < 10) file = file + " ";
                file = file + lineNumber + "| " + nextLine + "\n";
                lineNumber ++;
            }
            while (lineNumber < 16) {
                if (lineNumber < 10) file = file + " ";
                file = file + lineNumber + "|\n";
                lineNumber ++;
            }
            in.close();
            System.out.println(file);
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println(e);
        }
    }
    
    /**
     * Execute the load command
     * <p>
     * pop a number form the stack and execute that block
     * @param terp
     */
    private void load(pilly terp)
    {
        int block = (int) Math.round((double) pop(terp.stack));
        try {
            Scanner in = new Scanner(terp.block);
            String textLeft = terp.lexer.textRemaining();
            String file = "";
            String nextLine = "";
            int line = 0;
            int lineNumber = 0;
            while (line < block*16 && in.hasNext()) {
                in.nextLine();
                line ++;
            }
            while (lineNumber < 16 && in.hasNext()) {
                nextLine = in.nextLine();
                if (nextLine.length() > 64) nextLine = nextLine.substring(0,64);
                file = file + nextLine + "\n";
                lineNumber ++;
            }
            terp.lexer.setText(file);
            terp.lexer.addText(textLeft);
            in.close();
        }
        catch (NullPointerException e)
        {
            System.out.println(e);
        }
        catch (java.io.FileNotFoundException e)
        {
            System.out.println(e);
        }
    }
    
    /**
     * Execute the execute command
     * <p>
     * pop a list from the stack and interpret its contents
     * @param terp
     */
    private void exec(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            ArrayList<Object> func = (ArrayList<Object>) pop(terp.stack);
            terp.interpret(new method(func));
        }
    }
    
    /**
     * Execute the repeat command
     * <p>
     * pop a number and a list from the stack and interpret the contents of the stack that number of times
     */
    private void times(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else
        {
            double count = (double) pop(terp.stack);
            ArrayList<Object> func = (ArrayList<Object>) pop(terp.stack);
            method code = new method(func);
            for (int i = 0; i < count; i++) terp.interpret(code);
        }
    }
    
    /**
     * Execute the if true command
     * <p>
     * pop a list then a boolean from the stack and execute the list if the boolean is true
     * @param terp
     */
    private void iftrue(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else
        {
            ArrayList<Object> func = (ArrayList<Object>) pop(terp.stack);
            method code = new method(func);
            boolean cond = (boolean) pop(terp.stack);
            if (cond) terp.interpret(code);
        }
    }
    
    /**
     * Execute the if false command
     * <p>
     * pop a list then a boolean from the stack and execute the list if the booean is false
     * @param terp
     */
    private void iffalse(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else
        {
            ArrayList<Object> func = (ArrayList<Object>) pop(terp.stack);
            method code = new method(func);
            boolean cond = (boolean) pop(terp.stack);
            if (!cond) terp.interpret(code);
        }
    }
    
    /**
     * Execute the while command
     * <p>
     * pop two lists from the stack and execute the top list while the second list leaves a true on the top of the stack when executed
     * @param terp
     */
    private void whille(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else
        {
            method codeMethod = new method((ArrayList<Object>) pop(terp.stack));
            method condMethod = new method((ArrayList<Object>) pop(terp.stack));
            do {
                condMethod.run(terp);
                if (terp.stack.size() < 1)
                    System.out.println("Not enough items on stack");
                if (! (boolean) pop(terp.stack)) break;
                codeMethod.run(terp);
            } while (true);
        }
    }
    
    /**
     * Execute the continue command
     * <p>
     * pop a boolean from the stack and skip to the end of the current method / list if true
     */
    private void cont(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            boolean cond = (boolean) pop(terp.stack);
            if (cond) terp.pointer = Integer.MAX_VALUE-1;
        }
    }
    
    /**
     * Execute the break command
     * <p>
     * pop a boolean from the stack and skip to the end of the current method / list and set the break flag if the boolean is true
     * @param terp
     */
    private void br(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            boolean cond = (boolean) pop(terp.stack);
            if (cond)
            {
                terp.pointer = Integer.MAX_VALUE-1;
                terp.br = true;
            }
        }
    }
    
    /**
     * Execute the loop command
     * <p>
     * pop a list from the stack and loop it until the break flag is set
     */
    private void loop(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else
        {
            ArrayList<Object> code = (ArrayList<Object>) pop(terp.stack);
            method codeMethod = new method(code);
            boolean old_break_state = terp.br;
            terp.br = false;
            do { codeMethod.run(terp); } while (!terp.br);
            terp.br = old_break_state;
        }
    }
    
    /**
     * Execute the true command
     * <p>
     * push a true boolean onto the stack
     * @param terp
     */
    public void t(pilly terp)
    {
        terp.stack.add(true);
    }
    
    /**
     * Execute the false command
     * <p>
     * push a false boolean onto the stack
     */
    public void f(pilly terp)
    {
        terp.stack.add(false);
    }
    
    /**
     * Execute the and command
     * <p>
     * pop two booleans from the stack and push the result of their and onto the stack
     */
    public void and(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            boolean a = (boolean) pop(terp.stack);
            boolean b = (boolean) pop(terp.stack);
            terp.stack.add(b && a);
        }
    }
    
    /**
     * Execute the or command
     * <p>
     * pop two booleans from the stack and push the result of their or onto the stack
     * @param terp
     */
    public void or(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            boolean a = (boolean) pop(terp.stack);
            boolean b = (boolean) pop(terp.stack);
            terp.stack.add(b || a);
        }
    }
    
    /**
     * Execute the not command
     * <p>
     * pop a boolean from the stack and push its opposite onto the stack
     */
    public void not(pilly terp)
    {
        if (terp.stack.size() < 1) System.out.println("Not enough items on stack");
        else {
            boolean a = (boolean) pop(terp.stack);
            terp.stack.add(! a);
        }
    }
    
    /**
     * Execute the greater than command
     * <p>
     * pop two numbers from the stack and push the result of the greater than comparison on the second and the top onto the stack
     * @param terp
     */
    public void greater(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b > a);
        }
    }
    
    /**
     * Execute the greater than or equal to command
     * <p>
     * pop two numbers from the stack and push the result of the greater than or equal to comparison on the second and the top onto the stack
     * @param terp
     */
    public void greaterEq(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b >= a);
        }
    }
    
    /**
     * Execute the less than command
     * <p>
     * pop two numbers from the stack and push the result of the less than comparison on the second and the top onto the stack
     * @param terp
     */
    public void lesser(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b < a);
        }
    }
    
    /**
     * Execute the less than or equal to command
     * <p>
     * pop two numbers from the stack and push the result of the less than or equal to comparison on the second and the top onto the stack
     * @param terp
     */
    public void lesserEq(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b <= a);
        }
    }
    
    /**
     * Execute the equal to command
     * <p>
     * pop two numbers from the stack and push the result of the equal to comparison on the second and the top onto the stack
     * @param terp
     */
    public void eq(pilly terp)
    {
        if (terp.stack.size() < 2) System.out.println("Not enough items on stack");
        else {
            double a = (double) pop(terp.stack);
            double b = (double) pop(terp.stack);
            terp.stack.add(b == a);
        }
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
            case ".\"":
                pquote(terp);
                break;
            case "SPACES":
                spaces(terp);
                break;
            case "EMIT":
                emit(terp);
                break;
            case "CR":
                cr(terp);
                break;
            case "+":
                plus(terp);
                break;
            case "-":
                minus(terp);
                break;
            case "*":
                mult(terp);
                break;
            case "/":
                divide(terp);
                break;
            case "MOD":
                mod(terp);
                break;
            case "/MOD":
                divmod(terp);
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
            case "2DUP":
                dup2(terp);
                break;
            case "2DROP":
                drop2(terp);
                break;
            case "2SWAP":
                swap2(terp);
                break;
            case "2OVER":
                over2(terp);
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
            case "INCLUDE":
                include(terp);
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
                case "DEF":
                def(terp);
                break;
            case "END":
                end(terp);
                break;
            case "FORGET":
                forget(terp);
                break;
            case "COMPILE":
                compile(terp);
                break;
            case "IMMEDIATE":
                immediate(terp);
                break;
            case "[":
                array(terp);
                break;
            case "LENGTH":
                length(terp);
                break;
            case "ITEM":
                item(terp);
                break;
            case "USE":
                use(terp);
                break;
            case "LIST":
                list(terp);
                break;
            case "LOAD":
                load(terp);
                break;
            case "RUN":
                exec(terp);
                break;
            case "TIMES":
                times(terp);
                break;
            case "IFTRUE":
                iftrue(terp);
                break;
            case "IFFALSE":
                iffalse(terp);
                break;
            case "WHILE":
                whille(terp);
                break;
            case "?CONTINUE":
                cont(terp);
                break;
            case "?BREAK":
                br(terp);
                break;
            case "LOOP":
                loop(terp);
                break;
            case "TRUE":
                t(terp);
                break;
            case "FALSE":
                f(terp);
                break;
            case "AND":
                and(terp);
                break;
            case "OR":
                or(terp);
                break;
            case "NOT":
                not(terp);
                break;
            case ">":
                greater(terp);
                break;
            case ">=":
                greaterEq(terp);
                break;
            case "<":
                lesser(terp);
                break;
            case "<=":
                lesserEq(terp);
                break;
            case "=":
                eq(terp);
                break;
        }
    }
}
