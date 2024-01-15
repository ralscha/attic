package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

class AboutDialog extends Dialog {
	Button okButton;

	AboutDialog(Frame dw, String title) {
		super(dw, title, true);
		okButton = new Button("OK");

		okButton.addActionListener(new ActionListener() {
                           			public void actionPerformed(ActionEvent ae) {
                           				setVisible(false);
                           			}
                           		});

		add(new Label("Harmony 1.2", Label.CENTER), BorderLayout.NORTH);
		add(new Label("(c) 1996 by Ralph Schaer, Reto Eric Koenig"), BorderLayout.CENTER);
		add(okButton, BorderLayout.SOUTH);
		setSize(300, 150);
		pack();
	}

}