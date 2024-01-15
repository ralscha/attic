package ch.sr.applets;
import java.awt.*;

public class BlackMandel implements Runnable {
	Thread runner;

	int maxIterations;

	private double rmax;
	private double rmin;
	private double imax;
	private double imin;

	int w, h;
	double dx, dy;
	PixelArray pa;

	public BlackMandel() {
		super();
	}


	public void start(int width, int height) {
		w = width;
		h = height;
		pa = new PixelArray(w, h);

		initialize();
		if (runner == null) {
			runner = new Thread(this);
			runner.setPriority(Thread.MIN_PRIORITY);
			runner.start();
		}
	}

	public void stop() {
		runner = null;
		/*
		 if (runner != null)
		 {
		     runner.stop();
		     runner = null;
		 }
		 */
		pa = null;
	}


	boolean Iter(double r, double i) {
		double maxSize = 4;
		int count = 0;
		double x, p, q;
		p = r;
		q = i;

		while (r * r + i * i < maxSize && count < maxIterations) {
			x = r;
			r = (x + i) * (x - i) + p;
			i = 2.0 * x * i + q;
			count++;
		}

		if (count >= maxIterations) {
			return true;
		} else {
			return false;
		}
	}

	public synchronized PixelArray getPixelArray() {
		return pa;
	}

	public void run() {

		boolean vor = false;
		double p, q;

		int centerRow = h / 2;
		if ((h % 2) == 1)
			centerRow++;
		int lastRow = h;
		int maxRow = centerRow;

		int col = 0;

		while ((col < w) && (Thread.currentThread() == runner)) {
			p = rmin + col * dx;
			for (int row = 0; row < maxRow; row++) {
				q = imax -  row * dy;

				if (Iter(p, q)) {
					if (!vor) {
						pa.setPixel(col, row, Color.black);
						pa.setPixel(col, lastRow - row, Color.black);
						vor = true;
					} else {
						if ((row + 1) < maxRow) {
							q = imax - (row + 1) * dy;
							if (!Iter(p, q)) {
								pa.setPixel(col, row, Color.black);
								pa.setPixel(col, lastRow - row, Color.black);
								vor = false;
							}
						}
					}
				} else {
					vor = false;
				}
			}
			vor = false;
			col++;
		}


	}


	void initialize() {

		maxIterations = 64;
		imax = 1.4;
		imin = -1.4;
		rmin = -2.1;
		rmax = rmin + ((Math.abs(imin) + Math.abs(imax)) * w / h);

		dx = (rmax - rmin) / w;
		dy = (imax - imin) / h;

	}


}