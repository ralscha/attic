package ch.ess.cal.web.options;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;

@Option(id = "timeCustomerProjetsOptions")
public class TimeCustomerProjectOptions extends AbstractOptions {

  private TimeProjectDao timeProjectDao;

  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
    this.timeProjectDao = timeProjectDao;
  }

  @Override
  public void init(final HttpServletRequest request) {
    String projectId = (String)getTagAttributes().get("projectId");
    TimeProject project = timeProjectDao.findById(projectId);
    TimeCustomer customer = project.getTimeCustomer();

    for (TimeProject prj : customer.getTimeProjects()) {
      add(prj.getName(), prj.getId().toString());
    }

    sort();
  }

}
