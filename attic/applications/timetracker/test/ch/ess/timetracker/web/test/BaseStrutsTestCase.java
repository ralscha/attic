package ch.ess.timetracker.web.test;

import java.util.Locale;

import javax.servlet.jsp.jstl.core.Config;

import org.apache.cactus.WebRequest;
import org.apache.cactus.client.authentication.FormAuthentication;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.struts.Globals;
import org.apache.struts.util.RequestUtils;

import servletunit.struts.CactusStrutsTestCase;
import ch.ess.common.Constants;
import ch.ess.common.web.ClientInfo;
import ch.ess.timetracker.db.User;
import ch.ess.timetracker.resource.AppConfig;

/**
 * @author sr
 */
public class BaseStrutsTestCase extends CactusStrutsTestCase {

  static {
    BasicConfigurator.configure();
  }
  
  public BaseStrutsTestCase(String name) {
    super(name);
  }
  
  protected void authenticate(WebRequest theRequest) {
    theRequest.setRedirectorName("ServletRedirectorSecure");
    theRequest.setAuthentication(new FormAuthentication("admin", DigestUtils.shaHex("admin")));
  }
  
  public void setUp() throws Exception {
    super.setUp();

    
    User user = User.find("admin");
    
    Locale locale = user.getLocale();
    session.setAttribute(Globals.LOCALE_KEY, locale);
    Config.set(session, Config.FMT_LOCALE, locale);

    int timeoutInMinutes = AppConfig.getIntegerProperty(AppConfig.SESSION_TIMEOUT, 30);
    session.setMaxInactiveInterval(timeoutInMinutes * 60);

    ClientInfo info = new ClientInfo();
    RequestUtils.populate(info, request);
    session.setAttribute(Constants.CLIENT_INFO, info);
  }
  
  
}
