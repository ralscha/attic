package ch.ess.examples;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.validator.ValidatorForm;

/** 
  * @struts.form name="sendMailForm"
  */
public class SendMailForm extends ValidatorForm {

  private String subject;
  private String text;
  private String recipient;

  public String getRecipient() {
    return recipient;
  }

  public String getSubject() {
    return subject;
  }

  public String getText() {
    return text;
  }

  /**   
   * @struts.validator type="required,email"
   * @struts.validator-arg position="0" key="Recipient"
   */
  public void setRecipient(String string) {
    recipient = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="Subject"
   */
  public void setSubject(String string) {
    subject = string;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-arg position="0" key="Text"
   */
  public void setText(String string) {
    text = string;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}
