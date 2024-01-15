package ch.ess.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $  
 * 
 * @struts.form name="mapForm" 
 */
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
      if (GenericValidator.isBlankOrNull((String) value)) {
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