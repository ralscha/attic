
package ch.ess.calendar;

import java.sql.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import ch.ess.calendar.db.*;
import ch.ess.calendar.session.*;
import ch.ess.calendar.util.*;

public class AppointmentRequest {

  private static String smtp = null;
  private static String sender = null;
  

	private int category;
	private String title;
	private String enddate;
	private String startdate;
	private String onedate;
	private String forwhom;
	
	private String endtime;
	private String starttime;
	private boolean priv;
	private boolean privset;

	private int event;
	private int mode;
	private String notes;
	
	//reminder
	public final static int REMINDER_OFF = 0;
	public final static int REMINDER_ON = 1;
	
	private int reminderminutes;
	private String reminderemail;
	private int remindermode;
	
	
	//repeater
	public final static int REPEATING_OFF = 0;
	public final static int REPEATING_EVERY = 1;
	public final static int REPEATING_ON = 2;
	
	public final static int REPEATINGVALID_UNTIL = 1;
	public final static int REPEATINGVALID_ALWAYS = 2;
	
	private int repeatingmode;
	private String repeatinguntil;
	private int repeatingvalid;
	private int every;
	private int everyperiod;
	private int repeaton;
	private int repeatonweekday;
	private int repeatonperiod;
	
	
	public final static int ONE_MODE = 1;
	public final static int TWO_MODE = 2;
	
	public final static int ALLDAY_EVENT = 1;
	public final static int TIMED_EVENT = 2;
	

	
	private boolean save, addAnother, cancel, reset;
	private String errorText;
	
	private Calendar today;
	private AppointmentsTable appTable;
	
	private boolean update;
	private int updateappid;
	private AppointmentsCache cache;
	private HttpServletRequest request;
	
	public static void setSmtp(String smtp) {
    AppointmentRequest.smtp = smtp;
  }
	
  public static void setSender(String sender) {
    AppointmentRequest.sender = sender;
  }


	public AppointmentRequest() {
		init();
	}
	
	public void init() {
		today = new GregorianCalendar();
		category = -1;
		title = "";
		enddate = "";
		startdate = "";
		onedate = "";
		endtime = "";
		starttime = "";
		event = ALLDAY_EVENT;
		mode = ONE_MODE;
		notes = "";
		save = false;
		addAnother = false;
		reset = false;
		cancel = false;
		errorText = "";
		update = false;
		updateappid = -1;
		priv = false;
		privset = false;
		forwhom = null;
		
		reminderminutes = 5;
		reminderemail = "";
		remindermode = REMINDER_OFF;
		
		repeatingmode = REPEATING_OFF;
		repeatingvalid = REPEATINGVALID_ALWAYS;
		repeatinguntil = "";
		every = Repeaters.EVERY;
		everyperiod = Repeaters.EVERYPERIOD_DAY;
		repeaton = Repeaters.REPEATON_FIRST;
		repeatonweekday = Calendar.MONDAY;
		repeatonperiod = Repeaters.REPEATONPERIOD_MONTH;
	}

	public void initAfterAddAnother() {
		today = new GregorianCalendar();
		title = "";
		enddate = "";
		startdate = "";
		onedate = "";
		endtime = "";
		starttime = "";
		event = ALLDAY_EVENT;
		mode = ONE_MODE;
		notes = "";
		save = false;
		addAnother = false;
		reset = false;
		cancel = false;
		errorText = "";
		update = false;
		updateappid = -1;
		priv = false;
		privset = false;
		forwhom = null;
		
		reminderminutes = 5;
		reminderemail = "";
		remindermode = REMINDER_OFF;
		
		repeatingmode = REPEATING_OFF;
		repeatingvalid = REPEATINGVALID_ALWAYS;
		repeatinguntil = "";
		every = Repeaters.EVERY;
		everyperiod = Repeaters.EVERYPERIOD_DAY;
		repeaton = Repeaters.REPEATON_FIRST;
		repeatonweekday = Calendar.MONDAY;
		repeatonperiod = Repeaters.REPEATONPERIOD_MONTH;
	}
	//update
	
