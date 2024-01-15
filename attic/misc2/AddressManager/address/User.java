/*
 * @(#)User.java	1.1 6/1999
 *
 * Johannes Plachy (JPlachy@qualityservice.com)
 *
 *
 * wrapper fuer Benutzerdaten
 *
 * Version 1.0
 */


package address;


public class User implements java.io.Serializable {
	private String ivUsername;
	private int ivId = -1;

	public User(int Id, String Username) {
		setUserName(Username);
		setId(Id);
	}

	public void setUserName(String Username) {
		ivUsername = Username;
	}
	public void setId(int id) {
		ivId = id;
	}

	public String getUsername() {
		return ivUsername;
	}

	public int getId() {
		return ivId;
	}

	public String toString() {
		return getUsername() + ";"+getId();
	}
}

