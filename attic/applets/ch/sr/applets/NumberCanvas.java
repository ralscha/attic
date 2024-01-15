package ch.sr.applets;
import java.awt.*;

public class NumberCanvas extends Component {

	Font f, font;
	String s;
	Dimension old = new Dimension();
	FontMetrics fontMetrics;
	boolean textFits;
	int x, sw;

	public NumberCanvas(String s) {
		this.s = s;
	}

	public void change(String s) {
		this.s = s;
		repaint();
	}


	public void paint(Graphics g) {
		Dimension d = getSize();
		if ((d.width != old.width) || (d.height != old.height)) {
			old.width = d.width;
			old.height = d. height;

			textFits = false;
			f = new Font("Helvetica", Font.PLAIN, d.height);
			FontMetrics fontMetrics = g.getFontMetrics(f);
			while (!textFits) {
				if ((fontMetrics.getHeight() < d.height) &&
    					(fontMetrics.stringWidth("00") < d.width - 1)) {
					textFits = true;
				} else {
					f = new Font("Helvetica", Font.PLAIN, f.getSize() - 1);
					fontMetrics = g.getFontMetrics(f);
				}
			}
			setFont(f);
		}

		g.setColor(getBackground());
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.black);

		g.drawString(s, 1, getSize().height);
	}
}