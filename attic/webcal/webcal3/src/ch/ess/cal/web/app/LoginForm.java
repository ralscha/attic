package ch.ess.cal.web.app;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWValidatorForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $
 *  
 * @struts.form name="loginForm"
 */
public class LoginForm extends FWValidatorForm {

  private String userName;
  private String password;
  private boolean remember;
  private String submit;
  private String to;

  @Override
  public void reset(final ActionMapping actionMapping, final HttpServletRequest request) {
    remember = false;
  }

  public String getPassword() {
    return password;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.password"
   */
  public void setPassword(final String password) {
    this.password = password;
  }

  public boolean isRemember() {
    return remember;
  }

  public void setRemember(final boolean remember) {
    this.remember = remember;
  }

  public String getSubmit() {
    return submit;
  }

  public void setSubmit(final String submit) {
    this.submit = submit;
  }

  public String getUserName() {
    return userName;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.userName"
   */
  public void setUserName(final String userName) {
    this.userName = userName;
  }

  public String getTo() {
    return to;
  }

  public void setTo(final String to) {
    this.to = to;
  }

}
