package ch.ess.cal.web.time;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.BigDecimalConverter;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.base.web.PropertyComparator;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeDao;
import ch.ess.cal.enums.TimeRangeEnum;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.UserTimeCustomer;
import ch.ess.cal.service.HolidayRegistry;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.taglib.TagHelp;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.ExpansionMode;
import com.cc.framework.ui.ImageMap;
import com.cc.framework.ui.RunAt;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ControlButton;
import com.cc.framework.ui.control.TreelistControl;
import com.cc.framework.ui.model.ColumnHeaderDesignModel;
import com.cc.framework.ui.model.ColumnTextDesignModel;
import com.cc.framework.ui.model.ColumnTreeDesignModel;
import com.cc.framework.ui.model.TreeGroupDataModel;
import com.cc.framework.ui.model.TreelistDesignModel;
import com.cc.framework.ui.model.imp.ColumnHeaderDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnTextDesignModelImp;
import com.cc.framework.ui.model.imp.ColumnTreeDesignModelImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.model.imp.TreelistDesignModelImp;

@StrutsAction(path = "/timeMatrixReportList", form = TimeListForm.class, input = "/timematrixreportlist.jsp", scope = ActionScope.SESSION, roles = "$timeadmin;$time", validate = true)
public class TimeMatrixReportListAction extends AbstractTreeListAction {

	private TimeDao timeDao;
	private UserDao userDao;
	private UserConfigurationDao userConfigurationDao;
	private HolidayRegistry holidayRegistry;
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

	public void setHolidayRegistry(HolidayRegistry holidayRegistry) {
		this.holidayRegistry = holidayRegistry;
	}

	public void setTimeExcelCreator(TimeExcelCreator timeExcelCreator) {
		this.timeExcelCreator = timeExcelCreator;
	}

