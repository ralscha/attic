package common.ui.cellborder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class JCellBorder extends JPanel
  implements ActionListener, SwingConstants
{
  protected JButton northMore, southMore, eastMore, westMore;
  protected JButton northLess, southLess, eastLess, westLess;
  protected JToggleButton north, south, east, west;
  protected CellBorderPanel borderPanel;
    
  public JCellBorder()
  {
    setLayout(new BorderLayout(2, 2));
    setPreferredSize(new Dimension(200, 200));
    setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
      
    JPanel panel = new JPanel(new GridLayout());
    panel.setBorder(BorderFactory.createCompoundBorder(
      BorderFactory.createEtchedBorder(),
      BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    panel.add(borderPanel = new CellBorderPanel());

    add(BorderLayout.CENTER, panel);
    
    JPanel northPanel = new JPanel(new BorderLayout());
    northPanel.add(
      BorderLayout.CENTER, makeButtonPanel(
      northLess = makeArrowButton(NORTH),
      northMore = makeArrowButton(SOUTH),
      true));
    northPanel.add(BorderLayout.WEST,
      west = makeToggleButton(WEST));
    northPanel.add(BorderLayout.EAST,
      north = makeToggleButton(NORTH));
    add(BorderLayout.NORTH, northPanel);
    
    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.add(
      BorderLayout.CENTER, makeButtonPanel(
      southMore = makeArrowButton(NORTH),
      southLess = makeArrowButton(SOUTH),
      true));
    southPanel.add(BorderLayout.WEST,
      south = makeToggleButton(SOUTH));
    southPanel.add(BorderLayout.EAST,
      east = makeToggleButton(EAST));
    add(BorderLayout.SOUTH, southPanel);
    
    add(BorderLayout.WEST, makeButtonPanel(
      westLess = makeArrowButton(WEST),
      westMore = makeArrowButton(EAST),
      false));
    add(BorderLayout.EAST, makeButtonPanel(
      eastMore = makeArrowButton(WEST),
      eastLess = makeArrowButton(EAST),
      false));
    
    north.addActionListener(this);
    south.addActionListener(this);
    east.addActionListener(this);
    west.addActionListener(this);
    
    northMore.addActionListener(this);
    northLess.addActionListener(this);
    southMore.addActionListener(this);
    southLess.addActionListener(this);
    westMore.addActionListener(this);
    westLess.addActionListener(this);
    eastMore.addActionListener(this);
    eastLess.addActionListener(this);
  }
  
  public CellBorder getCellBorder()
  {
    return borderPanel.getCellBorder();
  }
  
  public void setCellBorder(CellBorder border)
  {
    borderPanel.setCellBorder(border);
  }

  public JPanel makeButtonPanel(
    JButton one, JButton two, boolean vertical)
  {
    JPanel panel = new JPanel(new BorderLayout());
    if (vertical)
    {
      panel.add(BorderLayout.NORTH, one);
      panel.add(BorderLayout.SOUTH, two);
    }
    else
    {
      panel.add(BorderLayout.WEST, one);
      panel.add(BorderLayout.EAST, two);
    }
    return panel;
  }
  
  public JToggleButton makeToggleButton(int dir)
  {
    JToggleButton button = new JToggleButton(new CellBorderIcon(dir));
    button.setPreferredSize(new Dimension(24, 24));
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setFocusPainted(false);
    button.setSelected(true);
    return button;
  }
  
  public JButton makeArrowButton(int dir)
  {
    JButton button = new JButton(new ArrowIcon(dir));
    button.setPreferredSize(new Dimension(12, 12));
    button.setMargin(new Insets(0, 0, 0, 0));
    button.setBorderPainted(false);
    button.setFocusPainted(false);
    return button;
  }
  
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == north)
    {
      borderPanel.toggleNorth(north.isSelected());
    }
    if (source == south)
    {
      borderPanel.toggleSouth(south.isSelected());
    }
    if (source == east)
    {
      borderPanel.toggleEast(east.isSelected());
    }
    if (source == west)
    {
      borderPanel.toggleWest(west.isSelected());
    }
    
    if (source == northMore)
    {
      borderPanel.adjustNorth(1);
    }
    if (source == southMore)
    {
      borderPanel.adjustSouth(1);
    }
    if (source == westMore)
    {
      borderPanel.adjustWest(1);
    }
    if (source == eastMore)
    {
      borderPanel.adjustEast(1);
    }
    
    if (source == northLess)
    {
      borderPanel.adjustNorth(-1);
    }
    if (source == southLess)
    {
      borderPanel.adjustSouth(-1);
    }
    if (source == westLess)
    {
      borderPanel.adjustWest(-1);
    }
    if (source == eastLess)
    {
      borderPanel.adjustEast(-1);
    }
  }
}

