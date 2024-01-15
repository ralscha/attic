
import java.util.*;
import java.beans.*;
import java.lang.reflect.*;

public class ClassTableModel implements TableModel  {

  private static Map descriptorsCache = new HashMap();

  private Map descriptorMap = new HashMap();
  private List objectList = new ArrayList();

  public ClassTableModel(Class rowClass) {
    init(rowClass);    
  }

  public void addRow(Object rowObject) {
    if (rowObject != null) {
      objectList.add(rowObject);
    }
  }

  public void addRow(Object[] rowObjects) {
    if (rowObjects != null) {
      for (int i = 0; i < rowObjects.length; i++) {
        addRow(rowObjects[i]);
      }
    }
  }

  public int getRowCount() {
    return objectList.size();
  }

  public Class getColumnClass(String property) {
    PropertyDescriptor descriptor = (PropertyDescriptor)descriptorMap.get(property);
    if (descriptor != null) {
      return (descriptor.getPropertyType());
    } else {
      return null;
    }
  }

  public Object getValueAt(int rowIndex, String property) {
    Object targetObject = objectList.get(rowIndex);
    if (targetObject == null) 
      return null;

    PropertyDescriptor descriptor = (PropertyDescriptor)descriptorMap.get(property);
    if (descriptor == null) 
      return null;

    Method readMethod = descriptor.getReadMethod();

    try {
      return (readMethod.invoke(targetObject, null));
    } catch (IllegalAccessException iae) {
    } catch (InvocationTargetException ite) {
    }
            
    return null;
 
  }
  
  public void setValueAt(Object aValue, int rowIndex, String property) {
    Object targetObject = objectList.get(rowIndex);
    if (targetObject == null) 
      return;

    PropertyDescriptor descriptor = (PropertyDescriptor)descriptorMap.get(property);
    if (descriptor == null) 
      return;

    Method writeMethod = descriptor.getWriteMethod();
    Object[] param = new Object[]{aValue};
    try {
      writeMethod.invoke(targetObject, param);
    } catch (IllegalAccessException iae) {
    } catch (InvocationTargetException ite) {
    }

  }


  public static void main(String[] args) {
    /*
    CommonTableModel ctm = new CommonTableModel(Lieferanten.class);
    for (int i = 0; i < 10; i++) {
      Lieferanten lief = new Lieferanten();
      lief.setLieferantId(new Integer(i*i));
      lief.setKollektivUnterschrift(Boolean.TRUE);
      ctm.addRow(lief);
    }
    System.out.println(ctm.getValueAt(9, "kollektivUnterschrift").getClass());
    */
  }


  private void init(Class clazz) {
    PropertyDescriptor[] descriptors = getPropertyDescriptors(clazz);
    for (int i = 0; i < descriptors.length; i++) {
      descriptorMap.put(descriptors[i].getName(), descriptors[i]);
    }
  }

  private static PropertyDescriptor[] getPropertyDescriptors(Class clazz) {
    if (clazz == null)
      return (new PropertyDescriptor[0]);

  	// Look up any cached descriptors for this bean class
	  String beanClassName = clazz.getName();
	  PropertyDescriptor descriptors[] = (PropertyDescriptor[])descriptorsCache.get(beanClassName);
	  if (descriptors != null)
	      return (descriptors);

	  // Introspect the bean and cache the generated descriptors
	  BeanInfo beanInfo = null;
	  try {
      beanInfo = Introspector.getBeanInfo(clazz);
	  } catch (IntrospectionException e) {
      return (new PropertyDescriptor[0]);
	  }

	  descriptors = beanInfo.getPropertyDescriptors();
	  if (descriptors == null)
	      descriptors = new PropertyDescriptor[0];

	  descriptorsCache.put(beanClassName, descriptors);
	  return (descriptors);

    }
}