	@Override
	public void doExecute(final ActionContext ctx) throws Exception {
		TreelistControl treeListControl = new TreelistControl();
		String callFromMenu = ctx.request().getParameter("startCrumb");
		TimeListForm searchForm = (TimeListForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Config userConfig = userConfigurationDao.getUserConfig(user);

		TreelistDesignModel designModel = new TreelistDesignModelImp();
		designModel.setLocaleName("false");
		designModel.setName("listControl");
		designModel.setTitle(Util.translate(ctx.request(), "time.report.matrix.menu"));
		designModel.setButtonPermission(ControlButton.REFRESH, TagHelp.toPermission("false"));
		designModel.setButtonPermission(ControlButton.EXPORTLIST, TagHelp.toPermission("true"));
		designModel.setId("timeMatrixReportList");
		designModel.setExpansionMode(ExpansionMode.MULTIPLE);
		designModel.showLinesAtRoot(true);
		designModel.showRoot(false);
		designModel.showCheckBoxes(false);
		designModel.setRunAt(RunAt.CLIENT);
		designModel.setGroupSelect(false);

		ImageMap imageMap = new ImageMap();
		ImageModelImp customerImage = new ImageModelImp();
		customerImage.setHeight(16);
		customerImage.setWidth(16);
		customerImage.setSource("images/timecustomer.png");

		ImageModelImp taskImage = new ImageModelImp();
		taskImage.setHeight(16);
		taskImage.setWidth(16);
		taskImage.setSource("images/timetask.png");

		imageMap.addImage("customer", customerImage);
		imageMap.addImage("task", taskImage);

		ColumnTreeDesignModel columnTreeDesignModel = new ColumnTreeDesignModelImp();
		columnTreeDesignModel.setTitle(Util.translate(ctx.request(), "time.customerProjectTask"));
		columnTreeDesignModel.setProperty("name");
		columnTreeDesignModel.setSortable(true);
		columnTreeDesignModel.setImageMap(imageMap);
		columnTreeDesignModel.setImageProperty("image");
		columnTreeDesignModel.setWidth(250);
		columnTreeDesignModel.setMaxLength(50);
		designModel.addColumn(columnTreeDesignModel);

		ColumnTextDesignModel textDesignModel = new ColumnTextDesignModelImp();
		textDesignModel.setTitle(Util.translate(ctx.request(), "time.report.totalCustomer"));
		textDesignModel.setAlignment(AlignmentType.RIGHT);
		textDesignModel.setProperty("totalCustomer");
		textDesignModel.setWidth(100);
		textDesignModel.setSortable(false);
		textDesignModel.setConverter(new BigDecimalConverter());
		designModel.addColumn(textDesignModel);

		textDesignModel = new ColumnTextDesignModelImp();
		textDesignModel.setTitle(Util.translate(ctx.request(), "time.report.totalProject"));
		textDesignModel.setAlignment(AlignmentType.RIGHT);
		textDesignModel.setProperty("totalProject");
		textDesignModel.setWidth(100);
		textDesignModel.setSortable(false);
		textDesignModel.setConverter(new BigDecimalConverter());
		designModel.addColumn(textDesignModel);

		treeListControl.setDesignModel(designModel);
		ctx.session().setAttribute(getListAttributeName(), treeListControl);

		treeListControl.setDataModel(getDataModel(ctx));
		setTreeListControlAttributes(ctx, treeListControl);

		if(callFromMenu == null) {
			searchForm = UserHelper.saveAttributes(user, userConfig, searchForm, userConfigurationDao);
		}

		searchForm = UserHelper.loadAttributes(user, userConfig, searchForm);

		ctx.forwardToInput();
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
		TimeListForm form = (TimeListForm)ctx.form();
		form.clear();
		ctx.session().removeAttribute(getListAttributeName());
		ctx.forwardToInput();
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public TreeGroupDataModel getDataModel(final ActionContext ctx) throws Exception {

		TreelistControl treeListControl = (TreelistControl)ctx.session().getAttribute(getListAttributeName());
		TreelistDesignModel designModel = (TreelistDesignModel)treeListControl.getDesignModel();

		TimeListForm searchForm = (TimeListForm)ctx.form();

		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());

		Config userConfig = userConfigurationDao.getUserConfig(user);
		int firstDayOfWeek = userConfig.getIntegerProperty(UserConfig.FIRST_DAY_OF_WEEK, Calendar.MONDAY);
		searchForm.calcTime(firstDayOfWeek);

		String searchWithInactive = searchForm.getSearchWithInactive();
		if(StringUtils.equals("on", searchForm.getSearchWithInactive())) {
			searchWithInactive = Boolean.TRUE.toString();
		} else {
			searchWithInactive = Boolean.FALSE.toString();
		}

		//Setzt die nicht angezeigten Checkboxen auf on, falls sie in der DB true sind, 
		//damit es nicht zu einer fehlerhaften Sicherung in der DB kommt
		if(StringUtils.equals("true", searchForm.getSearchWithCharges())) 
			searchForm.setSearchWithCharges("on");

		if(StringUtils.equals("true", searchForm.getSearchWithActivity())) 
			searchForm.setSearchWithActivity("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithComment())) 
			searchForm.setSearchWithComment("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithHour())) 
			searchForm.setSearchWithComment("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithCost())) 
			searchForm.setSearchWithComment("on");
		
		if(StringUtils.equals("true", searchForm.getSearchWithBudget())) 
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

		TimeRangeEnum timeRange = StringValuedEnumReflect.getEnum(TimeRangeEnum.class, searchForm.getScale());

		Map<MultiKey, Map<String, Object>> taskTimes = timeDao.findSum(searchForm.getFromDate(), searchForm.getToDate(),
				timeRange, firstDayOfWeek, timeCustomerId, timeProjectId, timeTaskId, timeCustomersSet, userId, groupId, Boolean.parseBoolean(searchWithInactive));

		//Holidays
		
		
		
		
		Calendar fromCal = new GregorianCalendar();
		fromCal.setTime(searchForm.getFromDate());
		Calendar toCal = new GregorianCalendar();
		toCal.setTime(searchForm.getToDate());
		MultiKeyMap holidayMap = holidayRegistry.getYearHolidays(getLocale(ctx.request()), fromCal, toCal);

		//Cols
		List<UserReportCol> colsList = new ArrayList<UserReportCol>();
		Iterator it = new CalendarRangeIterator(fromCal, toCal, timeRange, firstDayOfWeek);
		while (it.hasNext()) {
			CalendarRange range = (CalendarRange)it.next();
			String prop = range.getInternalDescription();

			if (timeRange.equals(TimeRangeEnum.DAY)) {
				boolean weekend = (range.getFrom().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
						|| (range.getFrom().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

				boolean holiday = holidayMap.containsKey(new Integer(range.getFrom().get(Calendar.YEAR)), new Integer(range
						.getFrom().get(Calendar.MONTH)), new Integer(range.getFrom().get(Calendar.DATE)));
				colsList.add(new UserReportCol(prop, range.getDescription(), holiday, weekend, range.isEnd()));
			} else {
				colsList.add(new UserReportCol(prop, range.getDescription()));
			}
		}

		//PrpareData
		List<DynaBean> resultDynaList = new ArrayList<DynaBean>();
		for (it = taskTimes.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry)it.next();

			MultiKey key = (MultiKey)entry.getKey();
			Map dayMap = (Map)entry.getValue();

			DynaBean dynaBean = new LazyDynaBean("taskTimeUserReport");
			dynaBean.set("customerId", key.getKey(0));
			dynaBean.set("customer", key.getKey(1));
			dynaBean.set("customerNumber", key.getKey(2));
			dynaBean.set("projectId", key.getKey(3));
			dynaBean.set("project", key.getKey(4));
			dynaBean.set("projectNumber", key.getKey(5));
			dynaBean.set("taskId", key.getKey(6));
			dynaBean.set("task", key.getKey(7));

			Iterator it2 = new CalendarRangeIterator(fromCal, toCal, timeRange, firstDayOfWeek);

			while (it2.hasNext()) {
				CalendarRange range = (CalendarRange)it2.next();
				Object ob = dayMap.get(range.getInternalDescription());

				if (ob != null) {
					Double dbl = Double.valueOf(ob.toString());
					BigDecimal value = BigDecimal.valueOf(dbl);
					dynaBean.set(range.getInternalDescription(), value);
				}
			}

			resultDynaList.add(dynaBean);
		}

		BigDecimal weekTotal = new BigDecimal(0);
		for (DynaBean rowElement : resultDynaList) {
			for (UserReportCol reportCol : colsList) {

				BigDecimal value = (BigDecimal)rowElement.get(reportCol.getProperty());
				if (value != null) {

					//tagestotal
					BigDecimal total = reportCol.getTotal();
					if (total != null) {
						total = total.add(value);
					} else {
						total = value;
					}
					reportCol.setTotal(total);

					//weektotal
					if (timeRange.equals(TimeRangeEnum.DAY)) {
						weekTotal = weekTotal.add(value);
					}
				}

				if (timeRange.equals(TimeRangeEnum.DAY)) {
					if (reportCol.isEnd()) {
						BigDecimal wTotal = reportCol.getWeekTotal();
						if (wTotal != null) {
							wTotal = wTotal.add(weekTotal);
						} else {
							wTotal = weekTotal;
						}
						reportCol.setWeekTotal(wTotal);
						weekTotal = new BigDecimal(0);
					}
				}
			}
		}

		Collections.sort(resultDynaList, new PropertyComparator("customerNumber", "customer", "projectNumber", "project", "task"));

		for (UserReportCol reportCol : colsList) {

			ColumnHeaderDesignModel header = new ColumnHeaderDesignModelImp();
			header.setTitle(reportCol.getTitle());
			header.setAlignment(AlignmentType.RIGHT);
			header.setFilter(false);

			if (reportCol.isHoliday()) {
				header.setStyle("background-color: Yellow; color: Black;");
			} else if (reportCol.isWeekend()) {
				header.setStyle("background-color: Gray;");
			}

			ColumnTextDesignModel textDesignModel = new ColumnTextDesignModelImp();
			textDesignModel.setProperty(reportCol.getProperty());
			textDesignModel.setWidth(80);
			textDesignModel.setAlignment(AlignmentType.RIGHT);
			textDesignModel.setSortable(false);
			textDesignModel.setHeader(header);

			textDesignModel.setConverter(new BigDecimalConverter());
			designModel.addColumn(textDesignModel);

		}

		DynaTreeDataModel root = new DynaTreeDataModel("root");
		root.set("id", "ROOT");
		root.set("name", "<root>");

		BigDecimal customerTotalHour = new BigDecimal(0);
		BigDecimal projectTotalHour = new BigDecimal(0);

		Integer mCustomerId = null;
		Integer mProjectId = null;
		DynaTreeDataModel currentCustomerModel = null;
		DynaTreeDataModel currentProjectModel = null;    

		for (DynaBean queryResult : resultDynaList) {

			Integer customerId = (Integer)queryResult.get("customerId");
			String customerName = (String)queryResult.get("customer");
			String customerNumber = (String)queryResult.get("customerNumber");

			Integer projectId = (Integer)queryResult.get("projectId");
			String projectName = (String)queryResult.get("project");
			String projectNumber = (String)queryResult.get("projectNumber");

			Integer taskId = (Integer)queryResult.get("taskId");
			String taskName = (String)queryResult.get("task");

			if (!customerId.equals(mCustomerId)) {
				//new customer

				if (currentCustomerModel != null) {
					currentCustomerModel.set("totalCustomer", customerTotalHour);
					customerTotalHour = new BigDecimal(0);
				}

				DynaTreeDataModel customerModel = new DynaTreeDataModel(customerName);
				customerModel.set("id", customerId.toString());
				customerModel.set("name", TimeCustomerUtil.GetCustomerNumber(customerName, customerNumber, appConfig));
				customerModel.set("image", "customer");
				root.addChild(customerModel);

				mCustomerId = customerId;
				currentCustomerModel = customerModel;

				mProjectId = null;
			}

			if (!projectId.equals(mProjectId)) {
				//new project
				if (currentProjectModel != null) {
					currentProjectModel.set("totalProject", projectTotalHour);
					projectTotalHour = new BigDecimal(0);
				}

				mProjectId = projectId;

				DynaTreeDataModel projectModel = new DynaTreeDataModel(projectName);
				projectModel.set("id", "proj_" + projectId.toString());
				projectModel.set("name", TimeCustomerUtil.GetProjectNumber(projectName, projectNumber, appConfig));
				currentCustomerModel.addChild(projectModel);

				currentProjectModel = projectModel;
			}

			DynaTreeDataModel taskModel = new DynaTreeDataModel(taskName);
			taskModel.set("id", "task_" + taskId.toString());
			taskModel.set("name", taskName);
			taskModel.set("image", "task");

			for (UserReportCol reportCol : colsList) {
				BigDecimal hour = (BigDecimal)queryResult.get(reportCol.getProperty());
				if (hour != null) {
					customerTotalHour = customerTotalHour.add(hour);
					projectTotalHour = projectTotalHour.add(hour);
					taskModel.set(reportCol.getProperty(), hour);
				}

			}

			currentProjectModel.addChild(taskModel);

		}

		if (currentProjectModel != null) {
			currentProjectModel.set("totalProject", projectTotalHour);
		}

		if (currentCustomerModel != null) {
			currentCustomerModel.set("totalCustomer", customerTotalHour);
		}

		ctx.session().setAttribute("timematrixreport.colslist", colsList);
		return root;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onExportList(ControlActionContext ctx) throws Exception {

		TimeListForm searchForm = (TimeListForm)ctx.session().getAttribute("TimeListForm");
		List<UserReportCol> colsList = (List<UserReportCol>)ctx.session().getAttribute("timematrixreport.colslist");

		String filename = "time_matrix_report.xls";
		Util.setExportHeader(ctx.response(), "application/vnd.ms-excel", filename);
		ctx.response().setHeader("extension", "xls");

		OutputStream out = ctx.response().getOutputStream();

		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		boolean isAdmin = userPrincipal.hasRight("timeadmin");

		timeExcelCreator.createMatrixExcelReport(searchForm, (DynaTreeDataModel)ctx.control().getDataModel(), out,
				getResources(ctx.request()), getLocale(ctx.request()), isAdmin, colsList);

		out.close();

		ctx.forwardToResponse();
	}

	@Override
	public void deleteObject(ControlActionContext ctx, String key) throws Exception {
		throw new UnsupportedOperationException("delete not supported");
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
