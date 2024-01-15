package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class MandelZoomCanvas extends Component implements Runnable, MouseListener,
MouseMotionListener {

	boolean calculating = false;
	Thread runner = null;
	Image fractalImage = null;
	Graphics fractalG;
	int maxIterations;
	int maxColor;
	int gap;
	Color color[];

	private double rmax;
	private double rmin;
	private double imax;
	private double imin;
	private double saveimax;

	double clickX, clickY;

	int maxY;
	private boolean doMirror;
	boolean fill;
	boolean first = true;

	int pat;
	int paintcounter;

	Rectangle currentRect;

	int w;
	int h;
	int pixels[];
	double dx, dy;
	Label s;

	public MandelZoomCanvas(Label state) {
		super();
		s = state;
		loadPalette("standard.txt");
		addMouseListener(this);
		addMouseMotionListener(this);
		setVisible(true);
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
		fractalImage = null;
		fractalG = null;
		runner = null;
		/*
		 if (runner != null)
		 {
		 runner.stop();
		 runner = null;
		 }
		 */
	}

	public void mouseMoved(MouseEvent me) {
		s.setText("x = " + (rmin + me.getX() * dx) + " / y = " +
          		(imin + me.getY() * dy));

	}

	public void mouseExited(MouseEvent me) {
		s.setText("Status");
	}


	public void mousePressed(MouseEvent me) {
		clickX = (rmin + me.getX() * dx);
		clickY = (imin + me.getY() * dy);

		currentRect = new Rectangle(me.getX(), me.getY(), 0, 0);
		repaint();
	}

	public void mouseDragged(MouseEvent me) {
		currentRect.setSize(me.getX() - currentRect.x, me.getY() - currentRect.y);
		repaint();
	}

	public void mouseEntered(MouseEvent me) { }
	public void mouseClicked(MouseEvent me) { }

	public void mouseReleased(MouseEvent me) {
		/*currentRect.resize(x - currentRect.x, y - currentRect.y);
		 repaint();*/

		if (currentRect.width != 0 && currentRect.height != 0) {

			Rectangle box = getDrawableRect(currentRect, getSize());
			currentRect = null;

			stop();

			rmin = rmin + box.x * dx;
			rmax = rmin + (box.width - 1) * dx;
			imin = imin + box.y * dy;
			imax = imin + (box.height - 1) * dy;

			initialize();

			start();
		} else
			currentRect = null;

	}

	public void loadPalette(String file) {
		int r, g, b;
		int ix = 0;

		try {
			URL pal = new URL(MandelApplet.getBase(), file);
			boolean eof = false;
			BufferedReader dis;
			String inputLine;

			//dis = new BufferedReader(new FileReader(file));
			dis = new BufferedReader(new InputStreamReader(pal.openStream()));
			while (!eof && (inputLine = dis.readLine()) != null) {
				try {
					StringTokenizer st = new StringTokenizer(inputLine, " ");
					r = Integer.parseInt(st.nextToken());
					g = Integer.parseInt(st.nextToken());
					b = Integer.parseInt(st.nextToken());


					if (color == null) {
						color = new Color[8];
					} else if (ix == color.length) {
						Color newcolor[] = new Color[ix * 2];
						System.arraycopy(color, 0, newcolor, 0, ix);
						color = newcolor;
					}
					color[ix++] = new Color(r, g, b);
				} catch (NoSuchElementException nee) {
					eof = true;
				}
			}
			ix--;
			maxColor = ix;
			dis.close();
		} catch (MalformedURLException me) {
			System.out.println("MalformedURLException: " + me);
		}
		catch (IOException ioe) {
			System.out.println("IOException: " + ioe);
		}
	}

	synchronized void setPixel(int x, int y, Color c) {
		if (Thread.currentThread() != runner)
			return;

		fractalG.setColor(c);
		fractalG.drawLine(x, y, x, y);

		pixels[y * w + x] =
  		0xff000000 | ((c.getRed() & 0xFF) << 16) | ((c.getGreen() & 0xFF) << 8) |
  		((c.getBlue() & 0xFF) << 0);
	}

	synchronized int getPixelColor(int x, int y) {
		return pixels[y * w + x];
	}


	boolean test(int xx1, int yy1, int xx2, int yy2) {
		int i;
		int c;
		c = getPixelColor(xx1, yy1);
		for (i = xx2; i >= xx1; i = i - gap) {
			if (getPixelColor(i, yy1) != c)
				return false;
			if (getPixelColor(i, yy2) != c)
				return false;
		}
		for (i = yy2 - 1; i > yy1; i = i - gap) {
			if (getPixelColor(xx1, i) != c)
				return false;
			if (getPixelColor(xx2, i) != c)
				return false;
		}
		return true;
	}

	public void update(Graphics g) {
		Dimension d = getSize();

		if ((fractalImage == null) || (d.width != fractalImage.getWidth(null)) ||
    			(d.height != fractalImage.getHeight(null))) {
			stop();
			imax = saveimax;
			fractalImage = createImage(d.width, d.height);
			fractalG = fractalImage.getGraphics();
			start();
		}
		paint(g);
	}

	public void paint(Graphics g) {
		if (fractalImage != null) {
			g.drawImage(fractalImage, 0, 0, this);
		}


		if (currentRect != null) {
			Rectangle box = getDrawableRect(currentRect, getSize());
			g.drawRect(box.x, box.y, box.width - 1, box.height - 1);
		}

	}

	Rectangle getDrawableRect(Rectangle originalRect, Dimension drawingArea) {
		int x = originalRect.x;
		int y = originalRect.y;
		int width = originalRect.width;
		int height = originalRect.height;

		if (width < 0) {
			width = 0 - width;
			x = x - width + 1;
			if (x < 0) {
				width += x;
				x = 0;
			}
		}

		if (height < 0) {
			height = 0 - height;
			y = y - height + 1;
			if (y < 0) {
				height += y;
				y = 0;
			}
		}

		width = height;

		if ((x + width) > drawingArea.width) {
			width = drawingArea.width - x;
			height = width;
		}
		if ((y + height) > drawingArea.height) {
			height = drawingArea.height - y;
			width = height;
		}


		return new Rectangle(x, y, width, height);
	}


	public synchronized double getClickX() {
		return clickX;
	}

	public synchronized double getClickY() {
		return clickY;
	}


	public void run() {
		calculating = true;
		doMirror = false;

		int centerRow = h / 2;
		if ((h % 2) == 1)
			centerRow++;

		if (imin + imax != 0) {
			saveimax = imax;
			maxY = h - 1;
		} else {
			maxY = centerRow;
			saveimax = imax;
			imax = 0;
			doMirror = true;
		}

		first(0, 0, w - 1, maxY);
		Apfel(0, 0, w - 1, maxY);
		repaint();

		calculating = false;
		imax = saveimax;
	}

	int Iter(double r, double i) {
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
			return maxColor;
		} else {
			return count % maxColor;
		}
	}


	void first(int x1, int y1, int x2, int y2) {
		double help;
		help = rmin;
		Color c1, c2;
		double ti = imin + maxY * dy;
		for (int x = x1; x <= x2; x++) {
			c1 = color[Iter(help, imin)];
			setPixel(x, 0, c1);
			if (doMirror)
				setPixel(x, h - 1, c1);
			setPixel(x, y2, color[Iter(help, ti)]);
			help += dx;
		}
		help = imin;
		for (int y = y1; y <= y2; y++) {
			c1 = color[Iter(rmin, help)];
			c2 = color[Iter(rmax, help)];
			setPixel(0, y, c1);
			setPixel(x2, y, c2);

			if (doMirror) {
				setPixel(0, (h - 1) - y, c1);
				setPixel(x2, (h - 1) - y, c2);
			}

			help += dy;
		}
		repaint();
	}


	void Apfel(int x1, int y1, int x2, int y2) {
		int xneu, yneu;
		int i;
		double help1, help2;

		if (x2 - x1 > 1 && y2 - y1 > 1) {
			if (test(x1, y1, x2, y2)) {
				if (fill) {
					fractalG.setColor(new Color(getPixelColor(x1, y1)));
					fractalG.fillRect(x1, y1, x2 - x1, y2 - y1);
					if (doMirror && (maxY + (maxY - y2) <= h - 1)) {
						int ty1 = (maxY + (maxY - y1)) -
          						((maxY + (maxY - y1)) - (maxY + (maxY - y2)));
						int ty2 = (maxY + (maxY - y1)) - (maxY + (maxY - y2));
						fractalG.fillRect(x1, ty1 , x2 - x1, ty2);
					}
				}

				paintcounter++;
				if ((paintcounter % pat == 0) && (Thread.currentThread() == runner)) {
					repaint();
					paintcounter = 0;
				}
			} else {
				if (x2 - x1 > y2 - y1) {
					xneu = (x1 + x2) >> 1;
					help1 = rmin + xneu * dx;
					help2 = (y1 + 1) * dy + imin;
					for (i = y1 + 1; i < y2; i++) {
						Color c = color[Iter(help1, help2)];
						setPixel(xneu, i, c);
						if (doMirror && (maxY + (maxY - i) <= h - 1))
							setPixel(xneu, maxY + (maxY - i), c);
						help2 += dy;
					}
					if (Thread.currentThread() != runner)
						return;
					Apfel(x1, y1, xneu, y2);
					if (Thread.currentThread() != runner)
						return;
					Apfel(xneu, y1, x2, y2);
					if (Thread.currentThread() != runner)
						return;
				} else {
					yneu = (y1 + y2) >> 1;
					help1 = imin + yneu * dy;
					help2 = (x1 + 1) * dx + rmin;
					for (i = x1 + 1; i < x2; i++) {
						Color c = color[Iter(help2, help1)];
						setPixel(i, yneu, c);
						if (doMirror && (maxY + (maxY - yneu) <= h - 1))
							setPixel(i, maxY + (maxY - yneu), c);
						help2 += dx;
					}
					if (Thread.currentThread() != runner)
						return;
					Apfel(x1, y1, x2, yneu);
					if (Thread.currentThread() != runner)
						return;
					Apfel(x1, yneu, x2, y2);
					if (Thread.currentThread() != runner)
						return;
				}
			}
		}
	}

	public double getrmax(double rmi, double imi, double ima) {
		return rmi + ((Math.abs(imi) + Math.abs(ima)) * getSize().width /
              		getSize().height);
	}

	void initialize() {
		if (first) {
			// Default
			pat = 10;
			gap = 1;
			maxIterations = 128;
			fill = true;
			imax = 1.4;
			imin = -1.4;
			/*rmax = 0.7;*/
			rmin = -2.1;
			rmax = rmin + ((Math.abs(imin) + Math.abs(imax)) * getSize().width /
               			getSize().height);
			first = false;
		}
		w = getSize().width;
		h = getSize().height;

		pixels = new int[w * h];

		dx = (rmax - rmin) / w;
		dy = (imax - imin) / h;

		fractalImage = createImage(w, h);
		fractalG = fractalImage.getGraphics();

	}
}