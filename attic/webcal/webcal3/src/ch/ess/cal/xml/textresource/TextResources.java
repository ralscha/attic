package ch.ess.cal.xml.textresource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.LogFactory;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:33 $ 
 * 
 * @spring.bean id="textResources" init-method="init"
 */
public class TextResources {

  private Map<String, Resource> textResourcesMap;

  public TextResources() {
    textResourcesMap = new HashMap<String, Resource>();
  }

  public void addResource(final Resource res) {
    textResourcesMap.put(res.getKey(), res);
  }

  public void addResource(final List<Resource> resourceList) {
    for (Resource resource : resourceList) {
      addResource(resource);
    }
  }

  public Map<String, Resource> getResources() {
    return textResourcesMap;
  }

  @SuppressWarnings("unchecked")
  public void init() {
    InputStream is = null;
    try {
      is = this.getClass().getResourceAsStream("/textresource.xml");
      if (is != null) {
        Digester digester = new Digester();
        digester.setClassLoader(getClass().getClassLoader());

        digester.addRuleSet(new TextresourceRuleSet());

        List<Resource> list = (List<Resource>)digester.parse(is);
        addResource(list);

      }
    } catch (Exception e) {
      LogFactory.getLog(getClass()).error("init", e);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          LogFactory.getLog(getClass()).error("init", e);
        }
      }
    }
  }

}
