package ch.ess.cal.admin.web;

import javax.servlet.http.*;

import org.apache.commons.logging.*;

public class AdminUserSessionBinding implements HttpSessionBindingListener {

  private static final Log logger = LogFactory.getLog(AdminUserSessionBinding.class);

  private AdminUser user;

  public AdminUserSessionBinding(AdminUser user) {
    this.user = user;
  }

  public void valueBound(HttpSessionBindingEvent event) {
    //do nothing
  }

  public void valueUnbound(HttpSessionBindingEvent event) {

    if (user != null) {
      logger.debug("user logged off: " + user.getUserId());
    }
  }
}