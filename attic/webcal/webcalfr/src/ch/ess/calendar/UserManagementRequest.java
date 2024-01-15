package ch.ess.calendar;


import java.sql.*;
import java.util.*;

import ch.ess.calendar.db.*;

public class UserManagementRequest {

	private Users user;
	private boolean update;
	private String retypePW;
	private boolean submit;
	private String errorText;
	private String adminRequest;
	
	public UserManagementRequest() {
		user = new Users();
		update = false;
		retypePW = null;
		submit = false;
		errorText = null;
		adminRequest = null;
	}

	public void setEdituserid(String userid) throws SQLException {
		if (userid == null) return;
		
		UsersTable usersTable = new UsersTable();
		Iterator it = usersTable.select("userid = '" + userid + "'");
		if (it.hasNext()) {
		  user = (Users)it.next();
			update = true;
		} 
	}
	
	public void setUpdate(String dummy) {	 
		update = true;
	}

	public boolean isUpdateMode() {
		return update;
	}
	

	public void setUserid(String userid) {
		user.setUserid(userid);
	}
	
	public String getUserid() {
		if (user.getUserid() != null)
			return user.getUserid();
		else
			return "";	
	}
	
	public void setName(String name) {
		user.setName(name);
	}

	public String getName() {
		if (user.getName() != null)
			return user.getName();
		else
			return "";	
	}

	public void setFirstname(String firstname) {
		user.setFirstname(firstname);
	}
	
	public String getFirstname() {
		if (user.getFirstname() != null)
			return user.getFirstname();
		else
			return "";	
	}
	
	public void setEmail(String email) {
    user.setEmail(email);
  }
  
  public String getEmail() {
    if (user.getEmail() != null) {
      return user.getEmail();
    } else {
      return "";
    }
  }
	
	public void setPassword(String password) {
		user.setPassword(password);
	}
	
	public void setAdministrator(String[] adm) {
		if (adm.length > 0) {
			user.setAdministrator(adm[0]);	
			adminRequest = adm[0];
		}
	}
	
	public boolean isAdmin() {
		return user.isAdmin();
	}
	
	public void setPasswordretype(String pw) {
		retypePW = pw;
	}
	
	public void setSubmit(String b) {
		submit = true;
	}
	
	public boolean isSubmitClicked() {
		return submit;
	}
	
	public boolean commitChange() throws SQLException {
	
		if (user == null) {
			errorText = "Internal error";
			return false;
		}
		
		if ((user.getUserid() == null) || (user.getUserid().trim() == "")) {
			errorText = "Please enter Userid";
			return false;
		}
		
    if ((user.getEmail() == null) || (user.getEmail().trim() == "")) {
      errorText = "Please enter Email";
      return false;
    }
  
			
		if (!isUpdateMode()) {	
			if ((user.getPassword() == null) || (user.getPassword().trim() == "")) {
				 errorText = "Please enter password";
				 return false;
			}

			if ((retypePW == null) || (retypePW.trim() == "")) {
				errorText = "Please retype password";
				return false;
			}		
		
			if (!retypePW.equals(user.getPassword())) {
				errorText = "Password not match";
				return false;
			}	
		}
			

		UsersTable usersTable = new UsersTable();
		if (isUpdateMode()) {
			if (adminRequest == null)				
				user.setAdministrator(false);
			
			Iterator it = usersTable.select("userid = '" + getUserid() + "'");
			if (it.hasNext()) {
			  	Users olduser = (Users)it.next();
				user.setPassword(olduser.getPassword());
			}
				
			usersTable.update(user);
			return true;
		} else { 		
			Iterator it = usersTable.select("userid = '" + getUserid() + "'");
			if (!it.hasNext()) {
				usersTable.insert(user);
				return true;
			} else {
				errorText = "ID exists already";
				return false;
			}		
		}
	}
	
	public String getErrorText() {
		return errorText;
	}
	
	public boolean hasError() {
		return (errorText != null);
	}
}

