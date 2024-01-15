package ch.ess.base.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.comparators.ReverseComparator;

import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.model.Sortable;

public class SimpleListDataModel implements Sortable, ListDataModel, Serializable {
  private List<Object> objectList;
  
  
  private String uniqueKeyProperty = "id";
  private String exportFileName;
  private List<ExportPropertyDescription> exportPropertiesList;
  
  public SimpleListDataModel() {
    objectList = new ArrayList<Object>();
    exportPropertiesList = new ArrayList<ExportPropertyDescription>();
  }

  @SuppressWarnings("unchecked")
  public void sort(String property, SortOrder direction) {
        
    if (direction.equals(SortOrder.ASCENDING)) {
      Collections.sort(objectList, new PropertyComparator(property));
    } else {
      Collections.sort(objectList, new PropertyComparator(property, new ReverseComparator(
          ComparableComparator.getInstance())));
    }

  }

  public Object getElementAt(int ix) {
    return objectList.get(ix);
  }

  public int size() {
    return objectList.size();
  }

  public String getUniqueKey(int ix) {
    try {
      return (String)PropertyUtils.getProperty(objectList.get(ix), uniqueKeyProperty);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
  
  public void remove(final int index) {
    objectList.remove(index);
  }

  public void add(final Object row) {
    objectList.add(row);
  }
  
  public void setUniqueKeyProperty(String uniqueKeyProperty) {
    this.uniqueKeyProperty = uniqueKeyProperty;
  }

  public String getExportFileName() {
    return exportFileName;
  }

  public void setExportFileName(String exportFileName) {
    this.exportFileName = exportFileName;
  }

  public void addExportProperty(String title, String propertyName) {
    exportPropertiesList.add(new ExportPropertyDescription(title, propertyName));
  }

  public void addExportProperty(String title, String propertyName, int colWidth) {
    exportPropertiesList.add(new ExportPropertyDescription(title, propertyName, colWidth));
  }

  public void addExportProperty(String title, String propertyName, int colWidth, AlignmentType alignment) {
    exportPropertiesList.add(new ExportPropertyDescription(title, propertyName, colWidth, alignment));
  }
  
  public void addExportProperty(ExportPropertyDescription descr) {
    exportPropertiesList.add(descr);
  }

  public List<ExportPropertyDescription> getExportPropertiesList() {
    return Collections.unmodifiableList(exportPropertiesList);
  }

  public void setExportPropertiesList(List<ExportPropertyDescription> exportPropertiesList) {
    this.exportPropertiesList = exportPropertiesList;
  }

  public List<Object> getObjectList() {
    return objectList;
  }

}
