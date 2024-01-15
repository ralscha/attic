package ch.ess.cal.web.holiday;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Transaction;

import org.apache.struts.action.ActionForward;

import ch.ess.cal.db.Holiday;
import ch.ess.cal.resource.HolidayRegistry;
import ch.ess.common.db.HibernateSession;
import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;


/** 
  * @author  Ralph Schaer
  * @version 1.0, 28.09.2003 
  *
  * @struts.action path="/newHoliday" name="holidayForm" input=".holiday.list" scope="session" validate="false" parameter="add" roles="admin" 
  * @struts.action path="/editHoliday" name="holidayForm" input=".holiday.list" scope="session" validate="false" parameter="edit" roles="admin"
  * @struts.action path="/storeHoliday" name="holidayForm" input=".holiday.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteHoliday" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".holiday.edit"
  * @struts.action-forward name="list" path="/listHoliday.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteHoliday.do" 
  * @struts.action-forward name="reload" path="/editHoliday.do" 
  */
public class HolidayEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Holiday.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Holiday.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Holiday();
  }
  protected ActionForward save(Persistent persist)
    throws HibernateException {
    
    ActionForward forward = super.save(persist);
    
    if (forward == null) { // ok
      Transaction t = HibernateSession.currentSession().beginTransaction();
      try {
        HolidayRegistry.addHoliday((Holiday)persist);
        t.commit();
      } catch (HibernateException e) {
        HibernateSession.rollback(t);
        throw e;        
      }
    }
    
    return forward;
    
  }

}