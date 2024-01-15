package common.util.match;

import java.util.*;

public class RegularExpression implements Match
{
  public static final char BOL  = '^';  // Beginning of line
  public static final char EOL  = '$';  // End of line
  public static final char ANY  = '.';  // Any single character
  public static final char BOC  = '[';  // Start of class
  public static final char EOC  = ']';  // End of class
  public static final char MORE = '*';  // Zero or more
  public static final char MANY = '+';  // One or more
  public static final char OPT  = '?';  // Optional

  protected ArrayList list; // Internal list of tokens
  protected String pattern;

/**
 * Constructor expects a regular expression pattern string which it
 * immediately parses into components. The result is stored
 * internally in the ArrayList and serves to later match against
 * any required strings.
 * @param pattern Any regular expression string desired.
**/
  public RegularExpression(String pattern)
  {
    this.pattern = pattern;
    list = new ArrayList();
    StringBuffer buffer = new StringBuffer(pattern);
    while (buffer.length() > 0)
    {
      parse(buffer);
    }
  }

/**
 * Return the last element from the list and remove it at
 * the same time. This is useful with the optional and repeating
 * elements where the operation applies to the preceding element.
 * The repeaters pop the list and make the value part of their
 * definition, folding the item into their structures.
**/
  private Expression pop()
  {
    return (Expression)list.remove(list.size() - 1);
  }

/**
 * Parse the next item, popping the characters off the work
 * string on each iteration. The repeaters fold the previous
 * item into their structures. The class range calls a method
 * that makes the ranges of characters explicit for faster
 * lookup during pattern matching.
**/
  private void parse(StringBuffer buffer)
  {
    char ch = buffer.charAt(0);
    buffer.deleteCharAt(0);
    switch(ch)
    {
      case BOL: list.add(new Terminal(BOL)); break;
      case EOL: list.add(new Terminal(EOL)); break;
      case ANY: list.add(new Terminal(ANY)); break;
      case MORE: list.add(new Group(MORE, pop())); break;
      case MANY: list.add(new Group(MANY, pop())); break;
      case OPT: list.add(new Group(OPT, pop())); break;
      case BOC: list.add(new Range(buffer)); break;
      default: list.add(new Literal(ch));
    }
  }

/**
 * External call to be made if you want to check a string against
 * the regular expression described by this object.
 * @param text The text to compare with the regular expression.
 * @return A boolean value indicating whether the string matches or not.
**/
  public boolean match(String text)
  {
    StringBuffer buffer = new StringBuffer(text);
    for (int i = 0; i < list.size(); i++)
    {
      if (buffer.length() == 0) return false;
      Expression expression = (Expression)list.get(i);
      if (expression instanceof Terminal &&
        ((Terminal)expression).type == BOL)
      {
        // BOL is only valid if this is the first element
        if (i > 0) return false;
      }
      else if (!expression.test(buffer)) return false;
    }
    return true;
  }

// ------------------------------------------------------------------
// Inner Support Classes
// ------------------------------------------------------------------

  protected abstract class Expression
  {
    public abstract boolean test(StringBuffer buffer);
  }
  
  protected class Literal extends Expression
  {
    protected char token;

    public Literal(char token)
    {
      this.token = token;
    }
    
    public boolean test(StringBuffer buffer)
    {
      char chr = buffer.charAt(0);
      if (token == chr)
        buffer.deleteCharAt(0);
      return token == chr;
    }

    public String toString()
    {
      return "literal('" + token + "')";
    }
  }
  
  protected class Terminal extends Expression
  {
    protected int type;

    public Terminal(int type)
    {
      this.type = type;
    }
    
    public boolean test(StringBuffer buffer)
    {
      if (type == EOL)
      {
        return buffer.length() == 0;
      }
      if (type == ANY)
      {
        buffer.deleteCharAt(0);
        return true;
      }
      return false;
    }

    public String toString()
    {
      String name = "any";
      if (type == BOL) name = "bol";
      if (type == EOL) name = "eol";
      return name;
    }
  }

  protected class Range extends Expression
  {
    protected String chars;

    public Range(StringBuffer text)
    {
      chars = parse(text);
    }
    
    public boolean test(StringBuffer buffer)
    {
      boolean result;
      char ch = buffer.charAt(0);
      String temp = chars;
      if (chars.charAt(0) == '^')
      {
        temp = temp.substring(1);
        result = temp.indexOf(ch) < 0;
      }
      else
      {
        result = temp.indexOf(ch) > -1;
      }
      if (result)
        buffer.deleteCharAt(0);
      return result;
    }

/**
 * Parse multiple ranges with or without a preceding NOT
 * operator. The NOT operator is a hardcoded '^' character.
 * This call returns an explicit list of characters with all
 * characters in the range in the string.
**/
    protected String parse(StringBuffer buffer)
    {
      String range, total = "";
      if (buffer.charAt(0) == '^')
      {
        buffer.deleteCharAt(0);
        total = "^";
      }
      while ((range = parseRange(buffer)) != null)
      {
       total += range;
      }
      return total;
    }

/**
 * Parse a range in the form a-b where 'a' is the low value and
 * 'b' is the high value, taking into account single character
 * ranges (where no dash '-' or second value is involved).
 * @return The string of explicit characters to match.
**/
    protected String parseRange(StringBuffer buffer)
    {
      if (buffer.length() == 0) return null;
      if (buffer.charAt(0) == EOC)
      {
        buffer.deleteCharAt(0);
        return null;
      }
      char from, to;
      if (buffer.charAt(1) == '-')
      {
        from = buffer.charAt(0);
        to = buffer.charAt(2);
        StringBuffer range = new StringBuffer();
        for (char i = from; i <= to; i++)
        {
          range.append(i);
        }
        buffer.delete(0, 2);
        return range.toString();
      }
      else
      {
        from = buffer.charAt(0);
        buffer.deleteCharAt(0);
        return "" + from;
      }
    }

    public String toString()
    {
      return "class(" + '"' + chars + '"' + ")";
    }
  }

  protected class Group extends Expression
  {
    protected int type;
    protected Expression expr;

    public Group(int type, Expression expr)
    {
      this.type = type;
      this.expr = expr;
    }

    public boolean test(StringBuffer buffer)
    {
      if (type == OPT)
      {
        expr.test(buffer);
        return true;
      }
      if (type == MORE)
      {
        while (expr.test(buffer)) {}
        return true;
      }
      if (type == MANY)
      {
        if (!expr.test(buffer)) return false;
        while (expr.test(buffer)) {}
        return true;
      }
      return false;
    }

    public String toString()
    {
      String name = "optional";
      if (type == MORE) name = "zeroormore";
      if (type == MANY) name = "oneormore";
      return name + "(" + expr + ")";
    }
  }

// ------------------------------------------------------------------
// Test
// ------------------------------------------------------------------

  public static void main(String args[])
  {
//	String pattern = "^1+2*3?[a-fA-F0-9]*456.78[^q-r]9$";
    String pattern = "12?3[456]*789";
    String test = "13456789";
    RegularExpression expr = new RegularExpression(pattern);
    System.out.println("Test = " + test);
    System.out.println("Pattern = " + pattern);
    System.out.println("List = " + expr.list);
    System.out.println("Match: " + expr.match(test));
  }
}

