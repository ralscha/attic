package ch.ess.addressbook.resource.text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.3 $ $Date: 2003/11/11 19:01:19 $ 
  */

public class TextResources {

  private final static TextResources INSTANCE = new TextResources();

  private Map textResources;

  private TextResources() {
    textResources = new HashMap();
  }

  public static void addResource(Resource res) {
    INSTANCE.textResources.put(res.getKey(), res);
  }

  public static void addResource(List resourceList) {
    for (Iterator it = resourceList.iterator(); it.hasNext();) {
      addResource((Resource)it.next());

    }
  }

  public static Map getResources() {
    return INSTANCE.textResources;
  }

}