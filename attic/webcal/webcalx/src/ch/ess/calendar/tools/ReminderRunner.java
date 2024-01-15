
package ch.ess.calendar.tools;

import ch.ess.calendar.util.MailSender;
import ch.ess.calendar.db.*;
import ch.ess.calendar.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;
import com.holub.asynch.*;

public class ReminderRunner {

	private int waitTimeInSeconds;
	private String smtp;
	private String sender;
	private Alarm clock;

	public ReminderRunner(String smtp, String sender, int waittime) {

		this.waitTimeInSeconds = waittime;
    this.smtp = smtp;
    this.sender = sender;
		
		if ( (smtp != null) && (!smtp.trim().equals("")) 
		      && (sender != null) && (!sender.trim().equals("")) ) {

			clock = new Alarm(waitTimeInSeconds * 1000);
      clock.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent ae) {
            checkReminders();
          }
        });			
      clock.start();		
		} else {
		  clock = null;
		}
	}

  
	public void checkReminders() {
    try {
			AppointmentsTable appTable = new AppointmentsTable();
			RemindersTable reminderTable = new RemindersTable();
			
			Iterator it = appTable.select();
			while(it.hasNext()) {
				Appointments app = (Appointments)it.next();
				
				if (app.hasReminders()) {
					List reminders = app.getReminders();
					
					Calendar today = new GregorianCalendar();
					
					Repeaters repeater = null;
					if (app.hasRepeaters()) {						
						List repeaters = app.getRepeaters();
						repeater = (Repeaters)repeaters.get(0);
					}
					
					if (repeater != null) {						
						for (int j = 0; j < reminders.size(); j++) {
							Reminders reminder = (Reminders)reminders.get(j);
							
							int min = reminder.getMinutesbefore();																								
								
							Calendar tmpCal = (Calendar)today.clone();	
							tmpCal.add(Calendar.MINUTE, min);
							
							if (app.inRange(tmpCal)) {
								Calendar startCal = new GregorianCalendar();
								startCal.setTime(app.getStartdate());
								
								tmpCal.set(Calendar.HOUR_OF_DAY, startCal.get(Calendar.HOUR_OF_DAY));
								tmpCal.set(Calendar.MINUTE, startCal.get(Calendar.MINUTE));
								
								tmpCal.add(Calendar.MINUTE, -min);
								
								if ( (tmpCal.get(Calendar.DATE) == today.get(Calendar.DATE)) &&
									   (tmpCal.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
										(tmpCal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) &&
										(tmpCal.get(Calendar.HOUR_OF_DAY) == today.get(Calendar.HOUR_OF_DAY)) &&
										(tmpCal.get(Calendar.MINUTE) == today.get(Calendar.MINUTE)) ) {
									
									tmpCal.set(Calendar.HOUR_OF_DAY, startCal.get(Calendar.HOUR_OF_DAY));
									tmpCal.set(Calendar.MINUTE, startCal.get(Calendar.MINUTE));
									
									sendEmail(app, reminder, tmpCal);
									
								}										
							}																	
						}
					} else {
						for (int j = 0; j < reminders.size(); j++) {
							Reminders reminder = (Reminders)reminders.get(j);
							
							int min = reminder.getMinutesbefore();																						
														
							Calendar startCal = new GregorianCalendar();
							startCal.setTime(app.getStartdate());
							
							startCal.add(Calendar.MINUTE, -min);
							
							if (today.after(startCal) || today.equals(startCal)) {
								sendEmail(app, reminder, null);
								reminderTable.delete(reminder);
							}
														
						}											
					}					
				}
			}
		} catch (Exception sqle) {
      sqle.printStackTrace();
      if (sqle instanceof SQLException) {
        SQLException s = (SQLException)sqle;
        System.err.println(s.getNextException());
      }
			System.err.println(sqle);
		}
	} 

	public void stop() {
    if (clock != null)
      clock.stop();
	} 

	private void sendEmail(Appointments app, Reminders reminder, Calendar repeatCal) {		
		String body = app.getBody();
		
		StringBuffer msg = new StringBuffer();
		msg.append("Reminder: ").append(app.getSubject()).append("\n");
		msg.append("\n");

		if (repeatCal == null) {		
			if (app.isAlldayevent()) {
				if (!app.getFormatedStartDate().equals(app.getFormatedEndDate())) {
					msg.append(app.getFormatedStartDate()).append(" - ");
					msg.append(app.getFormatedEndDate()).append("\n");
				} else {
					msg.append(app.getFormatedStartDate()).append("\n");
				}
			} else {
				msg.append(app.getFormatedStartDate()).append(" ");
				msg.append(app.getFormatedStartTime()).append(" - ");
				msg.append(app.getFormatedEndTime()).append("\n");
			}
		} else {
			if (app.isAlldayevent()) {
				msg.append(ch.ess.calendar.util.Constants.dateFormat.format(repeatCal.getTime()));
				msg.append("\n");
			} else {			
				msg.append(ch.ess.calendar.util.Constants.dateFormat.format(repeatCal.getTime()));
				msg.append(" ");
				msg.append(app.getFormatedStartTime()).append(" - ");
				msg.append(app.getFormatedEndTime()).append("\n");		
			}
		}
		msg.append("\n");
		
		msg.append(getCategoryDescription(app.getCategoryid()));
		msg.append("\n");
		
		if (app.getBody() != null) {
			msg.append(app.getBody());
			msg.append("\n\n");
		} 
		
		if (app.hasRepeaters()) {
			List repeaters = app.getRepeaters();
			for (int i = 0; i < repeaters.size(); i++) {
				Repeaters repeater = (Repeaters)repeaters.get(i);
				msg.append(repeater.getDescription()).append("\n");
			}
		}
		MailSender mailSender = new MailSender(smtp);
		mailSender.sendMail(sender, reminder.getEmail(), 
		                    "Reminder: " + app.getSubject(), msg.toString());
		
	}


	private String getCategoryDescription(int id) {	
		try {
			CategoriesTable catTable = new CategoriesTable();
			Iterator it = catTable.select("categoryid = " + id);
			if (it.hasNext()) {
				Categories category = (Categories)it.next();
				return category.getDescription();
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}
	
}