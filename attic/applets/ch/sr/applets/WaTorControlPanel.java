package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class WaTorControlPanel extends JPanel {
	final WaTorComponent wtc;

	public WaTorControlPanel(WaTorComponent wtcomp) {
		super();
		this.wtc = wtcomp;

		setBackground(Color.lightGray);

		setLayout(new FlowLayout(FlowLayout.LEFT));
		JButton pref, restart;
		add(pref = new JButton("Einstellungen"));
		add(restart = new JButton("Restart"));

		pref.addActionListener(new ActionListener() {
                       			public void actionPerformed(ActionEvent ae) {
                       				WaTorPrefDialog wtpd =
                         				new WaTorPrefDialog(wtc, wtc.gx, wtc.gy, wtc.fbrut,
                                             				wtc.hbrut, wtc.afischinit, wtc.ahaiinit, wtc.fasten);
                       				wtpd.show();
                       				wtpd.setfirstFocus();
                       			}
                       		}
                      		);

		restart.addActionListener(new ActionListener() {
                          			public void actionPerformed(ActionEvent ae) {
                          				wtc.stop();
                          				wtc.start();

                          			}
                          		}
                         		);

	}





}