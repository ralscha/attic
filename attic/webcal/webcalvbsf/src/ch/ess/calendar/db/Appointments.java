package ch.ess.calendar.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;
import java.text.*;

/**
 * Class Appointments.
 */
public class Appointments {

    /* attributes */
    private Long appointmentid;
    private OReference user;
    private Long categoryid;
    private Date startdate;
    private Date enddate;
    private String body;
    private String location;
    private String subject;
    private String alldayevent;
    private Long importance;
    private String priv;
    private OCollection reminders;
    private OCollection repeaters;

    /** Creates a new Appointments */    
    public Appointments() {
    }
    
    
    /** Accessor for attribute appointmentid */
    public Long getAppointmentid() {
        return appointmentid;
    }
    
    /** Accessor for attribute categoryid */
    public Long getCategoryid() {
        return categoryid;
    }
    
    /** Accessor for attribute startdate */
    public Date getStartdate() {
        return startdate;
    }
    
  public Calendar getStartCalendar() {
    GregorianCalendar startCal = new GregorianCalendar();
    startCal.setTime(startdate);
    return startCal;
  }    
    
    /** Accessor for attribute enddate */
    public Date getEnddate() {
        return enddate;
    }
  public Calendar getEndCalendar() {
    GregorianCalendar endCal = new GregorianCalendar();
    endCal.setTime(enddate);
    return endCal;
  }    
    
    /** Accessor for attribute body */
    public String getBody() {
        return body;
    }
    
    /** Accessor for attribute location */
    public String getLocation() {
        return location;
    }
    
    /** Accessor for attribute subject */
    public String getSubject() {
        return subject;
    }
    
    /** Accessor for attribute alldayevent */
    public String getAlldayevent() {
        return alldayevent;
    }
    

  public boolean isAlldayevent() {    
    if ("Y".equalsIgnoreCase(alldayevent))
      return true;
    else
      return false; 
  }
  
  public void setAlldayevent(boolean flag) {
    if (flag)
      this.alldayevent = "Y";
    else
      this.alldayevent = "N"; 
  }    
    
    /** Accessor for attribute importance */
    public Long getImportance() {
        return importance;
    }
    
    /** Accessor for attribute priv */
    public String getPriv() {
        return priv;
    }

  public boolean isPrivate() {    
    if ("Y".equalsIgnoreCase(priv))
      return true;
    else
      return false; 
  }

  public void setPrivate(boolean flag) {
    if (flag)
      this.priv = "Y";
    else
      this.priv = "N";  
  }

    
    /** Mutator for attribute appointmentid */
    public void setAppointmentid(Long newAppointmentid) {
        appointmentid = newAppointmentid;
    }
    
    /** Mutator for attribute categoryid */
    public void setCategoryid(Long newCategoryid) {
        categoryid = newCategoryid;
    }
    
    /** Mutator for attribute startdate */
    public void setStartdate(Date newStartdate) {
        startdate = newStartdate;
    }

  public void setStartdate(String datestr) {
    setStartdate(datestr, null);
  }

  public void setStartdate(String datestr, String timestr) {            
    this.startdate = new java.sql.Timestamp(getCalendar(datestr, timestr).getTime().getTime());
  }
    
    /** Mutator for attribute enddate */
    public void setEnddate(Date newEnddate) {
        enddate = newEnddate;
    }

  public void setEnddate(String datestr) {
    setEnddate(datestr, null);
  }

  public void setEnddate(String datestr, String timestr) {            
    this.enddate = new java.sql.Timestamp(getCalendar(datestr, timestr).getTime().getTime());
  }
    
    /** Mutator for attribute body */
    public void setBody(String newBody) {
        body = newBody;
    }
    
    /** Mutator for attribute location */
    public void setLocation(String newLocation) {
        location = newLocation;
    }
    
    /** Mutator for attribute subject */
    public void setSubject(String newSubject) {
        subject = newSubject;
    }
    
    /** Mutator for attribute alldayevent */
    public void setAlldayevent(String newAlldayevent) {
        alldayevent = newAlldayevent;
    }
    
    /** Mutator for attribute importance */
    public void setImportance(Long newImportance) {
        importance = newImportance;
    }
    
    /** Mutator for attribute priv */
    public void setPriv(String newPriv) {
        priv = newPriv;
    }
    
    /** Accessor for reference user */
    public Users getUser() throws BODBException {
        return (Users)user.get();
    }
    
    /** Mutator for reference user */
    public void setUser(Users newUser)
      throws BODBException, BOReferenceNotUpdatedException {
        user.set(newUser);
    }
    
    
    
    
    
