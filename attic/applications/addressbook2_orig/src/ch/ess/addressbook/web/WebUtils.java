package ch.ess.addressbook.web;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import ch.ess.addressbook.db.User;

public class WebUtils {
  public static User getUser(HttpServletRequest request) throws HibernateException {
    String userName = request.getUserPrincipal().getName();
    return User.find(userName);
  }
}
