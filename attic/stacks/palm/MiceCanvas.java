import com.sun.kjava.*;

public class MiceCanvas extends Thread {

	Point currentPos[] = new Point[8];
	Point newPos[] = new Point[8];
	int sx, sy;
	Graphics g = Graphics.getGraphics();


	int width = 160;
	int height = 140;

	public MiceCanvas(int sx, int sy) {
		this.sx = sx;
		this.sy = sy;
	}

	public void run() {

		currentPos[0] = new Point(sx, 0);
		currentPos[4] = new Point(width - sx, 0);
		currentPos[3] = new Point(width - 1, sy);
		currentPos[5] = new Point(width - 1, height - sy);
		currentPos[2] = new Point(width - sx, height - 1);
		currentPos[6] = new Point(sx, height - 1);
		currentPos[1] = new Point(0, height - sy);
		currentPos[7] = new Point(0, sy);

		for (int i = 0; i < 8; i++)
			newPos[i] = new Point(0, 0);

		int k = 0;

		while (k < 90) {
			int n = 7;
			int mg;
			
			g.drawLine(currentPos[n].x, currentPos[n].y, currentPos[0].x, currentPos[0].y, Graphics.PLAIN);
			for (int m = 1; m <= n; m++) {
				g.drawLine(currentPos[m - 1].x, currentPos[m - 1].y, currentPos[m].x,
           				currentPos[m].y, Graphics.PLAIN);
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
			
			k++;
			
		}
	}





}


