/**
 * a class to encapsulate variables
 */
public class variable implements runnable
{
    public Object value;
    public variable() {}
    
    public boolean isImmediate() { return false; } // variables are never immediate

    public void run(pilly terp)
    {
        terp.stack.add(this);
    }

    
}
