package ch.rasc.springplayground.equalsverifier;

public final class Point {
	private final int x;

	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

}
