package common.ui.scroller;

import java.awt.*;
import javax.swing.*;

public class ScrollerButton extends JButton
{
  public static final ImageIcon LEFT = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/left.gif")));
  public static final ImageIcon RIGHT = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/right.gif")));
  public static final ImageIcon TOP = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/top.gif")));
  public static final ImageIcon BOTTOM = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/bottom.gif")));
  public static final ImageIcon NORTH = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/north.gif")));
  public static final ImageIcon SOUTH = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/south.gif")));
  public static final ImageIcon EAST = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/east.gif")));
  public static final ImageIcon WEST = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/west.gif")));
  public static final ImageIcon PLUS = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/plus.gif")));
  public static final ImageIcon MINUS = new ImageIcon(
          			Toolkit.getDefaultToolkit().createImage(ScrollerButton.class.getResource("icons/minus.gif")));

  public ScrollerButton(ImageIcon icon)
  {
    super(icon);
    setPreferredSize(new Dimension(16, 16));
    setMargin(new Insets(0, 0, 1, 1));
    setFocusPainted(false);
    setDefaultCapable(false);
  }
  
  public boolean isFocusTraversable()
  {
    return false;
  }
}

