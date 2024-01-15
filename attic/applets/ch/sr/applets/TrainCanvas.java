package ch.sr.applets;
import java.awt.*;
import java.awt.image.*;

public class TrainCanvas extends Canvas implements Runnable {
	Image trainImage = null;
	Image newtrainImage = null;
	Graphics trainGraphics = null;
	Thread runner;
	BlackMandel bm = null;
	boolean newbm = true;
	PixelArray pa;
	int[] clearpa;
	int[] newpa;

	int maxIterations = 128;

	int w, h, pw, ph;
	double dx, dy, sx, sy;
	double xMin, xMax, yMin, yMax;

	public TrainCanvas(BlackMandel bm) {
		pw = 0;
		ph = 0;
		this.bm = bm;
	}


	public void start(double x, double y, double xMin, double xMax, double yMin, double yMax) {
		sx = x;
		sy = y;
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;

		initialize();
		if (runner == null) {
			runner = new Thread(this);
			runner.setPriority(Thread.MIN_PRIORITY);
			runner.start();
		}
	}

	public void stop() {
		runner = null;
		trainImage = null;
		trainGraphics = null;
	}

	public void paint(Graphics g) {
		if (newtrainImage != null) {
			g.drawImage(newtrainImage, 0, 0, null);
		}
	}

	public void update(Graphics g) {
		if (trainImage != null) {

			PixelGrabber pg = new PixelGrabber(trainImage, 0, 0, w, h, newpa, 0, w);

			try {
				pg.grabPixels();
			} catch (InterruptedException e) {}

			newtrainImage = createImage(pa.overlay(newpa));
		}

		paint(g);
	}



	public void run() {
		int count = 0;
		double x, y, yTemp;
		int prex, prey;
		boolean end = false;
		int dpx, dpy;
		x = sx;
		y = sy;
		prex = (int)((x - xMin) / dx);
		prey = (int)((y - yMin) / dy);

		trainGraphics.setColor(Color.black);
		trainGraphics.drawRect(0, 0, w - 1, h - 1);
		while ((count < maxIterations) && (x * x + y * y < 4.0) && !end &&
    			(Thread.currentThread() == runner)) {

			yTemp = 2 * x * y;
			x = x * x - y * y + sx;
			y = yTemp + sy;

			dpx = (int)((x - xMin) / dx);
			dpy = (int)((y - yMin) / dy);

			if (dpx > w) {
				dpx = w;
				end = true;
			}
			if (dpx < 0) {
				dpx = 0;
				end = true;
			}
			if (dpy > h) {
				dpy = h;
				end = true;
			}
			if (dpy < 0) {
				dpy = 0;
				end = true;
			}

			trainGraphics.drawLine(prex, prey , dpx, dpy);

			prex = dpx;
			prey = dpy;

			count++;
		}

		if (Thread.currentThread() == runner)
			repaint();
	}


	void initialize() {
		w = getSize().width;
		h = getSize().height;

		if (pw != w || ph != h) {
			bm.stop();
			bm.start(w, h);

			pw = w;
			ph = h;
			newpa = new int[w * h];
			pa = bm.getPixelArray();
		}

		dx = (xMax - xMin) / w;
		dy = (yMax - yMin) / h;

		trainImage = createImage(w, h);
		trainGraphics = trainImage.getGraphics();
	}


}