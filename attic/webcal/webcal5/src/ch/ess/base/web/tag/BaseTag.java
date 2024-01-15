package ch.ess.base.web.tag;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.web.UserPrincipal;

import com.cc.framework.security.SecurityUtil;

public abstract class BaseTag extends TagSupport {

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
    return userDao.findById(userPrincipal.getUserId());
  }

}
