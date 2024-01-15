package ch.ess.base.web;

import org.apache.struts.annotation.ValidatorField;
import org.apache.struts.validator.ValidatorForm;

/** 
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/07/03 10:25:05 $
 */
public class LoginNewPasswordForm extends ValidatorForm {

  private String name;

  public String getName() {
    return name;
  }

  @ValidatorField(key="login.userName", required=true)
  public void setName(final String userName) {
    this.name = userName;
  }
}
