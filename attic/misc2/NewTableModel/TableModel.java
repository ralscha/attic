
public interface TableModel {

    public int getRowCount();
    //public int getColumnCount();

    public Class getColumnClass(String property);
    public Object getValueAt(int rowIndex, String property);
    public void setValueAt(Object aValue, int rowIndex, String property);
}

