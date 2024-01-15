package ch.ess.blank.web.usergroup;

import java.net.MalformedURLException;

import net.sf.hibernate.HibernateException;
import ch.ess.common.web.ListDecorator;

public class UserGroupDecorator extends ListDecorator {

  public String getEdit() throws MalformedURLException {
    return getEditString("UserGroup");
  }

  public String getDelete() throws MalformedURLException, HibernateException {
    Boolean canDelete = getBooleanProperty("canDelete");

    if (canDelete.booleanValue()) {
      return getDeleteString("UserGroup", "groupName");
    }
    return getEmptyString();

  }

}