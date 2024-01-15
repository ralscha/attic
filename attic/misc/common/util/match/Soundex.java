package common.util.match;

public class Soundex implements Match
{
  protected String pattern;
  
  public Soundex(String pattern)
  {
    this.pattern = encode(pattern);
  }

  public boolean match(String text)
  {
    return pattern.equals(encode(text));
  }

  public String encode(String input)
  {
    String text = input.toLowerCase();
    for(int i = 0; i < input.length() - 1; i++)
    {
      char head = text.charAt(i);
      if (head >= 'a' && head <= 'z')
      {
        String tail = input.substring(i + 1);
        return head + phonetic(tail).substring(3);
      }
    }
    return "";
  }

  private String phonetic(String input)
  {
    StringBuffer buffer = new StringBuffer();
    // For each character, skipping adjacent duplicates
    for(int i = 0; i < input.length() - 1; i++)
    {
      if (input.charAt(i) != input.charAt(i + 1))
      {
        char code = charCode(input.charAt(i));
        // If soundex code is not zero, add to buffer
        if (code != '0') buffer.append(code);
      }
    }
    return buffer.toString();
  }

  private char charCode(char chr)
  {
    switch (chr)
    {
      case 'b': return '1';
      case 'c': return '2';
      case 'd': return '3';
      case 'f': return '1';
      case 'g': return '2';
      case 'j': return '2';
      case 'k': return '2';
      case 'l': return '4';
      case 'm': return '5';
      case 'n': return '5';
      case 'p': return '1';
      case 'q': return '2';
      case 'r': return '6';
      case 's': return '2';
      case 't': return '3';
      case 'v': return '1';
      case 'x': return '2';
      case 'z': return '2';
      default : return '0';
    }
  }
}

