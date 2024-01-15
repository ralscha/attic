package ch.sr.applets;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class MandelApplet extends Applet {

	private Mandel mandel = null;
	public static URL base;
	
	public String getAppletInfo() {
		return "Mandel V1.2 by Ralph Schaer";
	}
	
	public static URL getBase() {
		return base;
	}

	public void init() {
		super.init();
		base = getDocumentBase();
		Button startButton = new Button("Start Mandel");
		add(startButton, BorderLayout.CENTER);

		startButton.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent ae) {
                              				startMandel();
                              			}
                              		});
	}

	public void stop() {
		if (mandel != null) {
			mandel.setVisible(false);
			mandel.dispose();
		}
	}

	void startMandel() {
		mandel = new Mandel();
		mandel.pack();
		mandel.setVisible(true);
	}
}