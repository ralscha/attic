package ch.ess.blank.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

/** 
 * @author  Ralph Schaer
 * @version $Revision: 1.2 $ $Date: 2004/05/22 16:51:13 $
 *  
 * @struts.form name="loginForm"
 */
public class LoginForm extends ValidatorForm {

  private String userName;
  private String password;
  private boolean remember;
  private String submit;
  private String newPassword;
  private String to;

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    remember = false;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getPassword() {
    return password;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.password"
   */
  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isRemember() {
    return remember;
  }

  public void setRemember(boolean remember) {
    this.remember = remember;
  }

  public String getSubmit() {
    return submit;
  }

  public void setSubmit(String submit) {
    this.submit = submit;
  }

  public String getUserName() {
    return userName;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.userName"
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }
}