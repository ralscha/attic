
package calendar;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import common.ui.calendar.*;

public class JCalendarTest {
	public static void main(String[] args) {
		common.swing.PlafPanel.setNativeLookAndFeel(true);
		JFrame frame = new JFrame("JCalendar Test");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(BorderLayout.CENTER,
                           		new JCalendar(Calendar.getInstance(), 2, 3,
                                         		new DefaultListSelectionModel(), new DefaultCalendarRenderer(true),
                                         		new DefaultCalendarRenderer(false)));
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.show();
	}
}