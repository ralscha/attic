package ch.ess.calendar.session;

import java.util.*;
import java.sql.*;
import ch.ess.calendar.db.*;

public class Login {
	
	private boolean logout;
	private String userid;
	private String password;
	private String name;
	private String firstname;
	private boolean logonOK;
	private boolean admin;
	
	public Login() {
		init();
	}
	
	public void init() {
		logout = false;
		userid = null;
		password = null;
		logonOK = false;
		name = null;
		firstname = null;
		admin = false;
	}
	
	public void setLogout(String flag) {
		logout = Boolean.valueOf(flag).booleanValue();
		
		if (logout) {
			init();
		}
	}

	public boolean isAdmin() {
		return admin;
	}

	public boolean isLogoutMode() {
		return logout;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isLogonOK() throws SQLException {
		if (logonOK) 
			return true;
		
		if ((userid == null) || (password == null)) 
			return false;
				
		UsersTable usersTable = new UsersTable();

		Iterator it = usersTable.select("userid = '" + userid + "' AND password = '" + password + "'");
		if (it.hasNext()) {
			Users user = (Users)it.next();
			name = user.getName();
			firstname = user.getFirstname();
			admin = user.isAdmin();	
			logonOK = true;			
		} else {
			logonOK = false;
		}
		return logonOK;
	}

	public String getName() {
		return name;
	}
	
	public String getFirstname() {
		return firstname;
	}

}