    /** Returns all elements in the Reminders collection */
    public Reminders[] getReminders() throws BODBException {
        return (Reminders[])reminders.get();
    }
    
    /** Returns all elements in the Repeaters collection */
    public Repeaters[] getRepeaters() throws BODBException {
        return (Repeaters[])repeaters.get();
    }
    
    public boolean hasRepeaters() {
      return (getRepeaters() != null);
    }
    
    /** Adds the supplied element to the Reminders collection */
    public void addReminders(Reminders newReminders) throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        reminders.add(newReminders);
    }
    
    /** Adds the supplied element to the Repeaters collection */
    public void addRepeaters(Repeaters newRepeaters) throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        repeaters.add(newRepeaters);
    }
    
    /** Removes the supplied element from the Reminders collection */
    public void removeReminders(Reminders oldReminders)
      throws BODBException, BOReferenceNotUpdatedException {
        reminders.remove(oldReminders);
    }
    
    /** Removes the supplied element from the Repeaters collection */
    public void removeRepeaters(Repeaters oldRepeaters)
      throws BODBException, BOReferenceNotUpdatedException {
        repeaters.remove(oldRepeaters);
    }

  public String getFormatedStartTime() {
    return ch.ess.calendar.util.Constants.timeFormat.format(startdate);
  }
  
  public String getFormatedStartDate() {
    return ch.ess.calendar.util.Constants.dateFormat.format(startdate);
  }
  

  public String getFormatedEndTime() {
    return ch.ess.calendar.util.Constants.timeFormat.format(enddate);
  }
  
  public String getFormatedEndDate() {
    return ch.ess.calendar.util.Constants.dateFormat.format(enddate);
  }  
    
    
  private Calendar testCal = new GregorianCalendar();
  private Calendar startCal = new GregorianCalendar();
  private Calendar endCal = new GregorianCalendar();
  
  public boolean inRange(Calendar cal) {

    //Calendar testCal = (Calendar)cal.clone();   
    testCal.setTime(cal.getTime());
    testCal.set(Calendar.HOUR_OF_DAY, 0);
    testCal.set(Calendar.MINUTE, 0);
    testCal.set(Calendar.SECOND, 0);
    testCal.set(Calendar.MILLISECOND, 0);
  
    //Calendar startCal = new GregorianCalendar();
    startCal.setTime(startdate);    
    startCal.set(Calendar.HOUR_OF_DAY, 0);
    startCal.set(Calendar.MINUTE, 0);
    startCal.set(Calendar.SECOND, 0);
    startCal.set(Calendar.MILLISECOND, 0);
    
    //Calendar endCal = new GregorianCalendar();
    endCal.setTime(enddate);
    endCal.set(Calendar.HOUR_OF_DAY, 0);
    endCal.set(Calendar.MINUTE, 0);
    endCal.set(Calendar.SECOND, 0);
    endCal.set(Calendar.MILLISECOND, 0);
    
    if (getRepeaters() == null) {
      return ( (testCal.after(startCal) || testCal.equals(startCal)) &&
             (testCal.before(endCal) || testCal.equals(endCal)) );
          
    } else {
      if (testCal.after(startCal) || testCal.equals(startCal)) {
        Repeaters[] repeaters = getRepeaters();
        boolean inRange = false;
        for (int i = 0; i < repeaters.length; i++) {
          inRange = inRange || repeaters[i].inRange(startCal, cal);
        }                  
        return inRange;
      } else {
        return false;
      }
    
    }
    
  }


  private Calendar getCalendar(String datestr, String timestr) {
    Calendar tmpdate = new GregorianCalendar();
    Date date = null;
    try {   
      date = ch.ess.calendar.util.Constants.dateFormat.parse(datestr);
    } catch (ParseException pe) { }
    tmpdate.setTime(date);
    
    Calendar tmptime = null;
    if (timestr != null) {
      Date time = null;
      try { 
        time = ch.ess.calendar.util.Constants.timeFormat.parse(timestr); 
      } catch (ParseException pe) { }
      tmptime = new GregorianCalendar();
      tmptime.setTime(time);
    }
    
    
    Calendar cal;
    if (tmptime != null) {
      cal = new GregorianCalendar(tmpdate.get(Calendar.YEAR),
                                     tmpdate.get(Calendar.MONTH),
                            tmpdate.get(Calendar.DATE),
                            tmptime.get(Calendar.HOUR_OF_DAY),
                            tmptime.get(Calendar.MINUTE));
    } else {
      cal = new GregorianCalendar(tmpdate.get(Calendar.YEAR),
                                   tmpdate.get(Calendar.MONTH),
                          tmpdate.get(Calendar.DATE));
    
    }
    return cal;
  }    
    
}
