package common.ui.configure;


import javax.swing.JTextField;

public class PropertyTextField extends JTextField implements PropertyField {
	public PropertyTextField() {
		super();
	}

	public String getValue() {
		return getText();
	}

	public void setValue(String value) {
		setText(value);
	}
}