package ch.ess.cal.web.category;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.db.Category;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.NameEntry;
import ch.ess.common.web.PersistentForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.09.2003 
  * @struts.form name="categoryForm"
  */
public class CategoryForm extends PersistentForm {

  private NameEntry[] entries;
  private String name;
  
  public NameEntry[] getEntries() {
    return entries;
  }

  public void setEntries(NameEntry[] entries) {
    this.entries = entries;
  }
  
  public void setEntry(int i, NameEntry entry) {
    entries[i] = entry;
  }

  public NameEntry getEntry(int i) {
    return this.entries[i];
  }

  public String getName() {
    return name;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="category.colour"
   */
  public void setColour(String colour) {
    ((Category)getPersistent()).setColour(getTrimmedString(colour));
  }

  public String getColour() {
    return ((Category)getPersistent()).getColour();
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="category.bwColour"
   */
  public void setBwColour(String colour) {
    ((Category)getPersistent()).setBwColour(getTrimmedString(colour));
  }

  public String getBwColour() {
    return ((Category)getPersistent()).getBwColour();
  }


  public void setIcalKey(String colour) {
    ((Category)getPersistent()).setIcalKey(getTrimmedString(colour));
  }

  public String getIcalKey() {
    return ((Category)getPersistent()).getIcalKey();
  }


  public void setUnknown(boolean unknown) {
    ((Category)getPersistent()).setUnknown(unknown);
  }

  public boolean isUnknown() {
    return ((Category)getPersistent()).isUnknown();
  }

  
  protected void fromForm() {    
    
    Map translations = new HashMap();
    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      Locale l = Util.getLocale(entry.getLocale());
      String nameLocal = entry.getName();
      
      translations.put(l, nameLocal);
        
    }
    Category category = (Category)getPersistent();
    category.setTranslations(translations);
    
  }

  protected void toForm() {
    
    Category category = (Category)getPersistent();
    Map translations = category.getTranslations();
    NameEntry[] entriesL = new NameEntry[translations.size()];
    
    Iterator it = translations.entrySet().iterator();
    int ix = 0;
    while (it.hasNext()) {
      Map.Entry en = (Map.Entry)it.next();
                  
      NameEntry entry = new NameEntry();
      String localeStr = ((Locale)en.getKey()).toString();
      entry.setLanguage(getMessages().getMessage(getLocale(), localeStr));
      entry.setLocale(localeStr);
      entry.setName((String)en.getValue());

      entriesL[ix] = entry;
      ix++;
    }
          
    name = (String)translations.get(getLocale());
    setEntries(entriesL);
  }
  
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
    ActionErrors errors = super.validate(mapping, request);
    if (errors == null) {
      errors = new ActionErrors();
    }
    
    
    boolean ok = true;
    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      if (GenericValidator.isBlankOrNull(entry.getName())) {
        ok = false;
        break;
      }
    }
    
    if (!ok) {
      errors.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_FILL_ALL);
    }

    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }


  


}
