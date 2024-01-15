package ch.sr.applets;
import java.awt.*;

public class MiceComponent extends Component implements Runnable {
	Image kImg = null;
	Graphics kG = null;
	Point currentPos[] = new Point[8];
	Point newPos[] = new Point[8];
	Thread runner;
	int sx, sy;


	public void stop() {
		runner = null;
	}


	public void start(int sx, int sy) {
		this.sx = sx;
		this.sy = sy;

		runner = new Thread(this);


		currentPos[0] = new Point(sx, 0);
		currentPos[4] = new Point(getSize().width - sx, 0);
		currentPos[3] = new Point(getSize().width - 1, sy);
		currentPos[5] = new Point(getSize().width - 1, getSize().height - sy);
		currentPos[2] = new Point(getSize().width - sx, getSize().height - 1);
		currentPos[6] = new Point(sx, getSize().height - 1);
		currentPos[1] = new Point(0, getSize().height - sy);
		currentPos[7] = new Point(0, sy);

		for (int i = 0; i < 8; i++)
			newPos[i] = new Point(0, 0);


		if (kImg != null)
			clear();
		else
			createOffscreenImage();

		runner.start();
	}



	public void invalidate() {
		super.invalidate();
		kImg = null;
		createOffscreenImage();
	}

	public void clear() {
		kG.setColor(getBackground());
		kG.fillRect(0, 0, getSize().width, getSize().height);
		kG.setColor(Color.black);
		kG.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
		repaint();
	}

	public void run() {
		int k = 0;
		kG.setColor(Color.black);

		while ((Thread.currentThread() == runner) && (k < 90)) {
			
			int n = 7;
			int mg;

			kG.drawLine(currentPos[n].x, currentPos[n].y, currentPos[0].x, currentPos[0].y);
			for (int m = 1; m <= n; m++) {
				kG.drawLine(currentPos[m - 1].x, currentPos[m - 1].y, currentPos[m].x,
            				currentPos[m].y);
			}
			for (int m = 0; m <= n; m++) {
				if (m == n)
					mg = 0;
				else
					mg = m + 1;

				newPos[m].x = currentPos[m].x + ((currentPos[mg].x - currentPos[m].x) / 20);
				newPos[m].y = currentPos[m].y + ((currentPos[mg].y - currentPos[m].y) / 20);

			}
			for (int m = 0; m <= n; m++) {
				currentPos[m].x = newPos[m].x;
				currentPos[m].y = newPos[m].y;
			}
			repaint();
			k++;
		}
	}


	public void paint(Graphics g) {
		if (kImg != null) {
			g.drawImage(kImg, 0, 0, this);
		}
	}



	private void createOffscreenImage() {
		if (getSize().width > 0) {
			kImg = createImage(getSize().width, getSize().height);
			kG = kImg.getGraphics();
		}
	}
}