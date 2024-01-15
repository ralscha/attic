package ch.ess.tools;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

public class PropertyDataModel extends AbstractTableModel {
  
  public static final int COL_NAME = 0;
  public static final int COL_TYP = 1;
  public static final int COL_CANDELETE = 2;
  public static final int COL_LISTE = 3;
  public static final int COL_FILTER = 4;
  public static final int COL_REFERENCE = 6;
  public static final int COL_EDIT = 5;
  public static final int COL_MANYMANY = 7;
  
  private String[] columnNames = {"Name", "Typ", "CanDelete", "Liste", "Filter", "Edit", "Reference", "ManyManyReference"};
  private List<PropertyDescriptor> propertyList;
  private boolean[] canDelete;
  private String[] liste;
  private boolean[] filter;
  private String[] ref; 
  private String[] manymanyref;
  private String[] edit;
  
  private String role;
  private String selectedClass;
  private boolean createCopyColumn;
  private boolean createClearButton;
  private boolean overwrite;
  
  @SuppressWarnings("unchecked")
  public PropertyDataModel() {
    propertyList = Collections.EMPTY_LIST;
  }

  public void updateList(List<PropertyDescriptor> propList) {
    this.propertyList = propList;
    this.canDelete = new boolean[propList.size()];
    
    int ix = 0;
    for (PropertyDescriptor descriptor : propList) {
      if (descriptor.getPropertyType() == Set.class) {
        canDelete[ix] = true;
      }
      ix++;
    }
    
    liste = new String[propList.size()];
    filter = new boolean[propList.size()];
    ref = new String[propList.size()];
    manymanyref = new String[propList.size()];
    edit = new String[propList.size()];
    
  }
    
  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  public int getRowCount() {
    return propertyList.size();
  }

