package ch.ess.cal.web.group;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.annotation.struts.Role;
import ch.ess.base.service.TranslationService;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.model.Group;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

@Role("$admin")
public class GroupListAction extends AbstractListAction {

  private GroupDao groupDao;
  private TranslationService translationService;

  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    SimpleListDataModel dataModel = new SimpleListDataModel();

    MapForm searchForm = (MapForm)ctx.form();
    Locale locale = getLocale(ctx.request());

    String groupName = null;
    if (searchForm != null) {
      groupName = searchForm.getStringValue("groupName");
    }

    List<Group> groups = null;
    if (groupName != null) {
      groups = groupDao.find(locale, groupName);
    } else {
      groups = groupDao.findAll();
    }

    for (Group group : groups) {

      DynaBean dynaBean = new LazyDynaBean("groupList");

      dynaBean.set("id", group.getId().toString());
      dynaBean.set("groupName", translationService.getText(group, locale));
      
      dynaBean.set("eventGroup", group.getEventGroup());
      dynaBean.set("taskGroup", group.getTaskGroup());
      dynaBean.set("documentGroup", group.getDocumentGroup());
      dynaBean.set("timeGroup", group.getTimeGroup());
      
      dynaBean.set("deletable", groupDao.canDelete(group));
      
      dataModel.add(dynaBean);

    }

    dataModel.sort("groupName", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {    
    groupDao.delete(key);
  }
  
  @Override
  public String getTitle(String id, ActionContext ctx) {
    if (StringUtils.isNotBlank(id)) {
      Group group = groupDao.findById(id);
      if (group != null) {
        Locale locale = getLocale(ctx.request());
        return translationService.getText(group, locale);
      }
    }
    return null;
  } 
}
