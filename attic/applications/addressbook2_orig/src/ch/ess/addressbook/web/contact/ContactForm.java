package ch.ess.addressbook.web.contact;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.Contact;


/** 
  * @struts.form name="contactForm"
  */
public class ContactForm extends ValidatorForm {

  private FormFile uploadFile;
  private boolean deletePicture;

  private Contact contact;

  public ContactForm() {
    contact = null;
  }

  public Long getId() {
    if (contact != null) {
      return contact.getId();
    }

    return null;
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    uploadFile = null;
    deletePicture = false;

    if (contact != null) {
      contact.getAttributes().clear();
    }
  }

  public Object getValue(String key) {
    return contact.getAttributes().get(key);
  }

  public void setValue(String key, Object value) {
    if (value instanceof String) {
      if (GenericValidator.isBlankOrNull((String)value)) {
        value = null;
      }
    }

    if (value != null) {
      contact.getAttributes().put(key, value);
    } else {
      contact.getAttributes().remove(key);
    }
  }


  /**   
   * @struts.validator type="date"
   * @struts.validator-args arg0resource="birthday"
   * @struts.validator-var name="datePatternStrict" value="dd.MM.yyyy"
   */
  public void setBirthday(String birthday) {
    setValue(AttributeEnum.BIRTHDAY.getName(), birthday);
  }

  public String getBirthday() {
    return (String)getValue(AttributeEnum.BIRTHDAY.getName());
  }

  /**   
   * @struts.validator type="email"
   * @struts.validator-args arg0resource="email"
   */
  public void setEmail(String email) {
    setValue(AttributeEnum.EMAIL.getName(), email);
  }

  public String getEmail() {
    return (String)getValue(AttributeEnum.EMAIL.getName());
  }

  public FormFile getUploadFile() {
    return uploadFile;
  }

  /**   
   * @struts.validator type="contentType"
   * @struts.validator-args arg0resource="File"
   * @struts.validator-var name="accept" value="image/tiff,image/jpeg,image/pjpeg,image/gif,image/x-png"
   */
  public void setUploadFile(FormFile uploadFile) {
    this.uploadFile = uploadFile;
  }

  public boolean isDeletePicture() {
    return deletePicture;
  }

  public void setDeletePicture(boolean delete) {
    this.deletePicture = delete;
  }

  public Contact getContact() {
    return contact;
  }

  public void setContact(Contact ct) {
    contact = ct;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
