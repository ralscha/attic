package common.ui.surface;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.border.*;

public class TransparentRenderer extends JLabel
  implements ListCellRenderer, TreeCellRenderer, TableCellRenderer
{
  protected Color normalForeground = Color.lightGray;
  protected Color selectForeground = Color.lightGray;
  protected Color focusForeground = Color.white;
  protected Border normalBorder =
    BorderFactory.createEmptyBorder(1, 1, 1, 1);
  protected Border selectBorder =
    BorderFactory.createLineBorder(selectForeground);
  protected Border focusBorder =
    BorderFactory.createLineBorder(focusForeground);
  
  public TransparentRenderer()
  {
    setOpaque(false);
    setForeground(Color.white);
  }

  protected void generalSetup(
    JComponent parent, boolean isSelected, boolean hasFocus)
  {
    setComponentOrientation(parent.getComponentOrientation());
    setEnabled(parent.isEnabled());
    setFont(parent.getFont());
    setBorder(isSelected ?
      (hasFocus ? focusBorder : selectBorder)
      : normalBorder);
    setForeground(isSelected ?
      (hasFocus ? focusForeground : selectForeground)
      : normalForeground);
  }

  public Component getListCellRendererComponent(
    JList list, Object value, int index,
    boolean isSelected, boolean hasFocus)
  {
    generalSetup(list, isSelected, hasFocus);
    if (value instanceof Icon)
    {
      setIcon((Icon)value);
      setText("");
    }
    else
    {
      setIcon(null);
      setText((value == null) ? "" : value.toString());
    }
    return this;
  }

  public Component getTreeCellRendererComponent(
    JTree tree, Object value, boolean isSelected,
    boolean isExpanded, boolean isLeaf, int row,
    boolean hasFocus)
  {
    generalSetup(tree, isSelected, hasFocus);
    setText(tree.convertValueToText(
      value, isSelected, isExpanded,
      isLeaf, row, hasFocus));
    if (tree.isEnabled())
    {
      setIcon(isLeaf ? UIManager.getIcon("Tree.leafIcon") :
        (isExpanded ? UIManager.getIcon("Tree.openIcon")
        : UIManager.getIcon("Tree.closedIcon")));
    }
    else
    {
      setDisabledIcon(isLeaf ? UIManager.getIcon("Tree.leafIcon") :
        (isExpanded ? UIManager.getIcon("Tree.openIcon")
        : UIManager.getIcon("Tree.closedIcon")));
    }
    return this;
  }

  public Component getTableCellRendererComponent(
    JTable table, Object value, boolean isSelected,
    boolean hasFocus, int row, int column)
  {
    generalSetup(table, isSelected, hasFocus);
    setText(value.toString()); 
    return this;
  }
}

