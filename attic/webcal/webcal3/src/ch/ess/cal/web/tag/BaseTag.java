package ch.ess.cal.web.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.web.UserPrincipal;

import com.cc.framework.security.SecurityUtil;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/05/15 11:05:32 $
 */
public class BaseTag extends TagSupport {

  private ApplicationContext ctx;

  public Object getBean(final String name) {

    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    }
    return ctx.getBean(name);

  }

  public User getUser() {
    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(pageContext.getSession());
    UserDao userDao = (UserDao)getBean("userDao");
    User user = userDao.get(userPrincipal.getUserId());
    return user;
  }

}
