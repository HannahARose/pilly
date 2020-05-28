import java.util.HashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * The interpreter itself
 */
public class pilly
{
    public Lexer lexer = new Lexer();
    public boolean running = true;
    
    /**
     * Determines whether the given string can be parsed as a double
     * @param str
     * @return
     */
    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }
    
    HashMap<String, runnable> dictionary = new HashMap<String, runnable>(0);
    ArrayList<java.lang.Object> stack = new ArrayList<java.lang.Object>(0);
    
    public pilly()
    {
        HashMap<String, runnable> PrintingWords = new HashMap<String, runnable>();
        PrintingWords.put("PRINT", new function("PRINT"));
        PrintingWords.put("PSTACK", new function("PSTACK"));
        PrintingWords.put(".", new function("PRINT"));
        PrintingWords.put(".S", new function("PSTACK"));
        addWords(PrintingWords);
        
        
        HashMap<String, runnable> MathWords = new HashMap<String, runnable>();
        MathWords.put("+", new function("+"));
        MathWords.put("-", new function("-"));
        MathWords.put("*", new function("*"));
        MathWords.put("/", new function("/"));
        MathWords.put("SQRT", new function("SQRT"));
        addWords(MathWords);
        
        
        HashMap<String, runnable> StackWords = new HashMap<String, runnable>();
        StackWords.put("DUP", new function("DUP"));
        StackWords.put("DROP", new function("DROP"));
        StackWords.put("SWAP", new function("SWAP"));
        StackWords.put("OVER", new function("OVER"));
        StackWords.put("ROT", new function("ROT"));
        addWords(StackWords);
        
        
        HashMap<String, runnable> VariableWords = new HashMap<String, runnable>();
        VariableWords.put("VAR", new function("VAR"));
        VariableWords.put("STORE", new function("STORE"));
        VariableWords.put("FETCH", new function("FETCH"));
        VariableWords.put("VARIABLE", new function("VAR"));
        VariableWords.put("!", new function("STORE"));
        VariableWords.put("@", new function("FETCH"));
        addWords(VariableWords);
        
        HashMap<String, runnable> ConstantWords = new HashMap<String, runnable>();
        ConstantWords.put("CONST", new function("CONST"));
        ConstantWords.put("CONSTANT", new function("CONST"));
        addWords(ConstantWords);
        
        HashMap<String, runnable> StringWords = new HashMap<String, runnable>();
        StringWords.put("\"", new function("QUOTE"));
        StringWords.put("STRING", new function("STRING"));
        StringWords.put("STR", new function("STRING"));
        addWords(StringWords);
        
        HashMap<String, runnable> CommentWords = new HashMap<String, runnable>();
        CommentWords.put("/*", new function("/*"));
        CommentWords.put("//", new function("//"));
        CommentWords.put("(", new function("("));
        addWords(CommentWords);
        
        HashMap<String, runnable> CommandWords = new HashMap<String, runnable>();
        CommandWords.put("HALT", new function("HALT"));
        CommandWords.put("STOP", new function("HALT"));
        addWords(CommandWords);
    }
    
    /**
     * adds the given dictionary to the list of known words
     * @param new_dict
     */
    public void addWords (HashMap<String, runnable> new_dict)
    {
        dictionary.putAll(new_dict);
    }
    
    /**
     * adds the given word to the list of known words
     */
    public void define ( String word, runnable code )
    {
        dictionary.put(word, code);
    }
    
    /**
     * execute the given text
     * @param text
     */
    public void run (String text)
    {
        lexer.setText(text);
        String word = lexer.nextWord();
        double num_val;
        
        while ( word != "" && word != null && running )
        {
            if ( isDouble(word) )
            {
                num_val = Double.parseDouble(word);
                stack.add(num_val);
            } else if ( dictionary.containsKey(word) )
            {
                dictionary.get(word).run(this);
            } else {
                throw new IllegalArgumentException("Unknown word");
            }
            word = lexer.nextWord();
        }
    }
   
    /**
     * main class, will run a program if specified in arguments then enter an interactive interpreter
     */
    public static void main(String[] args)
    {
        pilly terp = new pilly();
        
        if (args.length > 0)
        {
            try
            {
                Scanner in = new Scanner(new File(args[0]));
                String file = "";
                while (in.hasNext()) {
                    file = file + in.nextLine() + "\n";
                }
                terp.run(file);
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
        Scanner term = new Scanner(System.in);
        String line;
        while (terp.running) {
            System.out.print(">>");
            line = term.nextLine() + "\n";
            try {
                terp.run(line);
            } catch(IllegalArgumentException e) {
                System.out.print(e);
            }
        }
        term.close();
    }
}
