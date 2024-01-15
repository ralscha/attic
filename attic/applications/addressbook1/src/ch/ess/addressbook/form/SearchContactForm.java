package ch.ess.addressbook.form;

import javax.servlet.http.*;

import org.apache.commons.lang.builder.*;
import org.apache.struts.action.*;

public class SearchContactForm extends ActionForm {

  private String addcontact;
  private String searchButton;
  private String searchfield;
  private String category;

  public String getAddcontact() {
    return addcontact;
  }

  public void setAddcontact(String addcontact) {
    this.addcontact = addcontact;
  }

  public String getSearchButton() {
    return searchButton;
  }

  public void setSearchButton(String search) {
    this.searchButton = search;
  }

  public String getSearchfield() {
    return searchfield;
  }

  public void setSearchfield(String searchfield) {
    this.searchfield = searchfield;
  }

  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest resquest) {
    return null;
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    
    if ((request.getParameter("search") != null) 
         && (request.getParameter("addcontact") == null)) {
      searchfield = null;
      category = null; 
    }      
    addcontact = null;
  }

  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

}
