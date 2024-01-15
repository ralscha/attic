package ch.ess.cal.web;

import javax.servlet.http.*;

import org.apache.commons.logging.*;

public class CalUserSessionBinding implements HttpSessionBindingListener {

  private static final Log logger = LogFactory.getLog(CalUserSessionBinding.class);

  private CalUser user;

  public CalUserSessionBinding(CalUser user) {
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