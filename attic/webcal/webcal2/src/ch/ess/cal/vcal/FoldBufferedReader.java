package ch.ess.cal.vcal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FoldBufferedReader extends BufferedReader {

  public FoldBufferedReader(Reader in) {
    super(in);
  }

  public FoldBufferedReader(Reader in, int sz) {
    super(in, sz);
  }

  public String readLine() throws IOException {
    String s = super.readLine();
 
    if (s == null) {
      return null;
    }
 
    boolean lineUnfoldingQuotedPrintable = false;
    if (s.toUpperCase().indexOf("QUOTED-PRINTABLE") < s.indexOf(":") & s.toUpperCase().indexOf("QUOTED-PRINTABLE") > 0) {
      lineUnfoldingQuotedPrintable = true;
    }

    mark(100);
 
    while (true) {
      String s2 = super.readLine();
      if (s2 == null || s2.length() == 0)
        return s;
      if (s2.charAt(0) == ' ' & !lineUnfoldingQuotedPrintable | s.endsWith("=") & lineUnfoldingQuotedPrintable) {
        mark(100);
        
        if (lineUnfoldingQuotedPrintable) {
          s = s.substring(0, s.length() - 1) + s2;
        } else {
          s = s + s2.substring(1);
        }

      } else {
        reset();
        break;
      }
    }

    return s;
  }
  
  public static void main(String[] args) {
    try {
      FoldBufferedReader br = new FoldBufferedReader(new FileReader("c:/temp/test.txt"));
      String line = null;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
        System.out.println(VCalendarUtil.quodedPrintable2eightBit(line.substring(35)));
        
      }
    } catch (FileNotFoundException e) {
      
      e.printStackTrace();
    } catch (IOException e) {
      
      e.printStackTrace();
    }
    

  }  
}
