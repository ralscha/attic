package ch.ess.examples;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.validator.ValidatorForm;

/** 
 * @struts.form name="waitActionForm"
 */
public class WaitActionForm extends ValidatorForm {

  private int seconds = 10;

  public int getSeconds() {
    return seconds;
  }

  /**   
   * @struts.validator type="required,integer"
   * @struts.validator-arg position="0" key="Seconds"
   */
  public void setSeconds(int i) {
    seconds = i;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}