package common.ui.binder;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class JBinder extends JPanel {
  protected BinderBook book;

  public JBinder(int ringCount) {
    setLayout(new OverlayLayout(this));
    JPanel rings = new JPanel();
    rings.setOpaque(false);
    rings.setBorder(BorderFactory.createEmptyBorder(16, 8, 16, 8));
    rings.setLayout(new GridLayout(ringCount, 1));
    for (int i = 0; i < ringCount; i++) {
      rings.add(new JLabel(new ImageIcon(JBinder.class.getResource("BinderRing.gif"))));
    }
    add(rings);
    add(book = new BinderBook());
  }

  public BinderPage addPage(JComponent child) {
    BinderPage page = new BinderPage(this, child);
    book.add(page);
    return page;
  }

  public BinderBook getBook() {
    return book;
  }

  public void increment() {
    book.increment();
    repaint();
  }

  public void decrement() {
    book.decrement();
    repaint();
  }
}

