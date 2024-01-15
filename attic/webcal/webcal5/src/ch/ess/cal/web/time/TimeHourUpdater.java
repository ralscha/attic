package ch.ess.cal.web.time;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.annotations.Param;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.cal.CalUtil;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;
import ch.ess.spring.annotation.Autowire;
import ch.ess.spring.annotation.SpringBean;

@RemoteProxy(name = "timeHourUpdater", creator = SpringCreator.class, creatorParams = @Param(name = "beanName", value = "timeHourUpdater"))
@SpringBean(id = "timeHourUpdater", autowire = Autowire.BYTYPE)
public class TimeHourUpdater {

  private TimeTaskDao timeTaskDao;
  private TimeProjectDao timeProjectDao;
  private TimeCustomerDao timeCustomerDao;
  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;

  public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
    this.timeTaskDao = timeTaskDao;
  }

  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }

  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
    this.timeProjectDao = timeProjectDao;
  }
  
  public void setUserConfigurationDao(UserConfigurationDao userConfigurationDao) {
    this.userConfigurationDao = userConfigurationDao;
  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }
  

  @RemoteMethod
  public Map<String, String> updateTimeTaskHour(String timeTaskId, HttpSession session) {

    Map<String, String> resultMap = new HashMap<String, String>();

    if (StringUtils.isNumeric(timeTaskId) && StringUtils.isNotEmpty(timeTaskId)) {
      
      User user = Util.getUser(session, userDao);
      Config userConfig = userConfigurationDao.getUserConfig(user);
      
      TimeTask timeTask = timeTaskDao.findById(timeTaskId);
      if (timeTask.getHourRate() != null) {
        resultMap.put("hourRate", Constants.TWO_DECIMAL_FORMAT.format(CalUtil.hourRateTimeFactor(timeTask.getHourRate(), userConfig)));
      } else if (timeTask.getTimeProject().getHourRate() != null) {
        resultMap.put("hourRate", Constants.TWO_DECIMAL_FORMAT.format(CalUtil.hourRateTimeFactor(timeTask.getTimeProject().getHourRate(), userConfig)));
      } else if (timeTask.getTimeProject().getTimeCustomer().getHourRate() != null) {
        resultMap.put("hourRate", Constants.TWO_DECIMAL_FORMAT.format(CalUtil.hourRateTimeFactor(timeTask.getTimeProject().getTimeCustomer().getHourRate(), userConfig)));
      }
    }

    return resultMap;

  }

  @RemoteMethod
  public Map<String, String> updateTimeProjectHour(String timeProjectId, HttpSession session) {

    Map<String, String> resultMap = new HashMap<String, String>();

    if (StringUtils.isNumeric(timeProjectId) && StringUtils.isNotEmpty(timeProjectId)) {
      
      User user = Util.getUser(session, userDao);
      Config userConfig = userConfigurationDao.getUserConfig(user);
      
      TimeProject timeProject = timeProjectDao.findById(timeProjectId);
      if (timeProject.getHourRate() != null) {
        resultMap.put("hourRate", Constants.TWO_DECIMAL_FORMAT.format(CalUtil.hourRateTimeFactor(timeProject.getHourRate(), userConfig)));
      } else if (timeProject.getTimeCustomer().getHourRate() != null) {
        resultMap.put("hourRate", Constants.TWO_DECIMAL_FORMAT.format(CalUtil.hourRateTimeFactor(timeProject.getTimeCustomer().getHourRate(), userConfig)));
      }
    }

    return resultMap;

  }

  @RemoteMethod
  public Map<String, String> updateTimeCustomerHour(String timeCustomerId, HttpSession session) {

    Map<String, String> resultMap = new HashMap<String, String>();

    if (StringUtils.isNumeric(timeCustomerId) && StringUtils.isNotEmpty(timeCustomerId)) {
      
      User user = Util.getUser(session, userDao);
      Config userConfig = userConfigurationDao.getUserConfig(user);
      
      TimeCustomer timeCustomer = timeCustomerDao.findById(timeCustomerId);
      if (timeCustomer.getHourRate() != null) {             
        resultMap.put("hourRate", Constants.TWO_DECIMAL_FORMAT.format(CalUtil.hourRateTimeFactor(timeCustomer.getHourRate(), userConfig)));
      }
    }

    return resultMap;
  }
  
  @RemoteMethod
  public Map<String, String> updateBudgetTask(String timeTaskId, HttpSession session, HttpServletRequest request) {
    Map<String, String> resultMap = new HashMap<String, String>();
    
    if (StringUtils.isNumeric(timeTaskId) && StringUtils.isNotEmpty(timeTaskId)) {
      
      TimeTask timeTask = timeTaskDao.findById(timeTaskId);
      User user = Util.getUser(session, userDao);
      
      BigDecimal budgetProject = timeProjectDao.getBudget(timeTask.getTimeProject().getId());
      BigDecimal budgetTask = timeTaskDao.getBudget(timeTask.getId());
      
      if (budgetProject != null) {
        BigDecimal costProject = timeProjectDao.getCost(timeTask.getTimeProject().getId());
        if (costProject == null) {
          costProject = new BigDecimal(0);
        }
        BigDecimal diffProject = budgetProject.subtract(costProject);
        
        ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources", user.getLocale());
        String msgBudget = rb.getString("task.budget.prj");
        String msgBudgetOpen = rb.getString("task.budget.open.prj");        
        String budgetStr = MessageFormat.format(msgBudget, Constants.TWO_DECIMAL_FORMAT.format(budgetProject));
        String budgetOpenStr = MessageFormat.format(msgBudgetOpen, Constants.TWO_DECIMAL_FORMAT.format(diffProject));
    
        resultMap.put("budget", budgetStr);
        resultMap.put("budgetOpen", budgetOpenStr);     
        
        
      } else if (budgetTask != null) {
        BigDecimal costTask = timeTaskDao.getCost(timeTask.getId());
        if (costTask == null) {
          costTask = new BigDecimal(0);        
        }
        BigDecimal diffTask = budgetTask.subtract(costTask);
        
        ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources", user.getLocale());
        String msgBudget = rb.getString("task.budget");
        String msgBudgetOpen = rb.getString("task.budget.open");                
        String budgetStr = MessageFormat.format(msgBudget, Constants.TWO_DECIMAL_FORMAT.format(budgetTask));
        String budgetOpenStr = MessageFormat.format(msgBudgetOpen, Constants.TWO_DECIMAL_FORMAT.format(diffTask));
    
        resultMap.put("budget", budgetStr);
        resultMap.put("budgetOpen", budgetOpenStr);            
        
      }     
            
    }
    
    return resultMap;
  }
  
  @RemoteMethod
  public Map<String, String> updateBudgetProject(String timeProjectId, HttpSession session, HttpServletRequest request) {
    Map<String, String> resultMap = new HashMap<String, String>();
    
    if (StringUtils.isNumeric(timeProjectId) && StringUtils.isNotEmpty(timeProjectId)) {
      
      TimeProject timeProject = timeProjectDao.findById(timeProjectId);
      User user = Util.getUser(session, userDao);
      
      BigDecimal budgetProject = timeProjectDao.getBudget(timeProject.getId());
      
      if (budgetProject != null) {
        BigDecimal costProject = timeProjectDao.getCost(timeProject.getId());
        if (costProject == null) {
          costProject = new BigDecimal(0);
        }
        BigDecimal diffProject = budgetProject.subtract(costProject);
        
        ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources", user.getLocale());
        String msgBudget = rb.getString("task.budget.prj");
        String msgBudgetOpen = rb.getString("task.budget.open.prj");        
        String budgetStr = MessageFormat.format(msgBudget, Constants.TWO_DECIMAL_FORMAT.format(budgetProject));
        String budgetOpenStr = MessageFormat.format(msgBudgetOpen, Constants.TWO_DECIMAL_FORMAT.format(diffProject));
    
        resultMap.put("budget", budgetStr);
        resultMap.put("budgetOpen", budgetOpenStr);     
        
        
      }  
            
    }
    
    return resultMap;
  }  

}
