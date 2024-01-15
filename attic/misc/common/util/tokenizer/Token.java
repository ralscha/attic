package common.util.tokenizer;

public interface Token
{
  public Object getToken();
  public void setToken(Object token);
  public Offset getOffset();
  public void setOffset(Offset pos);
  public boolean isIgnorable();
  public void setIgnorable(boolean ignorable);
  
// -------------------------------------------------------------
// Offset
// -------------------------------------------------------------

  public static class Offset
  {
    protected int fileOffset;
    protected int lineNumber;
    protected int lineOffset;

    public Offset(int fileOffset, int lineNumber, int lineOffset)
    {
      this.fileOffset = fileOffset;
      this.lineNumber = lineNumber;
      this.lineOffset = lineOffset;
    }
  
    public int getFileOffset()
    {
      return fileOffset;
    }
  
    public void setFileOffset(int fileOffset)
    {
      this.fileOffset = fileOffset;
    }
  
    public int getLineNumber()
    {
      return lineNumber;
    }
  
    public void setLineNumber(int lineNumber)
    {
      this.lineNumber = lineNumber;
    }
  
    public int getLineOffset()
    {
      return lineOffset;
    }

    public void setLineOffset(int lineOffset)
    {
      this.lineOffset = lineOffset;
    }
  
    public String toString()
    {
      StringBuffer buffer = new StringBuffer();
      buffer.append("offset(");
      buffer.append(fileOffset);
      buffer.append(", ");
      buffer.append(lineNumber);
      buffer.append(", ");
      buffer.append(lineOffset);
      buffer.append(')');
      return buffer.toString();
    }
  }

// -------------------------------------------------------------
// Abstract
// -------------------------------------------------------------

  public static abstract class Abstract
    implements Token
  {
    protected Object token;
    protected Offset offset;
    protected boolean ignorable = false;
  
    public Abstract(Object token, Offset offset)
    {
      this.token = token;
      this.offset = offset;
    }
  
    public Object getToken()
    {
      return token;
    }

    public void setToken(Object token)
    {
      this.token = token;
    }

    public Offset getOffset()
    {
      return offset;
    }
  
    public void setOffset(Offset offset)
    {
      this.offset = offset;
    }
  
    public boolean isIgnorable()
    {
      return ignorable;
    }
  
    public void setIgnorable(boolean ignorable)
    {
      this.ignorable = ignorable;
    }

    public String toString(String name, String value)
    {
      StringBuffer buffer = new StringBuffer();
      buffer.append(name);
      buffer.append('(');
      buffer.append(value);
      buffer.append(", ");
      if (offset == null) buffer.append("0");
      else buffer.append(offset.toString());
      buffer.append(')');
      return buffer.toString();
    }
  }
  
// -------------------------------------------------------------
// Space
// -------------------------------------------------------------

  public static class Space extends Abstract
  {
    public Space(Character chr, Offset offset)
    {
      super(chr, offset);
      super.setIgnorable(true);
    }

    public String toString()
    {
      return toString("space", "'" + token.toString() + "'");
    }
  }

// -------------------------------------------------------------
// Comment
// -------------------------------------------------------------

  public static class Comment extends Abstract
  {
    public Comment(String text, Offset offset)
    {
      super(text, offset);
      super.setIgnorable(true);
    }

    public String toString()
    {
      return toString("comment", '"' + token.toString() + '"');
    }
  }

// -------------------------------------------------------------
// Char
// -------------------------------------------------------------

  public static class Char extends Abstract
  {
    public Char(Character chr, Offset offset)
    {
      super(chr, offset);
    }

    public String toString()
    {
      return toString("char", "'" + token.toString() + "'");
    }
  }

// -------------------------------------------------------------
// Symbol
// -------------------------------------------------------------

  public static class Symbol extends Abstract
  {
    public Symbol(String symbol, Offset offset)
    {
      super(symbol, offset);
    }

    public String toString()
    {
      return toString("symbol", '"' + token.toString() + '"');
    }	
  }
  
// -------------------------------------------------------------
// Number
// -------------------------------------------------------------

  public static class Number extends Abstract
  {
    public Number(double number, Offset offset)
    {
      super(new Double(number), offset);
    }

    public String toString()
    {
      return toString("number", token.toString());
    }
  }	

// -------------------------------------------------------------
// Atom
// -------------------------------------------------------------

  public static class Atom extends Abstract
  {
    public Atom(String word, Offset offset)
    {
      super(word, offset);
    }

    public String toString()
    {
      return toString("atom", '"' + token.toString() + '"');
    }
  }

// -------------------------------------------------------------
// Text
// -------------------------------------------------------------

  public static class Text extends Abstract
  {
    public Text(String text, Offset offset)
    {
      super(text, offset);
    }

    public String toString()
    {
      return toString("text", '"' + token.toString() + '"');
    }
  }
}

