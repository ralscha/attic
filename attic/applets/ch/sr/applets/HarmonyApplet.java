package ch.sr.applets;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class HarmonyApplet extends Applet {

	private Harmony harmony = null;

	public String getAppletInfo() {
		return "Harmony V1.2 by Ralph Schaer";
	}

	public void init() {
		super.init();
		Button startButton = new Button("Start Harmony");
		add(startButton, BorderLayout.CENTER);

		startButton.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent ae) {
                              				startHarmony();
                              			}
                              		});
	}

	public void stop() {
		if (harmony != null) {
			harmony.setVisible(false);
			harmony.dispose();
		}
	}

	void startHarmony() {
		harmony = new Harmony();
		harmony.pack();
		harmony.setVisible(true);
	}
}
