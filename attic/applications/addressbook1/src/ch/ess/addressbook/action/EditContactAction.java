package ch.ess.addressbook.action;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import net.sf.hibernate.*;

import org.apache.lucene.index.*;
import org.apache.struts.action.*;

import ch.ess.addressbook.common.*;
import ch.ess.addressbook.db.*;
import ch.ess.addressbook.form.*;
import ch.ess.addressbook.resource.*;
import ch.ess.addressbook.search.*;
import ch.ess.util.*;

public class EditContactAction extends DispatchAction {

  protected ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpSession session)
    throws Exception {
    
    ContactForm contactForm = (ContactForm) form;
    
    Contact newContact = new Contact();
    newContact.setAttributes(new HashMap());
    
    contactForm.setContact(newContact);
          
    return mapping.findForward("success");
  }


  protected ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpSession session, Session sess)
    throws Exception {

    Long contactId = getId(request, SearchContactAction.MODEL, SearchContactAction.COL_ID);
    Contact contact = null;
        
    try {
      contact = (Contact) sess.load(Contact.class, contactId);
    } catch (ObjectNotFoundException onfe) {
      //onfe.printStackTrace();
    }

    if (contact != null) {
      Term t = new Term(SearchEngine.ID_FIELD, contactId.toString());
      SearchEngine.deleteTerm(t);
  
      CategoryCount cc = (CategoryCount) session.getServletContext().getAttribute("categoryCount");
      cc.dec(contact.getCategory());
  
      sess.delete(contact);
      
      ActionMessages messages = new ActionMessages();
      messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Kontaktgeloescht"));
      saveMessages(request, messages);      
      
    }
    
    return mapping.findForward("success");

  }


  protected ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpSession session, Session sess)
    throws Exception {

    ContactForm contactForm = (ContactForm) form;
    
    Long contactId = getId(request, SearchContactAction.MODEL, SearchContactAction.COL_ID);
    Contact contact = (Contact) sess.load(Contact.class, contactId);
    
    contactForm.setContact(contact);
    
    return mapping.findForward("success");
  }


  protected ActionForward store(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpSession session, Session sess)
    throws Exception {

    ContactForm contactForm = (ContactForm) form;

    if (isCancelled(request)) {
      contactForm.setContact(null);
      return mapping.findForward("cancel");
    }

    ActionErrors errors = new ActionErrors();
    ActionMessages messages = new ActionMessages();

    Contact contact = contactForm.getContact();

    if (contactForm.isDelete()) {
      if (contact.getId() != null) {
        contact.getAttributes().remove(AttributeEnum.WIDTH.getName());
        contact.getAttributes().remove(AttributeEnum.HEIGHT.getName());
        contact.getAttributes().remove(AttributeEnum.ORIGINAL_WIDTH.getName());
        contact.getAttributes().remove(AttributeEnum.ORIGINAL_HEIGHT.getName());
        contact.getAttributes().remove(AttributeEnum.CONTENT_TYPE.getName());

        UploadManager.delete(contact.getId().longValue());
      }
    }


    CategoryCount cc = (CategoryCount) session.getServletContext().getAttribute("categoryCount");
    cc.updateCategoryCount(contact);    

    sess.saveOrUpdate(contact);  
        
    if ((contactForm.getUploadFile() != null) && (contactForm.getUploadFile().getFileSize() > 0)) {
      saveFile(contactForm, contact);         
    }
      
    try {      
      HibernateManager.commit(sess);
    } catch (StaleObjectStateException sose) {
      contactForm.setContact(null);
      //contactForm.reset(mapping, request);      
      errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("AdresseConcurrent"));
      saveErrors(request, errors);
      
      return mapping.findForward("reload");
    }

    new UpdateAddressRunnable(contact.getId()).run();

    messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("UpdateOK"));
    saveMessages(request, messages);
    return mapping.findForward("saveandback");

  }



  private void saveFile(ContactForm contactForm, Contact contact) throws FileNotFoundException, IOException {
    long id = contact.getId().longValue();
    
    InputStream is = contactForm.getUploadFile().getInputStream();
    UploadManager.save(id, is);
    is.close();
    
    ImageInfo ii = UploadManager.getImageInfo(id);
    
    if (ii != null) {
    
      Dimension d = UploadManager.getImageDimension(ii);
        
      contact.getAttributes().put(AttributeEnum.WIDTH.getName(), String.valueOf(d.width));
      contact.getAttributes().put(AttributeEnum.HEIGHT.getName(), String.valueOf(d.height));
      contact.getAttributes().put(AttributeEnum.ORIGINAL_WIDTH.getName(), String.valueOf(Math.max(ii.getWidth() + 40, 150)));
      contact.getAttributes().put(AttributeEnum.ORIGINAL_HEIGHT.getName(), String.valueOf(Math.max(ii.getHeight() + 40, 150)));
    
      contact.getAttributes().put(AttributeEnum.CONTENT_TYPE.getName(), contactForm.getUploadFile().getContentType());
    
    }
    
    contactForm.getUploadFile().destroy();
    contactForm.setUploadFile(null);
  }

}