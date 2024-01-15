package ch.rasc.todo.dto;

public class Filter {
	private String property;

	private String value;

	public String getProperty() {
		return this.property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Filter [property=" + this.property + ", value=" + this.value + "]";
	}

}
