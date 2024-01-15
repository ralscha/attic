
import ch.ess.calendar.db.*;
import ch.ess.calendar.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;

public class TestRunner {

	public void checkReminders() {
    try {
			AppointmentsTable appTable = new AppointmentsTable();
			RemindersTable reminderTable = new RemindersTable();
			
			Iterator it = appTable.select();
			while(it.hasNext()) {
				Appointments app = (Appointments)it.next();
				
				if (app.hasReminders()) {
					List reminders = app.getReminders();
				}
			}					
		} catch (Exception sqle) {
      sqle.printStackTrace();
		}
	} 

	
}