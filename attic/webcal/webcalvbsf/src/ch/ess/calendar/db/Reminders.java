package ch.ess.calendar.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Reminders.
 */
public class Reminders {

    /* attributes */
    private Long reminderid;
    private OReference appointment;
    private Long minutesbefore;
    private String email;

    /** Creates a new Reminders */    
    public Reminders() {
    }
    
    
    /** Accessor for attribute reminderid */
    public Long getReminderid() {
        return reminderid;
    }
    
    /** Accessor for attribute minutesbefore */
    public Long getMinutesbefore() {
        return minutesbefore;
    }
    
    /** Accessor for attribute email */
    public String getEmail() {
        return email;
    }
    
    /** Mutator for attribute reminderid */
    public void setReminderid(Long newReminderid) {
        reminderid = newReminderid;
    }
    
    /** Mutator for attribute minutesbefore */
    public void setMinutesbefore(Long newMinutesbefore) {
        minutesbefore = newMinutesbefore;
    }
    
    /** Mutator for attribute email */
    public void setEmail(String newEmail) {
        email = newEmail;
    }
    
    /** Accessor for reference appointment */
    public Appointments getAppointment() throws BODBException {
        return (Appointments)appointment.get();
    }
    
    /** Mutator for reference appointment */
    public void setAppointment(Appointments newAppointment)
      throws BODBException, BOReferenceNotUpdatedException {
        appointment.set(newAppointment);
    }
    
    
    
    
    
    
    
}
