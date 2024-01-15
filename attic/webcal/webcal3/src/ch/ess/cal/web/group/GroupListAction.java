package ch.ess.cal.web.group;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.Group;
import ch.ess.cal.persistence.GroupDao;
import ch.ess.cal.service.impl.TranslationService;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.web.MapForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.5 $ $Date: 2005/04/04 11:31:12 $ 
 * 
 * @struts.action path="/listGroup" roles="$group" name="mapForm" input="/grouplist.jsp" scope="session" validate="false"
 * @struts.action-forward name="success" path="/grouplist.jsp" 
 * @struts.action-forward name="edit" path="/editGroup.do?id={0}" redirect="true"
 * @struts.action-forward name="create" path="/editGroup.do" redirect="true"
 * 
 * @spring.bean name="/listGroup" lazy-init="true"
 * @spring.property name="attribute" value="groups"
 */
public class GroupListAction extends AbstractListAction {

  private GroupDao groupDao;
  private TranslationService translationService;

  /**    
   * @spring.property reflocal="groupDao"
   */
  public void setGroupDao(final GroupDao groupDao) {
    this.groupDao = groupDao;
  }

  /**    
   * @spring.property reflocal="translationService"
   */
  public void setTranslationService(TranslationService translationService) {
    this.translationService = translationService;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

    DynaListDataModel dataModel = new DynaListDataModel();

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
      groups = groupDao.list();
    }

    for (Group group : groups) {

      DynaBean dynaBean = new LazyDynaBean("groupList");

      dynaBean.set("id", group.getId().toString());
      dynaBean.set("groupName", translationService.getText(group, locale));

      dataModel.add(dynaBean);

    }

    dataModel.sort("groupName", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return groupDao.deleteCond(key);
  }
}
