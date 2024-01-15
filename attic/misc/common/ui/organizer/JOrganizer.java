package common.ui.organizer;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class JOrganizer extends JPanel
  implements ActionListener, ListSelectionListener
{
  protected OrganizableList westOrganizer, eastOrganizer;
  ListSelectionModel westSelectionModel, eastSelectionModel;
  protected JButton westButton, eastButton;
  
  public JOrganizer(
    String westTitle, JList westList,
    String eastTitle, JList eastList,
    int orderButtons)
  {
    this(
      new OrganizableListPane(westTitle, westList,
        new OrganizerListInput(), orderButtons),
      new OrganizableListPane(eastTitle, eastList,
        new OrganizerListInput(), orderButtons));
  }
  
  public JOrganizer(
    String westTitle, JTable westTable,
    String eastTitle, JTable eastTable,
    int orderButtons)
  {
    this(
      new OrganizableTablePane(westTitle, westTable,
        new OrganizerTableInput(), orderButtons),
      new OrganizableTablePane(eastTitle, eastTable,
      new OrganizerTableInput(), orderButtons));
  }
  
  public JOrganizer(
    OrganizableList westOrganizer,
    OrganizableList eastOrganizer)
  {
    this.westOrganizer = westOrganizer;
    this.eastOrganizer = eastOrganizer;
    westSelectionModel = westOrganizer.getSelectionModel();
    eastSelectionModel = eastOrganizer.getSelectionModel();
    westSelectionModel.addListSelectionListener(this);
    eastSelectionModel.addListSelectionListener(this);

    JComponent source = westOrganizer.getComponent();
    JComponent target = eastOrganizer.getComponent();
    
    setLayout(new CenterLayout(8, 8));
    JPanel buttons = new JPanel(new GridLayout(2, 1, 4, 4));
    buttons.add(eastButton = makeButton("icons/RightIcon.gif", ">>"));
    buttons.add(westButton = makeButton("icons/LeftIcon.gif", "<<"));
    add(CenterLayout.CENTER, buttons);

    JPanel westPanel = new JPanel(new BorderLayout());
    westPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    westPanel.add(BorderLayout.CENTER, source);
    add(CenterLayout.WEST, westPanel);
    
    JPanel eastPanel = new JPanel(new BorderLayout());
    eastPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    eastPanel.add(BorderLayout.CENTER, target);
    add(CenterLayout.EAST, eastPanel);
    
    eastButton.addActionListener(this);
    westButton.addActionListener(this);
    eastButton.setEnabled(false);
    westButton.setEnabled(false);
  }
  
  public JButton makeButton(String iconFileName, String alternative)
  {
    if ((new File(iconFileName)).exists())
      return new JButton(new ImageIcon(iconFileName));
    return new JButton(alternative);
  }
  
  public void valueChanged(ListSelectionEvent event)
  {
    Object source = event.getSource();
    if (source == westSelectionModel)
    {
      boolean empty = westSelectionModel.isSelectionEmpty();
      eastButton.setEnabled(!empty);
    }
    if (source == eastSelectionModel)
    {
      boolean empty = eastSelectionModel.isSelectionEmpty();
      westButton.setEnabled(!empty);
    }
  }
  
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == eastButton)
    {
      Object[] selections = westOrganizer.getSelectionList();
      eastOrganizer.insertSelectionList(selections);
      westOrganizer.removeSelected();
      
      int size = westOrganizer.getElementCount();
      int index = westSelectionModel.getMinSelectionIndex();
      if (index >= size) index--;
      if (index >= 0)
        westSelectionModel.setSelectionInterval(index, index);
    }
    if (source == westButton)
    {
      Object[] selections = eastOrganizer.getSelectionList();
      westOrganizer.insertSelectionList(selections);
      eastOrganizer.removeSelected();

      int size = eastOrganizer.getElementCount();
      int index = eastSelectionModel.getMinSelectionIndex();
      if (index >= size) index--;
      if (index >= 0)
        eastSelectionModel.setSelectionInterval(index, index);
    }
  }
}

