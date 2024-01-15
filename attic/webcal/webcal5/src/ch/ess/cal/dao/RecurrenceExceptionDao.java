package ch.ess.cal.dao;

import ch.ess.base.dao.AbstractDao;
import ch.ess.cal.model.RecurrenceException;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;


@SpringBean(id = "recurrenceExceptionDao", autowire = Autowire.BYTYPE) 
public class RecurrenceExceptionDao extends AbstractDao<RecurrenceException> {

  public RecurrenceExceptionDao() {
    super(RecurrenceException.class);
  }
}