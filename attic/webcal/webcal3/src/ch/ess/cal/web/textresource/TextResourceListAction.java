package ch.ess.cal.web.textresource;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;

import ch.ess.cal.model.TextResource;
import ch.ess.cal.persistence.TextResourceDao;
import ch.ess.cal.web.AbstractListAction;
import ch.ess.cal.web.DynaListDataModel;
import ch.ess.cal.xml.textresource.Resource;
import ch.ess.cal.xml.textresource.TextResources;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

/** 
 * @author sr
 * @version $Revision: 1.1 $ $Date: 2005/05/09 07:46:08 $ 
 * 
 * @struts.action path="/listTextResource" roles="$textresource"
 * @struts.action-forward name="success" path="/textresourcelist.jsp"
 * @struts.action-forward name="edit" path="/editTextResource.do?id={0}" redirect="true"
 * 
 * @spring.bean name="/listTextResource" lazy-init="true"
 * @spring.property name="attribute" value="textresources"
 */
public class TextResourceListAction extends AbstractListAction {

  private TextResources textResources;
  private TextResourceDao textResourceDao;

  /** 
   * @spring.property reflocal="textResources"
   */
  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }

  /** 
   * @spring.property reflocal="textResourceDao"
   */
  public void setTextResourceDao(final TextResourceDao textResourceDao) {
    this.textResourceDao = textResourceDao;
  }

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {
    Locale locale = getLocale(ctx.request());
    DynaListDataModel dataModel = new DynaListDataModel();

    List<TextResource> allResources = textResourceDao.list();

    for (TextResource textResource : allResources) {

      String name = textResource.getName();
      Resource resource = textResources.getResources().get(name);

      DynaBean dynaBean = new LazyDynaBean("textResourceList");
      dynaBean.set("id", textResource.getId().toString());
      dynaBean.set("name", resource.getDescription().get(locale));

      dataModel.add(dynaBean);
    }

    dataModel.sort("name", SortOrder.ASCENDING);

    return dataModel;
  }

  @Override
  public boolean deleteObject(final ControlActionContext ctx, final String key) throws Exception {
    return textResourceDao.deleteCond(key);
  }

}
