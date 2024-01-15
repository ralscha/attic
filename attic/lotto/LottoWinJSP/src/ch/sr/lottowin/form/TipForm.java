package ch.sr.lottowin.form;

import java.util.*;

import javax.servlet.http.*;

import org.apache.log4j.*;
import org.apache.struts.action.*;

public final class TipForm extends ActionForm {

  private static Category CAT = Category.getInstance(TipForm.class.getName());

  private String z1;
  private String z2;
  private String z3;
  private String z4;
  private String z5;
  private String z6;
  private int[] zahlen;

  public TipForm() {
    z1 = null;
    z2 = null;
    z3 = null;
    z4 = null;
    z5 = null;
    z6 = null;
    zahlen = new int[6];
  }

  public int[] getZahlen() {
    return zahlen;
  }

  public String getZ1() {
    return z1;
  }

  public void setZ1(String z1) {
    this.z1 = z1.trim();
  }

  public String getZ2() {
    return z2;
  }

  public void setZ2(String z2) {
    this.z2 = z2.trim();
  }

  public String getZ3() {
    return z3;
  }

  public void setZ3(String z3) {
    this.z3 = z3.trim();
  }

  public String getZ4() {
    return z4;
  }

  public void setZ4(String z4) {
    this.z4 = z4.trim();
  }

  public String getZ5() {
    return z5;
  }

  public void setZ5(String z5) {
    this.z5 = z5.trim();
  }

  public String getZ6() {
    return z6;
  }

  public void setZ6(String z6) {
    this.z6 = z6.trim();
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

  

      ActionErrors errors = new ActionErrors();

      if ((z1 == null) || (z1.length() < 1) || (z2 == null) || (z2.length() < 1) ||
          (z3 == null) || (z3.length() < 1) || (z4 == null) || (z4.length() < 1) ||
          (z5 == null) || (z5.length() < 1) || (z6 == null) || (z6.length() < 1)) {
        CAT.debug("missing input");
        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("tip.all.required"));
      } else {

        try {
          zahlen[0] = Integer.parseInt(z1);
          zahlen[1] = Integer.parseInt(z2);
          zahlen[2] = Integer.parseInt(z3);
          zahlen[3] = Integer.parseInt(z4);
          zahlen[4] = Integer.parseInt(z5);
          zahlen[5] = Integer.parseInt(z6);

          boolean same = false;
          for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
              if (i != j) {
                if (zahlen[i] == zahlen[j]) {
                  same = true;
                  break;
                }
              }
            }
            if (same)
              break;
          }

          if (same) {
            CAT.debug("input contains duplicate");
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("tip.contains.duplicate"));
          }

          for (int i = 0; i < 6; i++) {
            if ((zahlen[i] < 1) || (zahlen[i] > 45)) {
              CAT.debug("number out of range");
              errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("tip.out.of.range"));
              break;
            }
          }

          Arrays.sort(zahlen);

        } catch (NumberFormatException nfe) {
          CAT.debug("wrong format", nfe);
          errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("tip.wrong.format"));
        }

      }

      return errors;


  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append(super.toString());
    buffer.append(";z1 = ").append(this.z1).append(";");
    buffer.append("z2 = ").append(this.z2).append(";");
    buffer.append("z3 = ").append(this.z3).append(";");
    buffer.append("z4 = ").append(this.z4).append(";");
    buffer.append("z5 = ").append(this.z5).append(";");
    buffer.append("z6 = ").append(this.z6).append(";");

    return buffer.toString();
  }


}
