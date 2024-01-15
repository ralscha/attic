package ch.ess.cal.vcal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract public class VObject implements VCalendarConstants {

  private Map table = new HashMap();

  /*
  public OskiCalendarEntity() {
     String strUniqueID = generateUniqueID();
     setOskiID(strUniqueID);
     addFieldValue(FIELD_DCREATED, Constants.DATE_UTC_FORMAT.format(new Date()));
     addFieldValue(FIELD_UID, strUniqueID);
  } 
  */

  public void clearField(String strFieldName) {
    table.remove(strFieldName);
  }

  public void addFieldValue(String strFieldName, String strValue) {
    if (strValue == null) {
      clearField(strFieldName);
    }
    addField(new Field(strFieldName, strValue));
  }

  public void addField(Field field) {
    if (isList(field.getFieldName())) {
      addFieldValueToList(field);
    } else {
      addFieldValueToSingleton(field);
    }
  }

  private void addFieldValueToList(Field field) {
    String strFieldName = field.getFieldName();

    List l = (List)table.get(strFieldName);
    if (l == null) {
      l = new ArrayList();
      table.put(strFieldName, l);
    }

    l.add(field);

  }

  private void addFieldValueToSingleton(Field field) {
    table.put(field.getFieldName(), field);

  }

  public Field getField(String strFieldName) {
    if (isList(strFieldName)) {
      throw new IllegalArgumentException("not a singleton");
    }

    return ((Field)table.get(strFieldName));
  }

  public String getPropertyValue(String strFieldName, String strPropertyName) {
    Field field = getField(strFieldName);
    if (field == null) {
      return (null);
    }
    return (field.getPropertyValue(strPropertyName));
  }

  public String getFieldValue(String strFieldName) {
    Field field = getField(strFieldName);
    if (field == null) {
      return (null);
    }
    return (field.getFieldValue());
  }

  public Iterator getFieldValueList(String strFieldName) {

    if (!isList(strFieldName)) {
      throw new IllegalArgumentException("not a list");
    }

    List vec = (List)table.get(strFieldName);
    if (vec == null) {
      return Collections.EMPTY_LIST.iterator();
    }
    return (vec.iterator());
  }

  public Iterator getAllExistingFieldNames() {
    return (table.keySet().iterator());
  }

  Iterator getAllListObjects() {
    List vecReturn = new ArrayList();
    Iterator enumeration = table.keySet().iterator();
    String strFieldName;

    while (enumeration.hasNext()) {
      strFieldName = (String)enumeration.next();
      if (isList(strFieldName) == true) {
        vecReturn.add(table.get(strFieldName));
      }
    }

    return (vecReturn.iterator());
  }

  void putFieldObject(String strFieldName, Object obj) {
    table.put(strFieldName, obj);
  }

  Object getFieldObject(String strFieldName) {

    Object obj = table.get(strFieldName);

    if (obj == null) {
      return (null);
    }

    return (obj);

  }

  public String toString() {
    StringBuffer strbuf = new StringBuffer();
    Iterator it = table.keySet().iterator();
    String strFieldName;
    Object obj;

    List vec;

    while (it.hasNext()) {
      strFieldName = (String)it.next();
      obj = table.get(strFieldName);

      if (isList(strFieldName) == true) {
        vec = (List)obj;
        strbuf.append(processList(strFieldName, vec));
      } else {
        if (((Field)obj).getFieldValue() == null)
          continue;
        strbuf.append(obj + "\r\n");
      }
    }
    return (strbuf.toString());

  }

  private String processList(String strFieldName, List vec) {
    if (isMultiList(strFieldName)) {
      return (processMultilist(vec));
    } else {
      return (strFieldName + ":" + processUnilist(vec));
    }
  }

  private String processMultilist(List vec) {
    Iterator enumeration = vec.iterator();
    StringBuffer strbuf = new StringBuffer();

    while (enumeration.hasNext()) {
      strbuf.append(enumeration.next() + "\r\n");
    }
    return (strbuf.toString());
  }

  private String processUnilist(List vec) {
    Iterator enumeration = vec.iterator();
    StringBuffer strbuf = new StringBuffer();
    Field field;

    while (enumeration.hasNext()) {
      field = (Field)enumeration.next();
      strbuf.append(field.getFieldValue() + ";");
    }
    strbuf.append("\r\n");
    return (strbuf.toString());
  }

  private boolean isList(String fieldName) {
    return "RDATE".equals(fieldName)
      || "ATTENDEE".equals(fieldName)
      || "ATTACH".equals(fieldName)
      || "EXDATE".equals(fieldName)
      || "RESOURCES".equals(fieldName)
      || "CATEGORIES".equals(fieldName);

  }

  private boolean isMultiList(String fieldName) {
    return ("ATTACH".equals(fieldName) || "ATTENDEE".equals(fieldName));
  }

}
