package ch.ess.cal.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:52 $ 
  */
public class InitLog {

  private static String propsFile = null;
  private static Map substMap = null;
  
  private InitLog() {
    //no action
  }
  
  public static void shutdown() {
    LogManager.shutdown();
    propsFile = null;
    substMap = null;
  }

  public static void init(String fileName, Map sMap) {
    propsFile = fileName;
    substMap = sMap;
  }

  public static Map getSubstMap() {
    return substMap;
  }

  public static void configure() throws IOException {
        
    Properties props = new Properties();
    InputStream is = null;

    try {
      is = InitLog.class.getResourceAsStream(propsFile);
      props.load(is);
    } finally {
      if (is != null) {
        is.close();
      }
    }

    replaceVariables(props);

    PropertyConfigurator.configure(props);
  }

  private static void replaceVariables(Properties props) {

    Iterator it = props.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry)it.next();

      String value = (String)entry.getValue();

      for (Iterator substIt = substMap.entrySet().iterator(); substIt.hasNext();) {
        Map.Entry substEntry = (Map.Entry)substIt.next();
        String var = (String)substEntry.getKey();

        if (value.indexOf(var) != -1) {
          value = StringUtils.replace(value, var, (String)substEntry.getValue());
          entry.setValue(value);
        }

      }

    }

  }

}
