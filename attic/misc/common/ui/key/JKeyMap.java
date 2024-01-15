package common.ui.key;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class JKeyMap extends JPanel
  implements ActionListener, ListSelectionListener
{
  protected KeyMapTable keyMapTable;
  protected KeyMapTableModel model;
  protected KeyStrokeField keyStrokeField;
  protected JButton assign, remove;

  public JKeyMap()
  {
    model = new KeyMapTableModel();
    //model.setModelInputMap(component.getInputMap());
    
    JPanel table = new JPanel(new GridLayout());
    table.add(new JScrollPane(
      keyMapTable = new KeyMapTable(model)));
    keyMapTable.getSelectionModel().addListSelectionListener(this); 
    table.setPreferredSize(new Dimension(400, 150));

    JPanel north = new JPanel(new GridLayout(2, 1, 4, 4));
    north.add(assign = new JButton("Assign"));
    north.add(remove = new JButton("Remove"));
    assign.setDefaultCapable(false);
    remove.setDefaultCapable(false);

    JPanel buttons = new JPanel(new BorderLayout());
    buttons.add(BorderLayout.NORTH, north);

    JPanel field = new JPanel(new BorderLayout(4, 4));
    field.add(BorderLayout.WEST, new JLabel("New key assignment:"));
    field.add(BorderLayout.CENTER, 
      keyStrokeField = new KeyStrokeField());
    
    JPanel main = new JPanel(new BorderLayout(8, 8));
    main.add(BorderLayout.CENTER, table);
    main.add(BorderLayout.SOUTH, field);
    
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    add(BorderLayout.CENTER, main);
    add(BorderLayout.EAST, buttons);
    
    assign.addActionListener(this);
    remove.addActionListener(this);
  }
  
  public void setModelInputMap(InputMap inputMap)
  {
    model.setModelInputMap(inputMap);
  }
  
  public InputMap getModelInputMap()
  {
    return model.getModelInputMap();
  }
  
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    int row = keyMapTable.getSelectedRow();
    if (source == assign)
    {
      KeyStroke stroke = keyStrokeField.getKeyStroke();
      model.setValueAt(stroke, row, 1);
    }
    if (source == remove)
    {
      model.setValueAt("", row, 1);
      keyStrokeField.setKeyStroke(null);
    }
  }

  public void valueChanged(ListSelectionEvent event)
  {
    int row = keyMapTable.getSelectedRow();
    Object value = model.getValueAt(row, 1);
    if (value instanceof KeyStroke)
    {
      keyStrokeField.setKeyStroke((KeyStroke)value);
    }
    else
    {
      keyStrokeField.setKeyStroke(null);
    }
  }
}

