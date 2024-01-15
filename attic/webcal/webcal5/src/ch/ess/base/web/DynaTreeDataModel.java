package ch.ess.base.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.collections.comparators.ReverseComparator;

import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.model.Checkable;
import com.cc.framework.ui.model.Sortable;
import com.cc.framework.ui.model.TreeGroupDataModel;
import com.cc.framework.ui.model.TreeNodeDataModel;

public class DynaTreeDataModel extends LazyDynaBean implements Checkable, TreeGroupDataModel, Sortable {

  private int checkState;
  private List<TreeNodeDataModel> children;
  private TreeGroupDataModel parent = null;

  private String exportFileName;
  private List<ExportPropertyDescription> exportPropertiesList;

  public DynaTreeDataModel(String keyPrefix) {
    super(keyPrefix);
    exportPropertiesList = new ArrayList<ExportPropertyDescription>();
  }

  public TreeNodeDataModel getChild(int ix) {
    return children.get(ix);
  }

  public void addChild(TreeNodeDataModel child) {
    if (children == null) {
      children = new ArrayList<TreeNodeDataModel>();
    }

    children.add(child);
    child.setParent(this);
  }

  public TreeGroupDataModel getParent() {
    return parent;
  }

  public void setParent(TreeGroupDataModel parent) {
    this.parent = parent;
  }

  public String getParentKey() {
    return parent == null ? null : parent.getUniqueKey();
  }

  public String getUniqueKey() {
    return (String)get("id");
  }

  public int size() {
    if (children != null) {
      return children.size();
    }
    return 0;
  }

  @SuppressWarnings("unchecked")
  public void sort(final String property, final SortOrder direction) {
    if (children != null) {

      if (direction.equals(SortOrder.ASCENDING)) {
        Collections.sort(children, new PropertyComparator(property));
      } else {
        Collections.sort(children, new PropertyComparator(property, new ReverseComparator(ComparableComparator
            .getInstance())));
      }

      for (TreeNodeDataModel child : children) {
        ((DynaTreeDataModel)child).sort(property, direction);
      }

    }
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

  public List<ExportPropertyDescription> getExportPropertiesList() {
    return Collections.unmodifiableList(exportPropertiesList);
  }

  public Boolean isDeletable() {
    return ((Boolean)get("deletable"));
  }

  public void setDeletable(Boolean deletable) {
    set("deletable", deletable);
  }

  public Boolean isEditable() {
    return ((Boolean)get("editable"));
  }

  public void setEditable(Boolean editable) {
    set("editable", editable);
  }

  public Boolean isAddable() {
    return ((Boolean)get("addable"));
  }

  public void setAddable(Boolean addable) {
    set("addable", addable);
  }

  public void setChecked(boolean flag) {
    setCheckState(flag ? 1 : 0);
  }
  
  public int getCheckState() {
    return checkState;
  }

  public void setCheckState(int i) {
    this.checkState = i;
  }
}
