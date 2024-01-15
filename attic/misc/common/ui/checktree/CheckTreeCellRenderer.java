package common.ui.checktree;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

public class CheckTreeCellRenderer extends JPanel
  implements TreeCellRenderer
{
  protected CheckTreeNode node;
  protected TreeCellRenderer renderer;
  protected JCheckBox check;

  public CheckTreeCellRenderer(JTree tree)
  {
    this(tree, new DefaultTreeCellRenderer());
  }

  public CheckTreeCellRenderer(JTree tree, TreeCellRenderer renderer)
  {
    setOpaque(false);
    setLayout(new BorderLayout());
    
    this.renderer = renderer;
    add(BorderLayout.CENTER, 
      renderer.getTreeCellRendererComponent(
        tree, "", true, true, true, 0, true));
    
    check = new JCheckBox();
    check.setMargin(new Insets(0, 0, 0, 0));
    check.setBorderPaintedFlat(true);
    check.setOpaque(false);
    add(BorderLayout.WEST, check);
  }

  public Component getTreeCellRendererComponent(
    JTree tree, Object value, boolean selected,
    boolean expanded, boolean leaf, int row,
    boolean hasFocus) 
  {
    if (value instanceof CheckTreeNode)
    {
      node = (CheckTreeNode)value;
      check.setSelected(node.isSelected());
      value = node.getUserObject();
    }
    renderer.getTreeCellRendererComponent(
      tree, value, selected, expanded, leaf, row, hasFocus);
    return this;
  }
  
}

