package ch.ess.cal.web.time;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;


import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.Forward;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeDao;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.UserTimeCustomer;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.TreelistControl;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.model.TreeGroupDataModel;

@StrutsAction(path = "/timeList", form = TimeListForm.class, input = "/timelist.jsp", scope = ActionScope.SESSION, roles = "$time", validate = true, forwards = {
	@Forward(name = "edit", path = "/timeEdit.do?id={0}", redirect = true),
	@Forward(name = "copy", path = "/timeEdit.do?copyid={0}", redirect = true),
	@Forward(name = "create", path = "/timeEdit.do", redirect = true)})
public class TimeListAction extends AbstractTreeListAction {
	
	private TimeDao timeDao;
	private UserDao userDao;
	private Config appConfig;
	private UserConfigurationDao userConfigurationDao;
	private TimeExcelCreator timeExcelCreator;
	private boolean isStarted;

	public void setTimeDao(final TimeDao timeDao) {
		this.timeDao = timeDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAppConfig(final Config appConfig) {
		this.appConfig = appConfig;
	}  

	public void setUserConfigurationDao(UserConfigurationDao userConfigurationDao) {
		this.userConfigurationDao = userConfigurationDao;
	}

	public void setTimeExcelCreator(TimeExcelCreator timeExcelCreator) {
		this.timeExcelCreator = timeExcelCreator;
	}

	@Override
	public void clearSearch_onClick(final FormActionContext ctx) throws Exception {
		TimeListForm form = (TimeListForm)ctx.form();
		form.clear();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Calendar cal = Calendar.getInstance();
		
		form.setWeek(String.valueOf(cal.get(Calendar.WEEK_OF_YEAR)));
		form.setYear(String.valueOf(cal.get(Calendar.YEAR)));
		form.setUserId(user.getId().toString());
		ctx.session().removeAttribute(getListAttributeName());
		doExecute(ctx);
	}


	@Override
	public void doExecute(ActionContext ctx) throws Exception {
		String callFromMenu = ctx.request().getParameter("startCrumb");
		TimeListForm searchForm = (TimeListForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Config userConfig = userConfigurationDao.getUserConfig(user);

		
		if(StringUtils.equals("true", searchForm.getSearchWithHour())) 
			searchForm.setSearchWithHour("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithCost())) 
			searchForm.setSearchWithCost("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithBudget())) 
			searchForm.setSearchWithBudget("on");
		
		//Suchmasken setzen
		if(callFromMenu == null) {
			searchForm = UserHelper.saveAttributes(user, userConfig, searchForm, userConfigurationDao);
		}
		
		if(callFromMenu != null)
			setFilterProperties(ctx);

		searchForm = UserHelper.loadAttributes(user, userConfig, searchForm);		
		super.doExecute(ctx);

		if(callFromMenu != null){
			this.setStarted(true);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public TreeGroupDataModel getDataModel(final ActionContext ctx) throws Exception {

		//SimpleListDataModel dataModel = new SimpleListDataModel();
		
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
			
		Set<TimeCustomer> timeCustomersSet = null;
		String userId = null;
		String groupId = null;

		String timeCustomerId = searchForm.getTimeCustomerId();
		String timeProjectId = searchForm.getTimeProjectId();
		
		String timeTaskId = searchForm.getTimeTaskId();
		String fullTextSearch = searchForm.getFullTextSearch();

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

		List<Time> times = timeDao.find(searchForm.getFromDate(), searchForm.getToDate(), timeCustomerId, timeProjectId,
				timeTaskId, timeCustomersSet, userId, groupId, fullTextSearch, Boolean.parseBoolean(searchWithInactive));

		BigDecimal grandTotal = new BigDecimal(0);
		BigDecimal dailyTotal = new BigDecimal(0);

		DynaTreeDataModel root = new DynaTreeDataModel("timeList");
		root.set("id", "ROOT");
		root.set("name", "<root>");
		if(searchForm.getToDate() == null){
			searchForm.setToDate(searchForm.getFromDate());
		}
		
		if(calculateTimeRange(searchForm.getFromDate(), searchForm.getToDate())){
			grandTotal = BigDecimal.ZERO;
			Calendar calFrom = Calendar.getInstance();
			calFrom.setTime(searchForm.getFromDate());
			Calendar calTo = Calendar.getInstance();
			calTo.setTime(searchForm.getToDate());
			Calendar dummy = Calendar.getInstance();
			dummy = (Calendar) calFrom.clone();
			
			
			long count = daysBetween(calFrom, calTo);
			for(int i = 0; i <= count; i++){		
				
				Boolean hasEntry = false;
				for (Time time : times) {
					if(time.getTaskTimeDate().getDate() == dummy.get(Calendar.DATE)){
						hasEntry = true;
					}	
				}
				
				if(hasEntry){
					DynaTreeDataModel day = new DynaTreeDataModel("Day");
					day.set("id", "day_"+String.valueOf(i));
					dailyTotal = BigDecimal.ZERO;
					day.setEditable(false);
					day.setDeletable(false);
				
					for (Time time : times) {
						if(time.getTaskTimeDate().getDate() == dummy.get(Calendar.DATE)){
							if(time.getWorkInHour() != null)dailyTotal = dailyTotal.add(time.getWorkInHour());
							
							Calendar cal = Calendar.getInstance();
							cal.setTime(time.getTaskTimeDate());
							SimpleDateFormat sdf = new SimpleDateFormat( "E, dd.MM.yyyy" );
							day.set("date", sdf.format(cal.getTime())+" ("+ Constants.TWO_DECIMAL_FORMAT.format(dailyTotal)+"h)");
							if(time.getWorkInHour() != null)grandTotal = grandTotal.add(time.getWorkInHour());
							//New Time Entry
							DynaTreeDataModel timeEntry = new DynaTreeDataModel("Time");	
							timeEntry.set("id", time.getId().toString());
							timeEntry.set("timeUser", time.getUser().getName() + " " + time.getUser().getFirstName());
							timeEntry.set("timeCustomer", setCustomerString(time.getTimeProject().getTimeCustomer()));
							timeEntry.set("timeProject", setProjectString(time.getTimeProject()));
							timeEntry.set("timeActivity", time.getActivity());
							if(time.getAmount() != null)timeEntry.set("chargesAmount", Constants.TWO_DECIMAL_FORMAT.format(time.getAmount()));
							timeEntry.set("chargesStyle", time.getChargesStyle());
							timeEntry.set("timeComment",  time.getComment());
							
							if(time.getTimeTask() != null){
								timeEntry.set("timeTask", time.getTimeTask().getName());
							}else{
								timeEntry.set("timeTask", "");
							}
							
							if(!time.getTimeProject().isActive() || !time.getTimeProject().getTimeCustomer().isActive()){
								timeEntry.setEditable(false);
								timeEntry.setDeletable(false);
							}
							
							if(time.getWorkInHour() != null)timeEntry.set("timeHour",  Constants.TWO_DECIMAL_FORMAT.format(time.getWorkInHour()));
							day.addChild(timeEntry);
						}
					}
					root.addChild(day);
				}
				dummy.add(Calendar.DATE, 1);
			}
		}else{
			//Weekly blocks
			Calendar calFrom = Calendar.getInstance();
			Calendar calTo = Calendar.getInstance();
			calFrom.setTime(searchForm.getFromDate());
			calTo.setTime(searchForm.getToDate());
			Calendar current = calFrom;

			grandTotal = BigDecimal.ZERO;
			while(current.getTimeInMillis() <= calTo.getTimeInMillis()){
				int i = current.get(Calendar.WEEK_OF_YEAR);
				Boolean hasEntry = false;
				int currentYear = current.get(Calendar.YEAR);
				for (Time time : times) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(time.getTaskTimeDate());
										
					if(cal.get(Calendar.WEEK_OF_YEAR) == i && cal.get(Calendar.YEAR) == currentYear){
						hasEntry = true;
					}
				}
				
				if(hasEntry){
					DynaTreeDataModel day = new DynaTreeDataModel("Week");
					
					day.set("id", "week_"+String.valueOf(i)+"_year_"+currentYear);
					dailyTotal = BigDecimal.ZERO;
					day.setEditable(false);
					day.setDeletable(false);
					
					for (Time time : times) {
						Calendar timeCal = Calendar.getInstance();
						timeCal.setTime(time.getTaskTimeDate());
						if(timeCal.get(Calendar.WEEK_OF_YEAR) == i && timeCal.get(Calendar.YEAR) == currentYear){
							dailyTotal = dailyTotal.add(time.getWorkInHour());
							grandTotal = grandTotal.add(time.getWorkInHour());
							//New Time Entry
							DynaTreeDataModel timeEntry = new DynaTreeDataModel("Time");	
							timeEntry.set("id", time.getId().toString());
							SimpleDateFormat sdf = new SimpleDateFormat( "E, dd.MM.yyyy" );
							timeEntry.set("date", sdf.format(timeCal.getTime()));
							timeEntry.set("timeUser", time.getUser().getName() + " " + time.getUser().getFirstName());
							timeEntry.set("timeCustomer", setCustomerString(time.getTimeProject().getTimeCustomer()));
							timeEntry.set("timeProject", setProjectString(time.getTimeProject()));
							timeEntry.set("timeActivity", time.getActivity());
							timeEntry.set("chargesAmount", Constants.TWO_DECIMAL_FORMAT.format(time.getAmount()));
							timeEntry.set("chargesStyle", time.getChargesStyle());
							timeEntry.set("timeComment",  time.getComment());
							if(time.getTimeTask() != null){
								timeEntry.set("timeTask", time.getTimeTask().getName());
							}else{
								timeEntry.set("timeTask", "");
							}
							
							if(!time.getTimeProject().isActive() || !time.getTimeProject().getTimeCustomer().isActive()){
								timeEntry.setEditable(false);
								timeEntry.setDeletable(false);
							}
							
							
							timeEntry.set("timeHour",  Constants.TWO_DECIMAL_FORMAT.format(time.getWorkInHour()));
							day.addChild(timeEntry);
						}
					}

					day.set("date", "Woche "+current.get(Calendar.WEEK_OF_YEAR)+" / "+currentYear+" ("+ Constants.TWO_DECIMAL_FORMAT.format(dailyTotal)+"h)");
					root.addChild(day);
				}
			
				current.add(Calendar.DATE, 7);
				
			}
		}
		ctx.session().setAttribute("grandTotal", Constants.TWO_DECIMAL_FORMAT.format(grandTotal)); 
		return root;
		
		//OLD TIMELIST
		/*for (Time time : times) {	
			//Null Pointer verhindern
			if(searchForm.getTimeTaskId() == null){
				searchForm.setTimeTaskId("");
			}
					      
			if(!searchForm.getTimeTaskId().isEmpty() && time.getTimeTask() == null){
				System.out.println(searchForm.getTimeTaskId());
				System.out.println(time.getTimeTask());
			}else{
				DynaBean dynaBean = new LazyDynaBean("timeList");
	
				dynaBean.set("id", time.getId().toString());
	
				dynaBean.set("taskTimeDate", time.getTaskTimeDate());
				String userName = time.getUser().getShortName();
				if (StringUtils.isBlank(userName)) {
					dynaBean.set("user", time.getUser().getName() + " " + time.getUser().getFirstName());
				} else {
					dynaBean.set("user", userName);
				}
				dynaBean.set("workInHour", time.getWorkInHour());
				if (time.getWorkInHour() != null) {
					grandTotal = grandTotal.add(time.getWorkInHour());
				}
				if(Boolean.parseBoolean(searchWithActivity)){
					dynaBean.set("description", time.getActivity());
				}
				//If task is set
				if(time.getTimeTask()!= null){
					dynaBean.set("timeTask", time.getTimeTask().getName());
				}else{
					dynaBean.set("timeTask", "-");
				}
				dynaBean.set("timeProject", time.getTimeProject().getName());
				setProjectString(dynaBean, time.getTimeProject());
				setCustomerString(dynaBean, time.getTimeProject().getTimeCustomer());
	
				if(Boolean.parseBoolean(searchWithComment)){
					dynaBean.set("comment", time.getComment());
				}
				if(Boolean.parseBoolean(searchWithCharges)){
					dynaBean.set("amount", time.getAmount());
					dynaBean.set("charges_style", time.getChargesStyle());
				}
				dynaBean.set("deletable", true);
				
				dataModel.add(dynaBean);
			}
		}

		ctx.session().setAttribute("grandTotal", Constants.TWO_DECIMAL_FORMAT.format(grandTotal)); 
		return dataModel;
		*/
	}
	
	@Override
	public void onExportList(ControlActionContext ctx) throws Exception {

		TimeListForm searchForm = (TimeListForm)ctx.session().getAttribute("TimeListForm");

		String filename = "activities.xls";
		Util.setExportHeader(ctx.response(), "application/vnd.ms-excel", filename);
		ctx.response().setHeader("extension", "xls");

		OutputStream out = ctx.response().getOutputStream();

		String searchWithCharges = searchForm.getSearchWithCharges();
		if(StringUtils.equals("true", searchWithCharges)) {
			searchWithCharges = Boolean.TRUE.toString();
		} else {
			searchWithCharges = Boolean.FALSE.toString();
			searchForm.setSearchWithCharges(null);
		}
		String searchWithActivity = searchForm.getSearchWithActivity();
		if(StringUtils.equals("true", searchWithActivity)) {
			searchWithActivity = Boolean.TRUE.toString();
		} else {
			searchWithActivity = Boolean.FALSE.toString();
			searchForm.setSearchWithActivity(null);
		}
		String searchWithComment = searchForm.getSearchWithComment();
		if(StringUtils.equals("true", searchWithComment)) {
			searchWithComment = Boolean.TRUE.toString();
		} else {
			searchWithComment = Boolean.FALSE.toString();
			searchForm.setSearchWithComment(null);
		}

		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		boolean isAdmin = userPrincipal.hasRight("timeadmin");
		timeExcelCreator.createListExcel(searchForm, (DynaTreeDataModel) ctx.control().getDataModel(), out, 
				getResources(ctx.request()), getLocale(ctx.request()), isAdmin, 
				Boolean.parseBoolean(searchWithCharges), Boolean.parseBoolean(searchWithActivity), Boolean.parseBoolean(searchWithComment));

		out.close();

		ctx.forwardToResponse();
	}

	@Override
	public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
		timeDao.delete(key);
	}

	@Override
	public String getTitle(String id, ActionContext ctx) {
		if (StringUtils.isNotBlank(id)) {
			Time time = timeDao.findById(id);
			if (time != null) {
				return time.getActivity();
			}
		}
		return null;
	}

	private String setCustomerString(TimeCustomer customer) {
		if(StringUtils.isNotBlank(customer.getCustomerNumber())){
			return  appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<") +
					customer.getCustomerNumber() +
					appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">") + " " +
					customer.getName();
		}
		
		return customer.getName();
		
	}

	private String setProjectString(TimeProject project) {
		if(StringUtils.isNotBlank(project.getProjectNumber())){
			return	appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<") +
					project.getProjectNumber() +
					appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">") + " " +
					project.getName()
					;
		}
		return project.getName();
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public boolean isStarted() {
		return isStarted;
	}

	private void setFilterProperties(ActionContext ctx) {
		TimeListForm form = (TimeListForm)ctx.form();
		Calendar today = Calendar.getInstance();
		String timeSetting = appConfig.getStringProperty(AppConfig.TIME_SETTING, null);
		if(timeSetting == null) timeSetting = String.valueOf(4); // Default wird auf Jahr gesetzt.
		int time = Integer.parseInt(timeSetting);

		// Aktueller Tag
		if(time == 1){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");	    	
			String day = dateFormat.format(new Date());
			form.setFrom(null);
			form.setTo(null);
			form.setWeek(String.valueOf(today.get(Calendar.WEEK_OF_YEAR)));
			form.setMonth(null);
			form.setYear(String.valueOf(today.get(Calendar.YEAR)));
		}
		// Aktuelle Woche
		else if(time == 2){
			form.setFrom(null);
			form.setTo(null);
			form.setWeek(String.valueOf(today.get(Calendar.WEEK_OF_YEAR)));
			form.setMonth(null);
			form.setYear(String.valueOf(today.get(Calendar.YEAR)));
		}
		// Aktueller Monat
		else if (time == 3) {
			form.setFrom(null);
			form.setTo(null);
			form.setWeek(null);
			form.setMonth(String.valueOf(today.get(Calendar.MONTH)));
			form.setYear(String.valueOf(today.get(Calendar.YEAR)));
		}
		// Aktuelles Jahr (=4)
		else {
			form.setFrom(null);
			form.setTo(null);
			form.setWeek(null);
			form.setMonth(null);
			form.setYear(String.valueOf(today.get(Calendar.YEAR)));
		}
	}

	private Boolean calculateTimeRange(Date from, Date to){
		
		Calendar calFrom = Calendar.getInstance();
		Calendar calTo = Calendar.getInstance();
		
		calFrom.setTime(from);
		calTo.setTime(to);
		
		
		if(daysBetween(calFrom, calTo) < 7 ){
			return true;
		}
		
		return false;
	}
	
    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
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
