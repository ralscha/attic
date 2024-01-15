package ch.ess.calendar.db;

public class Users implements java.io.Serializable {

  private static final long serialVersionUID = 1L;

  private String userid;
	private String firstname;
	private String name;
	private String password;
	private String administrator;
  private String email;

	public Users() {
		this.userid = null;
		this.firstname = null;
		this.name = null;
		this.password = null;
		this.administrator = null;
		this.email = null;
	}

	public Users(String userid, String firstname, String name, String password, String administrator, String email) {
		this.userid = userid;
		this.firstname = firstname;
		this.name = name;
		this.password = password;
		this.administrator = administrator;
		this.email = email;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAdministrator() {
		return administrator;
	}

	public boolean isAdmin() {
		return ("Y".equalsIgnoreCase(administrator));
	}
	
	public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}
	
	public void setAdministrator(boolean flag) {
		if (flag) {
			this.administrator = "Y";
		} else {
			this.administrator = "N";
		}
	}


	public String toString() {
    return "Users("+ userid + " " + firstname + " " + name + " " + password + " " + administrator+ " " +email+")";
	}
}
