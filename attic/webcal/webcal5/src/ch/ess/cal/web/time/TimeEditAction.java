package ch.ess.cal.web.time;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.RequestUtils;
import org.hibernate.criterion.Restrictions;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractEditAction;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;

@Role("$time")
public class TimeEditAction extends AbstractEditAction<Time> {

  private UserDao userDao;
  private UserConfigurationDao userConfigurationDao;
  private TimeTaskDao timeTaskDao;
  private TimeCustomerDao timeCustomerDao;
  private TimeProjectDao timeProjectDao;
  private Config appConfig;
  private String newTaskTime;

  public void setNewTaskTime(String newTaskTime) {
    this.newTaskTime = newTaskTime;
  }

  public String getNewTaskTime() {
    return newTaskTime;
  }  
  
  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

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
  
  public void setAppConfig(final Config appConfig) {
	this.appConfig = appConfig;
  }  
  
  @Override
  protected void afterCopy(ActionContext ctx, Time model) {
    TimeForm timeForm = (TimeForm)ctx.form();
    timeForm.setCost(null);    
  }
  
  @Override
  protected void afterNewItem(ActionContext ctx, ActionForm form) {
    TimeForm beforeTimeForm = (TimeForm)ctx.form();
    TimeForm timeForm = (TimeForm)form;
   
    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());
    if(getNewTaskTime()!= null && !getNewTaskTime().isEmpty() && ctx.request().getParameter("timeProjectId") != null){
      timeForm.setTaskTimeDate(getNewTaskTime());  
    } else{
      timeForm.setTaskTimeDate(df.format(new Date()));
    }
    User user = Util.getUser(ctx.session(), userDao);
    timeForm.setUserId(user.getId().toString());
    
       
    if (beforeTimeForm != null) {
      timeForm.setTimeCustomerId(beforeTimeForm.getTimeCustomerId());
      timeForm.setTimeProjectId(beforeTimeForm.getTimeProjectId());
      timeForm.setTimeTaskId(beforeTimeForm.getTimeTaskId());
      timeForm.setBudget(beforeTimeForm.getBudget());
      timeForm.setBudgetOpen(beforeTimeForm.getBudgetOpen());
    }    
    setVisibilityProjectTask(ctx);

  }
  

  @Override
  protected ActionErrors callValidate(final ActionContext ctx, final ActionForm form) {
	  if(((TimeForm) form).getHourRate() != null){
		  String amountForm = ((TimeForm) form).getHourRate();
		  int idx = amountForm.indexOf(".");
		  if(idx>=0){
			  String amount = amountForm.substring(0, idx).replace("'", "");
			  ((TimeForm) form).setHourRate(amount);		  
		  }
	  }
	  TimeForm timeForm = (TimeForm)ctx.form();
    return timeForm.validate(ctx.mapping(), ctx.request());
  }

  @Override
  public void formToModel(final ActionContext ctx, Time time) {
    TimeForm timeForm = (TimeForm)ctx.form();
    DateFormat df = new SimpleDateFormat(Constants.getParseDateFormatPattern());
    time.setUser(userDao.findById(timeForm.getUserId()));
    
    if(StringUtils.isNotBlank(timeForm.getActivity())) {
      time.setActivity(timeForm.getActivity());
    } else {
      time.setActivity(null);
    }

    if(StringUtils.isNotBlank(timeForm.getComment())) {
      time.setComment(timeForm.getComment());
    } else {
      time.setComment(null);
    }
    
    if(StringUtils.isNotBlank(timeForm.getChargesStyle())){
    	time.setChargesStyle(timeForm.getChargesStyle());
    } else{
    	time.setChargesStyle(null);
    }
    
    if(StringUtils.isNotBlank(timeForm.getAmount())){
    	time.setAmount(new BigDecimal(timeForm.getAmount()));
    } else {
    	time.setAmount(null);
    }

    try {
      time.setTaskTimeDate(df.parse(timeForm.getTaskTimeDate()));
      setNewTaskTime(timeForm.getTaskTimeDate());
    } catch (ParseException e) {
      //no action
    }

    if (GenericValidator.isInt(timeForm.getStartHour()) && GenericValidator.isInt(timeForm.getEndHour())) {

      Integer startHour = new Integer(timeForm.getStartHour());
      Integer endHour = new Integer(timeForm.getEndHour());

      time.setStartHour(startHour);
      time.setEndHour(endHour);

      Integer startMin = null;
      Integer endMin = null;

      if (GenericValidator.isInt(timeForm.getStartMin())) {
        startMin = new Integer(timeForm.getStartMin());
        time.setStartMin(startMin);
      } else {
        time.setStartMin(null);
      }

      if (GenericValidator.isInt(timeForm.getEndMin())) {
        endMin = new Integer(timeForm.getEndMin());
        time.setEndMin(endMin);
      } else {
        time.setEndMin(null);
      }
      Calendar start = new GregorianCalendar();
      start.set(Calendar.HOUR_OF_DAY, startHour.intValue());
      if (startMin != null) {
        start.set(Calendar.MINUTE, startMin.intValue());
      } else {
        start.set(Calendar.MINUTE, 0);
      }
      start.set(Calendar.SECOND, 0);
      start.set(Calendar.MILLISECOND, 0);

      Calendar end = new GregorianCalendar();
      end.set(Calendar.HOUR_OF_DAY, endHour.intValue());
      if (endMin != null) {
        end.set(Calendar.MINUTE, endMin.intValue());
      } else {
        end.set(Calendar.MINUTE, 0);
      }
      end.set(Calendar.SECOND, 0);
      end.set(Calendar.MILLISECOND, 0);

      long diff = end.getTimeInMillis() - start.getTimeInMillis();
      double hours = diff / (1000.0 * 60.0 * 60.0);

      time.setWorkInHour(new BigDecimal(hours));
      timeForm.setWorkInHour(Constants.TWO_DECIMAL_FORMAT.format(hours));

    } else {
      time.setStartHour(null);
      time.setEndHour(null);
      time.setStartMin(null);
      time.setEndMin(null);

      if (GenericValidator.isFloat(timeForm.getWorkInHour())) {
        time.setWorkInHour(new BigDecimal(timeForm.getWorkInHour()));
      } else {
        time.setWorkInHour(null);
      }
    }

    if (StringUtils.isNotBlank(timeForm.getNewProject())) {
      TimeCustomer customer = timeCustomerDao.findById(timeForm.getTimeCustomerId());
       
      List<TimeProject> projectTest = timeProjectDao.findByCriteria(Restrictions.eq("timeCustomer", customer),Restrictions.eq("name", timeForm.getNewProject()));
      if (projectTest.isEmpty()) {
        TimeProject newProject = new TimeProject();
        newProject.setActive(true);
        newProject.setName(timeForm.getNewProject());
        newProject.setTimeCustomer(customer);
        customer.getTimeProjects().add(newProject);
  
        timeProjectDao.save(newProject);
        time.setTimeProject(newProject);
        timeForm.setTimeProjectId(newProject.getId().toString());
        ctx.request().removeAttribute("timeCustomerProjectTaskOptions");
      } else {
        timeForm.setTimeProjectId(projectTest.iterator().next().getId().toString());
      }
      timeForm.setNewProject(null);
    }else{
    	time.setTimeProject(timeProjectDao.findById(timeForm.getTimeProjectId()));
    }

    if (StringUtils.isNotBlank(timeForm.getNewTask())) {
      TimeProject project = timeProjectDao.findById(timeForm.getTimeProjectId());

      List<TimeTask> timeTest = timeTaskDao.findByCriteria(Restrictions.eq("timeProject", project),Restrictions.eq("name", timeForm.getNewTask()));
      if (timeTest.isEmpty()) {
        TimeTask newTimeTask = new TimeTask();
        newTimeTask.setActive(true);
        newTimeTask.setName(timeForm.getNewTask());
        newTimeTask.setTimeProject(project);
        project.getTimeTasks().add(newTimeTask);
  
        timeTaskDao.save(newTimeTask);
  
        timeForm.setTimeTaskId(newTimeTask.getId().toString());
        time.setTimeTask(newTimeTask);
      } else {
        TimeTask timeTask = timeTest.iterator().next();
        timeForm.setTimeTaskId(timeTask.getId().toString());
        time.setTimeTask(timeTask);        
      }
      
      timeForm.setNewTask(null);
      ctx.request().removeAttribute("timeCustomerProjectTaskOptions");
    } else {
    	if(StringUtils.isNotBlank(timeForm.getTimeTaskId())){
    		time.setTimeTask(timeTaskDao.findById(timeForm.getTimeTaskId()));
    	}else{
    		time.setTimeTask(null);
    	}
}
	
	   	BigDecimal hour = BigDecimal.valueOf(Double.valueOf(timeForm.getWorkInHour()));
		BigDecimal rate = null;
		if(time.getTimeTask() != null && time.getTimeTask().getHourRate() != null ){
			//Time mit Task Rate verrechnen
			time.setCost(hour.multiply(time.getTimeTask().getHourRate()));
			rate = time.getTimeTask().getHourRate();
		}else if(time.getTimeProject().getHourRate() != null){
			//Time mit Project Rate verrechnen
			time.setCost(hour.multiply(time.getTimeProject().getHourRate()));
			rate = time.getTimeProject().getHourRate();
		}else if(time.getTimeProject().getTimeCustomer().getHourRate() != null){
			//Time mit Customer Rate verrechnen
			time.setCost(hour.multiply(time.getTimeProject().getTimeCustomer().getHourRate()));
			rate = time.getTimeProject().getTimeCustomer().getHourRate();
		}else{
			//Default Rate verwenden
			String defaultHourRate = appConfig.getStringProperty(AppConfig.DEFAULT_HOUR_RATE);
			time.setCost(hour.multiply(BigDecimal.valueOf(Double.valueOf(defaultHourRate))));
			rate = BigDecimal.valueOf(Double.valueOf(defaultHourRate));
		}
	      
	      time.setHourRate(rate);
	      timeForm.setHourRate(rate.toString());
	      timeForm.setCost(time.getCost().toString());
	      
	      updateBudget(ctx, time, (TimeForm)ctx.form());
	
	      if (rate != null) {
	        timeForm.setHourRate(Constants.TWO_DECIMAL_FORMAT.format(rate));
	      }
    }



  @Override
  public void modelToForm(final ActionContext ctx, final Time time) {

    TimeForm timeForm = (TimeForm)ctx.form();
    DateFormat df = new SimpleDateFormat(Constants.getDateFormatPattern());

    timeForm.setUserId(time.getUser().getId().toString());
    if(time.getTimeTask() != null){
    	timeForm.setTimeTaskId(time.getTimeTask().getId().toString());
    }
    timeForm.setTimeProjectId(time.getTimeProject().getId().toString());
    timeForm.setTimeCustomerId(time.getTimeProject().getTimeCustomer().getId().toString());

    timeForm.setTaskTimeDate(df.format(time.getTaskTimeDate()));

    if (time.getStartHour() != null) {
      timeForm.setStartHour(Constants.TWO_DIGIT_FORMAT.format(time.getStartHour()));
    } else {
      timeForm.setStartHour(null);
    }

    if (time.getStartMin() != null) {
      timeForm.setStartMin(Constants.TWO_DIGIT_FORMAT.format(time.getStartMin()));
    } else {
      timeForm.setStartMin(null);
    }

    if (time.getEndHour() != null) {
      timeForm.setEndHour(Constants.TWO_DIGIT_FORMAT.format(time.getEndHour()));
    } else {
      timeForm.setEndHour(null);
    }

    if (time.getEndMin() != null) {
      timeForm.setEndMin(Constants.TWO_DIGIT_FORMAT.format(time.getEndMin()));
    } else {
      timeForm.setEndMin(null);
    }

    if (time.getWorkInHour() != null) {
      timeForm.setWorkInHour(Constants.TWO_DECIMAL_FORMAT.format(time.getWorkInHour()));
    } else {
      timeForm.setWorkInHour(null);
    }

    if (time.getHourRate() != null) {
      timeForm.setHourRate(Constants.TWO_DECIMAL_FORMAT.format(time.getHourRate()));
    } else {
      timeForm.setHourRate(null);
    }
    
    if(time.getChargesStyle() != null){
    	timeForm.setChargesStyle(time.getChargesStyle());
    }else {
    	timeForm.setChargesStyle(null);
    }
    
    if(time.getAmount() != null){
    	timeForm.setAmount(Constants.TWO_DECIMAL_FORMAT.format(time.getAmount()));
    }else {
    	timeForm.setAmount(null);
    }

    timeForm.setActivity(time.getActivity());
    timeForm.setComment(time.getComment());
    updateBudget(ctx, time, timeForm);
        
    if(time.getCost() != null) {
      timeForm.setCost(Constants.TWO_DECIMAL_NOGROUPING_FORMAT.format(time.getCost()));
    }else {
      timeForm.setCost(null);
    }
    setVisibilityProjectTask(ctx);
  }

  private void updateBudget(final ActionContext ctx, final Time time, TimeForm timeForm) {
    BigDecimal budgetProject = timeProjectDao.getBudget(time.getTimeProject().getId());
    BigDecimal budgetTask = null;
    if(time.getTimeTask() != null){
    	budgetTask = timeTaskDao.getBudget(time.getTimeTask().getId());
    }
    
    if (budgetProject != null) {
      BigDecimal costProject = timeProjectDao.getCost(time.getTimeProject().getId());
      if (costProject == null) {
        costProject = new BigDecimal(0);
      }
      BigDecimal diffProject = budgetProject.subtract(costProject);
      
      String budgetStr = Util.translate(ctx.request(), "task.budget.prj", Constants.TWO_DECIMAL_FORMAT.format(budgetProject));
      String budgetOpenStr = Util.translate(ctx.request(), "task.budget.open.prj", Constants.TWO_DECIMAL_FORMAT.format(diffProject));
      timeForm.setBudget(budgetStr);
      timeForm.setBudgetOpen(budgetOpenStr);      
      
    } else if (budgetTask != null) {
      BigDecimal costTask = timeTaskDao.getCost(time.getTimeTask().getId());
      if (costTask == null) {
        costTask = new BigDecimal(0);        
      }
      BigDecimal diffTask = budgetTask.subtract(costTask);
      
      String budgetStr = Util.translate(ctx.request(), "task.budget", Constants.TWO_DECIMAL_FORMAT.format(budgetTask));
      String budgetOpenStr = Util.translate(ctx.request(), "task.budget.open", Constants.TWO_DECIMAL_FORMAT.format(diffTask));
  
      timeForm.setBudget(budgetStr);
      timeForm.setBudgetOpen(budgetOpenStr);       
      
    } else {
      timeForm.setBudget(null);
      timeForm.setBudgetOpen(null);
    }
  }

  @Override
  protected void afterSave(FormActionContext ctx, Time time) {
    updateBudget(ctx, time, (TimeForm)ctx.form());
  }  


  private void setVisibilityProjectTask(ActionContext ctx) {
	  Boolean newOrder = Boolean.parseBoolean(appConfig.getStringProperty(AppConfig.NEW_ORDER, "false"));
      ctx.session().setAttribute("newProject", newOrder.toString());

      Boolean allowActivity = Boolean.parseBoolean(appConfig.getStringProperty(AppConfig.ALLOW_ACTIVITY, "false"));
      ctx.session().setAttribute("newTask", allowActivity.toString());
  }

  	@Override
	public void save_onClick(final FormActionContext ctx) throws Exception {
		  TimeForm timeForm = (TimeForm)ctx.form();
		  Boolean newTaskSearch = false;
		  if(appConfig.getStringProperty(AppConfig.ALLOW_ACTIVITY).equals("true") &&  timeForm.getNewTask().isEmpty()){
			  newTaskSearch = true;
		  }
		  if(!timeForm.getTimeProjectId().isEmpty()){
		  TimeProject projectDummy = timeProjectDao.findById(timeForm.getTimeProjectId());
			  if(projectDummy != null){
				  if(!projectDummy.getTimeTasks().isEmpty() && timeForm.getTimeTaskId().isEmpty() && newTaskSearch){
					  ctx.addGlobalError("time.taskIsNeeded");
					  ctx.forwardToInput();
				  }else if(projectDummy.getTimeTasks() != null && projectDummy.getTimeTasks().isEmpty() && timeForm.getNewTask() != null && !newTaskSearch && !timeProjectDao.canDelete(projectDummy)){
					  ctx.addGlobalError("time.taskIsBlocked");
					  ctx.forwardToInput();
				  }else{
					  super.save_onClick(ctx);
					  
				  }
			  }
		  }else{
			  super.save_onClick(ctx);
		  }
		  
		
	  }
	}
