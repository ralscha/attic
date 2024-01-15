package ch.ess.calendar.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Users.
 */
public class Users {

    /* attributes */
    private String userid;
    private String firstname;
    private String name;
    private String password;
    private String administrator;
    private OCollection appointments;

    /** Creates a new Users */    
    public Users() {
    }
    
    
    /** Accessor for attribute userid */
    public String getUserid() {
        return userid;
    }
    
    /** Accessor for attribute firstname */
    public String getFirstname() {
        return firstname;
    }
    
    /** Accessor for attribute name */
    public String getName() {
        return name;
    }
    
    /** Accessor for attribute password */
    public String getPassword() {
        return password;
    }
    
    /** Accessor for attribute administrator */
    public String getAdministrator() {
        return administrator;
    }
    
    /** Mutator for attribute userid */
    public void setUserid(String newUserid) {
        userid = newUserid;
    }
    
    /** Mutator for attribute firstname */
    public void setFirstname(String newFirstname) {
        firstname = newFirstname;
    }
    
    /** Mutator for attribute name */
    public void setName(String newName) {
        name = newName;
    }
    
    /** Mutator for attribute password */
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    
    /** Mutator for attribute administrator */
    public void setAdministrator(String newAdministrator) {
        administrator = newAdministrator;
    }
    
    public boolean isAdmin() {      
      return ("Y".equalsIgnoreCase(administrator));
    }
  
    public void setAdministrator(boolean flag) {
      if (flag) {
        this.administrator = "Y";
      } else {
        this.administrator = "N";
      }
    }    
        
    /** Returns all elements in the Appointments collection */
    public Appointments[] getAppointments() throws BODBException {
        return (Appointments[])appointments.get();
    }
    
    /** Adds the supplied element to the Appointments collection */
    public void addAppointments(Appointments newAppointments) throws BODBException, BOReferenceNotUpdatedException, BOUpdateConflictException {
        appointments.add(newAppointments);
    }
    
    /** Removes the supplied element from the Appointments collection */
    public void removeAppointments(Appointments oldAppointments)
      throws BODBException, BOReferenceNotUpdatedException {
        appointments.remove(oldAppointments);
    }
}