	public void setUpdateappid(int appid) {
    updateappid = appid;
		update = true;
	}
	
	public int getUpdateappid() {
		return updateappid;
	}
		
  public void setAppCache(AppointmentsCache cache) {
    this.cache = cache;
  }		
  
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }
		
	public void setAppid(String appid) throws SQLException {
		if (appid == null) return;
				
		appTable = new AppointmentsTable();
		
		Iterator it = appTable.select("appointmentid = " + appid);
		if (it.hasNext()) {
		  	Appointments app = (Appointments)it.next();
			update = true;
									
			forwhom = app.getUserid();
			
			category = app.getCategoryid(); 
			title = app.getSubject();				
			updateappid = app.getAppointmentid();
			
			if (app.isAlldayevent()) {
				if (app.getFormatedStartDate().equals(app.getFormatedEndDate())) {
					onedate = app.getFormatedStartDate();
					mode = ONE_MODE;
					event = ALLDAY_EVENT;
				} else {
					enddate = app.getFormatedEndDate();
					startdate = app.getFormatedStartDate();
					mode = TWO_MODE;							
				}
			} else {
				event = TIMED_EVENT;
				onedate = app.getFormatedStartDate();
				starttime = app.getFormatedStartTime();
				endtime = app.getFormatedEndTime();
				mode = ONE_MODE;	
			}
			
			if (app.getBody() != null)
				notes = app.getBody();
			else
				notes = "";	
			
			
			priv = app.isPrivate();
			
			
			List reminderList = app.getReminders();
			if ((reminderList != null) && !reminderList.isEmpty()) {
				Reminders remind = (Reminders)reminderList.get(0);
				reminderminutes = remind.getMinutesbefore();
				reminderemail   = remind.getEmail();
				remindermode = REMINDER_ON;
			}
			
			List repeaterList = app.getRepeaters();
			if ((repeaterList != null) && !repeaterList.isEmpty()) {
				Repeaters repeater = (Repeaters)repeaterList.get(0);
				
				if (repeater.isEveryMode()) {
					repeatingmode = REPEATING_EVERY;
					every = repeater.getEvery();
					everyperiod = repeater.getEveryperiod();
				} else {
					repeatingmode = REPEATING_ON;										
					repeaton = repeater.getRepeaton();
					repeatonweekday = repeater.getRepeatonweekday();
					repeatonperiod = repeater.getRepeatonperiod();						
				}
				
				if (repeater.isAlways()) {
					repeatingvalid = REPEATINGVALID_ALWAYS;
				} else {
					repeatingvalid = REPEATINGVALID_UNTIL;
					repeatinguntil = repeater.getFormatedUntil();
				}
			}				
		} 
	}
	
	public boolean isUpdate() {
		return update;
	}

	public String getTodayString() {
		return ch.ess.calendar.util.Constants.dateFormat.format(today.getTime());
	}

	public void setForwhom(String whom) {
		forwhom = whom;
	}

	public String getForwhom() {
		return forwhom;
	}

	public boolean forGroup() {
		return "group".equals(forwhom);
	}
	
	public void setEnddate(String date) {
		this.enddate = date;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setStartdate(String date) {
		this.startdate = date;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setEndtime(String time) {
		this.endtime = time;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setStarttime(String time) {
		this.starttime = time;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setOnedate(String onedate) {
		this.onedate = onedate;
	}
	
	public String getOnedate() {
		return onedate;
	}

	public void setCategory(int id) {
		this.category = id;
	}

	public int getCategory() {
		return category;
	}
	
	public void setSecret(String[] privstr) {
		if (privstr.length > 0) {
			priv = true;
			privset = true;	
		}
	}
	
	public boolean isPriv() {
		return priv;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setNotes(String notes) {
		if (notes.length() < 200) {
			this.notes = notes;
		} else {
			this.notes = notes.substring(0, 200);
		}
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setMode(int mode) {
    this.mode = mode;
	}
	
	public int getMode() {
		return mode;
	}
	
	public int getEvent() {
		return event;
	}
	
	public void setEvent(int event) {
		this.event = event;
	}
	
	public void setReminderMode(int remindermode) {
		this.remindermode = remindermode;
	}
	
	public int getReminderMode() {
		return remindermode;
	}
	
	public void setReminderEmail(String email) {
		this.reminderemail = email;
	}
	
	public String getReminderEmail() {
		return reminderemail;
	}
	
	public void setReminderMinutes(int reminderminutes) {
		this.reminderminutes = reminderminutes;
	}
	
	public int getReminderMinutes() {
		return reminderminutes;
	}
	
	
	public void setRepeatingmode(int mode) {
		repeatingmode = mode;
	}
	
	public int getRepeatingmode() {
		return repeatingmode;
	}
	
	public void setRepeatingvalid(int valid) {
		repeatingvalid = valid;
	}
	
	public int getRepeatingvalid() {
		return repeatingvalid;
	}
	
	public void setRepeatinguntil(String until) {
		repeatinguntil = until;
	}	
	
	public String getRepeatinguntil() {
		return repeatinguntil;
	}
	
	public void setEvery(int every) {
		this.every = every;
	}
	
	public int getEvery() {
		return every;
	}
	
	public void setEveryperiod(int everyperiod) {
		this.everyperiod = everyperiod;
	}
	
	public int getEveryperiod() {
		return everyperiod;
	}
	
	public void setRepeaton(int repeaton) {
		this.repeaton = repeaton;
	}
	
	public int getRepeaton() {
		return repeaton;
	}
	
	public void setRepeatonweekday(int row) {
		repeatonweekday = row;
	}	
	
	public int getRepeatonweekday() {
		return repeatonweekday;
	}
	
	public void setRepeatonperiod(int rop) {
		repeatonperiod = rop;
	}
	
	public int getRepeatonperiod() {
		return repeatonperiod;
	}
	
	public void setCancel(String dummy) {
		cancel = true;
	}
	
	public void setSave(String dummy) {
		save = true;
		addAnother = false;
	}
	
	public void setSaveadd(String dummy) {
		save = true;
		addAnother = true;	
	}
	
	public void setReset(String dummy) {
		reset = true;
		save = false;
		addAnother = false;
	}
	
	public boolean isCancelClicked() {
		return cancel;
	}
	
	public boolean isSaveClicked() {
		return save;
	}
	
	public boolean isResetClicked() {
		return reset;
	}
	
	public boolean isAddAnotherClicked() {
		return addAnother;
	}
	
	public String getErrorText() {
		return errorText;
	}
	

	public boolean commitChange(String userid) throws SQLException {	
	
		if (!inputValidation()) 
			return false;

		
		//next id 
		int nextid = 1;
		
		appTable = new AppointmentsTable();
		
		if (!isUpdate()) {				
      nextid = appTable.getMaxid();				 
		}		
		
		Appointments newapp = new Appointments();	

		
		if ((mode == TWO_MODE) || (event == ALLDAY_EVENT))			
			newapp.setAlldayevent(true);
		else
			newapp.setAlldayevent(false);
				
		newapp.setBody(notes);
		newapp.setCategoryid(category);
		
		if (mode == TWO_MODE) {
			newapp.setEnddate(enddate);
			newapp.setStartdate(startdate);
		} else if (mode == ONE_MODE) {
			if (event == TIMED_EVENT) {
				newapp.setStartdate(onedate, starttime);
				newapp.setEnddate(onedate, endtime);					
			} else if (event == ALLDAY_EVENT) {
				newapp.setStartdate(onedate);
				newapp.setEnddate(onedate);
			}
		}
		
		newapp.setSubject(title);
		if (forwhom != null)
			newapp.setUserid(forwhom);
		else
			newapp.setUserid(userid);
				
		/*			
		if ("group".equals(forwhom))
			newapp.setUserid("group");
		else
			newapp.setUserid(userid);
		*/			
		if (!privset)
			newapp.setPrivate(false);
		else
			newapp.setPrivate(priv);	
		
		if (remindermode == REMINDER_ON) {
			Reminders reminder = new Reminders();
			reminder.setMinutesbefore(reminderminutes);
			reminder.setEmail(reminderemail);
			newapp.addReminder(reminder);
		}			

		Repeaters repeater;
		if (repeatingmode == REPEATING_EVERY) {
			repeater = new Repeaters();
			repeater.setEvery(every);
			repeater.setEveryperiod(everyperiod);
			if (repeatingvalid == REPEATINGVALID_UNTIL) {
				repeater.setUntil(repeatinguntil);
			}
			newapp.addRepeater(repeater);				
		} else if (repeatingmode == REPEATING_ON) {
			repeater = new Repeaters();
			repeater.setRepeaton(repeaton);
			repeater.setRepeatonweekday(repeatonweekday);
			repeater.setRepeatonperiod(repeatonperiod);
			if (repeatingvalid == REPEATINGVALID_UNTIL) {
				repeater.setUntil(repeatinguntil);
			}
			newapp.addRepeater(repeater);
		}
				
		if (isUpdate()) {
			newapp.setAppointmentid(updateappid);
			appTable.update(newapp);				
		} else {
			newapp.setAppointmentid(nextid);			
			appTable.insert(newapp);					        
		}
		
		try {				
		
  		if (forwhom != null) {
        cache.refresh(forwhom, request);              
      } else {
        cache.refresh(userid, request);      
      }
    } catch (Exception e) {
      System.err.println(e);
    }
    
    if (smtp != null) {
                  
      if (!userid.equals(forwhom)) {
        
        String fromname = null;
        UsersTable usersTable = new UsersTable();   
        Iterator it = usersTable.select("userid = '" + userid + "'" );
        if (it.hasNext()) {
          Users user = (Users)it.next();
          fromname = user.getFirstname() + " " + user.getName();
        }
        
        if ("group".equals(forwhom)) {
          
          UsersTable ut = new UsersTable();
          Iterator userit = ut.select();
          while (userit.hasNext()) {
            Users user = (Users)userit.next();
            if (!user.getUserid().equals(userid)) {
              sendEmail(newapp, user.getEmail(), fromname);
            }
          }
          
        } else {          
          usersTable = new UsersTable();   
          it = usersTable.select("userid = '" + forwhom + "'");
          if (it.hasNext()) {
            Users user = (Users)it.next();
            sendEmail(newapp, user.getEmail(), fromname);                        
          }
          
          
        }
        
      } 
    }
        
		return true;
	}	
		
  private void sendEmail(Appointments app, String toemail, String from) {        
    
    StringBuffer msg = new StringBuffer();
    msg.append("New Appointment: ").append(app.getSubject()).append("\n");
    msg.append("(Inserted from " + from + ")");
    msg.append("\n");
    msg.append("\n");

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
    mailSender.sendMail(sender, toemail, 
                        "New Appointment: " + app.getSubject(), msg.toString());
    
  }

  private String getCategoryDescription(int id) { 
    try {
      CategoriesTable catTable = new CategoriesTable();
      Iterator it = catTable.select("categoryid = " + id);
      if (it.hasNext()) {
        Categories category1 = (Categories)it.next();
        return category1.getDescription();
      } 
      return "";      
    } catch (Exception e) {
      return "";
    }
  }


	public boolean inputValidation() {
		if (isNullString(title)) {
			errorText = "Please enter a title";
			return false;
		}
		
		if (mode == ONE_MODE) {
			if (isNullString(onedate)) {
				errorText = "Please enter a date";
				return false;
			}
			
			try {
				java.util.Date d = ch.ess.calendar.util.Constants.sDateFormat.parse(onedate);
				setOnedate(ch.ess.calendar.util.Constants.dateFormat.format(d));

			} catch (ParseException pe1) { 
				errorText = "Wrong date format: "+onedate;
				return false;
			}
						
			if (event == TIMED_EVENT) {
				if (isNullString(starttime) || isNullString(endtime)) {
					errorText = "Please enter start and end time";
					return false;
				}
				
				try {
					java.util.Date t = ch.ess.calendar.util.Constants.sTimeFormat.parse(starttime);					
          setStarttime(ch.ess.calendar.util.Constants.timeFormat.format(t));
        } catch (ParseException pe1) {
          try {
            java.util.Date t = ch.ess.calendar.util.Constants.s2TimeFormat.parse(starttime);         
            setStarttime(ch.ess.calendar.util.Constants.timeFormat.format(t));
              
  				} catch (ParseException pe) {
            try {
              int hour = Integer.parseInt(starttime);
              if ((hour >= 0) && (hour <= 23)) {
                Calendar tmpCal = new GregorianCalendar();
                tmpCal.set(Calendar.HOUR_OF_DAY, hour);
                tmpCal.set(Calendar.MINUTE, 0);
                setStarttime(ch.ess.calendar.util.Constants.timeFormat.format(tmpCal.getTime()));
              } else {
                errorText = "Wrong start time: " + starttime;
                return false;
              }
              
            } catch (NumberFormatException nfe) {
    					errorText = "Wrong start time format: "+starttime;
  	  				return false;
            }
  				}
  			}
				
				try {
          java.util.Date t = ch.ess.calendar.util.Constants.sTimeFormat.parse(endtime);
          setEndtime(ch.ess.calendar.util.Constants.timeFormat.format(t));
				} catch (ParseException pe1) {
          try {
            java.util.Date t = ch.ess.calendar.util.Constants.s2TimeFormat.parse(endtime);
            setEndtime(ch.ess.calendar.util.Constants.timeFormat.format(t));         
				  } catch (ParseException pe) {
            try {
              int hour = Integer.parseInt(endtime);
              if ((hour >= 0) && (hour <= 23)) {
                Calendar tmpCal = new GregorianCalendar();
                tmpCal.set(Calendar.HOUR_OF_DAY, hour);
                tmpCal.set(Calendar.MINUTE, 0);
                setEndtime(ch.ess.calendar.util.Constants.timeFormat.format(tmpCal.getTime()));
              } else {
                errorText = "Wrong end time: " + endtime;
                return false;
              }            
            } catch (NumberFormatException nfe) {
    					errorText = "Wrong end time format: "+endtime;  
    					return false;
            }
          }
				}
				
			}
		} else if (mode == TWO_MODE) {
			if (isNullString(startdate)) {
				errorText = "Please enter a start date";
				return false;
			}
			
			if (isNullString(enddate)) {
				errorText = "Please enter a end date";
				return false;
			}

			try {
				java.util.Date d = ch.ess.calendar.util.Constants.sDateFormat.parse(startdate);
				setStartdate(ch.ess.calendar.util.Constants.dateFormat.format(d));

			} catch (ParseException pe1) { 
				errorText = "Wrong start date format: "+startdate;
				return false;
			}

			try {
				java.util.Date d = ch.ess.calendar.util.Constants.sDateFormat.parse(enddate);
				setEnddate(ch.ess.calendar.util.Constants.dateFormat.format(d));

			} catch (ParseException pe1) { 
				errorText = "Wrong end date format: "+startdate;
				return false;
			}



		} else {
			errorText = "No mode specified";
			return false;
		}
		
		return true;
	}
	
	public boolean isNullString(String str) {
		return ((str == null) || (str.trim().equals("")));
	}
}