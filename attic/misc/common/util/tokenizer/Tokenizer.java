package common.util.tokenizer;

import java.io.*;
import java.util.*;

public interface Tokenizer
{
  public Token nextToken(TokenReader reader)
    throws IOException;

// -------------------------------------------------------------
// Abstract
// -------------------------------------------------------------

  public static abstract class Abstract
    implements Tokenizer
  {
    protected int peek(TokenReader reader)
      throws IOException
    {
      reader.mark(1);
      int chr = reader.read();
      reader.reset();
      return chr;
    }
  
    protected boolean startsWith(StringBuffer buffer, String head)
    {
      int len = head.length();
      int size = buffer.length();
      if (len > size) return false;
      String text = buffer.substring(size - len);
      return text.equals(head);
    }

    protected boolean endsWith(StringBuffer buffer, String tail)
    {
      int len = tail.length();
      int size = buffer.length();
      if (len > size) return false;
      String text = buffer.substring(size - len, size);
      return text.equals(tail);
    }
  }

// -------------------------------------------------------------
// While
// -------------------------------------------------------------
  
/**
 * Parse a string token by using isValid and createToken
 * abstract methods. We test each buffered character string
 * for a match and quit looking when the match fails. We
 * can do final checking in the createToken method before
 * returning a token or null.
**/

  public static abstract class While extends Abstract
  {
    public Token nextToken(TokenReader reader)
      throws IOException	
    {
      Token.Offset offset = reader.getOffset();
      StringBuffer buffer = new StringBuffer();
      while (true)
      {
        int chr = peek(reader);
        buffer.append((char)chr);
        if (isValid(buffer.toString()))
        {
          reader.read();
        }
        else
        {
          buffer.setLength(buffer.length() - 1);
          break;
        }
      }
      return createToken(buffer.toString(), offset);
    }
  
    public abstract boolean isValid(String text);
    public abstract Token createToken(
      Object token, Token.Offset offset);
  }

// -------------------------------------------------------------
// Bounded
// -------------------------------------------------------------

  public static abstract class Bounded extends Abstract
  {
    protected String startsWith, endsWith;
    protected boolean includeStart, includeEnd;
  
    public Bounded(
      String startsWith, String endsWith,
      boolean includeStart, boolean includeEnd)
    {
      this.startsWith = startsWith;
      this.endsWith = endsWith;
      this.includeStart = includeStart;
      this.includeEnd = includeEnd;
    }
  
    public Token nextToken(TokenReader reader)
      throws IOException
    {
      Token.Offset offset = reader.getOffset();
      StringBuffer buffer = new StringBuffer();
      for (int i = 0; i < startsWith.length(); i++)
      {
        int chr = reader.read();
        buffer.append((char)chr);
      }
      //System.out.println(">>>>>>>>>>>>" +
      //	buffer.toString() + " == " + startsWith);
      if (!buffer.toString().equals(startsWith))
        return null;
      if (!includeStart) buffer.setLength(0);
    
      while (hasMore(buffer))
      {
        int chr = reader.read();
        buffer.append((char)chr);
      }
    
      int end = ((includeEnd) ? 0 : endsWith.length());
      buffer.setLength(buffer.length() - end);
      return createToken(buffer.toString(), offset);
    }

    protected boolean endsWith(StringBuffer buffer, String tail)
    {
      int len = tail.length();
      int size = buffer.length();
      if (len > size) return false;
      String text = buffer.substring(size - len, size);
      return text.equals(tail);
    }
  
    public boolean hasMore(StringBuffer buffer)
    {
      return !endsWith(buffer, endsWith);
    }

    public abstract Token createToken(
      Object token, Token.Offset offset);
  }

// -------------------------------------------------------------
// Space
// -------------------------------------------------------------

  public static class Space extends Abstract
  {
    public Token nextToken(TokenReader reader)
      throws IOException
    {
      Token.Offset offset = reader.getOffset();
      int chr = reader.read();
      return createToken(new Character((char)chr), offset);
    }
  
    public Token createToken(
      Object token, Token.Offset offset)
    {
      return new Token.Space((Character)token, offset);
    }
  }

// -------------------------------------------------------------
// CommentLine
// -------------------------------------------------------------

  public static class CommentLine extends Bounded
  {
    public CommentLine()
    {
      super("//", "\n", true, false);
    }

    public Token createToken(
      Object token, Token.Offset offset)
    {
      String comment = (String)token;
      if (comment.endsWith("\r"))
        comment = comment.substring(0, comment.length() - 1);
      return new Token.Comment(comment, offset);
    }
  }

// -------------------------------------------------------------
// CommentArea
// -------------------------------------------------------------

  public static class CommentArea extends Bounded
  {
    public CommentArea()
    {
      super("/*", "*/", true, true);
    }

