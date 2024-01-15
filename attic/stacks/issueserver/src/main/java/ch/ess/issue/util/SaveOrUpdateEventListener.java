package ch.ess.issue.util;

import org.hibernate.HibernateException;
import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveEventListener;
import ch.ess.issue.entity.AbstractEntity;

public class SaveOrUpdateEventListener extends DefaultSaveEventListener {

  @Override
  public void onSaveOrUpdate(SaveOrUpdateEvent event) throws HibernateException {
    AbstractEntity abstractEntity = (AbstractEntity)event.getObject();
    abstractEntity.uid();
    super.onSaveOrUpdate(event);
  }
}
