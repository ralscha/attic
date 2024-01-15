
package lottowin.db;

import com.objectmatter.bsf.*;
import java.util.*;
import java.math.*;

/**
 * Class Draw.
 */
public class Draw {

  /* attributes */
  private int number;
  private int drawyear;
  private Date drawdate;
  private int z1;
  private int z2;
  private int z3;
  private int z4;
  private int z5;
  private int z6;
  private int zz;
  private String joker;


  /** Creates a new Draw */
  public Draw() {
  }


  /** Accessor for attribute number */
  public int getNumber() {
    return number;
  }

  /** Accessor for attribute drawyear */
  public int getDrawyear() {
    return drawyear;
  }

  /** Accessor for attribute drawdate */
  public Date getDrawdate() {
    return drawdate;
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

  /** Accessor for attribute zz */
  public int getZz() {
    return zz;
  }

  /** Accessor for attribute joker */
  public String getJoker() {
    return joker;
  }


  /** Mutator for attribute number */
  public void setNumber(int newNumber) {
    number = newNumber;
  }

  /** Mutator for attribute drawyear */
  public void setDrawyear(int newDrawyear) {
    drawyear = newDrawyear;
  }

  /** Mutator for attribute drawdate */
  public void setDrawdate(Date newDrawdate) {
    drawdate = newDrawdate;
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

  /** Mutator for attribute zz */
  public void setZz(int newZz) {
    zz = newZz;
  }

  /** Mutator for attribute joker */
  public void setJoker(String newJoker) {
    joker = newJoker;
  }

  public int getWin(int[] tip) {
    int i, win = 0;

    for (i = 0; i < 6; i++) {
      if ((tip[i] == z1) || (tip[i] == z2) || (tip[i] == z3) || (tip[i] == z4) ||
          (tip[i] == z5) || (tip[i] == z6))
        win++;
    }

    if (win == 5) {
      for (i = 0; i < 6; i++)
        if (tip[i] == zz)
          return(51);
    }

    return win;
  }

  public int getJokerWin(String joker) {
    int win = 0;

    for (int i = 5; i >= 0; i--) {
      if (joker.substring(i, i + 1).equals(this.joker.substring(i, i + 1))) {
        win++;
      } else {
        break;
      }
    }

    return win;
  }


  public String toString() {
    StringBuffer buffer = new StringBuffer(500);

    buffer.append("number = ").append(this.number).append(";");
    buffer.append("drawyear = ").append(this.drawyear).append(";");

    buffer.append("drawdate = ");
    if (this.drawdate != null)
      buffer.append(this.drawdate.toString()).append(";");
    else
      buffer.append("null").append(";");

    buffer.append("z1 = ").append(this.z1).append(";");
    buffer.append("z2 = ").append(this.z2).append(";");
    buffer.append("z3 = ").append(this.z3).append(";");
    buffer.append("z4 = ").append(this.z4).append(";");
    buffer.append("z5 = ").append(this.z5).append(";");
    buffer.append("z6 = ").append(this.z6).append(";");
    buffer.append("zz = ").append(this.zz).append(";");
    buffer.append("joker = ").append(this.joker).append(";");

    return buffer.toString();
  }
}



