package lottowin.form;

import org.apache.struts.action.*;
import java.util.*;
import javax.servlet.http.*;
import org.log4j.*;

public final class JokerForm extends ActionForm {

  private static Category CAT = Category.getInstance(JokerForm.class.getName());

  private String joker;

  public JokerForm() {
    joker = null;
  }

  public String getJoker() {
    return joker;
  }

  public void setJoker(String joker) {
    this.joker = joker.trim();
  }


  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

    HttpSession session = request.getSession();
    NDC.push(session.getId());

    try {   

      ActionErrors errors = new ActionErrors();

      if ((joker == null) || (joker.length() < 1)) {
        CAT.debug("no input");
        errors.add("joker", new ActionError("joker.no.input"));
      } else {

        if (joker.length() != 6) {
          CAT.debug("wrong length");
          errors.add("joker", new ActionError("joker.wrong.length"));
        }

        try {
          int tmp = Integer.parseInt(joker);
        } catch (NumberFormatException nfe) {
          CAT.debug("wrong format", nfe);
          errors.add("joker", new ActionError("joker.wrong.format"));
        }

      }
      return errors;

    } finally {
      NDC.pop();
    }

  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append(super.toString());
    buffer.append(";joker = ").append(this.joker).append(";");
    return buffer.toString();
  }


}
