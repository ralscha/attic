package ch.ess.addressbook.web.contact;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.hibernate.Transaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.lucene.index.Term;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.CategoryCount;
import ch.ess.addressbook.db.Contact;
import ch.ess.addressbook.util.UploadManager;
import ch.ess.addressbook.web.contact.search.UpdateContactRunnable;
import ch.ess.common.Constants;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.search.SearchEngine;
import ch.ess.common.util.ImageInfo;
import ch.ess.common.web.DispatchAction;


/** 
  * 
  * @struts.action path="/editContact" name="contactForm" input=".default" scope="session" validate="false"
  * @struts.action path="/storeContact" name="contactForm" input=".contact.edit" scope="session" validate="true" parameter="store"
  * @struts.action path="/deleteContact" parameter="delete" validate="false"
  *
  * @struts.action-forward name="edit" path=".contact.edit"
  * @struts.action-forward name="list" path="/default.do"
  * @struts.action-forward name="searchagain" path="/searchContact.do"
  * @struts.action-forward name="delete" path="/deleteContact.do" 
  * @struts.action-forward name="reload" path="/editContact.do?action=edit" 
  */
public class ContactEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
      
    ContactForm contactForm = (ContactForm)form;

    Contact newContact = new Contact();
    newContact.setAttributes(new HashMap());

    contactForm.setContact(newContact);

    return mapping.findForward(Constants.FORWARD_EDIT);
  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {

    String idstr = request.getParameter(Constants.OBJECT_ID);
    Contact contact;
        
    if (!GenericValidator.isBlankOrNull(idstr)) {
      Long contactId = new Long(idstr);
      contact = (Contact)HibernateSession.currentSession().load(Contact.class, contactId);
    } else {
      ContactForm contactForm = (ContactForm)request.getSession().getAttribute("contactForm");
      contact = contactForm.getContact();    
    }
    
    if (contact != null) {
      Term t = new Term(Constants.SEARCH_ID, contact.getId().toString());
      SearchEngine.deleteTerm(t);
      
      UploadManager.delete(contact.getId().longValue());
      
      CategoryCount cc = (CategoryCount)request.getSession().getServletContext().getAttribute("categoryCount");
      cc.dec(contact.getCategory());

      contact.delete();
      addOneMessage(request, Constants.ACTION_MESSAGE_DELETE_OK);
    }

    return mapping.findForward("searchagain");


  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response)
    throws Exception {
      
    ContactForm contactForm = (ContactForm)form;
    Long contactId = getId(request);

    Contact contact = (Contact)HibernateSession.currentSession().load(Contact.class, contactId);

    contactForm.setContact(contact);

    return mapping.findForward(Constants.FORWARD_EDIT);
  }

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpServletResponse response,
    Transaction tx)
    throws Exception {

    ContactForm contactForm = (ContactForm)form;

    if (isCancelled(request)) {
      return mapping.findForward(Constants.FORWARD_LIST);
    }

    Contact contact = contactForm.getContact();
    

    if (contactForm.isDeletePicture()) {
      if (contact.getId() != null) {
        contact.getAttributes().remove(AttributeEnum.WIDTH.getName());
        contact.getAttributes().remove(AttributeEnum.HEIGHT.getName());
        contact.getAttributes().remove(AttributeEnum.ORIGINAL_WIDTH.getName());
        contact.getAttributes().remove(AttributeEnum.ORIGINAL_HEIGHT.getName());
        contact.getAttributes().remove(AttributeEnum.CONTENT_TYPE.getName());

        UploadManager.delete(contact.getId().longValue());
      }
    }
   
    CategoryCount cc = (CategoryCount)request.getSession().getServletContext().getAttribute("categoryCount");
    cc.updateCategoryCount(contact);
    

    ActionForward forward = save(mapping, request, tx, contactForm.getContact());
    if ((contactForm.getUploadFile() != null) && (contactForm.getUploadFile().getFileSize() > 0)) {
      saveFile(contactForm, contact);
    }
    
    if (forward == null) {
      new UpdateContactRunnable(contact.getId()).run();
      return mapping.findForward(Constants.FORWARD_EDIT);
    } else {
      return forward;
    }  
  
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
      contact.getAttributes().put(
        AttributeEnum.ORIGINAL_WIDTH.getName(),
        String.valueOf(Math.max(ii.getWidth() + 40, 150)));
      contact.getAttributes().put(
        AttributeEnum.ORIGINAL_HEIGHT.getName(),
        String.valueOf(Math.max(ii.getHeight() + 40, 150)));

      contact.getAttributes().put(AttributeEnum.CONTENT_TYPE.getName(), contactForm.getUploadFile().getContentType());

    }

    contactForm.getUploadFile().destroy();
    contactForm.setUploadFile(null);
  }

}