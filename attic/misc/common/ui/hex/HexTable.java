package common.ui.hex;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class HexTable extends JTable
{
  public HexTable(HexTableModel model)
  {
    super(model);
    setDefaultRenderer(String.class,
      new HexTableCellRenderer());
    setDefaultEditor(String.class,
      new HexTableCellEditor());
    
    int count = model.getColumnCount();
    for (int i = 0; i < count; i++)
    {
      int w = 22;
      if (i == 0) w = 50;
      if (i == (count - 1)) w = 140;
      String name = getColumnName(i);
      TableColumn column = getColumn(name);
      column.setWidth(w);
      column.setMinWidth(w);
      column.setPreferredWidth(w);
    }
    
    setShowGrid(false);
    setRowSelectionAllowed(false);
    setFont(new Font("monospaced", Font.PLAIN, 12));
    setIntercellSpacing(new Dimension(4, 0));
  }
  
  public void setModel(TableModel model)
  {
    if (model instanceof HexTableModel)
    {
      super.setModel(model);
    }
    else
    {
      throw new IllegalArgumentException(
        "HexTable expects to use a HexTableModel");
    }
  }
}

