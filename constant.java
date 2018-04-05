public class constant extends runnable
{
    private Object value;
    public constant(Object val)
    {
        value = val;
    }
    public void run(pilly terp)
    {
        terp.stack.add(value);
    }
}
