package common.ui.spinner;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.*;

public class ColorSpinRenderer extends JTextField implements SpinRenderer {
	private static Color focusColor = Color.white;
	private static Border focus = new LineBorder(focusColor, 1);

	public ColorSpinRenderer() {
		setOpaque(true);
		setEditable(false);
	}

	public Component getSpinCellRendererComponent(JSpinnerField spin, Object value,
        	boolean hasFocus, Format formatter, int selectedFieldID) {
		if (value instanceof Color) {
			Color color = (Color) value;
			setBackground(color);
			if (hasFocus)
				setBorder(focus);
			else
				setBorder(null);
		}
		return this;
	}
}