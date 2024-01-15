package ch.ess.cal.web.options;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.Group;


@Option(id = "groupOptions")
public class GroupOptions extends AbstractOptions {

  private GroupDao groupDao;
  private TranslationService translationService;

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    String documentGroup = (String)getTagAttributes().get("documentGroup");
    String timeGroup = (String)getTagAttributes().get("timeGroup");
    
    List<Group> groupList;
    
    if ("true".equals(documentGroup)) {
      groupList = groupDao.findDocumentGroups();
    } else if ("true".equals(timeGroup)) {
      groupList = groupDao.findTimeGroups();
    } else {
      groupList = groupDao.findAll();
    }
    

    Locale locale = getLocale(request);

    for (Group group : groupList) {
      add(translationService.getText(group, locale), group.getId().toString());
    }

    sort();
  }

}
