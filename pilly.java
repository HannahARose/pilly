import java.util.HashMap;
import java.util.ArrayList;
import java.lang.Object;
import java.util.Scanner;
import java.io.File;

/**
 * The interpreter itself
 */
public class pilly
{
    public Lexer lexer = new Lexer();
    public boolean running = true; // flag to continue execution
    public int pointer = 0; // current position in a method / list execution
    public boolean br = false; // flag to abort execution of the current method / list
    public boolean immediate = false; // flag to signal immediate execution
    public boolean compiling = false; // flad to signal deferred execution
    public String method = ""; // name of the method being currently compiled
    public File block = new File("block.p");
    
    HashMap<String, runnable> dictionary = new HashMap<String, runnable>(0);
    ArrayList<Object> dataStack = new ArrayList<Object>(0);
    ArrayList<Object> compileBuffer = new ArrayList<Object>(0);
    ArrayList<Object> stack = dataStack;
    
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

    /**
     * create a new interpreter with the standard wordsets
     */
    public pilly()
    {
                
        HashMap<String, runnable> PrintingWords = new HashMap<String, runnable>();
        PrintingWords.put("PRINT", new function("PRINT"));
        PrintingWords.put("PSTACK", new function("PSTACK"));
        PrintingWords.put(".", new function("PRINT"));
        PrintingWords.put(".S", new function("PSTACK"));
        PrintingWords.put(".\"", new function(".\""));
        PrintingWords.put("SPACES", new function("SPACES"));
        PrintingWords.put("EMIT", new function("EMIT"));
        PrintingWords.put("CR", new function("CR"));
        addWords(PrintingWords);
        
        
        HashMap<String, runnable> MathWords = new HashMap<String, runnable>();
        MathWords.put("+", new function("+"));
        MathWords.put("-", new function("-"));
        MathWords.put("*", new function("*"));
        MathWords.put("/", new function("/"));
        MathWords.put("MOD", new function("MOD"));
        MathWords.put("/MOD", new function("/MOD"));
        MathWords.put("SQRT", new function("SQRT"));
        addWords(MathWords);
        
        
        HashMap<String, runnable> StackWords = new HashMap<String, runnable>();
        StackWords.put("DUP", new function("DUP"));
        StackWords.put("DROP", new function("DROP"));
        StackWords.put("SWAP", new function("SWAP"));
        StackWords.put("OVER", new function("OVER"));
        StackWords.put("ROT", new function("ROT"));
        StackWords.put("2DUP", new function("2DUP"));
        StackWords.put("2DROP", new function("2DROP"));
        StackWords.put("2SWAP", new function("2SWAP"));
        StackWords.put("2OVER", new function("2OVER"));
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
        CommentWords.put("\\", new function("//"));
        CommentWords.put("(", new function("("));
        addWords(CommentWords);

        
        HashMap<String, runnable> ControlWords = new HashMap<String, runnable>();
        ControlWords.put("RUN", new function("RUN"));
        ControlWords.put("TIMES", new function("TIMES"));
        ControlWords.put("HALT", new function("HALT"));
        ControlWords.put("STOP", new function("HALT"));
        ControlWords.put("INCLUDE", new function("INCLUDE"));
        ControlWords.put("IFTRUE", new function("IFTRUE"));
        ControlWords.put("IFFALSE", new function("IFFALSE"));
        ControlWords.put("WHILE", new function("WHILE"));
        ControlWords.put("?BREAK", new function("?BREAK"));
        ControlWords.put("?CONTINUE", new function("?CONTINUE"));
        ControlWords.put("LOOP", new function("LOOP"));
        addWords(ControlWords);
        
        
        HashMap<String, runnable> LogicWords = new HashMap<String, runnable>();
        LogicWords.put("TRUE", new function("TRUE"));
        LogicWords.put("FALSE", new function("FALSE"));
        LogicWords.put("AND", new function("AND"));
        LogicWords.put("OR", new function("OR"));
        LogicWords.put("NOT", new function("NOT"));
        LogicWords.put("BOTH", new function("AND"));
        LogicWords.put("EITHER", new function("OR"));
        addWords(LogicWords);
        
        
        HashMap<String, runnable> CompareWords = new HashMap<String, runnable>();
        CompareWords.put("<", new function("<"));
        CompareWords.put("<=", new function("<="));
        CompareWords.put(">", new function(">"));
        CompareWords.put(">=", new function(">="));
        CompareWords.put("=", new function("="));
        addWords(CompareWords);
        
        
        HashMap<String, runnable> CompilingWords = new HashMap<String, runnable>();
        CompilingWords.put("DEF", new function("DEF"));
        CompilingWords.put(":", new function("DEF"));
        CompilingWords.put("COMPILE", new function("COMPILE"));
        CompilingWords.put("IMMEDIATE", new function("IMMEDIATE"));
        CompilingWords.put(";", new function("END"));
        CompilingWords.put("END", new function("END"));
        CompilingWords.put("FORGET", new function("FORGET"));
        addWords(CompilingWords);
        
        
        HashMap<String, runnable> ListWords = new HashMap<String, runnable>();
        ListWords.put("[", new function("["));
        ListWords.put("LENGTH", new function("LENGTH"));
        ListWords.put("ITEM", new function("ITEM"));
        addWords(ListWords);
        
        
        HashMap<String, runnable> BlockWords = new HashMap<String, runnable>();
        BlockWords.put("USE", new function("USE"));
        BlockWords.put("LIST", new function("LIST"));
        BlockWords.put("LOAD", new function("LOAD"));
        addWords(BlockWords);
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
        String word = "";
        Object obj = null;
        
        while ( lexer.hasNextWord() && running )
        {
            word = lexer.nextWord();
            obj = compile(word);
            if ( immediate )
            {
                interpret(obj);
                immediate = false;
            } else if ( isCompiling() )
            {
                stack.add(obj);
            } else {
                interpret(obj);
            }
        }
    }
  
    /**
     * lookup the given word and return the result
     * @param word
     * @return
     */
    public Object compile (String word)
    {
        word = word.toUpperCase();
        if ( isDouble(word) )
        {
            return Double.parseDouble(word);
        } else if ( dictionary.containsKey(word) )
        {
            immediate = dictionary.get(word).isImmediate();
            return dictionary.get(word);
        } else {
            System.out.println(word);
            throw new IllegalArgumentException("Unknown word");
        }
    }
    
    /**
     * evaluate the given object
     * @param word
     */
    public void interpret (Object word)
    {
        if (word instanceof runnable)
        {
            runnable exec = (runnable) word;
            exec.run(this);
        }
        else stack.add(word);
    }
    
    /**
     * switch the interpreter into compile mode
     */
    public void startCompiling()
    {
        compiling = true;
        stack = compileBuffer;
    }
    
    /**
     * switch the interpreter out of compile mode
     */
    public void stopCompiling()
    {
        compiling = false;
        stack = dataStack;
    }
    
    public boolean isCompiling()
    {
        return compiling;
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
            System.out.print("\n>>");
            line = term.nextLine() + "\n";
            try {
                terp.run(line);
                System.out.print("ok\n");
            } catch(IllegalArgumentException e) {
                System.out.print(e);
            }
        }
        term.close();
    }
}
