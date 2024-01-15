import Acme.JPM.Encoders.GifEncoder;
import java.io.*;
import java.awt.*;

public class ImageTest5 extends Frame {


  public ImageTest5() {
    
    setSize(4, 9);
    setVisible(true);

    try {
      

      for (int i = 0; i < 64; i++) {


        Image img = createImage(36, 9);

      Graphics g = img.getGraphics();
      GIFOutputStream gos = new GIFOutputStream(new FileOutputStream("pbroker/"+i+".gif"));

      g.setColor(Color.white);
      g.fillRect(0,0,36,9);

      //draw something
      String bits = getBits(i, 26, 6);
      for (int j = 0; j < 6; j++) {
        if (bits.charAt(j) == '0')
          g.setColor(Color.green);      
        else
          g.setColor(Color.red);      

        g.fillRect((5-j)*6, 0, 4, 9);
      }

      gos.write(img, Color.white);
      gos.close();
      }
    }
    catch (IOException ioe) {
      System.out.println(ioe);
    }
    //System.exit(0);

  }

 public static String getBits(int number, int begin, int length) {
    int bitmask;
    StringBuffer result = new StringBuffer(length);

    bitmask = 0x80000000 >>> begin;
    length+=begin;

    for(int i=begin; i < length; ++i) {
      if((number & bitmask) == 0)
        result.append('0');
      else
        result.append('1');
      bitmask >>>= 1;
    }
    return result.toString();
  }

  public static void main(String args[]) {
    new ImageTest5();
  }

}
