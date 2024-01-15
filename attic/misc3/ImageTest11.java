
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;


public class ImageTest11 extends JFrame {

  public ImageTest11() {
  	super("Test");
	  getContentPane().setLayout(new BorderLayout());	
	  JComponent page = new JLabel(new ImageIcon(createImage(250, 350, 30)));  
    getContentPane().add(BorderLayout.CENTER, page);
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    pack();
    setVisible(true);

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
	new ImageTest11();
  }
}

