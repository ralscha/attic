package ch.ess.calendar.session;

import java.util.*;
import java.sql.*;
import ch.ess.calendar.db.*;
import ch.ess.util.pool.*;
import com.objectmatter.bsf.*;

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


	public boolean isLogonOK() {
		if (logonOK) 
			return true;
		
		if ((userid == null) || (password == null)) 
			return false;
				
				
	  Database db = PoolManager.requestDatabase();
	  try {
	    OQuery query = new OQuery(Users.class);
	    query.add(userid, "userid");
	    query.add(password, "password");
	    query.setMaxCount(1);
	    
	    Users[] user = (Users[])query.execute(db);
      if (user != null) {        
        name = user[0].getName();
        firstname = user[0].getFirstname();
        admin = user[0].isAdmin(); 
        logonOK = true;     
      } else {
        logonOK = false;
      }     
	    
	  } finally {
	    PoolManager.returnDatabase(db);
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
