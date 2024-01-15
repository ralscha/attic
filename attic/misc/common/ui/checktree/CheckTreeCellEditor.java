package common.ui.checktree;

import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

public class CheckTreeCellEditor
  extends CheckTreeCellRenderer
  implements TreeCellEditor, ActionListener
{
  protected CellEditorListener list;

  public CheckTreeCellEditor(JTree tree)
  {
    this(tree, new DefaultTreeCellRenderer());
  }

  public CheckTreeCellEditor(JTree tree, TreeCellRenderer renderer)
  {
    super(tree, renderer);
    check.addActionListener(this);
  }

  public Component getTreeCellEditorComponent(
    JTree tree, Object value, boolean selected,
    boolean expanded, boolean leaf, int row)
  {
    return getTreeCellRendererComponent(
      tree, value, true, expanded, leaf, row, true);
  }

  public boolean stopCellEditing()
  {
    return true;
  }
  
  public Object getCellEditorValue()
  {
    node.setSelected(check.isSelected());
    return node;
  }
    
  public boolean isCellEditable(EventObject event)
  { 
    return true;
  } 

  public boolean shouldSelectCell(EventObject event)
  { 
    return true;
  }
    
  public void  cancelCellEditing()
  { 
    fireEditingCanceled();
  }

  public void addCellEditorListener(CellEditorListener listener)
  {
    list = SwingEventMulticaster.add(list, listener);
  }

  public void removeCellEditorListener(CellEditorListener listener)
  {
    list = SwingEventMulticaster.remove(list, listener);
  }

  protected void fireEditingStopped()
  {
    if (list != null)
      list.editingStopped(new ChangeEvent(this));
  }

  protected void fireEditingCanceled()
  {
    if (list != null)
      list.editingCanceled(new ChangeEvent(this));
  }
  
  public void actionPerformed(ActionEvent event)
  {
    fireEditingStopped();
  }
}

