package ch.ess.cal.web.time;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.JRPropertyDataSource;
import ch.ess.base.service.TranslationService;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.base.web.PropertyComparator;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.GroupDao;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.Group;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;
import ch.ess.cal.model.UserTimeCustomer;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.TreelistControl;
import com.cc.framework.ui.model.TreeGroupDataModel;

@StrutsAction(path = "/timeReportList", 
form = TimeListForm.class, 
input = "/timereportlist.jsp",
scope = ActionScope.SESSION, 
roles = "$timeadmin;$time", 
validate = true)
public class TimeReportListAction extends AbstractTreeListAction {

	private TimeDao timeDao;
	private UserDao userDao;
	private GroupDao groupDao;
	private TranslationService translateService;
	private UserConfigurationDao userConfigurationDao;
	private TimeCustomerDao timeCustomerDao;
	private TimeProjectDao timeProjectDao;
	private TimeTaskDao timeTaskDao;
	private TimeExcelCreator timeExcelCreator;
	private Config appConfig;

	public void setAppConfig(final Config appConfig) {
		this.appConfig = appConfig;
	} 

	public void setTimeDao(final TimeDao timeDao) {
		this.timeDao = timeDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserConfigurationDao(UserConfigurationDao userConfigurationDao) {
		this.userConfigurationDao = userConfigurationDao;
	}

	public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
		this.timeCustomerDao = timeCustomerDao;
	}

