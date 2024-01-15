package common.ui.organizer;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class CenterLayout extends common.ui.layout.AbstractLayout
  implements LayoutManager2, Serializable
{
  public static final String CENTER = "CENTER";
  public static final String NORTH = "NORTH";
  public static final String SOUTH = "SOUTH";
  public static final String WEST = "WEST";
  public static final String EAST = "EAST";
  
  public static final int VERTICAL = 0;
  public static final int HORIZONTAL = 1;
  
  protected Component center, north, south, east, west;
  protected int hgap, vgap;
  protected int orientation;
  
  public CenterLayout()
  {
    this(0, 0, VERTICAL);
  }

  public CenterLayout(int orientation)
  {
    this(0, 0, orientation);
  }

  public CenterLayout(int hgap, int vgap)
  {
    this(hgap, vgap, VERTICAL);
  }
  
  public CenterLayout(int hgap, int vgap, int orientation)
  {
    this.hgap = hgap;
    this.vgap = vgap;
    this.orientation = orientation;
  }

  public void addLayoutComponent(Component comp, Object constraint)
  {
    if (constraint == null) center = comp;
    if (constraint == CENTER) center = comp;
    if (constraint == NORTH) north = comp;
    if (constraint == SOUTH) south = comp;
    if (constraint == WEST) west = comp;
    if (constraint == EAST) east = comp;
  }

  public void removeLayoutComponent(Component comp)
  {
    if (center == comp) center = null;
    if (north == comp) north = null;
    if (south == comp) south = null;
    if (west == comp) west = null;
    if (east == comp) east = null;
  }

  public Dimension preferredLayoutSize(Container container)
  {
    Insets insets = container.getInsets();
    Dimension centerSize = center == null ?
      new Dimension(0, 0) : center.getPreferredSize();
    Dimension westSize = new Dimension(0, 0);
    Dimension eastSize = new Dimension(0, 0);
    Dimension northSize = new Dimension(0, 0);
    Dimension southSize = new Dimension(0, 0);
    if (west != null) westSize = west.getPreferredSize();
    if (east != null) eastSize = east.getPreferredSize();
    if (north != null) northSize = north.getPreferredSize();
    if (south != null) southSize = south.getPreferredSize();
  
    int height = 0;
    int width = 0;
    int insetsWidth = insets.left + insets.right;
    int insetsHeight = insets.top + insets.bottom;
    if (orientation == VERTICAL)
    {
      height = northSize.height + centerSize.height + southSize.height;
      width = westSize.width + centerSize.width + eastSize.width;
      height = Math.max(westSize.width, height);
      height = Math.max(eastSize.width, height);
    }
    else
    {
      height = northSize.height + centerSize.height + southSize.height;
      width = westSize.width + centerSize.width + eastSize.width;
      width = Math.max(northSize.width, width);
      width = Math.max(southSize.width, width);
    }
    return new Dimension(insetsWidth + width, insetsHeight + height);
  }

  public Dimension minimumLayoutSize(Container container)
  {
    Insets insets = container.getInsets();
    Dimension centerSize = center == null ?
      new Dimension(0, 0) : center.getPreferredSize();
    Dimension westSize = new Dimension(0, 0);
    Dimension eastSize = new Dimension(0, 0);
    Dimension northSize = new Dimension(0, 0);
    Dimension southSize = new Dimension(0, 0);
    if (west != null) westSize = west.getMinimumSize();
    if (east != null) eastSize = east.getMinimumSize();
    if (north != null) northSize = north.getMinimumSize();
    if (south != null) southSize = south.getMinimumSize();
  
    int height = 0;
    int width = 0;
    int insetsWidth = insets.left + insets.right;
    int insetsHeight = insets.top + insets.bottom;
    if (orientation == VERTICAL)
    {
      height = northSize.height + centerSize.height + southSize.height;
      width = westSize.width + centerSize.width + eastSize.width;
      height = Math.max(westSize.width, height);
      height = Math.max(eastSize.width, height);
    }
    else
    {
      height = northSize.height + centerSize.height + southSize.height;
      width = westSize.width + centerSize.width + eastSize.width;
      width = Math.max(northSize.width, width);
      width = Math.max(southSize.width, width);
    }
    return new Dimension(insetsWidth + width, insetsHeight + height);
  }

  public void layoutContainer(Container container)
  {
    Dimension size = container.getSize();
    Insets insets = container.getInsets();
    Dimension centerSize = center == null ?
      new Dimension(0, 0) : center.getPreferredSize();
    int width = size.width - (insets.left + insets.right);
    int height = size.height - (insets.top + insets.bottom);
    int x = insets.left;
    int y = insets.top;
    int w = centerSize.width;
    int h = centerSize.height;
    x += (width - w) / 2;
    y += (height - h) / 2;
    
    int x0 = insets.left;
    int y0 = insets.right;
    int x1 = x - hgap;
    int y1 = y - vgap;
    int x2 = x + w + hgap;
    int y2 = y + h + hgap;
    int x3 = size.width - insets.right;
    int y3 = size.height - insets.bottom;
    
    if (center != null)
      center.setBounds(x, y, w, h);
    
    if (orientation == VERTICAL)
    {
      if (west != null)
        west.setBounds(x0, y0, x1 - x0, y3 - y0);
      if (east != null)
        east.setBounds(x2, y0, x3 - x2, y3 - y0);
      
      if (north != null)
        north.setBounds(x, y0, w, y1 - y0);
      if (south != null)
        south.setBounds(x, y2, w, y3 - y2);
    }
    else
    {
      if (north != null)
        north.setBounds(x0, y0, x3 - x0, y1 - y0);
      if (south != null)
        south.setBounds(x0, y2, x3 - x0, y3 - y2);
      if (west != null)
        west.setBounds(x0, y, x1 - x0, h);
      if (east != null)
        east.setBounds(x2, y, x3 - x2, h);
    }
  }

  public static void main(String[] args)
  {
    JFrame frame = new JFrame();
    frame.getContentPane().setLayout(new BorderLayout());
    
    JPanel panel = new JPanel(new CenterLayout(9, 9, CenterLayout.VERTICAL));
    panel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
    JButton center = new JButton("Center");
    center.setPreferredSize(new Dimension(100, 100));
    panel.add(CenterLayout.CENTER, center);
    panel.add(CenterLayout.NORTH, new JButton("North"));
    panel.add(CenterLayout.SOUTH, new JButton("South"));
    panel.add(CenterLayout.WEST, new JButton("West"));
    panel.add(CenterLayout.EAST, new JButton("East"));
    
    frame.getContentPane().add(panel);
    frame.setSize(300, 300);
    frame.setVisible(true);
  }
}


