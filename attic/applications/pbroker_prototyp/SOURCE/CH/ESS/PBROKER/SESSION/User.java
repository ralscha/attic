

package ch.ess.pbroker.session;

import ch.ess.pbroker.db.*;
import java.sql.*;

public class User {

	private String name;
	private String firstname;
  private Benutzer benutzer;

  public User(Benutzer benutzer) {
    this.name = benutzer.getNachname();
    this.firstname = benutzer.getVorname();
    this.benutzer = benutzer;
  }

  public User(String first, String last) {
    this.name = last;
    this.firstname = first;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.name;
  }

  public void setBenutzer(Benutzer benuzter) {
    this.benutzer = benutzer;
  }

  public Benutzer getBenutzer() {
    return this.benutzer;
  }

}