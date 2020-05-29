import java.util.ArrayList;
import java.lang.Object;

/**
 * A class to encapsulate user defined functions
 */
public class method implements runnable
{
    public ArrayList<Object> code = new ArrayList<Object>();
    public boolean immediate = false; // whether the function should be executed immediately

    public method () {}
    
    /**
     * create a function with the given code
     */
    public method (ArrayList<Object> list)
    {
        code = list;
    }
    
    /**
     * set the function's code to the given list
     * @param list
     */
    public void setCode (ArrayList<Object> list)
    {
        code = list;
    }
    
    /**
     * make the function execute immediately
     */
    public void makeImmediate()
    {
        immediate = true;
    }

    public boolean isImmediate() { return immediate; }

    /**
     * execute the function's code
     */
    public void run(pilly terp)
    {
        int oldPointer = terp.pointer;
        while (terp.pointer < code.size())
        {
            terp.interpret(code.get(terp.pointer));
            terp.pointer ++;
        }
        terp.pointer = oldPointer;
    }
}
