package ch.sr.applets;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class WrapComponent extends Component {
	Image kImg = null;
	Graphics kG = null;
	java.util.List points = Collections.synchronizedList(new ArrayList());


	public WrapComponent() {
		super();
		addMouseListener(new WrapMouseListener());
	}

    public void invalidate() {
        super.invalidate();
        kImg = null;
        createOffscreenImage();
    }

	public void clear() {

		points.clear();
		kG.setColor(getBackground());
		kG.fillRect(0, 0, getSize().width, getSize().height);
		kG.setColor(Color.black);
		kG.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
		repaint();
	}

	public void paint(Graphics g) {		
		if (kImg != null) {
			g.drawImage(kImg, 0, 0, this);
		} else {
			createOffscreenImage();
			clear();
		}

	}
	
	class WrapMouseListener extends MouseAdapter {
		public void mousePressed(MouseEvent me) {
			Point p = me.getPoint();
			kG.setColor(Color.black);
			kG.fillOval(p.x-2,p.y-2,4,4);
			
			points.add(p);
			
			repaint();
		}
	}

	public void doWrap() {
		if (points.size() == 0)
			return;

		int i = wrap(points);

		for (int j = 0; j < i; j++)
			kG.drawLine(((Point) points.get(j)).x, ((Point) points.get(j)).y,
            			((Point) points.get(j + 1)).x, ((Point) points.get(j + 1)).y);

		kG.drawLine(((Point) points.get(i)).x, ((Point) points.get(i)).y,
            		((Point) points.get(0)).x, ((Point) points.get(0)).y);
		repaint();
	}

	int wrap(java.util.List p) {
		int i, min, M, N;
		Object help;
		float th, v;
		N = p.size();

		for (min = 0, i = 1; i < N; i++)
			if (((Point) p.get(i)).y < ((Point) p.get(min)).y)
				min = i;

		p.add(p.get(min));
		th = (float) 0.0;

		for (M = 0; M < N; M++) {
			help = p.get(M);
			p.set(M, p.get(min));
			p.set(min,help);

			min = N;
			v = th;
			th = (float) 360.0;

			for (i = M + 1; i <= N; i++)
				if (theta(p.get(M), p.get(i)) > v)
					if (theta(p.get(M), p.get(i)) < th) {
						min = i;
						th = theta(p.get(M), p.get(min));
					}
			if (min == N)
				return M;
		}
		return M;
	}

	private float theta(Object o1, Object o2) {
		Point p1 = (Point) o1;
		Point p2 = (Point) o2;
		int dx, dy, ax, ay;
		float t;
		dx = p2.x - p1.x;
		ax = Math.abs(dx);
		dy = p2.y - p1.y;
		ay = Math.abs(dy);
		t = (ax + ay == 0) ? 0 : (float) dy / (ax + ay);
		if (dx < 0)
			t = 2 - t;
		else if (dy < 0)
			t = 4 + t;

		return t * (float) 90.0;
	}

	private void createOffscreenImage() {
		if (getSize().width > 0) {
			kImg = createImage(getSize().width, getSize().height);
			kG   = kImg.getGraphics();
		}
	}
}