package ch.ess.base.web.textresource;

import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.struts.annotation.Forward;
import org.apache.struts.annotation.StrutsAction;

import ch.ess.base.model.TextResource;
import ch.ess.base.persistence.TextResourceDao;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.DynaListDataModel;
import ch.ess.base.xml.textresource.Resource;
import ch.ess.base.xml.textresource.TextResources;

/** 
 * @author sr
 * @version $Revision: 1.4 $ $Date: 2005/07/03 11:31:19 $ 
 * 
 * @spring.bean name="/listTextResource" lazy-init="true" autowire="byType"
 * @spring.property name="attribute" value="textresources"
 */
@StrutsAction(path = "/listTextResource", 
              roles = "$textresource", 
              forwards = {
                @Forward(name = "success", path = "/textresourcelist.jsp"),
                @Forward(name = "edit", path = "/editTextResource.do?id={0}", redirect = true)
              })
public class TextResourceListAction extends AbstractListAction {

  private TextResources textResources;
  private TextResourceDao textResourceDao;

  public void setTextResources(final TextResources textResources) {
    this.textResources = textResources;
  }

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
