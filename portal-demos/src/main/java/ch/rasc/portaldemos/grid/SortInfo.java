package ch.rasc.portaldemos.grid;

public class SortInfo {
	private String property;

	private SortDirection direction;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public SortDirection getDirection() {
		return direction;
	}

	public void setDirection(SortDirection direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "SortInfo [property=" + property + ", direction=" + direction + "]";
	}

}
