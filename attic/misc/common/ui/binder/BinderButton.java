package common.ui.binder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class BinderButton extends JPanel implements SwingConstants, MouseListener {
  protected ArrayList listeners = new ArrayList();
  protected int corner = NORTH_EAST;

  public BinderButton(int corner) {
    this.corner = corner;
    addMouseListener(this);
    setPreferredSize(new Dimension(16, 16));
  }

  public void paintComponent(Graphics g) {
    int w = getSize().width - 1;
    int h = getSize().height - 1;
    if (corner == NORTH_EAST) {
      g.drawLine(0, 0, w, h);
      g.drawLine(0, 0, 0, h);
      g.drawLine(0, h, w, h);
    }
    if (corner == NORTH_WEST) {
      g.drawLine(0, h, w, 0);
      g.drawLine(w, 0, w, h);
      g.drawLine(0, h, w, h);
    }
  }

  public void mouseEntered(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {}
  public void mouseReleased(MouseEvent event) {}
  public void mousePressed(MouseEvent event) {
    fireActionEvent();
  }

  public void addActionListener(ActionListener listener) {
    listeners.add(listener);
  }

  public void removeActionListener(ActionListener listener) {
    listeners.remove(listener);
  }

  public void fireActionEvent() {
    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Action");
    ArrayList list = (ArrayList) listeners.clone();
    for (int i = 0; i < list.size(); i++) {
      ActionListener listener = (ActionListener) listeners.get(i);
      listener.actionPerformed(event);
    }
  }
}

