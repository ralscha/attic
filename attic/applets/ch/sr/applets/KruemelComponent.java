package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class KruemelComponent extends Component implements Runnable {
	Thread runner;

	int zustaende = 15;
	Image newImg;
	int neu[];
	int alt[];
	IndexColorModel cm;

	public KruemelComponent() {
		addMouseListener(new KruemelMouseListener());
	}

	public void start() {
		if (runner == null) {
			runner = new Thread(this);
			runner.setPriority(Thread.MIN_PRIORITY);
			runner.start();
		}
	}

	public void stop() {
		runner = null;
	}

	public void paint(Graphics g) {
		if (newImg != null) {
			g.drawImage(newImg, 0, 0, this);
		}
	}

	public void run() {
		int w = getSize().width;
		int h = getSize().height;

		int x, y, i, j;
		neu = new int[w * h];
		alt = new int[w * h];

		byte red[] = new byte[16];
		byte green[] = new byte[16];
		byte blue[] = new byte[16];
		int aix = w;
		int bix = h;
		int x1, y1;

		for (i = 0; i < 16; i++) {
			red[i] = (byte)(i * 20 % 256);
			green[i] = (byte)(i * 30 % 256);
			blue[i] = (byte)(i * 50 % 256);
		}
		cm = new IndexColorModel(4, 16, red, green, blue);

		int ix;
		for (x = 0; x < w; x++) {
			for (y = 0; y < h; y++) {
				ix = y * w + x;
				neu[ix] = (int)(Math.random() * (zustaende + 1));
				alt[ix] = neu[ix];
			}
		}

		newImg = createImage(new MemoryImageSource(w, h, cm, neu, 0, w));
		repaint();


		while ((Thread.currentThread() == runner)) {
			for (i = 0; i < aix; i++) {
				for (j = 0; j < bix; j++) {
					x = i - 1;
					if (x < 0)
						x = aix - 1;

					y = j - 1;
					if (y < 0)
						y = bix - 1;

					x1 = (i + 1) % aix;
					y1 = (j + 1) % bix;

					int tmp1 = j * w + i;
					int tmpres = (alt[tmp1] + 1) % zustaende;
					if (alt[j * w + x] == tmpres) {
						neu[tmp1] = alt[j * w + x];
					} else if (alt[y * w + i] == tmpres) {
						neu[tmp1] = alt[y * w + i];
					} else if (alt[j * w + x1] == tmpres) {
						neu[tmp1] = alt[j * w + x1];
					} else if (alt[y1 * w + i] == tmpres) {
						neu[tmp1] = alt[y1 * w + i];
					}
				}
			}

			System.arraycopy(neu, 0, alt, 0, w*h);

			newImg = createImage(new MemoryImageSource(w, h, cm, neu, 0, w));
			repaint();
		}

	}

	class KruemelMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			if (runner == null) {
				start();
			} else {
				stop();
			}
		}
	}


}