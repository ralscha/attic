package ch.ess.struts;

import java.util.*;

import org.apache.struts.action.*;


/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.5 $ $Date: 2002/08/19 08:40:37 $
 * @author $Author: sr $
 */
public class MapForm extends ActionForm {

  private Map valueMap = new HashMap();

  /**
   * DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Map getValueMap() {

    return valueMap;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param valueMap DOCUMENT ME!
   */
  public void setValueMap(Map valueMap) {
    this.valueMap = valueMap;
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key DOCUMENT ME!
   * @param value DOCUMENT ME!
   */
  public void setValue(String key, Object value) {

    if (value instanceof String) {

      if (((String)value).trim().equals(ch.ess.Constants.BLANK_STRING)) {
        value = null;
      }
    }
    getValueMap().put(key, value);
  }

  /**
   * DOCUMENT ME!
   * 
   * @param key DOCUMENT ME!
   * 
   * @return DOCUMENT ME!
   */
  public Object getValue(String key) {

    return getValueMap().get(key);
  }
}