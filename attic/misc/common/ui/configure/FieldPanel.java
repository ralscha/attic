package common.ui.configure;


import java.util.Vector;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import common.ui.layout.*;

public class FieldPanel extends JPanel {
	protected String path;

	public FieldPanel(String path) {
		this.path = path;
		setLayout(new FieldLayout(4, 4));
		setBorder(new EmptyBorder(4, 4, 4, 4));
	}

	public void addEntry(String prompt, String value) {
		add(new JLabel(prompt));
		add(new JTextField(value));
	}

	public void addEntry(String prompt, Component comp) {
		add(new JLabel(prompt));
		add(comp);
	}

	public String[] getFieldNames() {
		int count = getComponentCount() / 2;
		String[] list = new String[count];
		JLabel label;
		for (int i = 0; i < count; i++) {
			label = (JLabel) getComponent(i * 2);
			list[i] = label.getText();
		}
		return list;
	}

	public String[] getFieldValues() {
		int count = getComponentCount() / 2;
		String[] list = new String[count];
		for (int i = 0; i < count; i++) {
			Component component = getComponent(i * 2 + 1);
			if (component instanceof PropertyField) {
				PropertyField field = (PropertyField) component;
				list[i] = field.getValue();
			} else {
				JTextField field = (JTextField) component;
				list[i] = field.getText();
			}
		}
		return list;
	}
}