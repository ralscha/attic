

import com.klg.jclass.util.calendar.*;
import com.klg.jclass.util.swing.*;
import com.ibm.mask.idate.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class DateChooser extends JPanel{

protected JCDateChooser dateChooser;


public DateChooser() {
	// Create a row to contain the type control and datechooser
	//setLayout(new JCRowLayout());
	
	//
	// Create the DateChooser - marking weekends and the current date
	// as "SpecialDates"; also limit date choices between 1950 and 2050
	//

	// create minimum date: today's date but in 1950
	Calendar min = Calendar.getInstance();
	min.set(min.YEAR, 1998);
	// create maximum date: today's date but in 1950
	Calendar max = Calendar.getInstance();
	max.set(max.YEAR, 2050);

	JCCalendar special_dates = new JCCalendar();
	// Sundays
	special_dates.addSpecialDate(new JCCalendar.DayOfWeek(0));
	// Saturdays
	special_dates.addSpecialDate(new JCCalendar.DayOfWeek(6));
	// Today
	special_dates.addSpecialDate(new JCCalendar.DateMonthYear(Calendar.getInstance()));

	dateChooser = new JCDateChooser();
	dateChooser.setSpecialDates(special_dates);
	dateChooser.setMaximumDate(max);
	dateChooser.setMinimumDate(min);
	dateChooser.setChooserType(JCDateChooser.QUICK_SELECT);
	
	//add(dateChooser);	
	
	
	ActionListener al = new ActionListener() {
								public void actionPerformed(ActionEvent ae) {
									Calendar cal = dateChooser.getValue();
									System.out.println(cal.get(Calendar.DAY_OF_MONTH));
									System.out.println(cal.get(Calendar.MONTH)+1);
									System.out.println(cal.get(Calendar.YEAR));
								}
							};
	dateChooser.addActionListener(al);
	dateChooser.getYearComponent().addActionListener(al);
	dateChooser.getMonthComponent().addActionListener(al);

	
	
	IDate id = new IDate("dd.mm.yyyy");
	add(id);
}

public static void main(String args[]) {
	JFrame frame = new JCExitFrame("DateChooser");
	JPanel app = new DateChooser();
	frame.getContentPane().add(app);
	frame.pack();
	frame.show();
}

}