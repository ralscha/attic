package ch.ess.cal.web.calresource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.db.Resource;
import ch.ess.cal.db.ResourceGroup;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.NameEntry;
import ch.ess.common.web.PersistentForm;

/** 
  * @author  Ralph Schaer
  * @version 1.0, 30.09.2003 
  * @struts.form name="calResourceForm"
  */
public class CalResourceForm extends PersistentForm {

  private NameEntry[] entries;
  private String name;
  private Long resourceGroupId;

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

  public Long getResourceGroupId() {
    return resourceGroupId;
  }

  /**   
   * @struts.validator type="required"
   * @struts.validator-args arg0resource="resourcegroup"
   */
  public void setResourceGroupId(Long long1) {
    resourceGroupId = long1;
  }

  protected void fromForm() throws HibernateException {    
    
    Map translations = new HashMap();
    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      Locale l = Util.getLocale(entry.getLocale());
      String na = entry.getName();      
      translations.put(l, na);        
    }
    
    Resource resource = (Resource)getPersistent();
    resource.setTranslations(translations);
    
    ResourceGroup g = ResourceGroup.load(resourceGroupId);
    resource.setResourceGroup(g);
    
  }

  protected void toForm() {
    
    Resource resource = (Resource)getPersistent();
    Map translations = resource.getTranslations();
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
    
    setEntries(entriesL);
        
    name = (String)resource.getTranslations().get(getLocale());
    
    if (resource.getResourceGroup() != null) {
      resourceGroupId = resource.getResourceGroup().getId();
    } else {
      resourceGroupId = null;
    }     

        
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
