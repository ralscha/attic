package common.ui.calendar;


import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public interface CalendarRenderer {
	public Component getCalendarRendererComponent(JComponent parent, Object value,
        	boolean isSelected, boolean hasFocus);
	public Color getBackdrop();
}