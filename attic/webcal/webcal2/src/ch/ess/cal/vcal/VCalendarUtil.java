//==============================================================================
//===   OskiVCalUtil.java   ====================================================

package ch.ess.cal.vcal;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Random;

import ch.ess.cal.Constants;
import ch.ess.cal.vcal.parser.ParseException;
import ch.ess.cal.vcal.parser.VCalParser;

public class VCalendarUtil {

  private static Random rand = new Random();

  private VCalendarUtil() {
    //no action
  }

  /**
   * Return a unique ID for this calendar entity. Or mostly unique, at least.
   */
  public static synchronized String generateUniqueID() {
    StringBuffer strbuf = new StringBuffer();
    strbuf.append("ESSWC");

    try {
      strbuf.append(java.net.InetAddress.getLocalHost().getHostAddress());
      strbuf.append("-");
    } catch (Exception e) {
      //no action
    }

    strbuf.append(Constants.DATE_UTC_FORMAT.format(new Date()));
    strbuf.append("-");

    strbuf.append(Math.abs(rand.nextInt()));
    return (strbuf.toString());
  }

  public static String addCarriageReturn(String str) {
    StringBuffer strbuf = new StringBuffer(str);
    boolean flagCR = false;

    for (int i = 0; i < str.length(); i++) {
      if (strbuf.charAt(i) == '\r') {
        flagCR = true;
        continue;
      }
      if (strbuf.charAt(i) == '\n') {
        if (flagCR == false) {
          strbuf.insert(i, '\r');
        }
        i++;
      }
      flagCR = false;
    }
    return (strbuf.toString());
  } // of addCarriageReturn

  //===========================================================================

  /**
   * Parse a String into a vcal entity.
   */
  public static synchronized VObject parseVCalEntity(String str) throws ParseException {

    ByteArrayInputStream bistream;
    VCalParser parser;
    VObject ent;

    bistream = new ByteArrayInputStream(str.getBytes());
    parser = new VCalParser(bistream);
    ent = parser.CalEntity();

    return (ent);
  } // of parseVCalEntity

  //===========================================================================

  /**
   * Parse a String into a vcalendar.
   */
  public static synchronized VCalendar parseVCalendar(String str) throws ParseException {

    ByteArrayInputStream bistream;
    VCalParser parser;
    VCalendar cal;

    bistream = new ByteArrayInputStream(str.getBytes());
    parser = new VCalParser(bistream);
    cal = parser.Input();

    return (cal);
  } // of parseVCalendar

  //===========================================================================

  /**
   * Parse a vcal VEvent.
   *
   * @param  str contains the VEvent text.
   * @return an OskiEvent object containing the parsed values.
   */
  public static synchronized VEvent parseVEvent(String str) throws ParseException {

    VEvent evt = (VEvent)parseVCalEntity(str);
    return (evt);
  } // of parseVEvent

  //===========================================================================

  /**
   * Parse a vcal VTodo.
   *
   * @param  str contains the VTodo text.
   * @return an OskiTodo object containing the parsed values.
   */
  public static synchronized VTodo parseVTodo(String str) throws ParseException {

    VTodo tdo = (VTodo)parseVCalEntity(str);
    return (tdo);
  }

  public static String quodedPrintable2eightBit(String input) {

    StringBuffer output = new StringBuffer(80);
    
    for (int i = 0; i < input.length(); i++) {
      if (input.charAt(i) == '=') {
        if (i + 2 < input.length()) {
          try {
            output.append((char)Integer.parseInt(input.substring(i + 1, i + 3), 16));
            i = i + 2;
          } catch (NumberFormatException nfe) {
            output.append(input.charAt(i));
          }
        }
      } else {
        output.append(input.charAt(i));
      }
    }
    return ("x" + output.toString()).trim().substring(1); // discard following spaces
  }

  public static String eightBit2quodedPrintable(String input) {
    StringBuffer output = new StringBuffer(80);
    output.append("=\r\n");
    int k = 65; // position to fold the line
    int o = 1; // length of output line

    for (int i = 0; i < input.length(); i++) {
      char ch = input.charAt(i);
      if (ch > 127 | ch == '=') {
        output.append("=" + Integer.toHexString(ch).toUpperCase());
        o = o + 3;
      } else if (ch == 13 | ch == 10) {
        output.append("=0D=0A");
        char ch2;
        if (i + 1 < input.length())
          ch2 = input.charAt(i + 1);
        else
          ch2 = ' ';
        if (ch2 == 13 | ch2 == 10)
          i++; // skip next character
        o = o + 6;
      } else {
        output.append(ch);
        o++;
      }
      if (o > k - 1) {
        output.append("=\r\n"); // fold line 
        k = k + 65;
      }
    }
    return output.toString();
  }


}