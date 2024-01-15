package common.ui.checktree;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class JCheckTree extends JTree
{
  public JCheckTree(TreeNode root)
  {
    super(root);
    super.setCellRenderer(new CheckTreeCellRenderer(this));
    setCellEditor(new CheckTreeCellEditor(this));
    setEditable(true);
  }
  
  public void setCellRenderer(TreeCellRenderer renderer)
  {
    super.setCellRenderer(new CheckTreeCellRenderer(this, renderer));
  }

  public void setEditorRenderer(TreeCellRenderer renderer)
  {
    setCellEditor(new CheckTreeCellEditor(this, renderer));
  }
}

