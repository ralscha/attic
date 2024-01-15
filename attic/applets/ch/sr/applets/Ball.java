package ch.sr.applets;
public class Ball {
	int x, y;
	int index[] = new int[11];
	int ix;

	public Ball() {
		x = -1;
		y = -1;

		ix = 0;
		for (int i = 0; i < 11; i++) {
			if (Math.random() > 0.5)
				ix++;

			index[i] = ix;
		}

	}
}