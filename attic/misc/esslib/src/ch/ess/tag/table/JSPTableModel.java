
package ch.ess.tag.table;

import java.util.*;

import javax.servlet.http.*;
import javax.swing.table.*;

import ch.ess.type.*;
import ch.ess.util.attr.*;



public class JSPTableModel {

  private TableModel model;
  private int currentPage = 1;
  private int rowsPerPage = 10;
  private boolean sortable;
  private int sortCol = -1;
  private boolean sortOrder;    /* true = ASC, false = DESC */
  private Map alignmentMap;
  private Map colourMap;
  private Map formatMap;
  private Map wrapMap;
  private String title;
  private String subtitle;
  private Map filter;
  private String bottomText;
  private String bottomText2;
  private String bottomText3;
  private Attributed attributes;

  public JSPTableModel() {
    model = null;
  }

  public JSPTableModel(TableModel model) {
    setModel(model);
  }

  public TableModel getModel() {
    return this.model;
  }
  
  private Attributed getAttributes() {
    if (attributes == null) {
      attributes = AttributedFactory.getAttributed();    
    } 
    return attributes; 
  }

  public Attr getAttribute(String name) {
    return (getAttributes().find(name));
    }

  public void setAttribute(Attr attribute) {
    if (attribute != null) {
      getAttributes().add(attribute);
    }
  }

  public void removeAttribute(Attr attribute) {
    if (attribute != null) {
      getAttributes().remove(attribute.getName());
    }
  }

  public void setModel(TableModel model, boolean keep) {

    this.model = model;
    sortable = model instanceof TableSorter;

    if (!keep) {
      currentPage = 1;
      sortCol = -1;

    } else {
      if ((sortable) && (sortCol != -1)) {
        ((TableSorter)model).sortByColumn(sortCol, sortOrder);
      }

      setCurrentPage(currentPage);
    }
  }

  public void setModel(TableModel model) {

    currentPage = 1;
    sortCol = -1;
    this.model = model;
    sortable = model instanceof TableSorter;
  }

  public int getRowCount() {
    return Math.min(getRowsPerPage(), getTotalRowCount() - getRowsPerPage() * (getCurrentPage() - 1));
  }

  public int getColumnCount() {

    if (model == null) {
      return 0;
    } else {
      return model.getColumnCount();
    }
  }

  public Class getColumnClass(int columnIndex) {
    if (model == null) {
      return null;
    } else {
      return model.getColumnClass(columnIndex);
    }
  }

  public String getColumnName(int columnIndex) {

    if (model == null) {
      return null;
    } else {
      return model.getColumnName(columnIndex);
    }
  }

  public Object getValueAt(int rowIndex, int colIndex) {

    if (model == null) {
      return null;
    } else {
      return model.getValueAt((getCurrentPage() - 1) * getRowsPerPage() + rowIndex, colIndex);
    }
  }

  public void setValueAt(Object obj, int rowIndex, int colIndex) {

    if (model != null) {
      model.setValueAt(obj, (getCurrentPage() - 1) * getRowsPerPage() + rowIndex, colIndex);
    }
  }

  public void setValueAtAll(Object obj, int rowIndex, int colIndex) {

    if (model != null) {
      model.setValueAt(obj, rowIndex, colIndex);
    }
  }

  public Object getValueAtAll(int rowIndex, int colIndex) {

    if (model == null) {
      return null;
    } else {
      return model.getValueAt(rowIndex, colIndex);
    }
  }

  public int getTotalRowCount() {

    if (model == null) {
      return 0;
    } else {
      return model.getRowCount();
    }
  }

  public int getCurrentPage() {
    return this.currentPage;
  }

  public void setCurrentPage(int currentPage) {

    int totalPages = getTotalPages();

    if (currentPage > totalPages) {
      this.currentPage = totalPages;
    } else {
      this.currentPage = currentPage;
    }
  }

  public int getRowsPerPage() {
    return this.rowsPerPage;
  }

  public void setRowsPerPage(int rowsPerPage) {
    this.rowsPerPage = rowsPerPage;
  }

  public int getTotalPages() {
    return (getTotalRowCount() - 1) / getRowsPerPage() + 1;
  }

  public boolean isFirstPage() {
    return (currentPage == 1);
  }

