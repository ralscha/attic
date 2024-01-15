package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Mice extends JApplet implements MouseListener, MouseMotionListener {

	MiceComponent mc;
	JButton start, setx, sety, rand;
	JPanel p;
	JLabel tx, ty;
	int sx, sy;
	Dimension d;
	boolean setXMode, setYMode;

	public void init() {
		super.init();
		getContentPane().setLayout(new BorderLayout(5, 5));


		p = new JPanel();

		p.setLayout(new FlowLayout());

		setx = new JButton("Set X");
		setx.addActionListener(new ActionListener() {
                       			public void actionPerformed(ActionEvent ae) {
                       				setXMode = true;
                       				setx.setEnabled(false);
                       			}
                       		}
                      		);

		sety = new JButton("Set Y");
		sety.addActionListener(new ActionListener() {
                       			public void actionPerformed(ActionEvent ae) {

                       				setYMode = true;
                       				sety.setEnabled(false);
                       			}
                       		}
                      		);



		rand = new JButton("Random");
		rand.addActionListener(new ActionListener() {
                       			public void actionPerformed(ActionEvent ae) {
                       				sx = (int)(Math.random() * getSize().height);
                       				sy = (int)(Math.random() * getSize().height);
                       				setLabel();
                       				mc.stop();
                       				mc.start(sx, sy);
                       			}
                       		}
                      		);


		start = new JButton("Start");
		start.addActionListener(new ActionListener() {
                        			public void actionPerformed(ActionEvent ae) {
                        				mc.stop();
                        				mc.start(sx, sy);
                        			}
                        		}
                       		);

		tx = new JLabel("000", SwingConstants.LEFT);
		ty = new JLabel("000", SwingConstants.LEFT);
		p.add(setx);
		p.add(tx);
		p.add(sety);
		p.add(ty);
		p.add(start);
		p.add(rand);

		mc = new MiceComponent();

		getContentPane().add(mc, BorderLayout.CENTER);
		getContentPane().add(p, BorderLayout.SOUTH);

		setXMode = false;
		setYMode = false;

		addMouseListener(this);
		addMouseMotionListener(this);

	}

	public void start() {
		sx = getSize().width / 3;
		sy = getSize().height / 3;
		setLabel();
		mc.start(sx, sy);
	}

	public void mouseClicked(MouseEvent e) {
		if (setXMode) {
			setXMode = false;
			setx.setEnabled(true);
		} else if (setYMode) {
			setYMode = false;
			sety.setEnabled(true);
		}
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		if (setXMode) {
			sx = e.getX();
			tx.setText(String.valueOf(sx));
		} else if (setYMode) {
			d = mc.getSize();
			if (e.getY() <= d.height - 1)
				sy = e.getY();
			else
				sy = d.height - 1;

			ty.setText(String.valueOf(sy));
		}

	}

	public String getAppletInfo() {
		return "Mice V1.1 by Ralph Schaer";
	}

	public String[][] getParameterInfo() {
		String[][] info = {{}};
		return info;
	}

	void setLabel() {
		tx.setText(String.valueOf(sx));
		ty.setText(String.valueOf(sy));
	}

}