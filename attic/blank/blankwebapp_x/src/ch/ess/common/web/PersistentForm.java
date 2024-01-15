package ch.ess.common.web;

import net.sf.hibernate.HibernateException;
import ch.ess.common.db.Persistent;

/**
 * @author Ralph Schaer
 * @version $Revision: 1.4 $ $Date: 2004/05/22 15:38:03 $
 */
public abstract class PersistentForm extends BaseForm {
  private Persistent persistent;
  private boolean deletable;
  private boolean readonly;

  public PersistentForm() {
    persistent = null;
  }

  public Long getId() {
    if (persistent != null) {
      return persistent.getId();
    }
    return null;
  }

  public boolean isDeletable() {
    return deletable;
  }

  public boolean isReadonly() {
    return readonly;
  }

  public void setReadonly(boolean flag) {
    readonly = flag;
  }

  public Persistent getPersistentFromForm() throws HibernateException {
    fromForm();
    return persistent;
  }

  public void setPersistentToForm(Persistent persistent) throws HibernateException {
    this.persistent = persistent;

    if (persistent != null) {
      deletable = persistent.canDelete();
    }

    toForm();
  }

  protected Persistent getPersistent() {
    return persistent;
  }

  protected abstract void fromForm() throws HibernateException;

  protected abstract void toForm() throws HibernateException;

}