
package lottowin.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Joker.
 */
public class Joker {

  /* attributes */
  private int id;
  private String joker;
  private OReference user;


  /** Creates a new Joker */
  public Joker() {
  }


  /** Accessor for attribute id */
  public int getId() {
    return id;
  }

  /** Accessor for attribute joker */
  public String getJoker() {
    return joker;
  }


  /** Mutator for attribute id */
  public void setId(int newId) {
    id = newId;
  }

  /** Mutator for attribute joker */
  public void setJoker(String newJoker) {
    joker = newJoker;
  }


  /** Accessor for reference user */
  public User getUser() throws BODBException {
    return (User) user.get();
  }


  /** Mutator for reference user */
  public void setUser(User newuser) throws BODBException, BOReferenceNotUpdatedException {
    user.set(newuser);
  }


  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append("id = ").append(this.id).append(";");
    buffer.append("joker = ").append(this.joker).append(";");

    buffer.append("user = ");
    if (this.user != null)
      buffer.append(this.user.toString()).append(";");
    else
      buffer.append("null").append(";");

    return buffer.toString();
  }

}



