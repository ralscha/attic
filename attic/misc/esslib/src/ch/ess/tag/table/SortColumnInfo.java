

package ch.ess.tag.table;


public class SortColumnInfo {

  private int column;
  private boolean ascending;

  public SortColumnInfo(int column, boolean ascending) {
    this.column = column;
    this.ascending = ascending;
  }

  public int getColumn() {
    return column;
  }

  public boolean isAscending() {
    return ascending;
  }

  public void setColumn(int column) {
    this.column = column;
  }

  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }
}
