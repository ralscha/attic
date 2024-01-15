
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.digester.Digester;

import ch.ess.common.xml.permission.PermissionRuleSet;

/**
 * @author sr
 */
public class Test {

  public static void main(String[] args) {

    InputStream is = null;
    try {
      is = Test.class.getResourceAsStream("/permissions.xml");
      if (is != null) {
        Digester digester = new Digester();

        digester.addRuleSet(new PermissionRuleSet());

        List l = (List) digester.parse(is);

        System.out.println(l.size());

        for (Iterator it = l.iterator(); it.hasNext();) {
          String element = (String) it.next();
          System.out.println(element);

        }

      }
    } catch (Exception e) {
      e.printStackTrace();

    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }

  }
}