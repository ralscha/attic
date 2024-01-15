
package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Wrap extends JApplet {

	WrapComponent kc;
	JButton wrap;
	JButton clear;

	public void init() {
		super.init();
		getContentPane().setLayout(new BorderLayout(5, 5));
		kc = new WrapComponent();
		getContentPane().add(kc, BorderLayout.CENTER);

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		wrap = new JButton("Wrap");
		clear = new JButton("Clear");

		wrap.addActionListener(new ActionListener() {
                       			public void actionPerformed(ActionEvent ae) {
                       				kc.doWrap();
                       			}
                       		}
                      		);
		clear.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent ae) {
                        				kc.clear();
                        			}
                        		}
                       		);


		p.add(wrap);
		p.add(clear);

		getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	public void start() {
		kc.clear();
	}

	public String getAppletInfo() {
		return "Wrap V1.1 by Ralph Schaer";
	}

	public String[][] getParameterInfo() {
		String[][] info = {{}};
		return info;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Wrap");
		frame.setDefaultCloseOperation(3);

		Wrap w = new Wrap();
		w.init();
		frame.add(w, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setVisible(true);
		w.start();
	}



}