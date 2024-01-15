package ch.ess.cal.admin.web.category;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.commons.lang.builder.*;
import org.apache.commons.validator.*;
import org.apache.struts.action.*;
import org.apache.struts.validator.*;

import ch.ess.cal.db.*;

public class CategoryForm extends ValidatorForm {

  private Category category;  
 
  public CategoryForm() {
    category = null;    
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
        
    if (category != null) {
      category.setName(null);
      category.setColour(null);
      category.setBwColour(null);
      category.setDescription(null);
      category.setIcalKey(null);
      category.setUnknown(false);                
    }
        
  }

  public void setName(String name) {
    if (!GenericValidator.isBlankOrNull(name)) {
      category.setName(name);
    } else {
      category.setName(null);
    }
  }

  public String getName() {
    return category.getName();
  }
  

  public void setIcalKey(String ical) {
    if (!GenericValidator.isBlankOrNull(ical)) {
      category.setIcalKey(ical);
    } else {
      category.setIcalKey(null);
    }
  }

  public String getIcalKey() {
    return category.getIcalKey();
  }
  
  
  public void setBwColour(String colour) {
    if (!GenericValidator.isBlankOrNull(colour)) {
      category.setBwColour(colour);
    } else {
      category.setBwColour(null);
    }
  }

  public String getBwColour() {
    return category.getBwColour();
  }  
  
  public void setColour(String colour) {
    if (!GenericValidator.isBlankOrNull(colour)) {
      category.setColour(colour);
    } else {
      category.setColour(null);
    }
  }

  public String getColour() {
    return category.getColour();
  }  

  public void setDescription(String description) {
    if (!GenericValidator.isBlankOrNull(description)) {
      category.setDescription(description);
    } else {
      category.setDescription(null);
    }
  }

  public String getDescription() {
    return category.getDescription();
  }


  public boolean isUnknown() {
    return category.isUnknown();
  }

  public void setUnknown(boolean b) {
    category.setUnknown(b);
  }
  

  public Category getCategory(Session sess) throws HibernateException {
    
    getFromForm(sess);
        
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;

    if (category != null) {
      setToForm();
    }
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  
  private void setToForm() {    
  }

  private void getFromForm(Session sess) throws HibernateException {
    if (category.getIcalKey() == null) {
      category.setIcalKey(category.getName().toUpperCase());
    }  
  }
  

}
