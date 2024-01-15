package common.ui.cellborder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class CellBorderPanel extends JPanel
  implements MouseListener
{
  protected CellBorder border;
  protected boolean northSelect, southSelect;
  protected boolean eastSelect, westSelect;
  protected int northInset, southInset;
  protected int eastInset, westInset;
  
  public CellBorderPanel()
  {
    setCellBorder(new CellBorder(
      new Insets(1, 1, 1, 1), Color.blue));
    addMouseListener(this);
    setOpaque(false);
  }
  
  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {}
  public void mouseReleased(MouseEvent event) {}
  
  public void mousePressed(MouseEvent event)
  {
    border.setFill(!border.getFill());
    refresh();
  }
  
  public CellBorder getCellBorder()
  {
    return border;
  }
  
  public void setCellBorder(CellBorder border)
  {
    this.border = border;
    setBorder(border);
    Insets insets = getInsets();
    northInset = insets.top;
    southInset = insets.bottom;
    eastInset = insets.right;
    westInset = insets.left;
    northSelect = true;
    southSelect = true;
    eastSelect = true;
    westSelect = true;
  }
  
  protected void refresh()
  {
    revalidate();
    repaint();
  }
  
  public void toggleNorth(boolean selected)
  {
    northSelect = selected;
    border.getInsets().top =
      northSelect ? northInset : 0;
    refresh();
  }
  
  public void toggleSouth(boolean selected)
  {
    southSelect = selected;
    border.getInsets().bottom =
      southSelect ? southInset : 0;
    refresh();
  }
  
  public void toggleEast(boolean selected)
  {
    eastSelect = selected;
    border.getInsets().right =
      eastSelect ? eastInset : 0;
    refresh();
  }
  
  public void toggleWest(boolean selected)
  {
    westSelect = selected;
    border.getInsets().left =
      westSelect ? westInset : 0;
    refresh();
  }
  
  public void adjustNorth(int increment)
  {
    if (!northSelect) return;
    northInset += increment;
    northInset = Math.max(0, northInset);
    border.getInsets().top = northInset;
    refresh();
  }
  
  public void adjustSouth(int increment)
  {
    if (!southSelect) return;
    southInset += increment;
    southInset = Math.max(0, southInset);
    border.getInsets().bottom = southInset;
    refresh();
  }

  public void adjustWest(int increment)
  {
    if (!westSelect) return;
    westInset += increment;
    westInset = Math.max(0, westInset);
    border.getInsets().left = westInset;
    refresh();
  }

  public void adjustEast(int increment)
  {
    if (!eastSelect) return;
    eastInset += increment;
    eastInset = Math.max(0, eastInset);
    border.getInsets().right = eastInset;
    refresh();
  }
}

