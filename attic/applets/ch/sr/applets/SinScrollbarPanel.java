package ch.sr.applets;
import java.awt.event.*;

public class SinScrollbarPanel extends ScrollbarPanel {
	

	public SinScrollbarPanel(String title, DrawCanvas draw) {
		super(title, draw);
		
		horz.addAdjustmentListener(new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e) {
						start = horz.getValue();
						for (int i = 0; i < 10; i++) {
							scrollbar[i].setValue(scrollValues[start + i]);
							sblabel[i].change(String.valueOf(i + start + 1));
						}
						
					}});
					
					
		AdjustmentListener adjListener = new AdjustmentListener() {
					public void adjustmentValueChanged(AdjustmentEvent e) {
						for (int i = 0; i < 10; i++)
							scrollValues[start + i] = scrollbar[i].getValue();
	
						int max = scrollbar[0].getMaximum()-1;
	
						for (int i = 0; i < Global.sb; i++) {
							int help = max - scrollValues[i];
							scrolls[i] = (double) help / (double) max;
						}
						drawer.setSin(scrolls);
						drawer.repaint();
					}};
				
					
		for (int i = 0; i < scrollbar.length; i++) {
			scrollbar[i].addAdjustmentListener(adjListener);
		}
	}

}