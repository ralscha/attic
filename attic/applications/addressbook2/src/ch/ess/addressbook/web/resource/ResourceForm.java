package ch.ess.addressbook.web.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ch.ess.addressbook.db.TextResource;
import ch.ess.addressbook.resource.text.Resource;
import ch.ess.addressbook.resource.text.TextResources;
import ch.ess.addressbook.resource.text.Variable;
import ch.ess.common.Constants;
import ch.ess.common.util.Util;
import ch.ess.common.web.NameEntry;
import ch.ess.common.web.PersistentForm;

/** 
  * @author  Ralph Schaer
  * @version $Revision: 1.2 $ $Date: 2004/04/03 15:44:42 $
  *  
  * @struts.form name="resourceForm"
  */
public class ResourceForm extends PersistentForm {

  private NameEntry[] entries;
  private Map variables;
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

  public Map getVariables() {
    return variables;
  }

  public void setVariables(Map map) {
    variables = map;
  }  
  
  protected void fromForm() {    
    
    Map translations = new HashMap();
    
    for (int i = 0; i < getEntries().length; i++) {
      NameEntry entry = getEntries()[i];
      Locale l = Util.getLocale(entry.getLocale());
      String na = entry.getName();
      
      translations.put(l, na);
        
    }
    TextResource resource = (TextResource)getPersistent();
   
    resource.setTranslations(translations);
    
  }

  protected void toForm() {
    
    TextResource resource = (TextResource)getPersistent();
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
    
    variables = new TreeMap();
    Resource res = (Resource)TextResources.getResources().get(resource.getName());
    
    name = (String)res.getDescription().get(getLocale());     

    
    List l = res.getVariableList();
    if (l != null) {
      for (it = l.iterator(); it.hasNext();) {
        Variable var = (Variable)it.next();
        variables.put(var.getName(), var.getDescription().get(getLocale()));
      }
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
