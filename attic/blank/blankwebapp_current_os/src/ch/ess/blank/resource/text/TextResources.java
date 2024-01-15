package ch.ess.blank.resource.text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 12:24:21 $
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
      addResource((Resource) it.next());

    }
  }

  public static Map getResources() {
    return INSTANCE.textResources;
  }

}