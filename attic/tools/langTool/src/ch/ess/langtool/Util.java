package ch.ess.langtool;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class Util {
  public static List<String> loadKeys(Reader reader) throws IOException {

    LineReader lr = new LineReader(reader);
    List<String> keyList = new ArrayList<String>();
    char[] convtBuf = new char[1024];
    int limit;
    int keyLen;
    //int valueStart;
    char c;
    //boolean hasSep;
    boolean precedingBackslash;

    while ((limit = lr.readLine()) >= 0) {
      c = 0;
      keyLen = 0;
      //valueStart = limit;
      //hasSep = false;

      //System.out.println("line=<" + new String(lineBuf, 0, limit) + ">");
      precedingBackslash = false;
      while (keyLen < limit) {
        c = lr.lineBuf[keyLen];
        //need check if escaped.
        if ((c == '=' || c == ':') && !precedingBackslash) {
          //valueStart = keyLen + 1;
          //hasSep = true;
          break;
        } else if ((c == ' ' || c == '\t' || c == '\f') && !precedingBackslash) {
          //valueStart = keyLen + 1;
          break;
        }
        if (c == '\\') {
          precedingBackslash = !precedingBackslash;
        } else {
          precedingBackslash = false;
        }
        keyLen++;
      }
//      while (valueStart < limit) {
//        c = lr.lineBuf[valueStart];
//        if (c != ' ' && c != '\t' && c != '\f') {
//          if (!hasSep && (c == '=' || c == ':')) {
//            hasSep = true;
//          } else {
//            break;
//          }
//        }
//        valueStart++;
//      }
      String key = loadConvert(lr.lineBuf, 0, keyLen, convtBuf);
      //String value = loadConvert(lr.lineBuf, valueStart, limit - valueStart, convtBuf);
      keyList.add(key);
    }

    return keyList;
  }

  private static String loadConvert(char[] in, int off, int len, char[] convtBuf) {
    if (convtBuf.length < len) {
      int newLen = len * 2;
      if (newLen < 0) {
        newLen = Integer.MAX_VALUE;
      }
      convtBuf = new char[newLen];
    }
    char aChar;
    char[] out = convtBuf;
    int outLen = 0;
    int end = off + len;

    while (off < end) {
      aChar = in[off++];
      if (aChar == '\\') {
        aChar = in[off++];
        if (aChar == 'u') {
          // Read the xxxx
          int value = 0;
          for (int i = 0; i < 4; i++) {
            aChar = in[off++];
            switch (aChar) {
              case '0' :
              case '1' :
              case '2' :
              case '3' :
              case '4' :
              case '5' :
              case '6' :
              case '7' :
              case '8' :
              case '9' :
                value = (value << 4) + aChar - '0';
                break;
              case 'a' :
              case 'b' :
              case 'c' :
              case 'd' :
              case 'e' :
              case 'f' :
                value = (value << 4) + 10 + aChar - 'a';
                break;
              case 'A' :
              case 'B' :
              case 'C' :
              case 'D' :
              case 'E' :
              case 'F' :
                value = (value << 4) + 10 + aChar - 'A';
                break;
              default :
                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
            }
          }
          out[outLen++] = (char)value;
        } else {
          if (aChar == 't')
            aChar = '\t';
          else if (aChar == 'r')
            aChar = '\r';
          else if (aChar == 'n')
            aChar = '\n';
          else if (aChar == 'f')
            aChar = '\f';
          out[outLen++] = aChar;
        }
      } else {
        out[outLen++] = aChar;
      }
    }
    return new String(out, 0, outLen);
  }

  public static String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
    int len = theString.length();
    int bufLen = len * 2;
    if (bufLen < 0) {
      bufLen = Integer.MAX_VALUE;
    }
    StringBuffer outBuffer = new StringBuffer(bufLen);

    for (int x = 0; x < len; x++) {
      char aChar = theString.charAt(x);
      // Handle common case first, selecting largest block that
      // avoids the specials below
      if ((aChar > 61) && (aChar < 127)) {
        if (aChar == '\\') {
          outBuffer.append('\\');
          outBuffer.append('\\');
          continue;
        }
        outBuffer.append(aChar);
        continue;
      }
      switch (aChar) {
        case ' ' :
          if (x == 0 || escapeSpace)
            outBuffer.append('\\');
          outBuffer.append(' ');
          break;
        case '\t' :
          outBuffer.append('\\');
          outBuffer.append('t');
          break;
        case '\n' :
          outBuffer.append('\\');
          outBuffer.append('n');
          break;
        case '\r' :
          outBuffer.append('\\');
          outBuffer.append('r');
          break;
        case '\f' :
          outBuffer.append('\\');
          outBuffer.append('f');
          break;
        case '=' : // Fall through
        case ':' : // Fall through
        case '#' : // Fall through
        case '!' :
          outBuffer.append('\\');
          outBuffer.append(aChar);
          break;
        default :
          if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
            outBuffer.append('\\');
            outBuffer.append('u');
            outBuffer.append(toHex((aChar >> 12) & 0xF));
            outBuffer.append(toHex((aChar >> 8) & 0xF));
            outBuffer.append(toHex((aChar >> 4) & 0xF));
            outBuffer.append(toHex(aChar & 0xF));
          } else {
            outBuffer.append(aChar);
          }
      }
    }
    return outBuffer.toString();
  }

  private static char toHex(int nibble) {
    return hexDigit[(nibble & 0xF)];
  }

  /** A table of hex digits */
  private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
      'F'};

}
