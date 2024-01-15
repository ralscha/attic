package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

import com.bdnm.awt.*;

public class Harmony extends Frame {
	boolean inAnApplet = true;

	DrawCanvas draw;
	ScrollbarPanel sinp, cosp;
	ControlPanel controlpanel;
	Panel allPanel;

	public Harmony() {

		allPanel = new Panel();
		setLayout(new BorderLayout());

		FractionalLayout fLay = new FractionalLayout();
		allPanel.setLayout(fLay);

		draw = new DrawCanvas();
		sinp = new SinScrollbarPanel("Sinus", draw);
		cosp = new CosScrollbarPanel("Cosinus", draw);
		controlpanel = new ControlPanel(draw, sinp, cosp, this);

		fLay.setConstraint(draw, new FrameConstraint(0.0, 5, 0.00, 5, 0.8, -5, 0.8, -5));
		allPanel.add(draw);

		fLay.setConstraint(controlpanel, new FrameConstraint(0.0, 5, 0.8, 0, 0.8, -5, 1.0, 0));
		allPanel.add(controlpanel);

		fLay.setConstraint(sinp, new FrameConstraint(0.8, 0, 0.00, 5, 1.0, -5, 0.5, -3));
		allPanel.add(sinp);

		fLay.setConstraint(cosp, new FrameConstraint(0.8, 0, 0.5, 3, 1.0, -5, 1.0, -5));
		allPanel.add(cosp);

		add("Center", allPanel);
		addWindowListener(new MyWindowAdapter());
	}

	private class MyWindowAdapter extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			if (inAnApplet) {
				dispose();
			} else {
				System.exit(0);
			}
		}
	}

	public static void main(String args[]) {
		Harmony window = new Harmony();
		window.inAnApplet = false;
		window.setTitle("Harmony V1.2");
		window.setSize(700, 450);
		window.setVisible(true);
	}

}