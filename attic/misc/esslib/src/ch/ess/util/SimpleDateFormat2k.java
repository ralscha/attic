
package ch.ess.util;

import java.text.*;
import java.util.*;



public class SimpleDateFormat2k extends java.text.SimpleDateFormat {

  public SimpleDateFormat2k(String pattern) {
    super(pattern);
  }

  public SimpleDateFormat2k(String pattern, java.text.DateFormatSymbols formatData) {
    super(pattern, formatData);
  }

  public SimpleDateFormat2k(String pattern, java.util.Locale loc) {
    super(pattern, loc);
  }

  public Date parse(String text, ParsePosition pos) {

    Date d = super.parse(text, pos);
    d = Util.rectifyDate(d, this.get2DigitYearStart());

    return d;
  }
}
