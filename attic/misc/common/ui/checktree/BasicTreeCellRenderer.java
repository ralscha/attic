package common.ui.checktree;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

public class BasicTreeCellRenderer
  extends DefaultTreeCellRenderer
{
  public BasicTreeCellRenderer() {}

  public Component getTreeCellRendererComponent(
    JTree tree, Object value, boolean selected,
    boolean expanded, boolean leaf, int row,
    boolean hasFocus) 
  {
    super.getTreeCellRendererComponent(tree, value,
      selected, expanded, leaf, row, hasFocus);
    if (value instanceof BasicTreeNode)
    {
      BasicTreeNode node = (BasicTreeNode)value;
      setIcon(expanded ? node.getOpen() : node.getIcon());
      setText(node.getText());
    }
    return this;
  }
}

