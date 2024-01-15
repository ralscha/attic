package ch.ess.addressbook.web.contact;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.Contact;
import ch.ess.addressbook.util.UploadManager;


/** 
  * @struts.action path="/showPicture" validate="false"
  */
public class ShowPictureAction extends Action {

  
  public ActionForward execute(ActionMapping mapping, ActionForm form, 
                               HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    ContactForm cf = (ContactForm)session.getAttribute("contactForm");
    Contact contact = (Contact)cf.getPersistentFromForm();
  
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