  public boolean isLastPage() {
    return (currentPage == getTotalPages());
  }

  public int getCurrentFirstRow() {
    return (getCurrentPage() - 1) * getRowsPerPage() + 1;
  }

  public int getCurrentLastRow() {
    return Math.min((getCurrentPage() * getRowsPerPage()), getTotalRowCount());
  }

  public void setSortColumn(int sortCol, String order) {

    if (sortable) {
      boolean same = false;

      same = (this.sortCol == sortCol) && (this.sortOrder == ("a".equals(order)));

      if (!same) {
        this.sortCol = sortCol;
        this.sortOrder = ("a".equals(order));

        ((TableSorter)model).sortByColumn(sortCol, sortOrder);
      }
    }
  }

  public void addSortColumn(int sortCol, String order) {

    if (sortable) {
      this.sortCol = sortCol;
      this.sortOrder = ("a".equals(order));

      ((TableSorter)model).addSortColumn(this.sortCol, this.sortOrder);
    }
  }

  public void sort() {

    if (sortable) {
      ((TableSorter)model).sort();
    }
  }

  public int getSortColumn() {

    if (sortable) {
      return sortCol;
    } else {
      return -1;
    }
  }

  public boolean isSortable() {
    return sortable;
  }

  public boolean getSortOrder() {
    return sortOrder;
  }
  
  public void setAlignment(int col, AlignmentType alignment) {
    if (this.alignmentMap == null) {
      this.alignmentMap = new HashMap();
    }
    
    this.alignmentMap.put(new Integer(col), alignment);
  }
  
  public void setColour(int col, String colour) {
    if (this.colourMap == null) {
      this.colourMap = new HashMap();
    }
    
    this.colourMap.put(new Integer(col), colour);  
  }
  
  public void setFormat(int col, String format) {
    if (this.formatMap == null) {
      this.formatMap = new HashMap();
    }
    
    this.formatMap.put(new Integer(col), format);  
  }  
  
  public void setWrap(int col, Boolean wrap) {
    if (this.wrapMap == null) {
      this.wrapMap = new HashMap();
    }
    this.wrapMap.put(new Integer(col), wrap);    
  }
  
  public Boolean getWrap(int col) {
    if (wrapMap != null) {
      Object o = wrapMap.get(new Integer(col));
      if (o != null) {
        return (Boolean)o;
      }
    }
    return Boolean.FALSE;
  }
  
  public AlignmentType getAlignment(int columnIndex) {
    if (alignmentMap != null) {
      Object o = alignmentMap.get(new Integer(columnIndex));
      if (o != null) {
        return (AlignmentType)o;
      } 
    } 

    return AlignmentType.LEFT;    
  }
  
  public String getColour(int columnIndex) {
    if (colourMap != null) {
      Object o = colourMap.get(new Integer(columnIndex));
      if (o != null) {
        return (String)o;
      } 
    } 

    return null;    
  }
    
  
  public String getFormat(int columnIndex) {
    if (formatMap != null) {
      Object o = formatMap.get(new Integer(columnIndex));
      if (o != null) {
        return (String)o;
      } 
    } 

    return null;    
  }  

  public void processRequest(HttpServletRequest req) {

    // get page parameter from http request
    String page = req.getParameter("tpage");

    // get sort parameter from http request
    String sort = req.getParameter("sort");

    // set the current page if the parameter is valid
    if (page != null) {
      try {
        int pageNum = Integer.parseInt(page);

        setCurrentPage(pageNum);
      } catch (Exception e) {}
    }

    if (sort != null) {
      try {
        if (sort.length() >= 2) {
          String order = sort.substring(0, 1);
          int sortCol = Integer.parseInt(sort.substring(1));

          setSortColumn(sortCol, order);
        }
      } catch (Exception e) {}
    }
  }
  
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Map getFilter() {
    return filter;
  }

  public void setFilter(Map filter) {
    this.filter = filter;
  }

  public String getBottomText() {
    return bottomText;
  }

  public void setBottomText(String bottomText) {
    this.bottomText = bottomText;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getBottomText2() {
    return bottomText2;
  }

  public String getBottomText3() {
    return bottomText3;
  }

  public void setBottomText2(String string) {
    bottomText2 = string;
  }

  public void setBottomText3(String string) {
    bottomText3 = string;
  }

}
