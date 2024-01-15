
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

public class Test {

	public static void main(String[] args) {
    try {
      Reader reader = new FileReader("test.html");
      new ParserDelegator().parse(reader, new FormParserCallback(true), false);
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
    
	}

}