	public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
		this.timeProjectDao = timeProjectDao;
	}

	public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
		this.timeTaskDao = timeTaskDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public void setTranslateService(TranslationService translateService) {
		this.translateService = translateService;
	}

	public void setTimeExcelCreator(TimeExcelCreator timeExcelCreator) {
		this.timeExcelCreator = timeExcelCreator;
	}

	@Override
	protected void setTreeListControlAttributes(ActionContext ctx, TreelistControl listControl) {
		TimeListForm searchForm = (TimeListForm)ctx.form();
		if (StringUtils.isNotBlank(searchForm.getTimeProjectId())) {
			listControl.expandToLevel(2);
		} else if (StringUtils.isNotBlank(searchForm.getTimeCustomerId())) {
			listControl.expandToLevel(1);
		} 
	}

	@Override
	public void clearSearch_onClick(final FormActionContext ctx) throws Exception {
		TimeListForm form = (TimeListForm) ctx.form();
		form.clear();
		ctx.session().removeAttribute(getListAttributeName());
		ctx.forwardToInput();
	}  

	@Override
	public void doExecute(final ActionContext ctx) throws Exception {
		String callFromMenu = ctx.request().getParameter("startCrumb");
		TimeListForm searchForm = (TimeListForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Config userConfig = userConfigurationDao.getUserConfig(user);

		if(callFromMenu == null) {
			searchForm = UserHelper.saveAttributes(user, userConfig, searchForm, userConfigurationDao);
		}

		searchForm = UserHelper.loadAttributes(user, userConfig, searchForm);
		
		if(callFromMenu != null){
			searchForm.setSearchWithHour("true");
		}
		super.doExecute(ctx);
		ctx.forwardToInput();
	}

	@Override
	public TreeGroupDataModel getDataModel(final ActionContext ctx) throws Exception {
		TimeListForm searchForm = (TimeListForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());

		Config userConfig = userConfigurationDao.getUserConfig(user);
		int firstDayOfWeek = userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);
		searchForm.calcTime(firstDayOfWeek);    

		String searchWithInactive = searchForm.getSearchWithInactive();
		if(StringUtils.equals("true", searchForm.getSearchWithInactive())) {
			searchWithInactive = Boolean.TRUE.toString();
		} else {
			searchWithInactive = Boolean.FALSE.toString();
		}
		
		String searchWithHour = searchForm.getSearchWithHour();
		if(StringUtils.equals("true", searchForm.getSearchWithHour())) {
			searchWithHour = Boolean.TRUE.toString();
		} else {
			searchWithHour = Boolean.FALSE.toString();
		}
		
		String searchWithCost = searchForm.getSearchWithCost();
		if(StringUtils.equals("true", searchForm.getSearchWithCost())) {
			searchWithCost = Boolean.TRUE.toString();
		} else {
			searchWithCost = Boolean.FALSE.toString();
		}
		
		String searchWithBudget = searchForm.getSearchWithBudget();
		if(StringUtils.equals("true", searchForm.getSearchWithBudget())) {
			searchWithBudget = Boolean.TRUE.toString();
		} else {
			searchWithBudget = Boolean.FALSE.toString();
		}
		
		String searchWithCharges = searchForm.getSearchWithCharges();
		if(StringUtils.equals("true", searchForm.getSearchWithCharges())) {
			searchWithCharges = Boolean.TRUE.toString();
		} else {
			searchWithCharges = Boolean.FALSE.toString();
		}
		
		//Setzt die nicht angezeigten Checkboxen auf on, falls sie in der DB true sind, 
		//damit es nicht zu einer fehlerhaften Sicherung in der DB kommt


		if(StringUtils.equals("true", searchForm.getSearchWithActivity())) 
			searchForm.setSearchWithActivity("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithComment())) 
			searchForm.setSearchWithComment("on");
		
		Set<TimeCustomer> timeCustomersSet = null;
		String userId = null;
		String groupId = null;

		String timeCustomerId = searchForm.getTimeCustomerId();
		String timeProjectId = searchForm.getTimeProjectId();
		String timeTaskId = searchForm.getTimeTaskId();

		if (userPrincipal.hasRight("timeadmin")) {   
			groupId = searchForm.getGroupId();
			userId = searchForm.getUserId();

			if (StringUtils.isNotBlank(userId)) {
				groupId = null;
				searchForm.setGroupId(null);
			}

		} else {
			timeCustomersSet = new HashSet<TimeCustomer>();
			for (UserTimeCustomer userTimeCustomer : user.getUserTimeCustomers()) {
				timeCustomersSet.add(userTimeCustomer.getTimeCustomer());
			}
			userId = user.getId().toString();
		}

		List<Time> times = timeDao.find(searchForm.getFromDate(), searchForm.getToDate(), timeCustomerId, timeProjectId, timeTaskId,
				timeCustomersSet, userId, groupId, null, Boolean.parseBoolean(searchWithInactive));


		DynaTreeDataModel root = new DynaTreeDataModel("root");
		root.set("id", "ROOT");
		root.set("name", "<root>");

	
		//Listen für alle Kunden, Projekte und Tasks erstellen
		List<TimeCustomer> customers = new ArrayList<TimeCustomer>();
		List<TimeProject> projects = new ArrayList<TimeProject>();
		List<TimeTask> tasks = new ArrayList<TimeTask>();
		Set<Integer> customerIds = new HashSet<Integer>();
		Set<Integer> projectIds = new HashSet<Integer>();
		Set<Integer> taskIds = new HashSet<Integer>();

		TimeTask emptyTask = new TimeTask();
		emptyTask.setId(-1);
		emptyTask.setName("-");
		for(Time time : times){
			if(!customerIds.contains(time.getTimeProject().getTimeCustomer().getId())){
				customers.add(time.getTimeProject().getTimeCustomer());
				customerIds.add(time.getTimeProject().getTimeCustomer().getId());
			}
			
			if(!projectIds.contains(time.getTimeProject().getId())){
				projects.add(time.getTimeProject());
				projectIds.add(time.getTimeProject().getId());
			}
			
			if(time.getTimeTask() == null){
				time.setTimeTask(emptyTask);
			}
			tasks.add(time.getTimeTask());

		}
		 


		//Sortieren   
		Collections.sort(customers, new PropertyComparator("customerNumber", "name"));
		Collections.sort(projects, new PropertyComparator("projectNumber", "name"));
		times = sortTasks(times);
		
		BigDecimal totalWork = new BigDecimal(0);
		BigDecimal totalCost = new BigDecimal(0);
		BigDecimal totalCharges = new BigDecimal(0);
		BigDecimal projectBudget = new BigDecimal(0);
		BigDecimal taskBudget = new BigDecimal(0);
		
		for(TimeCustomer customer : customers){
			DynaTreeDataModel customerModel = new DynaTreeDataModel(customer.getName());
			customerModel.set("id", customer.getId().toString());
			customerModel.set("name", TimeCustomerUtil.GetCustomerNumber(customer.getName(), customer.getCustomerNumber(), appConfig));
			customerModel.set("image", "customer");
			root.addChild(customerModel);
			BigDecimal customerWork = new BigDecimal(0);
			BigDecimal customerCost = new BigDecimal(0);
			BigDecimal customerCharges = new BigDecimal(0);
			for(TimeProject project : projects){
				if(customer.getId() == project.getTimeCustomer().getId()){
					BigDecimal projectWork = new BigDecimal(0);
					BigDecimal projectCost = new BigDecimal(0);
					BigDecimal projectCharges = new BigDecimal(0);
					
					DynaTreeDataModel projectModel = new DynaTreeDataModel(project.getName());
					projectModel.set("id", "proj_" + project.getId().toString());
					projectModel.set("name", TimeCustomerUtil.GetProjectNumber(project.getName(), project.getProjectNumber(), appConfig)); 
					projectBudget = timeProjectDao.getBudget(project.getId());
					projectModel.set("budgetProject", projectBudget);

					
					customerModel.addChild(projectModel);
					DynaTreeDataModel taskModel = null;
					int previousTaskId = -3;
					for(Time time : times){
						if(time.getTimeProject().getId() == project.getId()){
							projectWork = time.getWorkInHour() != null ? projectWork.add(time.getWorkInHour()) : projectWork.add(BigDecimal.valueOf(0));
							projectCost = time.getCost() != null ? projectCost.add(time.getCost()) : projectCost.add(BigDecimal.valueOf(0));
							projectCharges = time.getAmount() != null  ? time.getAmount().compareTo(BigDecimal.ZERO) > 0 ? projectCharges.add(time.getAmount()) : projectCharges.add(BigDecimal.valueOf(0)) : projectCharges.add(BigDecimal.valueOf(0));
							//Time ohne Task
							if(time.getTimeTask().getId() != -1){
								if(previousTaskId == time.getTimeTask().getId() && taskModel != null){
									BigDecimal hour = (BigDecimal) taskModel.get("totalHour");
									BigDecimal cost = (BigDecimal) taskModel.get("totalCost");
									BigDecimal charges = (BigDecimal) taskModel.get("totalCharges");
									if(hour!= null)taskModel.set("totalHour", hour.add(time.getWorkInHour() != null ? time.getWorkInHour() : new BigDecimal(0)));
									if(cost!= null)taskModel.set("totalCost", cost.add(time.getCost() != null ? time.getCost() : new BigDecimal(0)));
									if(charges!= null)taskModel.set("totalCharges", charges.add((time.getAmount() != null ? time.getAmount().compareTo(BigDecimal.ZERO) > 0  ? 
											time.getAmount() : BigDecimal.valueOf(0) : BigDecimal.valueOf(0))));
								}else{
										if(taskModel != null){
											projectModel.addChild(taskModel);	
										}
										taskModel = new DynaTreeDataModel(time.getTimeTask().getName());
										taskModel.set("id", "task_" + time.getTimeTask().getId().toString());
										taskIds.add(time.getTimeTask().getId());
										taskModel.set("name", time.getTimeTask().getName());
										taskModel.set("image", "task");
										taskModel.set("totalHour", time.getWorkInHour() != null ? time.getWorkInHour() : BigDecimal.valueOf(0));
										taskModel.set("totalCost", time.getCost() != null ? time.getCost() : BigDecimal.valueOf(0));
										taskModel.set("totalCharges", time.getAmount() != null && time.getAmount().compareTo(BigDecimal.ZERO) > 0 ? time.getAmount() : BigDecimal.valueOf(0));
										taskBudget = timeTaskDao.getBudget(time.getTimeTask().getId());
										taskModel.set("budgetTask", taskBudget);
									}
							}else{
								if(previousTaskId == time.getTimeTask().getId() && taskModel != null){
									BigDecimal hour = (BigDecimal) taskModel.get("totalHour");
									BigDecimal cost = (BigDecimal) taskModel.get("totalCost");
									BigDecimal charges = (BigDecimal) taskModel.get("totalCharges");
									if(hour!= null)taskModel.set("totalHour", hour.add(time.getWorkInHour() != null ? time.getWorkInHour() : new BigDecimal(0)));
									if(cost!= null)taskModel.set("totalCost", cost.add(time.getCost() != null ? time.getCost() : new BigDecimal(0)));
									if(charges!= null)taskModel.set("totalCharges", charges.add((time.getAmount() != null ? time.getAmount().compareTo(BigDecimal.ZERO) > 0  ? 
											time.getAmount() : BigDecimal.valueOf(0) : BigDecimal.valueOf(0))));
								}else{
									if(taskModel != null){
										projectModel.addChild(taskModel);	
									}
									taskModel = new DynaTreeDataModel(time.getTimeTask().getName());
									taskModel.set("id", "task_-1");
									taskIds.add(time.getTimeTask().getId());
									taskModel.set("name", time.getTimeTask().getName());
									taskModel.set("image", "task");
									taskModel.set("totalHour", time.getWorkInHour() != null ? time.getWorkInHour() : BigDecimal.valueOf(0));
									taskModel.set("totalCost", time.getCost() != null ? time.getCost() : BigDecimal.valueOf(0));
									taskModel.set("totalCharges", time.getAmount() != null && time.getAmount().compareTo(BigDecimal.ZERO) > 0 ? time.getAmount() : BigDecimal.valueOf(0));
									taskBudget = timeTaskDao.getBudget(time.getTimeTask().getId());
									taskModel.set("budgetTask", taskBudget);
								}
							}
							previousTaskId = time.getTimeTask().getId();	
						}	
					}
					if(taskModel != null)projectModel.addChild(taskModel);
					projectModel.set("totalProjectHour", projectWork);
					projectModel.set("totalProjectCost", projectCost);
					projectModel.set("totalProjectCharges", projectCharges);
					
					customerWork = customerWork.add(projectWork);
					customerCost = customerCost.add(projectCost);
					customerCharges = customerCharges.add(projectCharges);
				}
			}
			totalWork = totalWork.add(customerWork);
			customerModel.set("totalCustomerHour", customerWork);	
			totalCost = totalCost.add(customerCost);
			customerModel.set("totalCustomerCost", customerCost);	
			totalCost = totalCost.add(customerCost);
			customerModel.set("totalCustomerCharges", customerCharges);	
			
		}
		ctx.session().setAttribute("grandTotal", totalWork);
		return root;
	}



	@Override
	public void onPrintList(ControlActionContext ctx) throws Exception {

		TimeListForm searchForm = (TimeListForm)ctx.session().getAttribute("TimeListForm");

		String filename = "time_report.pdf";
		Util.setExportHeader(ctx.response(), "application/pdf", filename);
		ctx.response().setHeader("extension", "pdf");




		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("REPORT_LOCALE", getLocale(ctx.request()));

		if (StringUtils.isNotBlank(searchForm.getFrom())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
			parameters.put("filter_from", dateFormat.format(searchForm.getFromDate()));
		}

		if (StringUtils.isNotBlank(searchForm.getTo())) {
			DateFormat dateFormat = new SimpleDateFormat(Constants.getDateFormatPattern());
			parameters.put("filter_to", dateFormat.format(searchForm.getToDate()));
		}

		if (StringUtils.isNotBlank(searchForm.getWeek())) {
			parameters.put("filter_week", searchForm.getWeek());
		}

		if (StringUtils.isNotBlank(searchForm.getMonth())) {
			DateFormatSymbols dfs = new DateFormatSymbols(getLocale(ctx.request()));      
			parameters.put("filter_month", dfs.getMonths()[Integer.parseInt(searchForm.getMonth())]);
		}

		if (StringUtils.isNotBlank(searchForm.getYear())) {
			parameters.put("filter_year", searchForm.getYear());
		}

		if (StringUtils.isNotBlank(searchForm.getTimeCustomerId())) {
			TimeCustomer customer = timeCustomerDao.findById(searchForm.getTimeCustomerId());
			parameters.put("filter_customer", customer.getName());
		}

		if (StringUtils.isNotBlank(searchForm.getTimeProjectId())) {
			TimeProject project = timeProjectDao.findById(searchForm.getTimeProjectId());
			parameters.put("filter_project", project.getName());
		}

		if (StringUtils.isNotBlank(searchForm.getTimeTaskId())) {
			TimeTask task = timeTaskDao.findById(searchForm.getTimeTaskId());
			parameters.put("filter_task", task.getName());
		}


		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		if (userPrincipal.hasRight("timeadmin")) {  
			if (StringUtils.isNotBlank(searchForm.getGroupId())) {
				Group group = groupDao.findById(searchForm.getGroupId());
				parameters.put("filter_group", translateService.getText(group, getLocale(ctx.request())));
			}

			if (StringUtils.isNotBlank(searchForm.getUserId())) {
				User user = userDao.findById(searchForm.getUserId());
				parameters.put("filter_user", user.getName() + " " + user.getFirstName());
			}
		}


		List<DynaBean> dataSourceList = new ArrayList<DynaBean>();

		DynaTreeDataModel root = (DynaTreeDataModel)ctx.control().getDataModel();
		for (int i = 0; i < root.size(); i++) {
			DynaTreeDataModel customer = (DynaTreeDataModel)root.getChild(i);      

			for (int j = 0; j < customer.size(); j++) {
				DynaTreeDataModel project = (DynaTreeDataModel)customer.getChild(j);

				for (int k = 0; k < project.size(); k++) {
					DynaTreeDataModel task = (DynaTreeDataModel)project.getChild(k);

					DynaBean dynaBean = new LazyDynaBean("task");
					dynaBean.set("customer", customer.get("name"));
					dynaBean.set("project", project.get("name"));
					dynaBean.set("task", task.get("name"));

					dynaBean.set("totalCost", task.get("totalCost"));
					dynaBean.set("totalHours", task.get("totalHour"));

					dataSourceList.add(dynaBean);
				}
			}
		}

		JRPropertyDataSource dataSource = new JRPropertyDataSource(dataSourceList);

		String reportTemplate = "/WEB-INF/reports/tasktimereport.jasper";

		JasperPrint print = JasperFillManager.fillReport(ctx.session().getServletContext().getResourceAsStream(reportTemplate),
				parameters, dataSource);

		OutputStream out = ctx.response().getOutputStream();
		JasperExportManager.exportReportToPdfStream(print, out);    
		out.close();

		ctx.forwardToResponse();

	}

	@Override
	public void onExportList(ControlActionContext ctx) throws Exception {

		TimeListForm searchForm = (TimeListForm)ctx.session().getAttribute("TimeListForm");

		String filename = "time_report.xls";
		Util.setExportHeader(ctx.response(), "application/vnd.ms-excel", filename);
		ctx.response().setHeader("extension", "xls");

		OutputStream out = ctx.response().getOutputStream();

		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		boolean isAdmin = userPrincipal.hasRight("timeadmin");
		
		boolean hour = Boolean.valueOf(searchForm.getSearchWithHour());
		boolean cost = Boolean.valueOf(searchForm.getSearchWithCost());
		boolean budget = Boolean.valueOf(searchForm.getSearchWithBudget());
		boolean charges = Boolean.valueOf(searchForm.getSearchWithCharges());
		
		
		
		timeExcelCreator.createExcelReport(searchForm, (DynaTreeDataModel)ctx.control().getDataModel(), 
				out, getResources(ctx.request()), getLocale(ctx.request()), isAdmin, hour, cost, budget, charges);

		out.close();

		ctx.forwardToResponse();
	}

	@Override
	public void deleteObject(ControlActionContext ctx, String key) throws Exception {
		throw new UnsupportedOperationException("delete not supported");    
	}

  public static List<Time> sortTasks(List<Time> times){
	  	List<Time> timeToSort = times;
        boolean flag = true;  
        Time temp;
   
        //Sortiert nach IDs
        while (flag){
            flag = false;
            for (int i=0; i < timeToSort.size()-1; i++){
               if (timeToSort.get(i).getTimeTask().getId() > timeToSort.get(i+1).getTimeTask().getId()) {                      
                  temp       = timeToSort.get(i);
                  timeToSort.set(i, timeToSort.get(i+1));
                  timeToSort.set((i+1), temp);
                  flag = true;
               }    
            }
       }
       
       flag = true;

       //Sortiert nach Namen wenn gleiche ID
       while (flag){
           flag = false;
           for (int i=0; i < timeToSort.size()-1; i++){
           	  if (timeToSort.get(i).getTimeTask().getName().compareToIgnoreCase(timeToSort.get(i+1).getTimeTask().getName()) > 0 ){        
           		  if(timeToSort.get(i).getTimeTask().getId() == timeToSort.get(i+1).getTimeTask().getId()){
           			  temp       = timeToSort.get(i);
                      timeToSort.set(i, timeToSort.get(i+1));
                      timeToSort.set((i+1), temp);
                      flag = true;
          		  }
             }  
         }
    }
    return timeToSort;
  } 
  
  
  public void increaseWeek_onClick(FormActionContext ctx) throws Exception {
		  TimeListForm form = (TimeListForm) ctx.form();
		  if(form.getWeek() != null && !form.getWeek().isEmpty()){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(form.getWeek()) +1);
			  List<Integer> years = timeDao.getYears();
			  int high = years.get(0);
			  int low = years.get(years.size() -1);
			  Boolean allowedToChangeYear = true;
			  int old = Integer.valueOf(form.getWeek());
			  if(old > cal.get(Calendar.WEEK_OF_YEAR)){
				  int year = Integer.valueOf(form.getYear());
				  year = year +1;
				  if(year <= high){
					  form.setYear(String.valueOf(year));
					  allowedToChangeYear = true;
				  }else{
					  allowedToChangeYear = false;
				  }
			  }
			  if(allowedToChangeYear){
				  form.setWeek(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
			  }else{
				  ctx.addGlobalMessage("time.cantGoFurther");
			  }
		  }
	  }
	  
	  public void decreaseWeek_onClick(FormActionContext ctx) throws Exception {
		  TimeListForm form = (TimeListForm) ctx.form();
		  if(form.getWeek() != null && !form.getWeek().isEmpty()){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(form.getWeek()) -1);
			  List<Integer> years = timeDao.getYears();
			  int high = years.get(0);
			  int low = years.get(years.size() -1);
			  Boolean allowedToChangeYear = true;
			  int old = Integer.valueOf(form.getWeek());
			  if(old < cal.get(Calendar.WEEK_OF_YEAR)){
				  int year = Integer.valueOf(form.getYear());
				  year = year -1;
				  if(year >= low){
					  form.setYear(String.valueOf(year));
					  allowedToChangeYear = true;
				  }else{
					  allowedToChangeYear = false;
				  }
			  }
			  if(allowedToChangeYear){
				  form.setWeek(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
			  }else{
				  ctx.addGlobalMessage("time.cantGoFurther");
			  }
		  }

	  }
	  
  public void increaseMonth_onClick(FormActionContext ctx) throws Exception {
		  TimeListForm form = (TimeListForm) ctx.form();
		  if(form.getMonth() != null && !form.getMonth().isEmpty()){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.MONTH, Integer.valueOf(form.getMonth()) +1);
			  List<Integer> years = timeDao.getYears();
			  int high = years.get(0);
			  int low = years.get(years.size() -1);
			  Boolean allowedToChangeYear = true;
			  int old = Integer.valueOf(form.getMonth());
			  if(old > cal.get(Calendar.MONTH)){			  
				  int year = Integer.valueOf(form.getYear());
				  year = year +1;
				  if(year <= high){
					  form.setYear(String.valueOf(year));
					  allowedToChangeYear = true;
				  }else{
					  allowedToChangeYear = false;
				  }
			  }
			  if(allowedToChangeYear){
				  form.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
			  }else{
				  ctx.addGlobalMessage("time.cantGoFurther");
			  }			   
		  }
	  }
	  
	  public void decreaseMonth_onClick(FormActionContext ctx) throws Exception {
		  TimeListForm form = (TimeListForm) ctx.form();
		  if(form.getMonth() != null && !form.getMonth().isEmpty()){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.MONTH, Integer.valueOf(form.getMonth()) -1);
			  List<Integer> years = timeDao.getYears();
			  int high = years.get(0);
			  int low = years.get(years.size() -1);
			  Boolean allowedToChangeYear = true;
			  int old = Integer.valueOf(form.getMonth());
			  if(old < cal.get(Calendar.MONTH)){
				  int year = Integer.valueOf(form.getYear());
				  year = year -1;
				  if(year >= low){
					  form.setYear(String.valueOf(year));
					  allowedToChangeYear = true;
				  }else{
					  allowedToChangeYear = false;
				  }
			 }
			 if(allowedToChangeYear){
				  form.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
			 }else{
				  ctx.addGlobalMessage("time.cantGoFurther");
			 }

			  

		  }

	  }
	  
	    public void increaseYear_onClick(FormActionContext ctx) throws Exception {
			  TimeListForm form = (TimeListForm) ctx.form();
			  if(form.getYear() != null && !form.getYear().isEmpty()){
				  Calendar cal = Calendar.getInstance();
				  cal.set(Calendar.YEAR, Integer.valueOf(form.getYear()) +1);
				  List<Integer> years = timeDao.getYears();
				  int high = years.get(0);
				  int low = years.get(years.size() -1);
				  if(cal.get(Calendar.YEAR) <= high){
					  form.setYear(String.valueOf(cal.get(Calendar.YEAR)));
				  }else{
					  ctx.addGlobalMessage("time.cantGoFurther");
				  }		  
			  }
		  }
		  
		  public void decreaseYear_onClick(FormActionContext ctx) throws Exception {
			  TimeListForm form = (TimeListForm) ctx.form();
			  if(form.getYear() != null && !form.getYear().isEmpty()){
				  Calendar cal = Calendar.getInstance();
				  cal.set(Calendar.YEAR, Integer.valueOf(form.getYear()) -1);
				  List<Integer> years = timeDao.getYears();
				  int high = years.get(0);
				  int low = years.get(years.size() -1);
				  if(cal.get(Calendar.YEAR) >= low){
					  form.setYear(String.valueOf(cal.get(Calendar.YEAR)));
				  }else{
					  ctx.addGlobalMessage("time.cantGoFurther");
				  }	
			  }

		  }
}
