
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class ActionFormBeanMaker {

  public static void main(String[] args) {
    if (args.length >= 2) {
      try {
        boolean validating = false;
        boolean htmlPage = false;
        String fileName = null;
         
        if (args.length == 3) {
          if ("-v".equalsIgnoreCase(args[0]))
            validating = true;
          fileName = args[2];
          htmlPage = "-html".equalsIgnoreCase(args[1]);
        } else {
          htmlPage = "-html".equalsIgnoreCase(args[0]);
          fileName = args[1];
        }
        
        Reader reader = new BufferedReader(new FileReader(fileName));
        if (htmlPage) {        
          new ParserDelegator().parse(reader, new FormParserCallback(validating), false);
        } else {
          new ParserDelegator().parse(reader, new StrutsParserCallback(validating), false);
        }

      } catch (IOException ioe) {
        System.err.println(ioe);
      }
    } else {
      System.out.println("java ActionFormBeanMaker [-v] <-jsp|-html> <fileName>");
    }

  }

}