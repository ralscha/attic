package ch.sr.applets;
import java.awt.*;


public class WaTorChartComponent extends Component {
	Image img = null;
	Graphics gs = null;
	int max;
	int lastx, lastyS, lastyF;
	float dy;

	public WaTorChartComponent(int max, int fish, int shark) {
		super();
		this.max = max;
		lastx = 0;
		lastyS = shark;
		lastyF = fish;
	}

	public void initWTCC() {
		if ((getSize().width > 0) && (getSize().height > 0)) {
			img = createImage(getSize().width, getSize().height);
			gs = img.getGraphics();
			clearImage();
		}

		dy = (float) getSize().height / (float) max;
		lastyS = getSize().height - (int)(dy * lastyS);
		lastyF = getSize().height - (int)(dy * lastyF);
	}


	public void invalidate() {
		super.invalidate();

		if ((getSize().width > 0) && (getSize().height > 0)) {
			img = createImage(getSize().width, getSize().height);
			gs = img.getGraphics();
			clearImage();
		}
		dy = (float) getSize().height / (float) max;
	}




	public void paint(Graphics g) {
		if (img != null) {
			g.drawImage(img, 0, 0, this);
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

	void clearImage() {
		if (img != null) {
			gs.setColor(Color.lightGray);
			gs.fillRect(0, 0, getSize().width, getSize().height);
			gs.setColor(Color.black);
		}
	}

	void restart(int max, int fish, int shark) {
		clearImage();
		this.max = max;
		lastx = 0;
		lastyS = shark;
		lastyF = fish;
		dy = (float) getSize().height / (float) max;
		lastyS = getSize().height - (int)(dy * lastyS);
		lastyF = getSize().height - (int)(dy * lastyF);
	}

	void drawPoints(int fish, int shark) {
		int newx = ++lastx;
		int h = getSize().height;
		int newyS = h - (int)(dy * shark);
		int newyF = h - (int)(dy * fish);
		if (newyF == h)
			newyF--;
		if (newyS == h)
			newyS--;

		if (newx >= getSize().width) {
			newx = 1;
			lastx = 0;
			clearImage();
		}

		gs.setColor(Color.white);
		gs.drawLine(lastx, lastyS, newx, newyS);

		gs.setColor(Color.blue);
		gs.drawLine(lastx, lastyF, newx, newyF);

		lastx = newx;
		lastyS = newyS;
		lastyF = newyF;
		repaint();
	}

}