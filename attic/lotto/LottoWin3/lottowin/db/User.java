
package lottowin.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class User.
 */
public class User {

  /* attributes */
  private String userid;
  private String name;
  private String firstname;
  private String pass;
  private String language;
  private String email;
  private OCollection tips;
  private OCollection jokers;


  /** Creates a new User */
  public User() {
  }


  /** Accessor for attribute userid */
  public String getUserid() {
    return userid;
  }

  /** Accessor for attribute name */
  public String getName() {
    return name;
  }

  /** Accessor for attribute firstname */
  public String getFirstname() {
    return firstname;
  }

  /** Accessor for attribute pass */
  public String getPass() {
    return pass;
  }

  /** Accessor for attribute language */
  public String getLanguage() {
    return language;
  }

  /** Accessor for attribute email */
  public String getEmail() {
    return email;
  }


  /** Mutator for attribute userid */
  public void setUserid(String newUserid) {
    userid = newUserid;
  }

  /** Mutator for attribute name */
  public void setName(String newName) {
    name = newName;
  }

  /** Mutator for attribute firstname */
  public void setFirstname(String newFirstname) {
    firstname = newFirstname;
  }

  /** Mutator for attribute pass */
  public void setPass(String newPass) {
    pass = newPass;
  }

  /** Mutator for attribute language */
  public void setLanguage(String newLanguage) {
    language = newLanguage;
  }

  /** Mutator for attribute email */
  public void setEmail(String newEmail) {
    email = newEmail;
  }





  /** Returns all elements in the Tips collection */
  public Tip[] getTips() throws BODBException {
    return (Tip[]) tips.get();
  }

  public Tip getTip(int id) throws BODBException {
    return (Tip) tips.get(id);
  }

  /** Returns all elements in the Jokers collection */
  public Joker[] getJokers() throws BODBException {
    return (Joker[]) jokers.get();
  }

  public Joker getJoker(int id) throws BODBException {
    return (Joker) jokers.get(id);
  }

  /** Adds the supplied element to the Tips collection */
  public void addTips(Tip newTips) throws BOUpdateConflictException {
    tips.addOwned(newTips);
  }

  /** Adds the supplied element to the Jokers collection */
  public void addJokers(Joker newJokers) throws BOUpdateConflictException {
    jokers.addOwned(newJokers);
  }


  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append("userid = ").append(this.userid).append(";");
    buffer.append("name = ").append(this.name).append(";");
    buffer.append("firstname = ").append(this.firstname).append(";");
    buffer.append("pass = ").append(this.pass).append(";");
    buffer.append("language = ").append(this.language).append(";");
    buffer.append("email = ").append(this.email).append(";");

    buffer.append("tips = ");
    if (this.tips != null)
      buffer.append(this.tips.toString()).append(";");
    else
      buffer.append("null").append(";");

    buffer.append("jokers = ");
    if (this.jokers != null)
      buffer.append(this.jokers.toString()).append(";");
    else
      buffer.append("null").append(";");

    return buffer.toString();
  }



}



