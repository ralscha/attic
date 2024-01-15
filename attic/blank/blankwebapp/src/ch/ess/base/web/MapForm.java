package ch.ess.base.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * @author  sr
 * @version $Revision: 1.2 $ $Date: 2005/07/03 10:25:05 $  
 */
public class MapForm extends ActionForm {

  private Map<String, Object> valueMap = new HashMap<String, Object>();

  public Map<String, Object> getValueMap() {
    return valueMap;
  }

  @Override
  public void reset(ActionMapping mapping, HttpServletRequest request) {
    if (request.getParameter("btnSearch.x") != null) {
      valueMap.clear();
    }
  }

  public void setValueMap(final Map<String, Object> valueMap) {
    this.valueMap = valueMap;
  }

  public void setValue(final String key, final Object value) {

    Object tmpVal = value;

    if (tmpVal instanceof String) {
      if (GenericValidator.isBlankOrNull((String)tmpVal)) {
        tmpVal = null;
      }
    }
    getValueMap().put(key, tmpVal);
  }

  public Object getValue(final String key) {
    return getValueMap().get(key);
  }

  public String getStringValue(final String key) {
    return (String)getValue(key);
  }

}
