package wampclient;

public class Location {
	private int x;
	private int y;

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Location [x=" + this.x + ", y=" + this.y + "]";
	}

}
