
package lottowin.form;


import java.util.Vector;
import org.apache.struts.action.*;
import javax.servlet.http.*;
import org.log4j.*;

public final class LogonForm extends ActionForm {


  private static Category CAT = Category.getInstance(LogonForm.class.getName());

  /**
   * The password.
   */
  private String password = null;


  /**
   * The username.
   */
  private String username = null;


  /**
   * Return the password.
   */
  public String getPassword() {

    return (this.password);

  }


  /**
     * Set the password.
     *
     * @param password The new password
     */
  public void setPassword(String password) {
    this.password = password.trim();
  }


  /**
     * Return the username.
     */
  public String getUsername() {
    return (this.username);
  }


  /**
     * Set the username.
     *
     * @param username The new username
     */
  public void setUsername(String username) {
    this.username = username.trim();
  }


  /**
     * Validate the properties of this form bean, and return an array of
     * message keys for any errors we encounter.
     */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

    HttpSession session = request.getSession();
    NDC.push(session.getId());

    try {   

      ActionErrors errors = new ActionErrors();
      if ((username == null) || (username.length() < 1)) {
        CAT.debug("no username");
        errors.add("username", new ActionError("logon.username.required"));
      }

      if ((password == null) || (password.length() < 1)) {
        CAT.debug("no password");
        errors.add("password", new ActionError("logon.password.required"));
      }

      return errors;

    } finally {
      NDC.pop();
    }
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer(500);
    buffer.append(super.toString());
    buffer.append(";password = ").append(this.password).append(";");
    buffer.append("username = ").append(this.username).append(";");
    return buffer.toString();
  }


}
