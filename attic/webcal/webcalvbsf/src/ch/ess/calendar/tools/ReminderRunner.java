
package ch.ess.calendar.tools;


import ch.ess.calendar.db.*;
import ch.ess.calendar.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;
import com.holub.asynch.*;
import ch.ess.calendar.util.*;
import ch.ess.calendar.util.*;
import ch.ess.util.pool.*;
import com.objectmatter.bsf.*;

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
    Database db = PoolManager.requestDatabase();	
    try {
      
      Appointments[] appointments = (Appointments[])db.list(Appointments.class);
			if (appointments != null) {
      for (int i = 0; i < appointments.length; i++) {

				Appointments app = appointments[i];
				
				Reminders[] reminders = app.getReminders();
				if (reminders != null) {
					Calendar today = new GregorianCalendar();
					
					Repeaters[] repeaters = app.getRepeaters();
					Repeaters repeater = null;
					if (repeaters != null) {						
						repeater = repeaters[0];
					}
					
					if (repeater != null) {						
						for (int j = 0; j < reminders.length; j++) {
							Reminders reminder = reminders[j];
							
							int min = reminder.getMinutesbefore().intValue();																								
								
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
            for (int j = 0; j < reminders.length; j++) {
              Reminders reminder = reminders[j];
							
							int min = reminder.getMinutesbefore().intValue();																						
														
							Calendar startCal = new GregorianCalendar();
							startCal.setTime(app.getStartdate());
							
							startCal.add(Calendar.MINUTE, -min);
							
							if (today.after(startCal) || today.equals(startCal)) {
								sendEmail(app, reminder, null);
								db.delete(reminder);
							}
														
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
		} finally {
		  PoolManager.returnDatabase(db);
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
		
		Database db = PoolManager.requestDatabase();
		try {
		  Categories cat = (Categories)db.lookup(Categories.class, app.getCategoryid().intValue());
		  if (cat != null) {
		    msg.append(cat.getDescription());
		  }
	  } finally {
	    PoolManager.returnDatabase(db);
	  }
		msg.append("\n");
		
		if (app.getBody() != null) {
			msg.append(app.getBody());
			msg.append("\n\n");
		} 
		
    Repeaters[] repeaters = app.getRepeaters();
		if (repeaters != null) {		  			
			for (int i = 0; i < repeaters.length; i++) {				
				msg.append(repeaters[i].getDescription()).append("\n");
			}
		}
		MailSender mailSender = new MailSender(smtp);
		mailSender.sendMail(sender, reminder.getEmail(), 
		                    "Reminder: " + app.getSubject(), msg.toString());
		
	}


	
}