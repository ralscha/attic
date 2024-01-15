package common.ui.calendar;


import javax.swing.*;

public class CalendarTitle extends JLabel {
	public CalendarTitle(String text) {
		super(text);
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(new ThinBorder(ThinBorder.RAISED));
	}
}