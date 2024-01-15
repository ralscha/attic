
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Move extends JApplet implements Runnable, MouseListener {
	String s = null;
	String fontname = null;
	int fontSize;
	Font font;
	FontMetrics fm;
	Thread runner = null;
	int x = 0;
	int y = 0;
	int stringWidth;
	int stringHeight;
	boolean right = false;

	Color textColor;
	Color bgColor;
	int delay;


	public void init() {

		addMouseListener(this);

		String param;


		s = getParameter("text");
		if (s == null)
			s = "Ralph's Applet Page";

		fontname = getParameter("FONT");
		if (fontname == null)
			fontname = "TimesRoman";

		param = getParameter("FONTSIZE");
		if (param != null) {
			try {
				fontSize = Integer.parseInt(param);
			} catch (NumberFormatException nfe) {
				fontSize = 15;
			}
		} else
			fontSize = 15;


		font = new Font(fontname, Font.BOLD, fontSize);
		fm = getFontMetrics(font);
		stringWidth = fm.stringWidth(s);
		stringHeight = fm.getHeight();

		param = getParameter("TEXTCOLOR");
		if (param == null)
			textColor = Color.black;
		else
			textColor = parseColorString(param);

		param = getParameter("BGCOLOR");
		if (param == null)
			bgColor = Color.lightGray;
		else
			bgColor = parseColorString(param);
		setBackground(bgColor);


		param = getParameter("fps");
		int fps = (param != null) ? Integer.parseInt(param) : 10;
		delay = (fps > 0) ? (1000 / fps) : 100;

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
		x = (getSize().width - stringWidth) / 2;
		y = (getSize().height - stringHeight) / 2;

		while (Thread.currentThread() == runner) {
			repaint();

			try {
				startTime += delay;
				Thread.sleep( Math.max(0,
                       				startTime - System.currentTimeMillis()));
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void update(Graphics g) {
		int width = getSize().width;
		getWidth();
		int height = getSize().height;

		// Erase the previous image.
		g.setColor(bgColor);
		g.fillRect(0, 0, width, height);
		g.setColor(textColor);

		g.setFont(font);

		if (right) {
			x++;
			if (x + stringWidth >= width - 1)
				right = false;
		} else {
			x--;
			if (x <= 1)
				right = true;
		}

		g.drawString(s, x, y);

	}

	private Color parseColorString(String colorString) {
		if (colorString.length() == 6) {
			int R = Integer.valueOf(colorString.substring(0, 2),
                        			16).intValue();
			int G = Integer.valueOf(colorString.substring(2, 4),
                        			16).intValue();
			int B = Integer.valueOf(colorString.substring(4, 6),
                        			16).intValue();
			return new Color(R, G, B);
		} else
			return Color.lightGray;
	}

	public void mouseReleased(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	
	public void mouseClicked(MouseEvent e) {
		if (runner == null) {
			start();
		} else {
			stop();
		}
	}
}