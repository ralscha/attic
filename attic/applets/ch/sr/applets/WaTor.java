package ch.sr.applets;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import com.bdnm.awt.*;

public class WaTor extends JApplet {
	private WaTorComponent wtc;
	private WaTorChartComponent wtcc;
	private WaTorStatusPanel wtsp;
	private WaTorControlPanel wtcp;

	

	public void init() {
		super.init();
		// Default
		int fbrut = 3;
		int hbrut = 10;
		int afisch = 200;
		int ahai = 20;
		int fasten = 3;
		int gx = 60;
		int gy = 20;

		String gxstr = getParameter("gx");
		String gystr = getParameter("gy");
		String fbrutstr = getParameter("fbrut");
		String hbrutstr = getParameter("hbrut");
		String afischstr = getParameter("afisch");
		String ahaistr = getParameter("ahai");
		String fastenstr = getParameter("fasten");

		if (gxstr != null) {
			try {
				gx = Integer.parseInt(gxstr);
			} catch (NumberFormatException e) { }
		}
		if (gystr != null) {
			try {
				gy = Integer.parseInt(gystr);
			} catch (NumberFormatException e) { }
		}
		if (fbrutstr != null) {
			try {
				fbrut = Integer.parseInt(fbrutstr);
			} catch (NumberFormatException e) { }
		}
		if (hbrutstr != null) {
			try {
				hbrut = Integer.parseInt(hbrutstr);
			} catch (NumberFormatException e) { }
		}
		if (afischstr != null) {
			try {
				afisch = Integer.parseInt(afischstr);
			} catch (NumberFormatException e) { }
		}
		if (ahaistr != null) {
			try {
				ahai = Integer.parseInt(ahaistr);
			} catch (NumberFormatException e) { }
		}
		if (fastenstr != null) {
			try {
				fasten = Integer.parseInt(fastenstr);
			} catch (NumberFormatException e) { }
		}


		FractionalLayout fLay = new FractionalLayout();
		getContentPane().setLayout(fLay);

		JPanel bp1 = new JPanel();
		JPanel bp2 = new JPanel();

		Border etched = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                		BorderFactory.createEmptyBorder(3, 3, 3, 3));

		bp1.setBorder(etched);
		bp2.setBorder(etched);

		bp1.setLayout(new BorderLayout());
		bp2.setLayout(new BorderLayout());
		bp1.setBackground(Color.lightGray);
		bp2.setBackground(Color.lightGray);

		wtsp = new WaTorStatusPanel(afisch, ahai);
		wtcc = new WaTorChartComponent(gx * gy, afisch, ahai);
		wtc = new WaTorComponent(gx, gy, fbrut, hbrut, afisch, ahai, fasten, wtcc, wtsp);
		wtcp = new WaTorControlPanel(wtc);

		bp1.add("Center",wtc);
		bp2.add("Center",wtcc);

		fLay.setConstraint(bp1, new FrameConstraint(0.0, 0, 0.00, 0, 1.0, 0, 0.7, 0));
		getContentPane().add(bp1);

		fLay.setConstraint(bp2, new FrameConstraint(0.0, 0, 0.7, 0, 0.7, 0, 0.93, 0));
		getContentPane().add(bp2);

		fLay.setConstraint(wtsp, new FrameConstraint(0.7, 0, 0.7, 0, 1.0, 0, 0.93, 0));
		getContentPane().add(wtsp);

		fLay.setConstraint(wtcp, new FrameConstraint(0.0, 0, 0.93, 0, 1.0, 0, 1.0, 0));
		getContentPane().add(wtcp);

	}

	public void start() {
		wtcc.initWTCC();
		wtc.start();
	}

	public void stop() {
		wtc.stop();
	}

	public String getAppletInfo() {
		return "WaTor V1.0 by Ralph Schaer";
	}

	public String[][] getParameterInfo() {
		String[][] info = {{"gx", "int", "Gitterpunkte horizontal"}, 
								{"gy", "int",	"Gitterpunkte vertikal"}, 
								{"fbrut", "int", "Chrononen die ein Fisch existieren muss bis er einen Nachkommen hat"}, 
								{"hbrut",	"int", "Chrononen die ein Hai existieren muss bis er einen Nachkommen hat"}, 
								{"afisch", "int", "Fische zu Beginn"}, 
								{"ahai", "int", "Haie zu Beginn"}, 
								{"fasten", "int", "Chrononen die ein Hai ohne Nahrung auskommt"}};
		return info;
	}




}