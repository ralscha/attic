package ch.ess.cal.web.app;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.model.Group;
import ch.ess.cal.persistence.GroupDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractOptions;

/** 
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:11 $ 
 * 
 * @spring.bean name="groupOptions" singleton="false" 
 */
public class GroupOptions extends AbstractOptions {

  private GroupDao groupDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  /**
   * @spring.property reflocal="groupDao"
   */
  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Group> groupList = groupDao.list();

    Locale locale = getLocale(request);

    for (Group group : groupList) {
      add(translationService.getText(group, locale), group.getId().toString());
    }

    sort();
  }

}
