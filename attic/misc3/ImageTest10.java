import Acme.JPM.Encoders.GifEncoder;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import common.util.*;
import com.objectplanet.chart.*;

public class ImageTest10 {


  public ImageTest10() {
	  String[] labels = new String[]{"0", "1", "2", "3", "4", "5", "6"};
		double chartData[] = new double[]{100.0,200.0,150.0,90.00,140.0,110.0,130.0};

    try {
      BufferedImage bufferedImage = 
                  new BufferedImage(200, 200, java.awt.image.BufferedImage.TYPE_INT_RGB);

    
      Graphics2D graphics = (Graphics2D)bufferedImage.createGraphics();
      graphics.setColor(Color.blue);
      graphics.fillRect(0, 0, 200, 200);    

      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.addNotify();


        BarChart chart = new BarChart(7, 210);

				chart.setTitle("Gerade/Ungerade ");
				chart.setSampleValues(0, chartData);
				chart.setSampleLabels(labels);
				chart.setValueLinesOn(true);
				chart.setValueLabelsOn(true);
				chart.setBarLabelsOn(true);
				
				chart.setBackground(Color.white);	
				chart.setSize(400,250);	
        chart.render(graphics);
    	
    	GraphicsUtil.snapshot(bufferedImage, "cc.gif");
      graphics.dispose();
/*
      WindowEvent windowClosingEvent;
    	windowClosingEvent = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
    	frame.dispatchEvent(windowClosingEvent);
*/    	
      //OutputStream os = new FileOutputStream("cc.gif");
      //GifEncoder ge = new GifEncoder(bufferedImage, os);

      //ge.encode();
      //os.close();
    }
    catch (IOException ioe) {
      System.out.println(ioe);
    }
    //System.exit(0);

  }

  public static void main(String args[]) {
    new ImageTest10();
  }

}
