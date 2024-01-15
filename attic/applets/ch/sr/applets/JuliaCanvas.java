
package ch.sr.applets;
import java.awt.*;

public class JuliaCanvas extends Component implements Runnable {
	Image fractalImage = null;
	Graphics fractalGraphics = null;
	Thread runner;

	int maxIterations = 64;
	Color color[];

	double p, q;
	double xCenter = 0;
	double yCenter = 0;
	double xSize = 1;
	double ySize = 1;


	public JuliaCanvas() {
		super();
		setVisible(true);
	}


	public void start(double p, double q) {
		this.p = p;
		this.q = q;
		initialize();
		if (runner == null) {
			runner = new Thread(this);
			runner.setPriority(Thread.MIN_PRIORITY);
			runner.start();
		}
	}

	public void stop() {
		runner = null;
		fractalImage = null;
		fractalGraphics = null;
	}

	public void paint(Graphics g) {
		if (fractalImage != null)
			g.drawImage(fractalImage, 0, 0, this);
	}

	public void update(Graphics g) {

		Dimension d = getSize();

		if ((fractalImage == null) || (d.width != fractalImage.getWidth(null)) ||
    			(d.height != fractalImage.getHeight(null))) {
			stop();
			fractalImage = createImage(d.width, d.height);
			fractalGraphics = fractalImage.getGraphics();
			start(p, q);
		}
		paint(g);
	}


	public void run() {
		int w = getSize().width;
		int h = getSize().height;

		double xMin = xCenter - xSize;
		double yMin = yCenter - ySize;
		double xMax = xCenter + xSize;
		double yMax = yCenter + ySize;

		double delta_p = (xMax - xMin) / w;
		double delta_q = (yMax - yMin) / h;

		double yTemp = 0;
		double maxSize = 4;

		int count;
		double x, y;

		int col = 0;
		int row;

		while ((col < w) && (Thread.currentThread() == runner)) {
			row = 0;
			while ((row < h) && (Thread.currentThread() == runner)) {
				count = 0;
				x = xMin + col * delta_p;
				y = yMax - row * delta_q;
				while ((count < maxIterations) && (x * x + y * y < maxSize) &&
    					(Thread.currentThread() == runner)) {
					yTemp = 2 * x * y;
					x = x * x - y * y + p;
					y = yTemp + q;
					count++;
				}
				fractalGraphics.setColor(color[count]);
				fractalGraphics.drawLine(col, row, col, row);
				row++;
			}
			if (col % 5 == 0)
				repaint();

			col++;
		}
		repaint();
	}


	void initialize() {
		fractalImage = createImage(getSize().width, getSize().height);
		fractalGraphics = fractalImage.getGraphics();

		xCenter = 0;
		yCenter = 0;
		ySize = 1.5;
		xSize = ySize * getSize().width / getSize().height;

		int maxColor = 255;
		int uniqueColors = maxIterations / 4;
		Color startColor = new Color((int)(Math.random() * maxColor),
                             		(int)(Math.random() * maxColor), (int)(Math.random() * maxColor));
		double deltaRed;
		double deltaBlue;
		double deltaGreen;
		int distance;

		do {
			deltaRed = (Math.random() * maxColor) - startColor.getRed();
			deltaGreen = ((Math.random() * maxColor)) - startColor.getGreen();
			deltaBlue = ((Math.random() * maxColor)) - startColor.getBlue();

			distance = ((int) Math.sqrt(deltaRed * deltaRed + deltaBlue * deltaBlue +
                            			deltaGreen * deltaGreen));
		} while (distance < 220);

		deltaRed /= uniqueColors;
		deltaGreen /= uniqueColors;
		deltaBlue /= uniqueColors;

		color = new Color[maxIterations + 1];
		for (int i = 0; i < uniqueColors; i++) {
			color[i] = new Color(startColor.getRed() + (int)(deltaRed * i),
                     			startColor.getGreen() + (int)(deltaGreen * i),
                     			startColor.getBlue() + (int)(deltaBlue * i));
			color[maxIterations / 2 - i] = color[i];
			color[maxIterations / 2 + i] = color[i];
			color[maxIterations - i] = color[i];
		}
		color[maxIterations] = Color.black;
	}


}