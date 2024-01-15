package ch.ess.cal.web;

import java.io.Serializable;
import java.text.Format;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.LazyDynaBean;

import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.model.Checkable;
import com.cc.framework.ui.model.ListDataModel;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:07 $
 */
public class DynaListDataModel implements ListDataModel, Serializable {

  private List<DynaBean> objectList;

  private Map<String, Format> columnFormatter;
  private Map<Class, Format> classFormatter;

  public DynaListDataModel() {
    objectList = new ArrayList<DynaBean>();
    columnFormatter = null;
    classFormatter = null;
  }

  public void addClassFormatter(final Class clazz, final Format formatter) {
    if (classFormatter == null) {
      classFormatter = new HashMap<Class, Format>();
    }
    classFormatter.put(clazz, formatter);
  }

  public void addColumnFormatter(final String columnName, final Format formatter) {
    if (columnFormatter == null) {
      columnFormatter = new HashMap<String, Format>();
    }
    columnFormatter.put(columnName, formatter);
  }

  public Object getElementAt(final int ix) {

    if ((columnFormatter == null) && (classFormatter == null)) {
      return objectList.get(ix);
    }

    DynaBean db = objectList.get(ix);
    DynaClass clazz = db.getDynaClass();

    DynaProperty[] properties = clazz.getDynaProperties();

    DynaBean newdb;

    if (db instanceof CheckableLazyDynaBean) {
      newdb = new CheckableLazyDynaBean(clazz.getName() + "Str");
      ((Checkable)newdb).setCheckState(((Checkable)db).getCheckState());
    } else {
      newdb = new LazyDynaBean(clazz.getName() + "Str");
    }

    for (int i = 0; i < properties.length; i++) {
      String propName = properties[i].getName();
      Object obj = db.get(propName);
      Format format = getFormatter(propName, properties[i].getType());
      if (format != null) {
        newdb.set(propName, format.format(obj));
      } else {
        newdb.set(propName, obj);
      }
    }

    return newdb;

  }

  private Format getFormatter(final String name, final Class clazz) {
    if (columnFormatter != null) {
      Format format = columnFormatter.get(name);
      if (format != null) {
        return format;
      }
    }

    if (classFormatter != null) {
      return classFormatter.get(clazz);
    }

    return null;
  }

  public int size() {
    return objectList.size();
  }

  public void add(final DynaBean obj) {
    objectList.add(obj);
  }

  public void remove(final int index) {
    objectList.remove(index);
  }

  public String getUniqueKey(final int ix) {
    DynaBean obj = objectList.get(ix);
    return (String)obj.get("id");
  }

  public void sort(final String property, final SortOrder direction) {

    if (direction.equals(SortOrder.ASCENDING)) {
      Collections.sort(objectList, new DynaBeanComparator<String>(property));
    } else {
      Collections.sort(objectList, new DynaBeanComparator<String>(property, new ReverseComparator<String>()));
    }
  }

}
