
import com.objectplanet.chart.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import Acme.JPM.Encoders.GifEncoder;


public class BarChartDemo2 {

	// The bar chart
	private BarChart chart;

	public BarChartDemo2() {
		
		// create the chart
		chart = new BarChart(7, 600);
		double[] values = new double[]{23, 132, 400, 533, 452, 139, 17};
		String[] labels = new String[]{"0", "1", "2", "3", "4", "5", "6"};

		chart.setTitle("Gerade/Ungerade alle Ziehungen");
		chart.setSampleValues(0, values);
		chart.setSampleLabels(labels);

		//chart.set3DModeOn(true);
		//chart.setMultiColorOn(true);
		chart.setValueLinesOn(true);
		//chart.setTitleOn(true);
		//chart.setLegendOn(true);
		chart.setValueLabelsOn(true);
		chart.setBarLabelsOn(true);
		chart.setSize(400,200);
	
    Frame frame = new Frame();
    frame.add(chart, BorderLayout.CENTER);
//		chart.setVisible(true);
    
    chart.addNotify();
    frame.addNotify();
    frame.pack();
    frame.repaint();
   // frame.setVisible(true);

		try {
			Image img = frame.createImage(frame.getSize().width, frame.getSize().height);
			Graphics g = img.getGraphics();
			
			chart.paintAll(g);		
			
	       OutputStream os = new BufferedOutputStream(new FileOutputStream("test.gif"));
	       GifEncoder ge = new GifEncoder(img, os);
			ge.encode();
			os.close();
			frame.removeNotify();
      frame.dispose();
      System.exit(0);
        } catch (IOException ioe) {
        	System.out.println(ioe); 
        }
		
	
	}

	public static void main(String[] argv) {

		new BarChartDemo2();
	}
}