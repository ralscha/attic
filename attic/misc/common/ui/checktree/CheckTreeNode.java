package common.ui.checktree;

import java.util.*;
import javax.swing.tree.*;

public class CheckTreeNode
  extends DefaultMutableTreeNode
{
  protected boolean selected, propagate;
  
  public CheckTreeNode(Object data)
  {
    this(data, false, true);
  }
  
  public CheckTreeNode(Object data, boolean selected)
  {
    this(data, selected, true);
  }
  
  public CheckTreeNode(Object data, boolean selected, boolean propagate)
  {
    super(data);
    this.selected = selected;
    this.propagate = propagate;
  }
  
  public boolean isSelected()
  {
    return selected;
  }
  
  public void setSelected(boolean selected)
  {
    this.selected = selected;
    if (propagate)
      propagateSelected(selected);
  }
  
  public void propagateSelected(boolean selected)
  {
    Enumeration enum = children();
    while (enum.hasMoreElements())
    {
      CheckTreeNode node = (CheckTreeNode)enum.nextElement();
      node.setSelected(selected);
    }
  }
  
  public void setUserObject(Object obj)
  {
    if (obj == this) return;
    super.setUserObject(obj);
  }
}

