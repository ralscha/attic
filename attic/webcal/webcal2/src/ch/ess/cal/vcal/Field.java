package ch.ess.cal.vcal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Field {

  private String fieldName; // this is the name of the field
  private String fieldValue; // the value for this field
  private Map table = new HashMap(); // these contain the properties

  public Field() {
    this(null, null);
  }
  
  public Field(String newFieldName) {
    this(newFieldName, null);
  }

  public Field(String newFieldName, String newFieldValue) {
    this.fieldName = newFieldName;
    this.fieldValue = newFieldValue;
  }

  public void setPropertyValue(String param, String value) {
    table.put(param, value);
  }

  public String getFieldName() {
    return fieldName;
  }

  public String getFieldValue() {
    return fieldValue;
  }

  public void setFieldName(String string) {
    fieldName = string;
  }

  public void setFieldValue(String string) {
    fieldValue = string;
  }

  public String getPropertyValue(String key) {
    return (String)table.get(key);
  }

  public Iterator getPropertyNames() {
    return table.keySet().iterator();
  }

  public String getPropertyValueString() {
    StringBuffer strbuf = new StringBuffer();
    getPropertiesString(strbuf);
    strbuf.append(":" + getFieldValue());     
    return (strbuf.toString());
  }



  public String toString() {
     StringBuffer strbuf = new StringBuffer();

     if (fieldValue == null) {
        return ("");
     }
     
     strbuf.append(getFieldName());     
     getPropertiesString(strbuf);
               
     if (getFieldValue() != null) {       
       if (getFieldValue().startsWith(":") || getFieldValue().startsWith(";") ) {
         strbuf.append(getFieldValue());             
       } else {             
         strbuf.append(":" + getFieldValue());
       }     
     }

     return (strbuf.toString());
  } 
  
  private void getPropertiesString(StringBuffer strbuf) {
    if (!table.isEmpty()) {
      Iterator it = table.keySet().iterator();
      String strKey;
      while (it.hasNext()) {
         strKey = (String)it.next();
         strbuf.append(";" + strKey + "=" + table.get(strKey));
      }
    }
  }  


}
