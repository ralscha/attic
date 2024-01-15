package common.ui.key;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class KeyMapTable extends JTable
{
  public KeyMapTable(TableModel model)
  {
    super(model);
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setDefaultEditor(Object.class, null);
    setDefaultRenderer(Object.class, new KeyStrokeCellRenderer());
  }
}

