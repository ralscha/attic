package sr.mvs;

public class Util {

  public static String convertText(String text) {
    StringBuffer sb = new StringBuffer(text.length());
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
      if (c == 'ß')
        sb.append("ss");
      else if (c == '«')
        sb.append('"');
      else if (c == '»')
        sb.append('"');
      else if (c == '„') 
        sb.append('"');
      else if ((c == '´') && ((i == 0) || (i == text.length() - 1)))
        sb.append('"');
      else if ((c == '\'') && ((i == 0) || (i == text.length() - 1)))
        sb.append('"');
      else
        sb.append(c);
    }
    return (sb.toString());

  }

}
