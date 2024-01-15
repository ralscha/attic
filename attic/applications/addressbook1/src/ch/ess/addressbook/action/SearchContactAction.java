package ch.ess.addressbook.action;

import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.struts.action.*;

import ch.ess.addressbook.common.*;
import ch.ess.addressbook.db.*;
import ch.ess.addressbook.form.*;
import ch.ess.addressbook.search.*;
import ch.ess.tag.table.*;
import ch.ess.util.*;

public class SearchContactAction extends ListHibernateAction {


  public static final String MODEL = "listmodel";
  
  public static final int COL_ID = 0;
  public static final int COL_FIRSTNAME = 1;
  public static final int COL_LASTNAME = 2;
  public static final int COL_COMPANY = 3;
  public static final int COL_PHONE = 4;
  public static final int COL_EMAIL = 5;
  
  

  public ActionForward execute(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    net.sf.hibernate.Session hSession)
    throws Exception {

    SearchContactForm searchForm = (SearchContactForm) form;
    Util.blank2nullBean(searchForm);

    if (searchForm.getAddcontact() != null) {
      return mapping.findForward("addContact");
    }

    EventlessTableModel dtm = new EventlessTableModel();
    dtm.setColumnCount(6);
    Object[] oArray = new Object[dtm.getColumnCount()];

    String category = searchForm.getCategory();

    List resultList = new ArrayList();

    //Search
    if (category != null) {

      resultList = hSession.find("from Contact as contact where contact.category = ?", category, Hibernate.STRING);

    } else if (searchForm.getSearchfield() != null) {
      List tmpList = SearchEngine.search(searchForm.getSearchfield());

      if (tmpList != null) {
        Iterator it = tmpList.iterator();
        while (it.hasNext()) {
          String id = (String) it.next();

          Contact contact = (Contact) hSession.load(Contact.class, new Long(id));
          resultList.add(contact);
        }
      }
    } else {
      resultList = hSession.find("from Contact as contact");      
    }

    //Abfüllen
    Iterator it = resultList.iterator();
    while (it.hasNext()) {
      Contact element = (Contact) it.next();

      oArray[COL_ID] = element.getId();
      oArray[COL_FIRSTNAME] = element.getAttributes().get(AttributeEnum.FIRST_NAME.getName());
      oArray[COL_LASTNAME] = element.getAttributes().get(AttributeEnum.LAST_NAME.getName());
      oArray[COL_COMPANY] = element.getAttributes().get(AttributeEnum.COMPANY_NAME.getName());

      //phone
      String phone = (String) element.getAttributes().get(AttributeEnum.BUSINESS_NUMBER.getName());
      if (phone == null) {
        phone = (String) element.getAttributes().get(AttributeEnum.HOME_NUMBER.getName());
      }
      if (phone == null) {
        phone = (String) element.getAttributes().get(AttributeEnum.HOME_NUMBER_2.getName());
      }
      if (phone == null) {
        phone = (String) element.getAttributes().get(AttributeEnum.MOBILE_NUMBER.getName());
      }
      if (phone == null) {
        phone = (String) element.getAttributes().get(AttributeEnum.OFFICE_NUMBER_2.getName());
      }
      if (phone == null) {
        phone = (String) element.getAttributes().get(AttributeEnum.COMPANY_MAIN_PHONE_NUMBER.getName());
      }
      oArray[COL_PHONE] = phone;

      oArray[COL_EMAIL] = (String) element.getAttributes().get(AttributeEnum.EMAIL.getName());

      removeNulls(oArray);
      dtm.addRow(oArray);

    }

    JSPTableModel tableModel = createJSPTableModel(request.getSession(), dtm, MODEL, true);
    tableModel.addSortColumn(COL_LASTNAME, "a");
    tableModel.sort();

    addEmptyListErrorMessage(dtm, request, "KeineAdressengefunden");
    return mapping.findForward("success");
  }

}
