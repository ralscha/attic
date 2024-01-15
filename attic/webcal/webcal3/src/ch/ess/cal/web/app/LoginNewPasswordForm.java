package ch.ess.cal.web.app;

import com.cc.framework.adapter.struts.FWValidatorForm;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:06 $
 *  
 * @struts.form name="loginNewPasswordForm"
 */
public class LoginNewPasswordForm extends FWValidatorForm {

  private String name;

  public String getName() {
    return name;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="login.userName"
   */
  public void setName(final String userName) {
    this.name = userName;
  }
}
