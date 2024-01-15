package ch.ess.task.web.project;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import ch.ess.common.util.Util;
import ch.ess.common.web.NameEntry;
import ch.ess.common.web.PersistentForm;
import ch.ess.task.db.Project;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 13.09.2003 
  * @struts.form name="projectForm"
  */
public class ProjectForm extends PersistentForm {

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

  
  protected void fromForm() {    
    
    Map translations = new HashMap();
    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      Locale l = Util.getLocale(entry.getLocale());
      String na = entry.getName();
      
      translations.put(l, na);
        
    }
    Project project = (Project)getPersistent();   
    project.setTranslations(translations);    
    
  }

  protected void toForm() {
    
    Project project = (Project)getPersistent();
    Map translations = project.getTranslations();
    NameEntry[] entriesl = new NameEntry[translations.size()];
    
    Iterator it = translations.entrySet().iterator();
    int ix = 0;
    while (it.hasNext()) {
      Map.Entry en = (Map.Entry)it.next();
                  
      NameEntry entry = new NameEntry();
      String localeStr = ((Locale)en.getKey()).toString();
      entry.setLanguage(getMessages().getMessage(getLocale(), localeStr));
      entry.setLocale(localeStr);
      entry.setName((String)en.getValue());

      entriesl[ix] = entry;
      ix++;
    }    
    
    name = (String)translations.get(getLocale());
    setEntries(entriesl);
    
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
      errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.fillallrequiredfields"));
    }

    if (errors.isEmpty()) {
      return null;
    } else {
      return errors;
    }
  }


  


}
