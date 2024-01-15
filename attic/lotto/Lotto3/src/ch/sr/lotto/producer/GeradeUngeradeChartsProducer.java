package ch.sr.lotto.producer;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import ch.sr.lotto.statistic.*;
import ch.sr.lotto.util.*;
import com.objectmatter.bsf.*;
import com.objectplanet.chart.*;

public class GeradeUngeradeChartsProducer extends Producer {
	private static final String[] labels = new String[]{"0", "1", "2", "3", "4", "5", "6"};
	
	private static final String gifFiles[] = {"gualle.gif", "gu42.gif", "gu45.gif", "gu97.gif"};		
	private JFrame frame;			
	
	public void producePage(Database db, Properties props, AusspielungenStatistiken as) {
		try {
			
		  int hger[];
		  double chartData[] = new double[7];
		
		  AusspielungenType[] types = AusspielungenType.getTypesAsArray();

      frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.addNotify();
      Image image = frame.createImage(400,250);
      Graphics g = image.getGraphics();
		
		
      for (int i = 0; i < 4; i++) {     
      
        hger = as.getAusspielungenStatistik(types[i]).getVerteilungHaeufigkeitGerade();
        
        int max = copyData(hger, chartData);
        
        if (max < 100)
        	max = ((max / 10) + 1) * 10;
        else
        	max = ((max / 100) + 1) * 100;
        
        BarChart chart = new BarChart(7, max);

				chart.setTitle("Gerade/Ungerade "+TITLES[i]);
				chart.setSampleValues(0, chartData);
				chart.setSampleLabels(labels);
				chart.setValueLinesOn(true);
				chart.setValueLabelsOn(true);
				chart.setBarLabelsOn(true);
				
				chart.setBackground(Color.white);	
				chart.setSize(400,250);	
        chart.paint(g);
				GraphicsUtil.snapshot(image, lottoBinPath + gifFiles[i]);

      }        	
                

		} catch (Exception fe) {
			System.err.println(fe);
		}
	}	
	
	public JFrame getFrame() {
		return frame;
	}
	
	private int copyData(int[] ints, double[] doubles) {
		int max = 0;
		for (int i = 0; i < ints.length; i++) {
			if (ints[i] > max) max = ints[i];
			doubles[i] = ints[i];
		}
		return max;
	}
}