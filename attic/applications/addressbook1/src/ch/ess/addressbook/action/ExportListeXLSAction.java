package ch.ess.addressbook.action;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;
import org.apache.struts.util.*;

import ch.ess.addressbook.common.*;
import ch.ess.addressbook.db.*;
import ch.ess.tag.table.*;

/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.2 $ $Date: 2003/08/27 06:40:47 $
 * @author $Author: sr $
 */
public class ExportListeXLSAction extends ListHibernateAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, HttpServletResponse response,
                               net.sf.hibernate.Session sess) throws Exception {

    HttpSession session = request.getSession(true);

    JSPTableModel model = (JSPTableModel) session.getAttribute(SearchContactAction.MODEL);
    response.setContentType("application/vnd.ms-excel");

    OutputStream out = response.getOutputStream();
    MessageResources messages = getResources(request);
    Locale locale = getLocale(request);

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
      AttributeEnum element = (AttributeEnum) it.next();
      colHeader.add(messages.getMessage(locale, element.getName()));
    }
    Object[] row = new Object[colHeader.size()];

    generator.setColumnHeader(colHeader);

    for (int i = 0; i < model.getTotalRowCount(); i++) {

      Long id = (Long) model.getValueAt(i, SearchContactAction.COL_ID);
      Contact contact = (Contact) sess.load(Contact.class, id);

      it = myEnumList.iterator();
      int col = 0;
      while (it.hasNext()) {
        AttributeEnum attr = (AttributeEnum) it.next();

        String value = (String) contact.getAttributes().get(attr.getName());

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