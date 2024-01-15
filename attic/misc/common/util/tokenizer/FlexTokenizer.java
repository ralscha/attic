package common.util.tokenizer;

import java.io.*;
import java.util.*;

public class FlexTokenizer
{

// -------------------------------------------------------------
// Sequence
// -------------------------------------------------------------

  public static class Sequence extends ArrayList
  {
    public Sequence(Tokenizer tokenizer)
    {
      add(tokenizer);
    }
  
    public Sequence(Tokenizer one, Tokenizer two)
    {
      add(one);
      add(two);
    }
  
    public Sequence(Tokenizer one,
      Tokenizer two, Tokenizer three)
    {
      add(one);
      add(two);
      add(three);
    }

    public Tokenizer getTokenizer(int index)
    {
      return (Tokenizer)get(index);
    }
  }

// -------------------------------------------------------------
// Tokenizer
// -------------------------------------------------------------

  protected boolean skipIgnorables = true;
  protected int readAheadLimit = 255;
  protected TokenReader reader;
  
  protected Sequence[] charTable = new Sequence[256];
  
  public static final Tokenizer
    SYMBOL_TOKENIZER = new Tokenizer.Symbol();
  public static final Tokenizer
    SPACE_TOKENIZER = new Tokenizer.Space();
  public static final Tokenizer
    TEXT_TOKENIZER = new Tokenizer.Text();
  public static final Tokenizer
    CHAR_TOKENIZER = new Tokenizer.Char();
  public static final Tokenizer
    NUMBER_TOKENIZER = new Tokenizer.Number();
  public static final Tokenizer
    ATOM_TOKENIZER = new Tokenizer.Atom();
  public static final Tokenizer
    COMMENT_LINE_TOKENIZER = new Tokenizer.CommentLine();
  public static final Tokenizer
    COMMENT_AREA_TOKENIZER = new Tokenizer.CommentArea();
  
  public static final Sequence
    SYMBOL_SEQUENCE = new Sequence(
      SYMBOL_TOKENIZER);
  public static final Sequence
    SPACE_SEQUENCE = new Sequence(
      SPACE_TOKENIZER,
      SYMBOL_TOKENIZER);
  public static final Sequence
    TEXT_SEQUENCE = new Sequence(
      TEXT_TOKENIZER,
      SYMBOL_TOKENIZER);
  public static final Sequence
    CHAR_SEQUENCE = new Sequence(
      CHAR_TOKENIZER,
      SYMBOL_TOKENIZER);
  public static final Sequence
    NUMBER_SEQUENCE = new Sequence(
      NUMBER_TOKENIZER,
      SYMBOL_TOKENIZER);
  public static final Sequence
    ATOM_SEQUENCE = new Sequence(
      ATOM_TOKENIZER,
      SYMBOL_TOKENIZER);
  public static final Sequence
    COMMENT_SEQUENCE = new Sequence(
      COMMENT_LINE_TOKENIZER,
      COMMENT_AREA_TOKENIZER,
      SYMBOL_TOKENIZER);

  public FlexTokenizer(String text)
  {
    this(new TokenReader(new StringReader(text)));
  }
  
  public FlexTokenizer(TokenReader reader)
  {
    this.reader = reader;
    initCharTable();
  }
  
  public void initCharTable()
  {
    setTokenizerSequence(   0,   255, SYMBOL_SEQUENCE);
    setTokenizerSequence(   0,   ' ', SPACE_SEQUENCE);
    setTokenizerSequence( 'a',   'z', ATOM_SEQUENCE);
    setTokenizerSequence( 'A',   'Z', ATOM_SEQUENCE);
    setTokenizerSequence(0xc0,  0xff, ATOM_SEQUENCE);
    setTokenizerSequence( '\'', '\'', CHAR_SEQUENCE);
    setTokenizerSequence( '"',   '"', TEXT_SEQUENCE);
    setTokenizerSequence( '-',   '-', NUMBER_SEQUENCE);
    setTokenizerSequence( '.',   '.', NUMBER_SEQUENCE);
    setTokenizerSequence( '0',   '9', NUMBER_SEQUENCE);
    setTokenizerSequence( '/',   '/', COMMENT_SEQUENCE);
  }
  
  public void setTokenizerSequence(
    int from, int to, Sequence sequence)
  {
    for (int i = from; i <= to; i++)
    {
      charTable[i] = sequence;
    }
  }

  public void setSkipIgnorables(boolean skipIgnorables)
  {
    this.skipIgnorables = skipIgnorables;
  }
  
  public boolean getSkipIgnorables()
  {
    return skipIgnorables;
  }
  
  protected int peek()
    throws IOException
  {
    reader.mark(1);
    int chr = reader.read();
    reader.reset();
    return chr;
  }
  
  public Token nextToken()
    throws IOException
  {
    int chr = peek();
    if (chr == -1) return null;
    
    Token token = null;
    Sequence sequence = charTable[chr];
    reader.mark(readAheadLimit);
    for (int i = 0; i < sequence.size(); i++)
    {
      reader.reset();
      Tokenizer tokenizer = sequence.getTokenizer(i);
      token = tokenizer.nextToken(reader);
      if (token != null) break;
    }
    if (skipIgnorables && token.isIgnorable())
    {
      token = nextToken();
    }
    return token;
  }
  
  public static void main(String[] args)
    throws IOException
  {
    String filename = "d:/javaprojects/common/util/tokenizer/FlexTokenizer.java";
    FlexTokenizer tokenizer = new FlexTokenizer(
      new TokenReader(new FileReader(filename)));
    
    Token tok;
    while ((tok = tokenizer.nextToken()) != null)
    {
      System.out.println(tok);
    }
  }
}

