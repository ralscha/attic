package ch.ess.addressbook.web.contact;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.Contact;
import ch.ess.addressbook.db.TextResource;
import ch.ess.addressbook.web.contact.search.SearchContact;
import ch.ess.common.Constants;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;

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
          new DynaProperty("email", String.class),
          new DynaProperty("info", String.class),
          });

  }

  public ActionForward doAction() throws Exception {

    WebContext ctx = WebContext.currentContext();

    String resource = TextResource.getTextResource(getLocale(ctx.getRequest()), "info.contact");
    
    ContactListForm searchForm = (ContactListForm)ctx.getForm();

    if (!GenericValidator.isBlankOrNull(searchForm.getAddcontact())) {
      return findForward("addContact");
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

    //Abf�llen
    List resultDynaList = new ArrayList();

    for (Iterator it = resultIterator; it.hasNext();) {
      Contact contact = (Contact)it.next();
      DynaBean dynaBean = resultClass.newInstance();
      dynaBean.set("id", contact.getId());
      dynaBean.set("firstName", contact.getAttributes().get(AttributeEnum.FIRST_NAME.getName()));
      dynaBean.set("lastName", contact.getAttributes().get(AttributeEnum.LAST_NAME.getName()));
      dynaBean.set("company", contact.getAttributes().get(AttributeEnum.COMPANY_NAME.getName()));
      dynaBean.set("email", contact.getAttributes().get(AttributeEnum.EMAIL.getName()));

      dynaBean.set("info", getInfo(createVelocityContext(contact), resource));
      
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

    ctx.getSession().setAttribute(Constants.RESULT_ID + "ab", resultDynaList);

    if (resultDynaList.isEmpty()) {
      addOneMessageRequest(Constants.ACTION_MESSAGE_NO_RESULTS);
    }

    return findForward(Constants.FORWARD_SUCCESS);

  }
  
  private VelocityContext createVelocityContext(Contact contact) {
    Map defaultMap = new HashMap();
    
    Iterator it = AttributeEnum.iterator();
    while (it.hasNext()) {
      AttributeEnum ae = (AttributeEnum)it.next();
      defaultMap.put(ae.getName(), "");      
    }

    defaultMap.putAll(contact.getAttributes());
    
    VelocityContext ctx = new VelocityContext(defaultMap);
    

    return ctx;
  }

  private String getInfo(VelocityContext context, String resource)
    throws ParseErrorException, MethodInvocationException, ResourceNotFoundException, IOException {
    StringWriter w = new StringWriter();
    Velocity.evaluate(context, w, "log", resource);

    String output = w.getBuffer().toString();
    
    StringBuffer sb = new StringBuffer(output.length());
    for (int i = 0; i < output.length(); i++) {
      char c = output.charAt(i);
      if ((c != '\n') && (c != '\r')) {
        sb.append(c);
      }
      
    }
    
    return sb.toString();
  }
}
