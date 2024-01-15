package ch.ess.cal.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.LabelValueBean;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.AppLinkDao;
import ch.ess.base.model.AppLink;
import ch.ess.base.web.options.AbstractOptions;

@Option(id = "appLinkOptions")
public class AppLinkOptions extends AbstractOptions {
  private AppLinkDao appLinkDao;

  public void setAppLinkDao(final AppLinkDao appLinkDao) {
    this.appLinkDao = appLinkDao;
  }

  @Override
  public void init(final HttpServletRequest request) {
	
	List<AppLink> test = appLinkDao.findAll();
	
	for (AppLink link : test){
		add(new LabelValueBean(link.getLinkName(), link.getAppLink()));
	}
	
	sort();
	
  }

}