  public int getColumnCount() {
    return columnNames.length;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    PropertyDescriptor descr = propertyList.get(rowIndex);
    if (columnIndex == COL_NAME) {
      return descr.getName();
    } else if (columnIndex == COL_TYP) {
      Class typ = descr.getPropertyType();
      return typ.getName();
    } else if (columnIndex == COL_CANDELETE) {
      return canDelete[rowIndex];
    } else if (columnIndex == COL_FILTER) {
      return filter[rowIndex];
    } else if (columnIndex == COL_REFERENCE) {
      return ref[rowIndex];
    } else if (columnIndex == COL_EDIT) {
      return edit[rowIndex];
    } else if (columnIndex == COL_MANYMANY) {
      return manymanyref[rowIndex];
    } 
    return liste[rowIndex]; 
    
    
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Class getColumnClass(int col) {
    if (col == COL_CANDELETE) {
      return Boolean.class;
    } else if (col == COL_FILTER) {
      return Boolean.class;
    } 
    return String.class;
  }

  
  @Override
  public boolean isCellEditable(int row, int col) { 
    if (col == COL_CANDELETE) {
      PropertyDescriptor descr = propertyList.get(row);
      if (descr.getPropertyType() == Set.class) {
        return true;
      }
      return false;
    } else if (col == COL_LISTE) {
      return true;
    } else if (col == COL_FILTER) {
      return true;
    } else if (col == COL_REFERENCE) {
      PropertyDescriptor descr = propertyList.get(row);
      if (descr.getPropertyType().getName().startsWith("ch.ess.")) {
        return true;
      } else if (descr.getPropertyType() == Set.class) {
        return true;
      }
      return false;      
    } else if (col == COL_EDIT) {
      return true;
    } else if (col == COL_MANYMANY) {
      if (StringUtils.isNotBlank(ref[row])) {
        return true;
      }
      return false;
    }
    return false;  
  }
  
  @Override
  public void setValueAt(Object value, int row, int col) {
    if (col == COL_CANDELETE) {
      canDelete[row] = ((Boolean)value).booleanValue();
    } else if (col == COL_LISTE) {
      liste[row] = (String)value;
    } else if (col == COL_FILTER) {
      filter[row] = ((Boolean)value).booleanValue();
    } else if (col == COL_REFERENCE) {
      ref[row] = (String)value;
      manymanyref[row] = null;
    } else if (col == COL_EDIT) {
      edit[row] = (String)value;
    } else if (col == COL_MANYMANY) {
      manymanyref[row] = (String)value;
    }
    fireTableCellUpdated(row, col); 
  }

  public List<PropertyDescriptor> getPropertyList() {
    return propertyList;
  }

  public String getSelectedClass() {
    return selectedClass;
  }

  public void setSelectedClass(String selectedClass) {
    this.selectedClass = selectedClass;
  }

  public boolean getCanDelete(int ix) {
    return canDelete[ix];
  }

  public String getListe(int ix) {
    return liste[ix];
  }

  public boolean getFilter(int ix) {
    return filter[ix];
  }

  
  public String getRef(int ix) {
    return ref[ix];
  }

  public boolean isCreateCopyColumn() {
    return createCopyColumn;
  }

  public void setCreateCopyColumn(boolean createCopyColumn) {
    this.createCopyColumn = createCopyColumn;
  }
  
  public boolean isCreateClearButton() {
    return createClearButton;
  }

  public void setCreateClearButton(boolean createClearButton) {
    this.createClearButton = createClearButton;
  }

  public boolean isOverwrite() {
    return overwrite;
  }

  public void setOverwrite(boolean overwrite) {
    this.overwrite = overwrite;
  }

  public String getEdit(int ix) {
    return edit[ix];
  }

  public String getManymanyref(int ix) {
    return manymanyref[ix];
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<PropertyDescriptor> getReferenceProperties(int rowIndex) {
    try {
      PropertyDescriptor descr = propertyList.get(rowIndex);
      if (descr.getPropertyType() == Set.class) {
        return getSetReferenceProperties(rowIndex);
      }
      Class clazz = Class.forName(selectedClass);
      Method readMethod = clazz.getMethod(descr.getReadMethod().getName(), (Class[])null);
      
      List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();
      
      if (readMethod.isAnnotationPresent(ManyToOne.class)) {

        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(descr.getPropertyType());
        
        
        for (PropertyDescriptor descriptor : descriptors) {
          if (!"id".equals(descriptor.getName()) && !"version".equals(descriptor.getName()) && !"class".equals(descriptor.getName())) {
            if (descriptor.getPropertyType() == String.class) {
              propList.add(descriptor);
            }
          }
        }
      }
      
      return propList;
      
    } catch (ClassNotFoundException e) {     
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }
  
  public List<PropertyDescriptor> getSetReferenceProperties(int rowIndex) {
    try {
      PropertyDescriptor descr = propertyList.get(rowIndex);
      Class clazz = Class.forName(selectedClass);

      List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();

      Class actualType = getGenericType(descr, clazz);

      PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(actualType);      
      
      for (PropertyDescriptor descriptor : descriptors) {
        if (!"id".equals(descriptor.getName()) && !"version".equals(descriptor.getName()) && !"class".equals(descriptor.getName())) {
          //if (descriptor.getPropertyType() == String.class) {
            propList.add(descriptor);
          //}
        }
      }

      
      return propList;
      
    } catch (ClassNotFoundException e) {     
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<PropertyDescriptor> getManyReferenceProperties(int rowIndex) {
    try {
      PropertyDescriptor descr = propertyList.get(rowIndex);
      Class clazz = Class.forName(selectedClass);

      List<PropertyDescriptor> propList = new ArrayList<PropertyDescriptor>();

      Class actualType = getGenericType(descr, clazz);

      PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(actualType);      
      PropertyDescriptor manyDescriptor = null;
      for (PropertyDescriptor descriptor : descriptors) {
        if (descriptor.getName().equals(ref[rowIndex])) {
          manyDescriptor = descriptor;
        }        
      }
      
      if (manyDescriptor != null) {
        descriptors = PropertyUtils.getPropertyDescriptors(manyDescriptor.getPropertyType());

        for (PropertyDescriptor descriptor : descriptors) {
          if (!"id".equals(descriptor.getName()) && !"version".equals(descriptor.getName()) && !"class".equals(descriptor.getName())) {
            if (descriptor.getPropertyType() == String.class) {
              propList.add(descriptor);
            }
          }
        }
        
      }
      
      return propList;
      
    } catch (ClassNotFoundException e) {     
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    }
    return null;
  }

  
  public Class getGenericType(PropertyDescriptor descr, Class clazz) throws NoSuchMethodException {
    Method readMethod = clazz.getMethod(descr.getReadMethod().getName(), (Class[])null);
    ParameterizedType pt = (ParameterizedType)readMethod.getGenericReturnType();      
    Type actualType = null;
    for (Type ty : pt.getActualTypeArguments()) {
      actualType = ty; 
    }
    return (Class)actualType;
  }  
}