    public Token createToken(
      Object token, Token.Offset offset)
    {
      return new Token.Comment((String)token, offset);
    }
  }
  
// -------------------------------------------------------------
// Char
// -------------------------------------------------------------

  public static class Char extends Bounded
  {
    public Char()
    {
      super("'", "'", false, false);
    }

    public boolean hasMore(StringBuffer buffer)
    {
      //if (buffer.length() > 4) return false;
      if (endsWith(buffer, "\\" + endsWith)) return true;
      return !endsWith(buffer, endsWith);
    }

    public Token createToken(
      Object token, Token.Offset offset)
    {
      String text = (String)token;
      Character chr = null;
      if (text.length() == 1)
      {
        chr = new Character(text.charAt(0));
      }
      if (text.startsWith("\\"))
      {
        // Slash-special character
        if (text.length() == 2)
        {
          char c = text.charAt(1);
          if (c == 'n') c = '\n';
          if (c == 'r') c = '\r';
          chr = new Character(c);
        }
        else // Slash-number character
        {
          String num = text.substring(1);
          try
          {
            int val = Integer.parseInt(num);
            chr = new Character((char)val);
          }
          catch (NumberFormatException e)
          {
            return null;
          }
        }
      }
      return new Token.Char(chr, offset);
    }
  }

// -------------------------------------------------------------
// Symbol
// -------------------------------------------------------------

  public static class Symbol extends While
  {
    protected ArrayList extendedSymbolList = new ArrayList();

    public Symbol()
    {
      add("==");
      add("!=");
      add(">=");
      add("<=");
      add("&&");
      add("||");
      add("--");
      add("++");
      add("-=");
      add("+=");
      add("*=");
      add("/=");
    }
    
    public void add(String symbol)
    {
      boolean added = false;
      int len = symbol.length();
      int count = extendedSymbolList.size();
      for (int i = 0; i < count; i++)
      {
        String test = (String)extendedSymbolList.get(i);
        if (len > test.length())
        {
          extendedSymbolList.add(i, symbol);
          added = true;
        }
      }
      if (!added) extendedSymbolList.add(symbol);
    }
  
    public void remove(String symbol)
    {
      extendedSymbolList.remove(symbol);
    }

    public void clear()
    {
      extendedSymbolList.clear();
    }

    public boolean isValid(String text)
    {
      if (text.length() == 1) return true;
      int count = extendedSymbolList.size();
      for (int i = 0; i < count; i++)
      {
        String symbol = (String)extendedSymbolList.get(i);
        if (symbol.startsWith(text)) return true;
      }
      return false;
    }

    public Token createToken(
      Object token, Token.Offset offset)
    {
      return new Token.Symbol((String)token, offset);
    }
  }

// -------------------------------------------------------------
// Number
// -------------------------------------------------------------

  public static class Number extends While
  {
    public boolean isValid(String text)
    {
      try
      {
        Double.parseDouble(text);
        return true;
      }
      catch (NumberFormatException e)
      {
        return false;
      }
    }
  
    public Token createToken(
      Object token, Token.Offset offset)
    {
      String text = (String)token;
      if (text.equals("-") || text.equals(".") ||
        text.length() == 0) return null;
      try
      {
        double val = Double.parseDouble(text);
        return new Token.Number(val, offset);
      }
      catch (NumberFormatException e)
      {
        return null;
      }
    }
  }

// -------------------------------------------------------------
// Atom
// -------------------------------------------------------------

  public static class Atom extends Abstract
  {
    public Token nextToken(TokenReader reader)
      throws IOException
    {
      Token.Offset offset = reader.getOffset();
      int chr = reader.read();
      StringBuffer buffer = new StringBuffer();
      buffer.append((char)chr);
    
      while (validChar(peek(reader)))
      {
        chr = reader.read();
        buffer.append((char)chr);
      }
      return createToken(buffer.toString(), offset);
    }

    protected boolean validChar(int chr)
    {
      return chr == '_' ||
        (chr >= '0' && chr <= '9') ||
        (chr >= 'a' && chr <= 'z') ||
        (chr >= 'A' && chr <= 'Z');
    }
  
    public Token createToken(
      Object token, Token.Offset offset)
    {
      return new Token.Atom((String)token, offset);
    }
  }

// -------------------------------------------------------------
// Text
// -------------------------------------------------------------

  public class Text extends Bounded
  {
    public Text()
    {
      super("\"", "\"", false, false);
    }

    public boolean hasMore(StringBuffer buffer)
    {
      if (endsWith(buffer, "\\" + endsWith)) return true;
      return !endsWith(buffer, endsWith);
    }

    public Token createToken(
      Object token, Token.Offset offset)
    {
      return new Token.Text((String)token, offset);
    }
  }
}

