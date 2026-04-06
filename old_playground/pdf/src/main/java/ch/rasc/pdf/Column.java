package ch.rasc.pdf;

public class Column {

	private String name;
	private float width;

	public Column(String name, float width) {
		this.name = name;
		this.width = width;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWidth() {
		return this.width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
}