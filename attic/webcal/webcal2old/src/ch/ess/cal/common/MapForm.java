package ch.ess.cal.common;

import java.util.*;

import javax.servlet.http.*;

import org.apache.commons.validator.*;
import org.apache.struts.action.*;

public class MapForm extends ActionForm {

  private Map valueMap = new HashMap();

  public Map getValueMap() {
    return valueMap;
  }

  public void setValueMap(Map valueMap) {
    this.valueMap = valueMap;
  }

  public void setValue(String key, Object value) {

    if (value instanceof String) {
      if (GenericValidator.isBlankOrNull((String)value)) {
        value = null;
      }
    }
    getValueMap().put(key, value);
  }

  public Object getValue(String key) {
    return getValueMap().get(key);
  }

  public void reset(ActionMapping mapping, HttpServletRequest request) {
    if (request.getParameter("search") != null) {
      valueMap.clear();
    }
  }

}