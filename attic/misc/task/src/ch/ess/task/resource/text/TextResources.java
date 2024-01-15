package ch.ess.task.resource.text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2003/11/15 10:33:28 $ 
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