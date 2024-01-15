package common.ui.key;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class KeyStrokeCellRenderer
  extends DefaultTableCellRenderer
{
  public Component getTableCellRendererComponent(
    JTable table, Object value, boolean isSelected,
    boolean hasFocus, int row, int col)
  {
    String text = value.toString();
    if (value instanceof KeyStroke)
    {
      KeyStroke keyStroke = (KeyStroke)value;
      text = KeyStrokeField.formatKeyStroke(keyStroke);
    }
    return super.getTableCellRendererComponent(
      table, text, isSelected, false, row, col);
  }
}

