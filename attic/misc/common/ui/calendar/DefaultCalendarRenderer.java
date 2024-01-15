package common.ui.calendar;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

public class DefaultCalendarRenderer extends JLabel implements CalendarRenderer {
	private boolean isHeader = false;

	private static final Border raised = new ThinBorder(ThinBorder.RAISED);
	private static final Border lowered = new ThinBorder(ThinBorder.LOWERED);

	public DefaultCalendarRenderer(boolean isHeader) {
		setOpaque(true);
		setBorder(raised);
		setVerticalAlignment(JLabel.TOP);
		setHorizontalAlignment(isHeader ? JLabel.CENTER : JLabel.LEFT);
		setPreferredSize(new Dimension(19, 18));
		setMinimumSize(new Dimension(19, 18));
		this.isHeader = isHeader;
	}

	public Color getBackdrop() {
		return Color.lightGray;
	}

	public Component getCalendarRendererComponent(JComponent parent, Object value,
        	boolean isSelected, boolean hasFocus) {
		//this.hasFocus = hasFocus;
		//this.isSelected = isSelected;
		setText(value.toString());
		if (isSelected) {
			setBorder(lowered);
			setBackground(hasFocus ? Color.blue : Color.lightGray);
			setForeground(hasFocus ? Color.white : Color.black);
		} else {
			setBorder(raised);
			setBackground(isHeader ? Color.gray : Color.lightGray);
			setForeground(Color.black);
		}
		return this;
	}
}