package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;


public class Y2001 extends JApplet implements Runnable {

	private final static String strt = "Noch";
	private final static String str2 = "bis zum 1.1.2001";
	private final static Font f = new Font("TimesRoman", Font.PLAIN, 25);

	Thread runner;
	private FontMetrics fm = null;
	private String str1 = null;

	public void init() {
		super.init();
		fm = getFontMetrics(f);
		addMouseListener(new MyMouseAdapter());
	}

	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.start();
		}
	}

	public void stop() {
		runner = null;
	}

	public void run() {
		long startTime = System.currentTimeMillis();

		while (Thread.currentThread() == runner) {

			StringBuffer sb = new StringBuffer();
			JulianCalendar today = new JulianCalendar();
			JulianCalendar y2 = new JulianCalendar(2001, Calendar.JANUARY, 1);
			long days = y2.getJulianDay() - today.getJulianDay();

			int h = 23 - today.get(Calendar.HOUR_OF_DAY);
			int m = 59 - today.get(Calendar.MINUTE);
			int s = 59 - today.get(Calendar.SECOND);

			if (days > 0)
				sb.append(days + " Tage ");


			if (h > 0) {
				if (h < 10)
					sb.append("0");
				sb.append(h + " Stunden ");
			}

			if (m > 0) {
				if (m < 10)
					sb.append("0");
				sb.append(m + " Minuten ");
			}

			if (s < 10)
				sb.append("0");
			sb.append(s + " Sekunden");
			str1 = sb.toString();

			repaint();

			try {
				startTime += 1000;
				Thread.sleep(Math.max(0, startTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void paint(Graphics g) {
		if (str1 == null)
			return;
		g.setColor(getBackground());

		int width = getSize().width;
		int strWidth = fm.stringWidth(str1);
		int strHeight = fm.getHeight();
		int x = (getSize().width - strWidth) / 2;
		int y = 50;
		g.fillRect(x, y, strWidth, strHeight);

		g.setColor(Color.black);
		g.setFont(f);
		x = (width - fm.stringWidth(strt)) / 2;
		g.drawString(strt, x, y);

		x = (width - strWidth) / 2;
		y += strHeight;
		g.drawString(str1, x, y);

		x = (width - fm.stringWidth(str2)) / 2;
		y += strHeight;
		g.drawString(str2, x, y);
	}


	class MyMouseAdapter extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (runner == null)
				start();
			else
				stop();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Y2001 Clock");
		frame.setDefaultCloseOperation(3);
		Y2001 y2001 = new Y2001();
		frame.getContentPane().add(y2001, BorderLayout.CENTER);
		frame.setSize(560, 180);
		frame.setVisible(true);
		y2001.init();
		y2001.start();
	}

}