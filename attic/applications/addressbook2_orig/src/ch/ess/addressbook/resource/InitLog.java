package ch.ess.addressbook.resource;

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
  * @version 1.0, 23.07.2003 
  */
public class InitLog {

  private static String propsFile = null;
  private static Map substMap = null;
  
  private InitLog() {
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

    replaceVariables(props, substMap);

    PropertyConfigurator.configure(props);
  }

  private static void replaceVariables(Properties props, Map substMap) {

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
