package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;


public class GaltonComponent extends Component implements Runnable {

	Thread runner;
	private Image offImg;
	private Graphics offG;
	private int bars[] = new int[12];
	private Ball balls[] = new Ball[11];
	private int barheight;
	private int ballrad;
	private int drawBally;
	private float barx;

	private int wx, len, wd, h50, rad;

	private final static int maxBalls = 200;

	public GaltonComponent() {
		addMouseListener(new GaltonMouseListener());
	}


	public void start() {
		createOffscreenImage();
		runner = new Thread(this);
		runner.setPriority(Thread.MIN_PRIORITY);

		initialize();

		if (offImg != null) {
			clearImage();

			drawPlayfield();
			drawBars();
			repaint();
		}

		runner.start();
	}

	public void stop() {
		if (runner != null)
			runner = null;
	}

	public void paint(Graphics g) {
		if (offImg != null) {
			g.drawImage(offImg, 0, 0, this);
		}
	}

	private void createOffscreenImage() {
		if (getSize().width > 0) {
			offImg = createImage(getSize().width, getSize().height);
			offG = offImg.getGraphics();
		}
	}



	public void run() {
		//int w = getSize().width;
		//int h = getSize().height;
		int i;

		for (i = 0; i < 11; i++) {
			balls[i] = new Ball();
		}

		while ((Thread.currentThread() == runner)) {
			for (i = 10; i > 0; i--)
				balls[i] = balls[i - 1];

			balls[0] = new Ball();


			for (i = 10; i >= 0; i--) {
				drawBall(balls[i], balls[i].index[i], i);
			}

			bars[balls[10].index[10]]++;
			removeBall(balls[10]);

			if (bars[balls[10].index[10]] >= maxBalls)
				return;

			drawBars();

			repaint();
		}
	}

	void initialize() {
		int w = getSize().width;
		int h = getSize().height;
		wx = w / 14;
		len = wx * 12;
		wd = (w - len) / 2;
		h50 = h / 2;
		rad = h50 / 24;
		ballrad = rad - 1;

		barheight = (h - 12) - (h - h50);
		drawBally = h - (h50 + rad) - rad;

		barx = (float) barheight / (float) maxBalls;


		for (int i = 0; i < 12; i++)
			bars[i] = 0;
	}

	void drawPlayfield() {
		int wh, hh, wox;
		
		int h = getSize().height;

		offG.drawLine(wd, h - 10, wd + len, h - 10);
		for (int i = 0; i <= 12; i++) {
			wh = wd + (i * wx);
			offG.drawLine(wh, h - h50, wh, h - 10);
		}


		for (int j = 1; j <= 11; j++) {
			if (j % 2 != 0) {
				wh = wd + (((j + 1) / 2) * wx);
				for (int i = 0; i < 12 - j; i++) {

					wox = wh - (rad / 2);
					hh = h - (h50 + rad) - (j * (rad + rad));
					offG.drawOval(wox, hh, rad, rad);
					wh += wx;
				}
			} else {
				wh = wd + ((j / 2) * wx) + wx / 2;
				for (int i = 0; i < 12 - j; i++) {
					wox = wh - (rad / 2);
					hh = h - (h50 + rad) - (j * (rad + rad));
					offG.drawOval(wox, hh, rad, rad);
					wh += wx;
				}

			}

		}

	}

	void removeBall(Ball b) {
		if (b.x != -1) {
			offG.setColor(getBackground());
			offG.fillOval(b.x, b.y, ballrad, ballrad);
		}
	}

	void drawBall(Ball b, int index, int i) {
		int x, y, wh;

		removeBall(b);
		offG.setColor(Color.blue);

		i = 10 - i;
		if (i % 2 != 0) {
			wh = wd + (((i + 1) / 2) * wx) + (index * wx);
			x = wh - (ballrad / 2);
			y = drawBally - (i * (rad + rad));
		} else {
			wh = wd + ((i / 2) * wx) + wx / 2 + (index * wx);
			x = wh - (ballrad / 2);
			y = drawBally - (i * (rad + rad));
		}

		offG.fillOval(x, y, ballrad, ballrad);
		b.x = x;
		b.y = y;
		offG.setColor(Color.black);
		repaint();
	}

	void drawBars() {
		int wh, bh;
		
		int h = getSize().height;

		offG.setColor(Color.blue);
		for (int i = 0; i < 12; i++) {
			wh = wd + (i * wx);
			bh = (int)(barx * bars[i]);
			offG.fillRect(wh + 4, h - h50 + (barheight - bh), wx - 7, bh);
		}
		offG.setColor(Color.black);

	}

	public void clearImage() {
		if (offImg != null) {
			offG.setColor(getBackground());
			offG.fillRect(0, 0, getSize().width, getSize().height);
			offG.setColor(Color.black);
			repaint();
		}
	}

	class GaltonMouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			if (runner == null) {
				start();
			} else {
				stop();
			}
		}
	}


}