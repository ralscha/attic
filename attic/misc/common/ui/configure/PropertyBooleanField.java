package common.ui.configure;


import javax.swing.JComboBox;

public class PropertyBooleanField extends JComboBox implements PropertyField {
	private static String[] list = {"true", "false"};

	public PropertyBooleanField() {
		super(list);
	}

	public String getValue() {
		return (String) getSelectedItem();
	}

	public void setValue(String value) {
		setSelectedItem(value);
	}
}