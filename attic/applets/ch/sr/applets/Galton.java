package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Galton extends JApplet {

	GaltonComponent gc;
	private JButton restart;

	public void init() {
		super.init();
		getContentPane().setLayout(new BorderLayout());

		gc = new GaltonComponent();

		getContentPane().add(gc, BorderLayout.CENTER);
		restart = new JButton("Restart");
		restart.addActionListener(new ActionListener() {
                          			public void actionPerformed(ActionEvent ae) {
                          				gc.stop();
                          				gc.start();
                          			}});

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		p.add(restart);
		getContentPane().add(p, BorderLayout.SOUTH);

	}

	public void start() {
		gc.start();
	}

	public void stop() {
		gc.stop();
	}

	public String getAppletInfo() {
		return "Galton V1.1 by Ralph Schaer";
	}

}