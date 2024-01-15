package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.bdnm.awt.*;

class WaTorPrefDialog extends JFrame {
	JButton restartButton;
	JButton closeButton;
	JTextField gxtf, gytf, fbruttf, hbruttf, afischtf, ahaitf, fastentf;
	int gx, gy, fbrut, hbrut, afisch, ahai, fasten;
	WaTorComponent wtc;

	WaTorPrefDialog(WaTorComponent wtc, int gx, int gy, int fbrut, int hbrut, int afisch,
                	int ahai, int fasten) {
		super("WaTor Preference");
		this.wtc = wtc;
		this.gx = gx;
		this.gy = gy;
		this.fbrut = fbrut;
		this.hbrut = hbrut;
		this.afisch = afisch;
		this.ahai = ahai;
		this.fasten = fasten;

		addWindowListener(new WindowAdapter() {
                  			public void windowClosing(WindowEvent we) {
                  				dispose();
                  			}
                  		}
                 		);



		setBackground(Color.lightGray);

		getContentPane().setLayout(new BorderLayout());
		JPanel p = new JPanel();
		FractionalLayout fLay = new FractionalLayout();
		p.setLayout(fLay);

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		p1.setLayout(new GridLayout(7, 1));
		p2.setLayout(new GridLayout(7, 1));

		gxtf = new JTextField(String.valueOf(gx), 4);
		gytf = new JTextField(String.valueOf(gy), 4);
		fbruttf = new JTextField(String.valueOf(fbrut), 4);
		hbruttf = new JTextField(String.valueOf(hbrut), 4);
		afischtf = new JTextField(String.valueOf(afisch), 4);
		ahaitf = new JTextField(String.valueOf(ahai), 4);
		fastentf = new JTextField(String.valueOf(fasten), 4);


		JPanel pa[] = new JPanel[7];
		int i;
		for (i = 0; i < 7; i++) {
			pa[i] = new JPanel();
			pa[i].setLayout(new FlowLayout());
		}

		pa[0].add(gxtf);
		pa[1].add(gytf);
		pa[2].add(afischtf);
		pa[3].add(ahaitf);
		pa[4].add(fbruttf);
		pa[5].add(hbruttf);
		pa[6].add(fastentf);

		for (i = 0; i < 7; i++)
			p2.add(pa[i]);

		p1.add(new Label("Gitterpunkte horizontal"));
		p1.add(new Label("Gitterpunkte vertikal"));
		p1.add(new Label("Fische zu Beginn"));
		p1.add(new Label("Haie zu Beginn"));

		JPanel p11 = new JPanel();
		p11.setLayout(new GridLayout(2, 1, 0, -5));
		p11.add(new Label("Chrononen die ein Fisch existieren"));
		p11.add(new Label("muss bis er einen Nachkommen hat"));
		p1.add(p11);

		JPanel p12 = new JPanel();
		p12.setLayout(new GridLayout(2, 1, 0, -5));
		p12.add(new Label("Chrononen die ein Hai existieren"));
		p12.add(new Label("muss bis er einen Nachkommen hat"));
		p1.add(p12);

		JPanel p13 = new JPanel();
		p13.setLayout(new GridLayout(2, 1, 0, -5));
		p13.add(new Label("Chrononen die ein Hai ohne"));
		p13.add(new Label("Nahrung auskommt"));
		p1.add(p13);


		fLay.setConstraint(p1, new FrameConstraint(0.0, 0, 0.0, 0, 0.7, 0, 1.0, 0));
		p.add(p1);
		fLay.setConstraint(p2, new FrameConstraint(0.7, 0, 0.0, 0, 1.0, 0, 1.0, 0));
		p.add(p2);
		getContentPane().add(p, BorderLayout.CENTER);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));
		restartButton = new JButton("Restart");
		closeButton = new JButton("Close");
		p3.add(restartButton);
		p3.add(closeButton);


		restartButton.addActionListener(new ActionListener() {
                                			public void actionPerformed(ActionEvent ae) {
                                				restart();
                                			}
                                		}
                               		);

		closeButton.addActionListener(new ActionListener() {
                              			public void actionPerformed(ActionEvent ae) {
                              				dispose();
                              			}
                              		}
                             		);

		getContentPane().add(p3, BorderLayout.SOUTH);
		setSize(320, 150);
		pack();

	}

	public void restart() {
		wtc.stop();

		try {
			gx = Integer.parseInt(gxtf.getText());
		} catch (NumberFormatException nfe) {}

		try {
			gy = Integer.parseInt(gytf.getText());
		} catch (NumberFormatException nfe) {}

		try {
			fbrut = Integer.parseInt(fbruttf.getText());
		} catch (NumberFormatException nfe) {}

		try {
			hbrut = Integer.parseInt(hbruttf.getText());
		} catch (NumberFormatException nfe) {}

		try {
			afisch = Integer.parseInt(afischtf.getText());
		} catch (NumberFormatException nfe) {}

		try {
			ahai = Integer.parseInt(ahaitf.getText());
		} catch (NumberFormatException nfe) {}

		try {
			fasten = Integer.parseInt(fastentf.getText());
		} catch (NumberFormatException nfe) {}

		wtc.gx = gx;
		wtc.gy = gy;
		wtc.fbrut = fbrut;
		wtc.hbrut = hbrut;
		wtc.afischinit = afisch;
		wtc.ahaiinit = ahai;
		wtc.fasten = fasten;

		wtc.start();
	}




	public void setfirstFocus() {
		gxtf.requestFocus();
		gxtf.selectAll();
	}





}
