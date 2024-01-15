import java.io.*;
import java.util.*;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

public class Test {


  public static void main(String[] args) {

    PrintStream out = System.out;

    try {

      TemplateParser tp = new TemplateParser("resovertrag.xml");
      

      List idList = tp.getIDList();

      for (int i = 0; i < idList.size(); i++) {
        System.out.println(idList.get(i));
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}