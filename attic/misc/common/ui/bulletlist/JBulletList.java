package common.ui.bulletlist;

import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class JBulletList extends JPanel
{
  public static final int CONTENT_RIGHT = 1;
  public static final int CONTENT_BELOW = 2;

  protected int[] selectionList;
  protected ListSelectionModel selectionModel =
    new DefaultListSelectionModel();
  
  public JBulletList()
  {
    setLayout(new SpaceLayout());
  }
  
  public ListSelectionModel getSelectionModel()
  {
    return selectionModel;
  }

  public void setSelectionModel(ListSelectionModel selectionModel)
  {
    this.selectionModel = selectionModel;
  }

  public BasicListEntry[] getListEntries()
  {
    int count = getComponentCount();
    BasicListEntry[] listEntries =
      new BasicListEntry[count];
    for (int i = 0; i < count; i++)
    {
      listEntries[i] = (BasicListEntry)getComponent(i);
    }
    return listEntries;
  }
  
  public int getSelectionIndex()
  {
    if (selectionList == null ||
      selectionList.length == 0)
      return -1;
    return selectionList[0];
  }
  
  public int[] getSelectionList()
  {
    return selectionList;
  }

  public String toString()
  {
    if (selectionList == null) return "JBulletList={}";
    StringBuffer buffer = new StringBuffer("JBulletList={");
    for (int i = 0; i < selectionList.length; i++)
    {
      if (i > 0) buffer.append(",");
      buffer.append("" + selectionList[i]);
    }
    return buffer.toString() + "}";
  }

// --------------------
// --- Notification ---
// --------------------

  protected Vector listeners = new Vector();
  
  public void addActionListener(ActionListener listener)
  {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener)
  {
    listeners.remove(listener);
  }
  
  protected void fireActionEvent(String command)
  {
    Vector list = (Vector)listeners.clone();
    ActionEvent event = new ActionEvent(
      this, ActionEvent.ACTION_PERFORMED, command);
    for (int i = 0; i < list.size(); i++)
    {
      ActionListener listener = (ActionListener)list.get(i);
      listener.actionPerformed(event);
    }
  }

// -----------------------
// --- BulletListPanel ---
// -----------------------

  public static JBulletList createBulletList(
    String[] labels)
  {
    return new BulletListPanel(labels);
  }

  public static JBulletList createBulletList(
    String[] labels, JComponent[] components)
  {
    return new BulletListPanel(labels, components);
  }

  public static JBulletList createBulletList(
    ImageIcon icon, String[] labels)
  {
    return new BulletListPanel(icon, labels);
  }

  public static JBulletList createBulletList(
    ImageIcon icon, String[] labels, JComponent[] components,
    int position)
  {
    return new BulletListPanel(icon, labels, components, position);
  }

// -----------------------
// --- NumberListPanel ---
// -----------------------

  public static JBulletList createNumberList(
    String[] labels, JComponent[] components)
  {
    return new NumberListPanel(labels, components);
  }

  public static JBulletList createNumberList(
    String[] labels)
  {
    return new NumberListPanel(labels);
  }

  public static JBulletList createNumberList(
    String[] labels, JComponent[] components, int position)
  {
    return new NumberListPanel(labels, components, position);
  }

  public static JBulletList createNumberList(
    String[] labels, JComponent[] components,
    int position, String prefix, String suffix)
  {
    return new NumberListPanel(labels, components,
      position, prefix, suffix);
  }

// ----------------------
// --- CheckListPanel ---
// ----------------------

  public static CheckListPanel createCheckList(
    String[] labels)
  {
    return new CheckListPanel(labels);
  }

  public static CheckListPanel createCheckList(
    String[] labels, JComponent[] components)
  {
    return new CheckListPanel(labels, components);
  }
  
  public static CheckListPanel createCheckList(
    String[] labels, JComponent[] components, int position)
  {
    return new CheckListPanel(labels, components, position);
  }

// ----------------------
// --- RadioListPanel ---
// ----------------------

  public static RadioListPanel createRadioList(
    String[] labels)
  {
    return new RadioListPanel(labels);
  }

  public static RadioListPanel createRadioList(
    String[] labels, JComponent[] components)
  {
    return new RadioListPanel(labels, components);
  }

  public static RadioListPanel createRadioList(
    String[] labels, JComponent[] components, int position)
  {
    return new RadioListPanel(labels, components, position);
  }
  
}

