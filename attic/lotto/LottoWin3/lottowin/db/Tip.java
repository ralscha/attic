
package lottowin.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Tip.
 */
public class Tip {

  /* attributes */
  private int id;
  private int z1;
  private int z2;
  private int z3;
  private int z4;
  private int z5;
  private int z6;
  private OReference user;

  private int[] zarray = new int[6];

  /** Creates a new Tip */
  public Tip() {
  }


  /** Accessor for attribute id */
  public int getId() {
    return id;
  }

  /** Accessor for attribute z1 */
  public int getZ1() {
    return z1;
  }

  /** Accessor for attribute z2 */
  public int getZ2() {
    return z2;
  }

  /** Accessor for attribute z3 */
  public int getZ3() {
    return z3;
  }

  /** Accessor for attribute z4 */
  public int getZ4() {
    return z4;
  }

  /** Accessor for attribute z5 */
  public int getZ5() {
    return z5;
  }

  /** Accessor for attribute z6 */
  public int getZ6() {
    return z6;
  }


  /** Mutator for attribute id */
  public void setId(int newId) {
    id = newId;
  }

  /** Mutator for attribute z1 */
  public void setZ1(int newZ1) {
    z1 = newZ1;
  }

  /** Mutator for attribute z2 */
  public void setZ2(int newZ2) {
    z2 = newZ2;
  }

  /** Mutator for attribute z3 */
  public void setZ3(int newZ3) {
    z3 = newZ3;
  }

  /** Mutator for attribute z4 */
  public void setZ4(int newZ4) {
    z4 = newZ4;
  }

  /** Mutator for attribute z5 */
  public void setZ5(int newZ5) {
    z5 = newZ5;
  }

  /** Mutator for attribute z6 */
  public void setZ6(int newZ6) {
    z6 = newZ6;
  }

  public int[] getZArray() {
    zarray[0] = z1;
    zarray[1] = z2;
    zarray[2] = z3;
    zarray[3] = z4;
    zarray[4] = z5;
    zarray[5] = z6;
    return zarray;
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
    buffer.append("z1 = ").append(this.z1).append(";");
    buffer.append("z2 = ").append(this.z2).append(";");
    buffer.append("z3 = ").append(this.z3).append(";");
    buffer.append("z4 = ").append(this.z4).append(";");
    buffer.append("z5 = ").append(this.z5).append(";");
    buffer.append("z6 = ").append(this.z6).append(";");

    buffer.append("user = ");
    if (this.user != null)
      buffer.append(this.user.toString()).append(";");
    else
      buffer.append("null").append(";");

    return buffer.toString();
  }






}



