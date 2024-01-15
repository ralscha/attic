package ch.ess.cal.web.options;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.Util;
import ch.ess.base.annotation.option.Option;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;

@Option(id = "timeCustomerProjectTaskOptions")
public class TimeCustomerProjectTaskOptions extends AbstractOptions {

  private TimeCustomerDao timeCustomerDao;
  private UserDao userDao;
  private Config appConfig;
  
  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }
  
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  
  public void setAppConfig(final Config appConfig) {
	this.appConfig = appConfig;
  } 

  @Override
  public void init(final HttpServletRequest request) {

    String userCustomer = (String)getTagAttributes().get("userCustomer");
    
    clear();

    //add empty

    DynaBean dynaBean = new LazyDynaBean();
    dynaBean.set("idCustomer", "");
    dynaBean.set("customer", "");
    dynaBean.set("idProject", "");
    dynaBean.set("project", "");
    dynaBean.set("idTask", "");
    dynaBean.set("task", "");
    addObject(dynaBean);

    List<Object[]> customerList;
    if ("true".equals(userCustomer)) {
      User user = Util.getUser(request.getSession(), userDao);
      customerList = timeCustomerDao.findWithTasks(user);
    } else {
      customerList = timeCustomerDao.findWithTasks();
    }

    Set<Integer> customerSet = new HashSet<Integer>();
    Set<Integer> projectSet = new HashSet<Integer>();

    for (Iterator<Object[]> it = customerList.iterator(); it.hasNext();) {
      Object[] resultArray = it.next();

      TimeCustomer customer = (TimeCustomer)resultArray[0];
      TimeProject project = (TimeProject)resultArray[1];
      TimeTask task = (TimeTask)resultArray[2];

      if (!customerSet.contains(customer.getId()) && customer.isActive()) {
        dynaBean = new LazyDynaBean();
        dynaBean.set("idCustomer", customer.getId().toString());
        setCustomerString(dynaBean, customer);     
        dynaBean.set("idProject", "");
        dynaBean.set("project", "");
        dynaBean.set("idTask", "");
        dynaBean.set("task", "");
        addObject(dynaBean);
        customerSet.add(customer.getId());
      }

      if (project != null && project.isActive()) {
        if (!projectSet.contains(project.getId())) {
          dynaBean = new LazyDynaBean();
          dynaBean.set("idCustomer", customer.getId().toString());
          setCustomerString(dynaBean, customer);
          dynaBean.set("idProject", project.getId().toString());
          setProjectString(dynaBean, project);
          dynaBean.set("idTask", "");
          dynaBean.set("task", "");
          addObject(dynaBean);
          projectSet.add(project.getId());
        }
      
      
        if (task != null && task.isActive()) {
          dynaBean = new LazyDynaBean();
          dynaBean.set("idCustomer", customer.getId().toString());
          setCustomerString(dynaBean, customer);
          dynaBean.set("idProject", project.getId().toString());
          setProjectString(dynaBean, project);
          dynaBean.set("idTask", task.getId().toString());
          dynaBean.set("task", task.getName());
          addObject(dynaBean);
        }
      }
    }

  }
  
  private void setCustomerString(DynaBean dynaBean, TimeCustomer customer) {
	  	if(StringUtils.isNotBlank(customer.getCustomerNumber())){
	  		dynaBean.set("customer", 
	  				appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<") +
	  				customer.getCustomerNumber() +
	  				appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">") + " " +
	  				customer.getName()
	  				);
	  	} else {
	  		dynaBean.set("customer", customer.getName());
	  	}
	  }

	  private void setProjectString(DynaBean dynaBean, TimeProject project) {
	  	if(StringUtils.isNotBlank(project.getProjectNumber())){
	  		dynaBean.set("project", 
	  				appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<") +
	  				project.getProjectNumber() +
	  				appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">") + " " +
	  				project.getName()
	  				);
	  	} else {
	  		dynaBean.set("project", project.getName());
	  	}
	  }

}