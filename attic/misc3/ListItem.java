import java.awt.*;

class ListItem {
	private Color color;
	private String value;

	public ListItem(Color c, String s) {
		color = c;
		value = s;
	}
	public Color getColor() {
		return color;
	}
	public String getValue() {
		return value;
	}
}
