package ch.ess.cal.web.category;

import java.util.Iterator;
import java.util.List;

import net.sf.hibernate.Hibernate;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Category;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003
  *
  * @struts.action path="/newCategory" name="categoryForm" input=".category.list" scope="session" validate="false" parameter="add" roles="admin"
  * @struts.action path="/editCategory" name="categoryForm" input=".category.list" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action path="/storeCategory" name="categoryForm" input=".category.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteCategory" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".category.edit"
  * @struts.action-forward name="list" path="/listCategory.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteCategory.do" 
  * @struts.action-forward name="reload" path="/editCategory.do" 
  */
public class CategoryEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Category.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Category.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Category();
  }
  protected ActionForward save(Persistent persist)
    throws HibernateException {
    
    ActionForward forward = super.save(persist);
    if (forward == null) {
      Category category = (Category)persist;
      if (category.isUnknown()) {
        Transaction t = HibernateSession.currentSession().beginTransaction();
        try {
          //alle anderen unknowns auf false setzen
          List r = HibernateSession.currentSession().find("from Category as category where category.unknown = true and category.id <> ?", 
                              category.getId(), Hibernate.LONG);
          for (Iterator it = r.iterator(); it.hasNext();) {
            Category cat = (Category)it.next();
            cat.setUnknown(false);
          }      
          
          t.commit();
        } catch (HibernateException e) {
          HibernateSession.rollback(t);
          throw e;        
        }
      }
    
    }
    return forward;
  }

}