package ch.ess.cal.web.preferences;

import java.util.Locale;

import net.sf.hibernate.Session;

import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import ch.ess.cal.common.Options;

import com.ibm.icu.util.TimeZone;

public class TimezoneOptions extends Options {


  public TimezoneOptions(Session sess, Locale locale, MessageResources messages) {
    super(sess, locale, messages);
    init();
  }

  public void init() {
    
    String[] ids = TimeZone.getAvailableIDs();
        
    
    for (int i = 0; i < ids.length; i++) {
      getLabelValue().add(new LabelValueBean(ids[i], ids[i]));
    }    
      
  }

}
