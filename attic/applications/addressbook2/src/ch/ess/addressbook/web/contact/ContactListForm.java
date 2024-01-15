package ch.ess.addressbook.web.contact;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
  * @struts.form name="searchContactForm"
  */
public class ContactListForm extends ActionForm {

  private String addcontact;
  private String searchButton;
  private String searchfield;
  private String category;
  private String all;

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

  public String getAll() {
    return all;
  }

  public void setAll(String string) {
    all = string;
  }

  public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest resquest) {
    return null;
  }

  public void reset(ActionMapping actionMapping, HttpServletRequest request) {

    if ((request.getParameter("search") != null) && (request.getParameter("addcontact") == null)) {
      searchfield = null;
      category = null;
      all = null;
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
