package ch.ess.base.web.login;

import ch.ess.base.annotation.struts.ValidatorField;

import com.cc.framework.adapter.struts.FWValidatorForm;

public class LoginNewPasswordForm extends FWValidatorForm {

  private String name;

  public String getName() {
    return name;
  }

  @ValidatorField(key="login.userName", required=true)
  public void setName(final String userName) {
    this.name = userName;
  }
}
