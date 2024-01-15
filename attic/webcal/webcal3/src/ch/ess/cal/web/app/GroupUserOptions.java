package ch.ess.cal.web.app;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.Util;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.User;
import ch.ess.cal.persistence.UserDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:10 $ 
 * 
 * @spring.bean name="groupUserOptions" singleton="false" 
 */
public class GroupUserOptions extends AbstractOptions {

  private UserDao userDao;
  private TranslationService translationService;

  /**
   * @spring.property reflocal="userDao"
   */
  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public void init(final HttpServletRequest request) {

    User user = Util.getUser(request.getSession(), userDao);

    Locale locale = getLocale(request);
    Set<Group> userGroups = user.getGroups();
    for (Group group : userGroups) {
      add(translationService.getText(group, locale), group.getId().toString());
    }

    sort();
  }

}
