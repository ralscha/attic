package common.ui.organizer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class OrganizerToolbar extends JPanel
  implements OrganizerConstants, ActionListener,
    ListSelectionListener
{
  protected OrganizerInput input;
  protected JButton insertButton, deleteButton, upButton, downButton;
  protected ListSelectionModel selectionModel;
  protected OrganizableList organizer;
  
  public OrganizerToolbar(OrganizableList organizer,
    OrganizerInput input, int orderButtons)
  {
    this.organizer = organizer;
    this.input = input;
    selectionModel = organizer.getSelectionModel();
    
    int count = 0;
    if (isFlagSet(orderButtons, TOOLBAR_UPDOWN)) count += 2;
    if (isFlagSet(orderButtons, TOOLBAR_INSERT)) count++;
    if (isFlagSet(orderButtons, TOOLBAR_DELETE)) count++;
    setLayout(new GridLayout(1, count, 2, 2));
    
    if (isFlagSet(orderButtons, TOOLBAR_INSERT))
    {
      add(insertButton = new OrganizerButton("Insert"));
      insertButton.addActionListener(this);
    }
    if (isFlagSet(orderButtons, TOOLBAR_DELETE))
    {
      add(deleteButton = new OrganizerButton("Delete"));
      deleteButton.addActionListener(this);
      deleteButton.setEnabled(false);
    }	
    if (isFlagSet(orderButtons, TOOLBAR_UPDOWN))
    {
      add(upButton = new OrganizerButton("Up"));
      add(downButton = new OrganizerButton("Down"));
      upButton.addActionListener(this);
      downButton.addActionListener(this);
      upButton.setEnabled(false);
      downButton.setEnabled(false);
    }
  }
  
  protected boolean isFlagSet(int orderButtons, int flag)
  {
    return (orderButtons & flag) == flag;
  }

  public void valueChanged(ListSelectionEvent event)
  {
    boolean empty = selectionModel.isSelectionEmpty();
    int min = selectionModel.getMinSelectionIndex();
    int max = selectionModel.getMaxSelectionIndex();
    int size = organizer.getElementCount() - 1;
    if (upButton != null)
      upButton.setEnabled(!empty && min > 0);
    if (downButton != null)
      downButton.setEnabled(!empty && max < size);
    if (deleteButton != null)
      deleteButton.setEnabled(!empty);
  }
  
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == insertButton)
    {
      Object[] insert = input.getInputData(getParent());
      if (insert != null)
        organizer.insertSelectionList(insert);
    }
    if (source == deleteButton)
    {
      organizer.removeSelected();
    }
    if (source == upButton)
    {
      organizer.moveSelectedUp();
    }
    if (source == downButton)
    {
      organizer.moveSelectedDown();
    }
  }
}

