package common.ui.hex;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class JHexEdit extends JPanel
{
  public JHexEdit(File file)
    throws IOException
  {
    this(new HexFile(file));
  }
  
  public JHexEdit(HexData data)
  {
    HexTableModel model = new HexTableModel(data);
    JTable table = new HexTable(model);
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    setLayout(new GridLayout());
    add(new JScrollPane(table));
    setPreferredSize(new Dimension(
      table.getPreferredSize().width + 8, 400));
  }
}

