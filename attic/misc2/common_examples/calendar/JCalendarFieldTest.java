package calendar;


import java.awt.*;
import javax.swing.*;
import common.ui.calendar.*;


public class JCalendarFieldTest {
	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("JCalendarField Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.NORTH, new JCalendarField());

		/*
		frame.getContentPane().add(
			BorderLayout.CENTER, new JCalendar());
		*/
		frame.setBounds(0, 0, 200, 200);
		frame.setDefaultCloseOperation(3);
		frame.show();
	}
}