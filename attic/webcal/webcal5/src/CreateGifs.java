import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;



public class CreateGifs {

  private final static int BOX_WIDTH = 3;
  private final static int BOX_HEIGHT = 3;
  

  public static void main(String[] args) {
    try {
      
      
      int width = 26;
      int height = 26;
      
      String[] colourValues = new String[]{"00","33","66","99","CC","FF"};
      
      for (int i = 0; i < colourValues.length; i++) {
        for (int j = 0; j < colourValues.length; j++) {
          for (int k = 0; k < colourValues.length; k++) {
            
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics gr = image.getGraphics();

            int r = Integer.parseInt(colourValues[i], 16);
            int g = Integer.parseInt(colourValues[j], 16);
            int b = Integer.parseInt(colourValues[k], 16);

            
            gr.setColor(new Color(r, g, b));
            gr.fillRect(0, 0, width, height);

            FileOutputStream fos = new FileOutputStream("c:/temp/pix/"+colourValues[i] + colourValues[j] + colourValues[k]+".gif");
            ImageIO.write(image, "GIF", fos);
            fos.close();
            
            int halfHeight = height / 2;
            
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            gr = image.getGraphics();
            gr.setColor(Color.WHITE);
            gr.fillRect(0, 0, width, height);
            gr.setColor(new Color(r, g, b));
            boolean oddrow = false;
            int curX = 0;
            int curY = 0;
            while (curY <= halfHeight) {
              int drawHeight = Math.min(BOX_HEIGHT, halfHeight - curY);
              gr.fillRect(curX, curY, BOX_WIDTH, drawHeight);
              curX = curX + (BOX_WIDTH * 2);
              if (curX > width) {
                curY = curY + (BOX_HEIGHT);
                if (oddrow) {
                  curX = 0;
                  oddrow = false;
                } else {
                  curX = BOX_WIDTH;
                  oddrow = true;
                }
              }
            }
            fos = new FileOutputStream("c:/temp/pix/"+colourValues[i] + colourValues[j] + colourValues[k]+"th.gif");
            ImageIO.write(image, "GIF", fos);
            fos.close();
            
            
            
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            gr = image.getGraphics();
            gr.setColor(Color.WHITE);
            gr.fillRect(0, 0, width, height);
            gr.setColor(new Color(r, g, b));
            oddrow = false;
            curX = 0;
            curY = halfHeight;
            while (curY <= height) {
              int drawHeight = Math.min(BOX_HEIGHT, height - curY);
              gr.fillRect(curX, curY, BOX_WIDTH, drawHeight);
              curX = curX + (BOX_WIDTH * 2);
              if (curX > width) {
                curY = curY + (BOX_HEIGHT);
                if (oddrow) {
                  curX = 0;
                  oddrow = false;
                } else {
                  curX = BOX_WIDTH;
                  oddrow = true;
                }
              }
            }
            fos = new FileOutputStream("c:/temp/pix/"+colourValues[i] + colourValues[j] + colourValues[k]+"bh.gif");
            ImageIO.write(image, "GIF", fos);
            fos.close();
            
            
            
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            gr = image.getGraphics();
            gr.setColor(Color.WHITE);
            gr.fillRect(0, 0, width, height);
            gr.setColor(new Color(r, g, b));
            gr.fillRect(0, 0, width, halfHeight);
            fos = new FileOutputStream("c:/temp/pix/"+colourValues[i] + colourValues[j] + colourValues[k]+"tf.gif");
            ImageIO.write(image, "GIF", fos);
            fos.close();
            
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            gr = image.getGraphics();
            gr.setColor(Color.WHITE);
            gr.fillRect(0, 0, width, height);
            gr.setColor(new Color(r, g, b));
            gr.fillRect(0, halfHeight, width, height);
            fos = new FileOutputStream("c:/temp/pix/"+colourValues[i] + colourValues[j] + colourValues[k]+"bf.gif");
            ImageIO.write(image, "GIF", fos);
            fos.close();            
            
          }
        }
      }
    } catch (IOException e) {      
      e.printStackTrace();
    }

  }

}
