public class Lexer
{
    public String text;
    int position;
    
    public static boolean isWhitespace(char ch)
    {
        return (ch==' ')||(ch=='\t')||(ch=='\r')||(ch=='\n');
    }
    
    public Lexer()
    {
    }
    
    public void setText(String in)
    {
        text = in;
        position = 0;
    }
    
    public String nextWord()
    {
        if ( position >= text.length() ) return null;
        while (isWhitespace(text.charAt(position)))
        {
            position ++;
            if (position >= text.length()) return null;
        }
        int newPos = position;
        while (!isWhitespace(text.charAt(newPos)))
        {
            newPos ++;
            if (newPos >= text.length()) break;
        }
        String collector = text.substring(position, newPos);
        newPos ++;
        position = newPos;
        return collector;
    }
    
    public String nextCharsUpTo(char ch)
    {
        if (position >= text.length()) return null;
        int newPos = position;
        while (text.charAt(newPos) != ch)
        {
            newPos ++;
            if (newPos >= text.length())
                System.out.println("Unexpected end of input");
        }
        String collector = text.substring(position, newPos);
        newPos ++;
        position = newPos;
        return collector;
    }
    
    public String nextCharsUpTo(String end)
    {
        if (position >= text.length()) return null;
        int newPos = position;
        while (!text.substring(position, newPos).contains(end))
        {
            newPos ++;
            if (newPos >= text.length())
                System.out.println("Unexpected end of input");
        }
        String collector = text.substring(position, newPos-end.length());
        newPos ++;
        position = newPos;
        return collector;
    }
}
