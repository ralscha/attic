package ch.sr.applets;
import java.awt.*;

public class MandelCanvas extends Component implements Runnable {
	Label s;
	Image fractalImage = null;
	Graphics fractalGraphics = null;
	Thread runner;

	int maxIterations;

	private double rmax;
	private double rmin;
	private double imax;
	private double imin;

	int w, h;
	double dx, dy;

	public MandelCanvas() {
		super();
	}


	public void start() {
		initialize();
		if (runner == null) {
			runner = new Thread(this);
			runner.setPriority(Thread.MIN_PRIORITY);
			runner.start();
		}
	}

	public void stop() {
		//runner.stop();
		runner = null;
		fractalImage = null;
		fractalGraphics = null;
	}

	public void paint(Graphics g) {
		if (fractalImage != null) {
			g.drawImage(fractalImage, 0, 0, this);
		}
	}

	public void update(Graphics g) {

		Dimension d = getSize();

		if ((fractalImage == null) || (d.width != fractalImage.getWidth(null)) ||
    			(d.height != fractalImage.getHeight(null))) {
			stop();
			fractalImage = createImage(d.width, d.height);
			fractalGraphics = fractalImage.getGraphics();
			start();
		}
		paint(g);
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


	public void run() {
		boolean vor = false;
		double p, q;

		int centerRow = h / 2;
		if ((h % 2) == 1)
			centerRow++;
		int lastRow = h;
		int maxRow = centerRow;
		fractalGraphics.setColor(Color.black);

		for (int col = 0; col < w; col++) {
			p = rmin + col * dx;
			for (int row = 0; row < maxRow; row++) {
				q = imax - row * dy;

				if (Iter(p, q)) {
					if (!vor) {
						fractalGraphics.drawLine(col, row, col, row);
						fractalGraphics.drawLine(col, lastRow - row, col, lastRow - row);
						vor = true;
					} else {
						if ((row + 1) < maxRow) {
							q = imax - (row + 1) * dy;
							if (!Iter(p, q)) {
								fractalGraphics.drawLine(col, row, col, row);
								fractalGraphics.drawLine(col, lastRow - row, col,
                         								lastRow - row);
								vor = false;
							}
						}
					}
				} else {
					vor = false;
				}
			}
			vor = false;
			if (col % 10 == 0) {
				repaint();
			}
		}
		repaint();
	}


	void initialize() {

		maxIterations = 64;
		imax = 1.4;
		imin = -1.4;
		rmin = -2.1;
		rmax = rmin + ((Math.abs(imin) + Math.abs(imax)) * getSize().width /
               		getSize().height);

		w = getSize().width;
		h = getSize().height;

		dx = (rmax - rmin) / w;
		dy = (imax - imin) / h;

		fractalImage = createImage(w, h);
		fractalGraphics = fractalImage.getGraphics();

	}


}