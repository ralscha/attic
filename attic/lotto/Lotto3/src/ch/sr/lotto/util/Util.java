package ch.sr.lotto.util;

public class Util {

  public static String removeNonDigits(String digit) {
    StringBuffer sb = new StringBuffer();
    char[] chars = digit.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      if (Character.isDigit(chars[i]))
        sb.append(chars[i]);
    }
    return sb.toString();
  }
}
