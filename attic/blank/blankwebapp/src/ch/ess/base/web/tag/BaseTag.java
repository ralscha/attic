package ch.ess.base.web.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.Constants;
import ch.ess.base.model.User;
import ch.ess.base.persistence.UserDao;
import ch.ess.base.web.UserPrincipal;

/**
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/07/03 04:48:22 $
 */
public abstract class BaseTag extends TagSupport {

  private ApplicationContext ctx;

  public Object getBean(final String name) {

    if (ctx == null) {
      ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext.getServletContext());
    }
    return ctx.getBean(name);

  }

  public User getUser() {
    
    UserPrincipal userPrincipal = (UserPrincipal)pageContext.getSession().getAttribute(Constants.USER_SESSION);
    UserDao userDao = (UserDao)getBean("userDao");
    User user = userDao.get(userPrincipal.getUserId());
    return user;
  }

}