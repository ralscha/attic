package binder;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import common.ui.binder.*;

public class JBinderTest extends JPanel {
  public JBinderTest(int pageCount) {
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    JBinder binder = new JBinder(10);
    for (int i = 0; i < pageCount; i++) {
      JComponent page = new JLabel(new ImageIcon(createImage(250, 350, 30)));
      page.setBorder(BorderFactory.createLineBorder(Color.orange, 5));
      binder.addPage(page);
    }
    add(BorderLayout.CENTER, binder);
  }

  protected BufferedImage createImage(int w, int h, int count) {
    BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    for (int i = 0; i < count; i++) {
      Color color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255),
                              (int)(Math.random() * 255));
      g.setColor(color);
      int x = (int)(Math.random() * (w - 20)) + 10;
      int y = (int)(Math.random() * (h - 20)) + 10;
      g.drawLine(x, y, w / 2, h / 2);
    }
    return image;
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("JBinder Test");
    frame.getContentPane().setLayout(new GridLayout(1, 1));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new JBinderTest(10));
    frame.pack();
    frame.show();
  }
}

