
/**
 * Base class for various translate tables.
 * Each char may translate to another char or to
 * a String of chars.
 */
public class Translator {
  // possible encodings
  public static final int LATIN1 = 0;
  public static final int WIN = 1;
  public static final int IBMOEM = 2;

  /**
   * translation table when input is latin1 encoding
   */
  String[] latin1;

  /**
   * translate table when input is Windows encoding
   */
  String[] win;

  /**
   * translation table when input is IBMOEM encoding
   */
  String[] ibmoem;

  /** which translate table we are using right now */
  String[] table;


  /**
   * no-arg constructor.
   */
  public Translator () {
    table = latin1;
  }


  /**
    * Set the encoding of the coming input text
    *
    * @param encoding LATIN1 WIN IBMOEM
    */
  public void setEncoding (int encoding) {
    switch (encoding) {

      default:
      case LATIN1:
        table = latin1;
        break;

      case WIN:
        table = win;
        break;

      case IBMOEM:
        table = ibmoem;
        break;
    } // end switch

  } // end setEncoding


  /**
   * Converts text to Java String literals by quoting dangerous characters
   * e.g. " ==> \"
   *
   * @param raw  the raw text to be translated.
   *
   * @param highAscii Char to use to represent high ASCII
   * chars that don't have entries in the table.
   * -1 means leave char unmolested.
   * -2 means convert to \ u x x x x form
   * @param enclose, true if generated string should be enclosed in quotes
   * @return String representing the raw input translated
   */
  String process (String raw, int highAscii, boolean enclose) {
    if (raw == null || raw.length() == 0) {
      return null;
    }
    if (table == null) {
      table = latin1;
    }
    StringBuffer cooked = new StringBuffer(raw.length() * 120 / 100);
    //
    if (enclose) {
      cooked.append("  \"");
    }
    for (int i = 0; i < raw.length(); i++) {
      char c = raw.charAt(i);
      if (c < 256) {
        cooked.append(table[c]);
      } else {
        switch (highAscii) {
          default:
            cooked.append((char) highAscii);
            break;
          case - 2:
            cooked.append("\\u" + Integer.toHexString(c + 0x10000).substring(1, 5));
            break;
          case - 1:
            cooked.append(c);
            break;
        }
      } // end else
    } // end for

    if (enclose) {
      // get rid of trailing junk, we may have appended too much.
      cooked.append("\"");
      String cookedString = cooked.toString();
      if (cookedString.endsWith("+ \"\"")) {
        cookedString = cookedString.substring(0, cookedString.length() - 4);
      }
      return cookedString;
    } else {
      return cooked.toString();
    }
  } // end process


  /**
   * Converts text to Java String literals by quoting dangerous characters
   * e.g. " ==> \". Leaves high ASCII chars unmolested.
   *
   * @param raw the  raw text to be translated.
   *
   * @return translated text
   */
  public String process (String raw) {
    return process(raw, -1, false);
  } // end process

} // end class Translator




