/**
 * A class to break text input into meaningful pieces
 */
public class Lexer
{
    public String text;
    int position;
    
    /**
     * Determines whether the given character is whitespace
     * @param ch
     * @return
     */
    public static boolean isWhitespace(char ch)
    {
        return (ch==' ')||(ch=='\t')||(ch=='\r')||(ch=='\n');
    }
    
    public Lexer()
    {
    }
    
    /**
     * replaces the current text buffer with given text
     * @param in
     */
    public void setText(String in)
    {
        text = in;
        position = 0;
    }
    
    /**
     * returns the next word delimited by whitespace and updates the position
     * @return
     */
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
    
    /**
     * returns the next characters upto and not including the given character and updates the position
     * @param ch
     * @return
     */
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
 
    /**
     * returns the next characters upto and not including the given string and updates the position
     */
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
