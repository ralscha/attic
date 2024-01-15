
import com.objectplanet.chart.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import common.util.GraphicsUtil;

public class BarChartDemo {

	// The bar chart
	private BarChart chart;

	public BarChartDemo() {

    Frame frame = new Frame();
    frame.addNotify();
    Image image = frame.createImage(400,250);
    Graphics g = image.getGraphics();
		
		// create the chart
		chart = new BarChart(7, 400);
		double[] values = new double[]{17, 93, 279, 368, 311, 95, 12};
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
		
		chart.setSize(400, 250);
		
		chart.setBackground(Color.white);
		chart.paint(g);
    
    try {
		  GraphicsUtil.snapshot(image, "test1.gif");
		  values = new double[]{2, 13, 40, 53, 45, 13, 1};
		  chart.setRange(60);
		  chart.setSampleValues(0, values);
      chart.paint(g);

		  GraphicsUtil.snapshot(image, "test2.gif");
    } catch (IOException ioe) {
      System.err.println(ioe);
    }
		
	}
	

	public static void main(String[] argv) {

		BarChartDemo demo = new BarChartDemo();
		
		System.exit(0);
	}
}