package ch.ess.cal.admin.web.holiday;


import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.util.RequestUtils;
import org.apache.taglibs.display.TableDecorator;

import ch.ess.cal.db.Holiday;

import com.ibm.icu.text.DateFormatSymbols;

public class HolidayDecorator extends TableDecorator {

  private DateFormatSymbols symbols;

  public String getName() throws JspException  {
    Holiday obj = (Holiday)this.getObject();        
        
    String s = RequestUtils.message(getPageContext(), null, null, obj.getName());
    if (s != null) {
      return s;
    } else {
      return "";
    }
    
  }
  
  public String getMonth() {
    Holiday obj = (Holiday)this.getObject();
    
    if (obj.getMonth() != null) {
      int month = obj.getMonth().intValue();
      return symbols.getMonths()[month];
    } 
    
    return "";
  }
  
  public String getCountry() {
    Holiday obj = (Holiday)this.getObject();
    String country = obj.getCountry();
    if (country != null) {
      return country;
    } else {
      return "";
    }
  }
  
  public String getCheck() {
    Holiday obj = (Holiday)this.getObject(); 
    String result = "<input type=\"checkbox\" name=\"check\" value=\""+obj.getHolidayId()+"\"";
    
    if (obj.isActive()) {
      result += " checked";
    }
    result += ">";
    
    if (obj.isActive()) {
      result += "<input type=\"hidden\" name=\"activeId\" value=\""+obj.getHolidayId()+"\">";
    } else {
      result += "<input type=\"hidden\" name=\"notactiveId\" value=\""+obj.getHolidayId()+"\">";
    }
    
    return result;         
  }

  public void init(PageContext ctx, Object list) {    
    super.init(ctx, list);
    
    Locale locale = RequestUtils.retrieveUserLocale(ctx, null);
    symbols = new DateFormatSymbols(locale);
  }

}
