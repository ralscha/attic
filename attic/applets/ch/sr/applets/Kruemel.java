package ch.sr.applets;
import java.awt.*;

import javax.swing.*;

public class Kruemel extends JApplet {

	KruemelComponent kc;

	public void init() {
		super.init();
		getContentPane().setLayout(new BorderLayout());

		kc = new KruemelComponent();

		getContentPane().add(kc, BorderLayout.CENTER);
		kc.start();

	}

	public String getAppletInfo() {
		return "Kruemel V1.1 by Ralph Schaer";
	}

	public String[][] getParameterInfo() {
		String[][] info = {{}};
		return info;
	}

}