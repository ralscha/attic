package ch.ess.cal.web.options;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.model.Group;


@Option(id = "groupUserOptions")
public class GroupUserOptions extends AbstractOptions {

  private UserDao userDao;
  private TranslationService translationService;

  public void setUserDao(final UserDao userDao) {
    this.userDao = userDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public void init(final HttpServletRequest request) {

    String eventGroup = (String)getTagAttributes().get("eventGroup");
    String taskGroup = (String)getTagAttributes().get("taskGroup");
    
    
    User user = Util.getUser(request.getSession(), userDao);

    Locale locale = getLocale(request);
    Set<Group> userGroups = user.getGroups();
    for (Group group : userGroups) {
      if (("true".equals(eventGroup) && group.getEventGroup()) || 
          ("true".equals(taskGroup) && group.getTaskGroup()) ||
          (eventGroup == null && taskGroup == null)) {
        add(translationService.getText(group, locale), group.getId().toString());
      }
    }

    sort();
  }

}
