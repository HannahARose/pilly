public class variable implements runnable
{
    public Object value;
    public variable()
    {
        
    }
    public void run(pilly terp)
    {
        terp.stack.add(this);
    }
}
