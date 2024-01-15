package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class Quassel extends JApplet {

	Vector words[] = new Vector[3];
	Label q;
	JButton shake;

	public String getAppletInfo() {
		return "Quassler by Ralph Schaer";
	}

	public String[][] getParameterInfo() {
		String[][] info = {{"File", "URL", "Datafile"}};
		return info;
	}


	public void init() {
		super.init();

		words[0] = new Vector();
		words[1] = new Vector();
		words[2] = new Vector();

		URL u = null;
		String textfile = getParameter("FILE");
		if (textfile == null)
			textfile = "quassel.txt";

		try {
			u = new URL(getDocumentBase(), textfile);
			BufferedReader dis;
			String inputLine;

			dis = new BufferedReader(new InputStreamReader(u.openStream()));
			int count = 0;
			showStatus("Lade Datenfile");
			while ((inputLine = dis.readLine()) != null) {
				if (inputLine.equals("##")) {
					count++;
					inputLine = dis.readLine();
				}
				words[count].addElement(inputLine);
			}

			dis.close();
			showStatus("Ready");
		} catch (MalformedURLException me) {
			System.out.println("MalformedURLException: " + me);
		}
		catch (IOException ioe) {
			System.out.println("IOException: " + ioe);
		}


		JPanel nPanel = new JPanel();
		nPanel.setFont(new Font("Helvetica", Font.BOLD, 20));
		nPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		nPanel.add(new Label("Der Quassler", Label.CENTER));

		q = new Label((String) words[0].elementAt(0) + " "+(String) words[1].elementAt(0) +
              		(String) words[2].elementAt(0), Label.CENTER);
		q.setFont(new Font("Courier", Font.BOLD, 16));
		JPanel sPanel = new JPanel();
		sPanel.setFont(new Font("Helvetica", Font.PLAIN, 15));
		sPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		shake = new JButton("SHAKE");

		shake.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent ae) {
                        				performShake();
                        			}
                        		}
                       		);

		sPanel.add(shake);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(nPanel, BorderLayout.NORTH);
		getContentPane().add(q, BorderLayout.CENTER);
		getContentPane().add(sPanel, BorderLayout.SOUTH);
	}

	void performShake() {
		int z[] = new int[3];
		Random r = new Random();

		for (int i = 0; i < 3; i++) {
			z[i] = Math.abs(r.nextInt() % words[i].size());
		}
		q.setText((String) words[0].elementAt(z[0]) + " "+
          		(String) words[1].elementAt(z[1]) + (String) words[2].elementAt(z[2]));

	}


}

