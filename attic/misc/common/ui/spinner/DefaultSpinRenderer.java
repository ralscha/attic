package common.ui.spinner;

import java.awt.*;
import javax.swing.*;
import java.text.*;

public class DefaultSpinRenderer extends JTextField implements SpinRenderer {
	private static Color focusColor = new Color(0, 0, 128);

	public DefaultSpinRenderer() {
		setOpaque(true);
		setEditable(false);
	}

	public Component getSpinCellRendererComponent(JSpinnerField spin, Object value,
        	boolean hasFocus, Format formatter, int selectedFieldID) {
		String text = formatter.format(value);
		setText(text);
		FieldPosition pos = LocaleUtil.getFieldPosition(formatter, value, selectedFieldID);
		// Make non-selections expand to full selections
		if (pos.getBeginIndex() == pos.getEndIndex()) {
			pos.setBeginIndex(0);
			pos.setEndIndex(text.length());
		}
		if (hasFocus)
			select(pos.getBeginIndex(), pos.getEndIndex());
		else
			select(0, 0);
		return this;
	}
}