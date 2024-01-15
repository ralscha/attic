package ch.ess.cal.resource.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.1 $ $Date: 2004/02/14 16:32:53 $ 
  */

public class Resource {
  private String key;
  private Map text;
  private List variableList;
  private Map description;
    
  public String getKey() {
    return key;
  }

  public List getVariableList() {
    return variableList;
  }

  public void setKey(String string) {
    key = string;
  }

  public void setVariableList(List list) {
    variableList = list;
  }

  public void addText(String locale, String t) {
    if (text == null) {
      text = new HashMap();
    }
    text.put(new Locale(locale), t);
  }

  public Map getText() {
    return text;
  }

  public void addVariable(Variable variable) {
    if (variableList == null) {
      variableList = new ArrayList();
    }
    variableList.add(variable);
  }  
  
  public void addDescription(String locale, String txt) {
    if (description == null) {
      description = new HashMap();
    }    
    description.put(new Locale(locale), txt);
  }  

  public Map getDescription() {
    return description;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  } 

}
