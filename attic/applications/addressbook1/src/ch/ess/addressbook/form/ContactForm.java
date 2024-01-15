package ch.ess.addressbook.form;

import javax.servlet.http.*;

import org.apache.commons.lang.builder.*;
import org.apache.struts.action.*;
import org.apache.struts.upload.*;
import org.apache.struts.validator.*;

import ch.ess.addressbook.db.*;

public class ContactForm extends ValidatorForm  {

  private FormFile uploadFile;
  private boolean delete;
  
  private Contact contact;
  
  public ContactForm() {
    contact = null;
  }
  

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    uploadFile = null;
    delete = false;
    
    if (contact != null) {
      contact.getAttributes().clear();
    }
  }
  

  
  public Object getValue(String key) {
    return contact.getAttributes().get(key);
  }

  public void setValue(String key, Object value) {
    if (value instanceof String) {
      if (((String)value).trim().equals(ch.ess.Constants.BLANK_STRING)) {
        value = null;
      }
    }
    
    if (value != null) {
      contact.getAttributes().put(key, value);
    } else {
      contact.getAttributes().remove(key);
    }
  }  


  public void setBirthday(String birthday) {
    setValue(AttributeEnum.BIRTHDAY.getName(), birthday);
  }
  
  public String getBirthday() {
    return (String)getValue(AttributeEnum.BIRTHDAY.getName());
  }
  
  
  public void setEmail(String email) {
    setValue(AttributeEnum.EMAIL.getName(), email);
  }
  
  public String getEmail() {
    return (String)getValue(AttributeEnum.EMAIL.getName());
  }
       
  public FormFile getUploadFile() {
    return uploadFile;
  }

  public void setUploadFile(FormFile uploadFile) {
    this.uploadFile = uploadFile;
  }

  public boolean isDelete() {
    return delete;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
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
