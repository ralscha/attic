package common.ui.configure;


import java.awt.GridLayout;
import java.util.StringTokenizer;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.*;

public class PropertyRectangleField extends JPanel implements PropertyField {
	JTextField left, top, width, height;

	public PropertyRectangleField() {
		setBorder(new EtchedBorder());
		setLayout(new GridLayout(2, 4));
		add(new JLabel("Left: ", JLabel.RIGHT));
		add(left = new JTextField(3));
		add(new JLabel("Top: ", JLabel.RIGHT));
		add(top = new JTextField(3));
		add(new JLabel("Width: ", JLabel.RIGHT));
		add(width = new JTextField(3));
		add(new JLabel("Height: ", JLabel.RIGHT));
		add(height = new JTextField(3));
	}

	public String getValue() {
		return left.getText() + "," + top.getText() + "," + width.getText() + "," +
       		height.getText();
	}

	public void setValue(String value) {
		StringTokenizer tokenizer = new StringTokenizer(value, ",", false);
		left.setText(tokenizer.nextToken());
		top.setText(tokenizer.nextToken());
		width.setText(tokenizer.nextToken());
		height.setText(tokenizer.nextToken());
	}
}