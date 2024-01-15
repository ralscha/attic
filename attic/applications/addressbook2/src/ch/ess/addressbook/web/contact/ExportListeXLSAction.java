package ch.ess.addressbook.web.contact;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForward;
import org.apache.struts.util.MessageResources;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.Contact;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.util.ExcelGeneratorHSSF;
import ch.ess.common.web.HibernateAction;
import ch.ess.common.web.WebContext;


/** 
  * @struts.action path="/export" validate="false"
  */
public class ExportListeXLSAction extends HibernateAction {

  public ActionForward doAction() throws Exception {
    WebContext ctx = WebContext.currentContext();
    
    HttpSession session = ctx.getSession();

    List resultList = (List)session.getAttribute(Constants.RESULT_ID+"ab");
    if (resultList == null) {
      return null;
    }
    
    ctx.getResponse().setContentType("application/vnd.ms-excel");

    OutputStream out = ctx.getResponse().getOutputStream();
    MessageResources messages = getResources(ctx.getRequest());
    Locale locale = getLocale(ctx.getRequest());

    ExcelGeneratorHSSF generator = new ExcelGeneratorHSSF();

    List enumList = AttributeEnum.getEnumList();

    List myEnumList = new ArrayList(enumList);
    myEnumList.remove(AttributeEnum.WIDTH);
    myEnumList.remove(AttributeEnum.HEIGHT);
    myEnumList.remove(AttributeEnum.ORIGINAL_HEIGHT);
    myEnumList.remove(AttributeEnum.ORIGINAL_WIDTH);
    myEnumList.remove(AttributeEnum.CONTENT_TYPE);

    List colHeader = new ArrayList();

    Iterator it = myEnumList.iterator();
    while (it.hasNext()) {
      AttributeEnum element = (AttributeEnum)it.next();
      colHeader.add(messages.getMessage(locale, element.getName()));
    }
    Object[] row = new Object[colHeader.size()];

    generator.setColumnHeader(colHeader);

    for (int i = 0; i < resultList.size(); i++) {

      DynaBean obj = (DynaBean)resultList.get(i);

      Long id = (Long)obj.get("id");
      Contact contact = (Contact)HibernateSession.currentSession().load(Contact.class, id);

      it = myEnumList.iterator();
      int col = 0;
      while (it.hasNext()) {
        AttributeEnum attr = (AttributeEnum)it.next();

        String value = (String)contact.getAttributes().get(attr.getName());

        row[col] = value;
        col++;
      }
      generator.addRow(row);
    }

    generator.generate(out);
    out.close();
    return null;
  }

}