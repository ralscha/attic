import Acme.JPM.Encoders.GifEncoder;
import java.io.*;
import java.awt.*;
import java.util.*;
import cmp.business.BigDate;

public class ImageTest4 extends Frame {


  public ImageTest4() {
    setSize(200, 200);
    setVisible(true);
    try {
      Image img = createImage(20, 20);
      Image smallImg = createImage(9, 9);
      Image halfimg = createImage(19, 9);

      Graphics g = img.getGraphics();
      Graphics gsmall = smallImg.getGraphics();
      Graphics ghalf = halfimg.getGraphics();

      int[] value = {0, 51, 102, 153, 204, 255};
      String[] valuestr = {"00", "33", "66", "99", "CC", "FF"};

      for (int ri = 0; ri < 6; ri++) {
        for (int gi = 0; gi < 6; gi++) {
          for (int bi = 0; bi < 6; bi++) {


            int r = value[ri];
            int gr = value[gi];
            int b = value[bi];

            OutputStream os = new FileOutputStream("images\\full\\"+valuestr[ri] +
                                                   valuestr[gi] + valuestr[bi] + ".gif");
            GifEncoder ge = new GifEncoder(img, os);

            //draw something
            g.setColor(new Color(r, gr, b));
            g.fillRect(0, 0, 20, 20);
            //g.setColor(Color.blue);
            //g.fillOval(50,50,30,30);

            ge.encode();
            //img.getSource().startProduction(ge);
            os.close();

            os = new FileOutputStream("images\\small\\"+valuestr[ri] +
                                      valuestr[gi] + valuestr[bi] + ".gif");
            ge = new GifEncoder(smallImg, os);

            //draw something
            gsmall.setColor(new Color(r, gr, b));
            gsmall.fillRect(0, 0, 9, 9);
            //g.setColor(Color.blue);
            //g.fillOval(50,50,30,30);

            ge.encode();
            //img.getSource().startProduction(ge);
            os.close();

            os = new FileOutputStream("images\\part\\"+valuestr[ri] +
                                      valuestr[gi] + valuestr[bi] + ".gif");
            ge = new GifEncoder(img, os);

            //draw something
            g.setColor(new Color(r, gr, b));
            g.fillRect(0, 0, 20, 20);

            g.setColor(Color.white);
            
            /*
            for (int i = 0; i < 20; i++) {
              g.drawLine(i + 1, i, 20, i);
            }
            */
            boolean flag = false;
            
            for (int i = 0; i < 20; i++) {
              if (i % 3 == 0) {
                flag = !flag;
              }
              
              if (i < 10) {                                
                g.drawLine(0,i,20,i);
               
              } else {    
                if (flag) {
                  for (int j = 3; j < 20; j+=6) {                  
                    g.drawLine(j,i,j+2,i);
                  }
                } else {
                  for (int j = 0; j < 20; j+=6) {                  
                    g.drawLine(j,i,j+2,i);
                  }
                }
              }
            }
                        
            

            //g.setColor(Color.blue);
            //g.fillOval(50,50,30,30);

            ge.encode();
            //img.getSource().startProduction(ge);
            os.close();


          }
        }
      }

      OutputStream os = new FileOutputStream("images\\small\\half.gif");
      GifEncoder ge = new GifEncoder(halfimg, os);

      //draw something
      ghalf.setColor(new Color(0, 0, 0));
      ghalf.fillRect(0, 0, 19, 9);

      ge.encode();
      os.close();

    }
    catch (IOException ioe) {
      System.out.println(ioe);
    }
    System.exit(0);

  }

  public static void main(String args[]) {    
    new ImageTest4();
  }

}
