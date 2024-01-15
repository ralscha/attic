package ch.ess.addressbook.action;

import java.io.*;

import javax.servlet.http.*;

import org.apache.struts.action.*;

import ch.ess.addressbook.db.*;
import ch.ess.addressbook.form.*;
import ch.ess.addressbook.resource.*;


/**
 * DOCUMENT ME!
 * 
 * @version $Revision: 1.1 $ $Date: 2003/03/31 06:38:35 $
 * @author $Author: sr $
 */
public class ShowPictureAction extends Action {

  
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    ContactForm cf = (ContactForm)session.getAttribute("contactForm");
    Contact contact = cf.getContact();
  
    if (contact.getId() != null) {
      String contentType = (String)cf.getValue(AttributeEnum.CONTENT_TYPE.getName());
      if (contentType != null) {
        response.setContentType(contentType);
        
        OutputStream out = response.getOutputStream();    
        UploadManager.read(contact.getId(), out);
        out.close();
      }      
    }
    
    return null;
  }


}