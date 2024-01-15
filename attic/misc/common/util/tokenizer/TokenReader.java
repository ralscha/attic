package common.util.tokenizer;

import java.io.*;

public class TokenReader extends BufferedReader
{
  protected int fileOffset = 0;
  protected int lineNumber = 0;
  protected int lineOffset = 0;
  protected int markFileOffset = 0;
  protected int markLineNumber = 0;
  protected int markLineOffset = 0;
    
  public TokenReader(Reader reader)
  {
    super(reader);
  }

  public int read() throws IOException
  {
    int chr = super.read();
    fileOffset++;
    lineOffset++;
    if (chr == '\n')
    {
      lineNumber++;
      lineOffset = 0;
    }
    return chr;
  }
  
  public void mark(int max) throws IOException
  {
    markFileOffset = fileOffset;
    markLineNumber = lineNumber;
    markLineOffset = lineOffset;
    super.mark(max);
  }

  public void reset() throws IOException
  {
    fileOffset = markFileOffset;
    lineNumber = markLineNumber;
    lineOffset = markLineOffset;
    super.reset();
  }

  public Token.Offset getOffset()
  {
    return new Token.Offset(fileOffset, lineNumber, lineOffset);
  }
}

