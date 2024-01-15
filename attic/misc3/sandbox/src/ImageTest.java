import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import Acme.JPM.Encoders.GifEncoder;

public class ImageTest {

  public ImageTest() {

    try {

      for (int i = 1; i < 400; i++) {
        String line = String.valueOf(i);
        BufferedImage bufferedImage = new BufferedImage(400, 400, java.awt.image.BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 400, 400);

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Verdana", Font.BOLD, 40));
        graphics.drawString(line, 100, 200);

        OutputStream os = new FileOutputStream("c:/temp/" + line + ".gif");
        GifEncoder ge = new GifEncoder(bufferedImage, os);

        ge.encode();
        os.close();
      }

      } catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static void main(String args[]) {
    new ImageTest();
  }

}
