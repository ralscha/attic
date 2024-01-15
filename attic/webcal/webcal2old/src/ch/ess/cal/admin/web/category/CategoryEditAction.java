package ch.ess.cal.admin.web.category;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.ObjectNotFoundException;
import net.sf.hibernate.Session;
import net.sf.hibernate.StaleObjectStateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import ch.ess.cal.Constants;
import ch.ess.cal.common.DispatchAction;
import ch.ess.cal.db.Category;

public class CategoryEditAction extends DispatchAction {

  protected ActionForward add(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages)
    throws Exception {

    CategoryForm categoryForm = (CategoryForm)form;

    Category newCategory = new Category();
    categoryForm.setCategory(newCategory);

    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

  protected ActionForward delete(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess)
    throws Exception {

    Long categoryId = new Long(request.getParameter(Constants.OBJECT_ID));
    Category category = null;

    try {
      category = (Category)sess.load(Category.class, categoryId);
    } catch (ObjectNotFoundException onfe) {
      //do nothing
    }

    if (category != null) {
      sess.delete(category);
    }

    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_DELETE_OK);
    return mapping.findForward(Constants.FORWARD_SUCCESS);

  }

  protected ActionForward edit(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess)
    throws Exception {

    CategoryForm categoryForm = (CategoryForm)form;
    String idStr = request.getParameter(Constants.OBJECT_ID);

    Long categoryId = null;

    if (idStr != null) {
      categoryId = new Long(idStr);
    } else {
      //concurrent access
      categoryId = (Long)session.getAttribute(Constants.CONCURRENT_ID);
      session.removeAttribute(Constants.CONCURRENT_ID);
      messages.add(ActionErrors.GLOBAL_ERROR, Constants.ACTION_MESSAGE_CONCURRENT);
    }

    Category category = (Category)sess.load(Category.class, categoryId);
    Hibernate.initialize(category);
    categoryForm.setCategory(category);

    return mapping.findForward(Constants.FORWARD_SUCCESS);
  }

  protected ActionForward store(
    ActionMapping mapping,
    ActionForm form,
    HttpServletRequest request,
    HttpSession session,
    ActionMessages messages,
    Session sess,
    Transaction tx)
    throws Exception {

    CategoryForm categoryForm = (CategoryForm)form;

    if (isCancelled(request)) {
      categoryForm.setCategory(null);
      tx.commit();
      return mapping.findForward(Constants.FORWARD_CANCEL);
    }
    
    Category category = categoryForm.getCategory(sess);    
    sess.saveOrUpdate(category);

    if (category.isUnknown()) {
      //alle anderen unknowns auf false setzen
      List r = sess.find("from Category as category where category.unknown = true and category.id <> ?", 
                          category.getCategoryId(), Hibernate.LONG);
      for (Iterator it = r.iterator(); it.hasNext();) {
        Category cat = (Category)it.next();
        cat.setUnknown(false);
      }      
    }

    try {
      tx.commit();
    } catch (StaleObjectStateException sose) {
      // concurrent access
      tx.rollback();
      categoryForm.setCategory(null);
      session.setAttribute(Constants.CONCURRENT_ID, category.getCategoryId());
      return mapping.findForward(Constants.FORWARD_RELOAD);
    }
    


    messages.add(ActionMessages.GLOBAL_MESSAGE, Constants.ACTION_MESSAGE_UPDATE_OK);
    return mapping.findForward(Constants.FORWARD_SAVEANDBACK);

  }

}