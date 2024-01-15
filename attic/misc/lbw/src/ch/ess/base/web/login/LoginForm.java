package ch.ess.base.web.login;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import ch.ess.base.annotation.struts.ValidatorField;

import com.cc.framework.adapter.struts.FWValidatorForm;

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

  @ValidatorField(key = "login.password", required = true)
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

  @ValidatorField(key = "login.userName", required = true)
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