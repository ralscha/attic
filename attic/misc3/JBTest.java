

import java.util.*;
import java.io.*;
import com.quiotix.html.parser.*;

public class JBTest {

	public static void main(String[] args) {

    

    try {	

      FileInputStream fis = new FileInputStream("quicksearch.txt");       
      byte[] buffer = new byte[4096];
      StringBuffer sb = new StringBuffer();
			while (true) {
				int bytes_read = fis.read(buffer);
				if (bytes_read == -1)
					break;
        for (int i = 0; i < bytes_read; i++) {
          sb.append((char)buffer[i]);
        }

			}
      
      fis.close();

     String s = sb.toString();

		// Execute the method
		long start = System.currentTimeMillis();
    /*
    StringReader sr = new StringReader(s);
    HtmlDocument document = new HtmlParser(sr).HtmlDocument();

    document.accept(new HtmlScrubber(HtmlScrubber.TRIM_SPACES));
		document.accept(new HtmlDumper(new FileOutputStream("quicksearchopt.txt")));
	*/
	  StringBuffer stringbuffer = new StringBuffer(2048);

    char c1 = ' ';
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c == '\r') 
        c = '\n';

      if (((c == ' ') || (c == '\t')) && c1 == '\n' || c1 == '\t') {
        while(((c == ' ') || (c == '\t')) && (i < s.length()-1)) {
          i++;
          
          c = s.charAt(i);
          if (c == '\r') 
            c = '\n';          
        }
      }

      if ((c1 != '\n' || c != '\n' && c != '\t') &&
          (c1 != '\t' && c != '\t')) {
        c1 = c;
        stringbuffer.append(c);
      }
    }
    

    PrintWriter pw = new PrintWriter(new FileWriter("quicksearchopt.txt"));
    pw.print(stringbuffer.toString());
    pw.close();
  

		long stop = System.currentTimeMillis();

		// Display elapsed time
		System.out.println((stop - start) + "ms");

    } catch (Exception ioe) {
      System.err.println(ioe);
    }
	}

}