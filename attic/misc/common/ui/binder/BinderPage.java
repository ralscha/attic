package common.ui.binder;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BinderPage extends JPanel implements ActionListener {
  protected JBinder binder;
  protected BinderButton westButton, eastButton;
  protected JLabel footer;

  public BinderPage(JBinder binder, Component paper) {
    this.binder = binder;
    setLayout(new BorderLayout());

    JPanel header = new JPanel(new BorderLayout());
    header.add(BorderLayout.EAST, eastButton = new BinderButton(BinderButton.NORTH_EAST));
    header.add(BorderLayout.WEST, westButton = new BinderButton(BinderButton.NORTH_WEST));
    westButton.addActionListener(this);
    eastButton.addActionListener(this);

    header.setBackground(Color.white);
    header.setPreferredSize(new Dimension(16, 16));
    header.setOpaque(true);

    footer = new JLabel("", JLabel.CENTER);
    footer.setBackground(Color.white);
    footer.setOpaque(true);

    JPanel west = new JPanel();
    west.setPreferredSize(new Dimension(16, 16));
    west.setBackground(Color.white);
    west.setOpaque(true);

    JPanel east = new JPanel();
    east.setPreferredSize(new Dimension(16, 16));
    east.setBackground(Color.white);
    east.setOpaque(true);

    add(BorderLayout.NORTH, header);
    add(BorderLayout.SOUTH, footer);
    add(BorderLayout.WEST, west);
    add(BorderLayout.EAST, east);
    add(BorderLayout.CENTER, paper);
    setBorder( BorderFactory.createCompoundBorder(new ShadowBorder(3),
               BorderFactory.createLineBorder(Color.black)));
  }

  public void setVisible(boolean visible) {
    super.setVisible(visible);
    if (visible) {
      BinderBook book = binder.getBook();
      int page = book.getPageNumber(this);
      int last = book.getComponentCount() - 1;
      eastButton.setVisible(page % 2 == 0 && page < last);
      westButton.setVisible(page % 2 != 0);
      footer.setText("Page " + (page + 1));
    }
  }

  public void actionPerformed(ActionEvent event) {
    if (westButton.isVisible())
      binder.decrement();
    if (eastButton.isVisible())
      binder.increment();
  }
}

