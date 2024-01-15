package calendar;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import common.ui.calendar.*;


public class CalendarMonthTest {
	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);

		JFrame frame = new JFrame("CalendarMonth Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER,
                           		new CalendarMonth(Calendar.getInstance(), new DefaultListSelectionModel(),
                                             		new DefaultCalendarRenderer(false), new CalendarGroup()));
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.setVisible(true);
	}
}