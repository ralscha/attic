package ch.ess.addressbook.web.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.Contact;
import ch.ess.addressbook.web.contact.search.SearchContact;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;


/** 
  * @struts.action path="/searchContact" name="searchContactForm" input=".default" scope="session" validate="false"
  * @struts.action-forward name="success" path=".default" 
  */
public class ContactListAction extends HibernateAction {

  private static DynaClass resultClass;

  static {
    resultClass =
      new BasicDynaClass(
        "ResultClassContact",
        null,
        new DynaProperty[] {
          new DynaProperty("id", Long.class),
          new DynaProperty("firstName", String.class),
          new DynaProperty("lastName", String.class),
          new DynaProperty("company", String.class),
          new DynaProperty("phone", String.class),
          new DynaProperty("email", String.class)});

  }

  public ActionForward doAction(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    ContactListForm searchForm = (ContactListForm)form;

    if (!GenericValidator.isBlankOrNull(searchForm.getAddcontact())) {
      return mapping.findForward("addContact");
    }

    Iterator resultIterator;

    //Search
    if (!GenericValidator.isBlankOrNull(searchForm.getCategory())) {
      resultIterator = Contact.findWithCategory(searchForm.getCategory());
    } else if (!GenericValidator.isBlankOrNull(searchForm.getSearchfield())) {
      String ids = SearchContact.search(searchForm.getSearchfield());

      if (ids != null) {
        resultIterator = Contact.findWithIds(ids);        
      } else {
        resultIterator = Collections.EMPTY_LIST.iterator();
      }
    } else {
      searchForm.setAll("true");
      resultIterator = Contact.findAll();
    }

    //Abfüllen
    List resultDynaList = new ArrayList();

    for (Iterator it = resultIterator; it.hasNext();) {
      Contact contact = (Contact)it.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", contact.getId());
      dynaBean.set("firstName", contact.getAttributes().get(AttributeEnum.FIRST_NAME.getName()));
      dynaBean.set("lastName", contact.getAttributes().get(AttributeEnum.LAST_NAME.getName()));
      dynaBean.set("company", contact.getAttributes().get(AttributeEnum.COMPANY_NAME.getName()));
      dynaBean.set("email", contact.getAttributes().get(AttributeEnum.EMAIL.getName()));

      //phone
      String phone = (String)contact.getAttributes().get(AttributeEnum.BUSINESS_NUMBER.getName());
      if (phone == null) {
        phone = (String)contact.getAttributes().get(AttributeEnum.HOME_NUMBER.getName());
      }
      if (phone == null) {
        phone = (String)contact.getAttributes().get(AttributeEnum.HOME_NUMBER_2.getName());
      }
      if (phone == null) {
        phone = (String)contact.getAttributes().get(AttributeEnum.MOBILE_NUMBER.getName());
      }
      if (phone == null) {
        phone = (String)contact.getAttributes().get(AttributeEnum.OFFICE_NUMBER_2.getName());
      }
      if (phone == null) {
        phone = (String)contact.getAttributes().get(AttributeEnum.COMPANY_MAIN_PHONE_NUMBER.getName());
      }

      dynaBean.set("phone", phone);

      resultDynaList.add(dynaBean);
    }

    request.getSession().setAttribute(Constants.RESULT_ID+"ab", resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessage(request, Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

}
