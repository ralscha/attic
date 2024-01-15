package ch.ess.addressbook.web.contact;

import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.lucene.index.Term;
import org.apache.struts.action.ActionForward;

import ch.ess.addressbook.db.AttributeEnum;
import ch.ess.addressbook.db.CategoryCount;
import ch.ess.addressbook.db.Contact;
import ch.ess.addressbook.util.UploadManager;
import ch.ess.addressbook.web.contact.search.UpdateContactRunnable;
import ch.ess.common.Constants;
import ch.ess.common.db.Persistent;
import ch.ess.common.search.SearchEngine;
import ch.ess.common.util.ImageInfo;
import ch.ess.common.web.PersistentAction;
import ch.ess.common.web.WebContext;

/** 
  * @struts.action path="/newContact" name="contactForm" input=".default" scope="session" validate="false" parameter="add" 
  * @struts.action path="/editContact" name="contactForm" input=".default" scope="session" validate="false" parameter="edit"
  * @struts.action path="/storeContact" name="contactForm" input=".contact.edit" scope="session" validate="true" parameter="store"
  * @struts.action path="/deleteContact" parameter="delete" validate="false"
  *
  * @struts.action-forward name="edit" path=".contact.edit"
  * @struts.action-forward name="list" path="/searchContact.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteContact.do" 
  * @struts.action-forward name="reload" path="/editContact.do" 
  */
public class ContactEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {

    Contact contact;

    if (id != null) {
      contact = Contact.load(id);
    } else {
      ContactForm contactForm = (ContactForm)WebContext.currentContext().getSession().getAttribute("contactForm");
      contact = (Contact)contactForm.getPersistentFromForm();
    }

    if (contact != null) {
      Term t = new Term(Constants.SEARCH_ID, contact.getId().toString());
      SearchEngine.deleteTerm(t);

      UploadManager.delete(contact.getId().longValue());

      CategoryCount cc =
        (CategoryCount)WebContext.currentContext().getSession().getServletContext().getAttribute("categoryCount");
      cc.dec(contact.getCategory());
    }

    return Contact.delete(contact.getId());
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Contact.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    Contact newContact = new Contact();
    newContact.setAttributes(new HashMap());
    return newContact;
  }

  //overriden method
  protected ActionForward store() throws Exception {
    WebContext ctx = WebContext.currentContext();

    ContactForm contactForm = (ContactForm)ctx.getForm();

    if (isCancelled(ctx.getRequest())) {
      return findForward(Constants.FORWARD_LIST);
    }

    Contact contact = (Contact)contactForm.getPersistentFromForm();

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

    CategoryCount cc = (CategoryCount)ctx.getServletContext().getAttribute("categoryCount");
    cc.updateCategoryCount(contact);

    ActionForward forward = save(contact);
    if ((contactForm.getUploadFile() != null) && (contactForm.getUploadFile().getFileSize() > 0)) {
      saveFile(contactForm, contact);
    }

    if (forward == null) {
      new UpdateContactRunnable(contact.getId()).run();
      return findForward(Constants.FORWARD_EDIT);
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