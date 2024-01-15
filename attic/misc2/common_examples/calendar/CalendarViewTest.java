package calendar;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import common.ui.calendar.*;

public class CalendarViewTest {
	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("CalendarView Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER,
                           		new CalendarView(Calendar.getInstance(), true, true,
                                            		new DefaultListSelectionModel(), new DefaultCalendarRenderer(true),
                                            		new DefaultCalendarRenderer(false), new CalendarGroup()));
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.show();
	}
}