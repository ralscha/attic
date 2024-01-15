package lottowin.form;

import java.util.*;
import org.apache.struts.action.*;
import javax.servlet.http.*;
import org.log4j.*;

public final class UserForm extends ActionForm {

  private static Category CAT = Category.getInstance(UserForm.class.getName());

  private String userID;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String passwordConfirm;

  public UserForm() {
    userID = null;
    firstName = null;
    lastName = null;
    email = null;
    password = null;
    passwordConfirm = null;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID.trim();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName.trim();
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName.trim();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email.trim();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password.trim();
  }

  public String getPasswordConfirm() {
    return passwordConfirm;
  }

  public void setPasswordConfirm(String passwordConfirm) {
    this.passwordConfirm = passwordConfirm.trim();
  }

  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

    HttpSession session = request.getSession();
    NDC.push(session.getId());

    try {     

      ActionErrors errors = new ActionErrors();

      if ((userID == null) || (userID.length() < 1) || (firstName == null) ||
          (firstName.length() < 1) || (lastName == null) ||
          (lastName.length() < 1) || (email == null) || (email.length() < 1) ||
          (password == null) || (password.length() < 1) ||
          (passwordConfirm == null) || (passwordConfirm.length() < 1)) {
        CAT.debug("missing input");
        errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("user.all.required"));
      } else {
        if (!passwordConfirm.equals(password)) {
          CAT.debug("password confirmation not match");
          errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("user.password.notmatch"));
        }

        int pos1 = email.indexOf("@");
        int pos2 = email.lastIndexOf(".");
        if ((pos1 == -1) || (pos2 == -1) || (pos2 < pos1)) {
          CAT.debug("email format not valid");
          errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("user.email.notvalid"));
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
    buffer.append(";userID = ").append(this.userID).append(";");
    buffer.append("firstName = ").append(this.firstName).append(";");
    buffer.append("lastName = ").append(this.lastName).append(";");
    buffer.append("email = ").append(this.email).append(";");
    buffer.append("password = ").append(this.password).append(";");
    buffer.append("passwordConfirm = ").append(this.passwordConfirm).append(";");
    return buffer.toString();
  }

}
