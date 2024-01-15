package ch.ess.cal.web.time;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

import ch.ess.base.Constants;
import ch.ess.base.Util;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.enums.StringValuedEnumReflect;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractTreeListAction;
import ch.ess.base.web.BigDecimalConverter;
import ch.ess.base.web.DynaTreeDataModel;
import ch.ess.base.web.PropertyComparator;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.enums.TimeRangeEnum;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.UserTimeCustomer;
import ch.ess.cal.service.HolidayRegistry;
import ch.ess.cal.web.TimeMonthViewForm;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.taglib.TagHelp;
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.ExpansionMode;
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
import com.cc.framework.ui.model.imp.TreelistDesignModelImp;

@StrutsAction(path = "/timeMonthView", form = TimeMonthViewForm.class, input = "/timemonthview.jsp", scope = ActionScope.SESSION, roles = "$timeadmin;$time", validate = true)
public class TimeMonthViewAction extends AbstractTreeListAction {

	private TimeDao timeDao;
	private UserDao userDao;
	private UserConfigurationDao userConfigurationDao;
	private HolidayRegistry holidayRegistry;
	private TimeExcelCreator timeExcelCreator;
	private Config appConfig;
	private TimeCustomerDao timeCustomerDao;
	private TimeProjectDao timeProjectDao;

	public void setAppConfig(final Config appConfig) {
		this.appConfig = appConfig;
	} 

	public void setTimeDao(final TimeDao timeDao) {
		this.timeDao = timeDao;
	}
	public void setTimeCustomerDao(final TimeCustomerDao timeCustomerDao) {
		this.timeCustomerDao = timeCustomerDao;
	}
	
	public void setTimeProjectDao(final TimeProjectDao timeProjectDao) {
		this.timeProjectDao = timeProjectDao;
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
		String callFromMenu = ctx.request().getParameter("startCrumb");
		TreelistControl treeListControl = new TreelistControl();
		TimeMonthViewForm searchForm = (TimeMonthViewForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Config userConfig = userConfigurationDao.getUserConfig(user);
	
		if(callFromMenu == null) {
			searchForm = UserHelper.saveAttributes(user, userConfig, searchForm, userConfigurationDao);
		}else{
			Calendar cal = Calendar.getInstance();
			Date now = new Date();
			cal.setTime(now);
			searchForm.setMonth(String.valueOf(cal.get(Calendar.MONTH)));
			searchForm.setYear(String.valueOf(cal.get(Calendar.YEAR)));
			searchForm.setUserId(userPrincipal.getUserId());
		}
		searchForm = UserHelper.loadAttributes(user, userConfig, searchForm);
		
		TreelistDesignModel designModel = new TreelistDesignModelImp();
		designModel.setLocaleName("false");
		designModel.setName("listControl");
		designModel.setTitle(Util.translate(ctx.request(), "timemonthview.title"));
		designModel.setButtonPermission(ControlButton.REFRESH, TagHelp.toPermission("false"));
		designModel.setButtonPermission(ControlButton.EXPORTLIST, TagHelp.toPermission("true"));
		designModel.setWidth("1550px");
		designModel.setId("timemonthviewlist");
		designModel.setExpansionMode(ExpansionMode.FULL);
		designModel.showCheckBoxes(false);
		designModel.showLinesAtRoot(false);
		designModel.setRunAt(RunAt.CLIENT);
		designModel.setGroupSelect(false);

		ColumnTreeDesignModel columnTreeDesignModel = new ColumnTreeDesignModelImp();
		columnTreeDesignModel.setTitle(Util.translate(ctx.request(), "time.customerProjectTask"));
		columnTreeDesignModel.setProperty("name");
		designModel.addColumn(columnTreeDesignModel);

		treeListControl.setDesignModel(designModel);
		ctx.session().setAttribute(getListAttributeName(), treeListControl);

		treeListControl.setDataModel(getDataModel(ctx));
		setTreeListControlAttributes(ctx, treeListControl);
		ctx.forwardToInput();
	}


	@Override
	public TreeGroupDataModel getDataModel(final ActionContext ctx) throws Exception {

		TreelistControl treeListControl = (TreelistControl)ctx.session().getAttribute(getListAttributeName());
		TreelistDesignModel designModel = (TreelistDesignModel)treeListControl.getDesignModel();

		TimeMonthViewForm searchForm = (TimeMonthViewForm)ctx.form();
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

		
		Set<TimeCustomer> timeCustomersSet = null;
		String userId = null;
		String groupId = null;

		String timeCustomerId = "";
		String timeProjectId = "";
		String timeTaskId = "";

		if (userPrincipal.hasRight("timeadmin")) {
			groupId = "";
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

		TimeRangeEnum timeRange = StringValuedEnumReflect.getEnum(TimeRangeEnum.class, "D");

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
				colsList.add(new UserReportCol(prop, range.getDescription().substring(0,2), holiday, weekend, range.isEnd()));
			} else {
				colsList.add(new UserReportCol(prop, range.getDescription().substring(0,2)));
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

		int oldWeek = 0;
		Date from = searchForm.getFromDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(from);
		for (UserReportCol reportCol : colsList) {
			SimpleDateFormat sdf = new SimpleDateFormat( "E" );
			
			ColumnHeaderDesignModel header = new ColumnHeaderDesignModelImp();
			String day = "<span style='border-top: 1px dotted white; border-bottom: 1px dotted white;'>"+sdf.format(cal.getTime())+ "</span>";
			if(oldWeek == 0 || oldWeek != cal.get(Calendar.WEEK_OF_YEAR)){
				header.setTitle("<span style='font-size:15px;'>"+cal.get(Calendar.WEEK_OF_YEAR) + "</span><br />"+ day+"<br />" + reportCol.getTitle());
			}else{
				header.setTitle("<span style='font-size:15px;'>&nbsp;</span><br /> "+day+" <br />" + reportCol.getTitle());
			}
			oldWeek = cal.get(Calendar.WEEK_OF_YEAR);
			cal.add(Calendar.DATE, +1);
			
			header.setAlignment(AlignmentType.CENTER);
			header.setFilter(false);
			header.setStyle("width: 25px;");
			
			if (reportCol.isHoliday()) {
				header.setStyle("background-color: #cacf3b; color: Black; width: 25px;");
			} else if (reportCol.isWeekend()) {
				header.setStyle("background-color: Gray; width: 25px;");
			}

			ColumnTextDesignModel textDesignModel = new ColumnTextDesignModelImp();
			textDesignModel.setProperty(reportCol.getProperty());
			textDesignModel.setAlignment(AlignmentType.CENTER);
			textDesignModel.setSortable(false);
			textDesignModel.setHeader(header);
			textDesignModel.setAlignment("center");
			textDesignModel.setConverter(new BigDecimalConverter());
			designModel.addColumn(textDesignModel);
		}
			
		DynaTreeDataModel root = new DynaTreeDataModel("root");
		root.set("id", "ROOT");
		root.set("name", "<root>");
		Boolean sameCust = false;
		Integer customerId = null;
		for (DynaBean queryResult : resultDynaList) {
			sameCust = false;
			if(customerId == (Integer)queryResult.get("customerId")){
				sameCust = true;
			}
			
			customerId = (Integer)queryResult.get("customerId");
			Integer projectId = (Integer)queryResult.get("projectId");
			Integer taskId = (Integer)queryResult.get("taskId");
			String taskName = (String)queryResult.get("task");


			DynaTreeDataModel taskModel = new DynaTreeDataModel(taskName);
			taskModel.set("id", "task_" + taskId.toString());
			
			if(sameCust){
				taskModel.set("name", setCustomerString(timeCustomerDao.findById(customerId.toString())) + " / "+ setProjectString(timeProjectDao.findById(projectId.toString()))+ " / " +taskName);
			}else{
				taskModel.set("name", setCustomerString(timeCustomerDao.findById(customerId.toString())) + " / "+ setProjectString(timeProjectDao.findById(projectId.toString()))+ " / " +taskName);
			}

			for (UserReportCol reportCol : colsList) {
				BigDecimal hour = (BigDecimal)queryResult.get(reportCol.getProperty());
					taskModel.set(reportCol.getProperty(), hour);
			}

			root.addChild(taskModel);
		}
		
		
		DynaTreeDataModel empty = new DynaTreeDataModel("empty");
		empty.set("id", "empty");
		root.addChild(empty);
		//Daily Total
		DynaTreeDataModel total = new DynaTreeDataModel("Total");
		total.set("id", "total");
		from = searchForm.getFromDate();
		Date to = searchForm.getToDate();
		Date fromCopy = from;
		Calendar caca = Calendar.getInstance();
		Calendar cacaca = Calendar.getInstance();
		cacaca.setTime(fromCopy);
		BigDecimal grandTotal = BigDecimal.ZERO;
		BigDecimal hour = BigDecimal.ZERO;
		List<Time> timesByDay = timeDao.find(from, to, "", "", "", timeCustomersSet, userId, "", "", Boolean.parseBoolean(searchWithInactive));
		for (UserReportCol reportCol : colsList) {	
			hour = BigDecimal.ZERO;
			if(timesByDay != null && !timesByDay.isEmpty()){
				for(Time t : timesByDay){
					caca.setTime(t.getTaskTimeDate());
					if(caca.equals(cacaca)){
						if(t.getWorkInHour() != null)hour = hour.add(t.getWorkInHour());
					}
				}
			}
			grandTotal = grandTotal.add(hour);
			total.set(reportCol.getProperty(), hour);
			total.set("name", "Total");
			cacaca.add(Calendar.DATE, +1);
		}
		ctx.session().setAttribute("colsList", colsList);
		ctx.session().setAttribute("dailySum", Constants.TWO_DECIMAL_FORMAT.format(grandTotal));
		ctx.session().setAttribute("monthViewTree", root);
		root.addChild(total);
		return root;
	}

	  
	@SuppressWarnings("unchecked")
	@Override
	public void onExportList(ControlActionContext ctx) throws Exception {

		TimeMonthViewForm searchForm = (TimeMonthViewForm)ctx.session().getAttribute("TimeMonthViewForm");
		List<UserReportCol> colsList = (List<UserReportCol>)ctx.session().getAttribute("colsList");
		DynaTreeDataModel root = (DynaTreeDataModel)ctx.session().getAttribute("monthViewTree");
		String dailySum = (String)ctx.session().getAttribute("dailySum");
		
		String filename = "time_month_view.xls";
		Util.setExportHeader(ctx.response(), "application/vnd.ms-excel", filename);
		ctx.response().setHeader("extension", "xls");

		OutputStream out = ctx.response().getOutputStream();

		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		boolean isAdmin = userPrincipal.hasRight("timeadmin");

		timeExcelCreator.createMonthViewExport(searchForm, root, out,
				getResources(ctx.request()), getLocale(ctx.request()), isAdmin, colsList, dailySum);

		out.close();

		ctx.forwardToResponse();
	}

    public void increaseMonth_onClick(FormActionContext ctx) throws Exception {
		  TimeMonthViewForm form = (TimeMonthViewForm) ctx.form();
		  if(form.getMonth() != null && !form.getMonth().isEmpty()){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.MONTH, Integer.valueOf(form.getMonth()) +1);
			  List<Integer> years = timeDao.getYears();
			  int high = years.get(0);
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
		  TimeMonthViewForm form = (TimeMonthViewForm) ctx.form();
		  if(form.getMonth() != null && !form.getMonth().isEmpty()){
			  Calendar cal = Calendar.getInstance();
			  cal.set(Calendar.MONTH, Integer.valueOf(form.getMonth()) -1);
			  List<Integer> years = timeDao.getYears();
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
			  TimeMonthViewForm form = (TimeMonthViewForm) ctx.form();
			  if(form.getYear() != null && !form.getYear().isEmpty()){
				  Calendar cal = Calendar.getInstance();
				  cal.set(Calendar.YEAR, Integer.valueOf(form.getYear()) +1);
				  List<Integer> years = timeDao.getYears();
				  int high = years.get(0);
				  if(cal.get(Calendar.YEAR) <= high){
					  form.setYear(String.valueOf(cal.get(Calendar.YEAR)));
				  }else{
					  ctx.addGlobalMessage("time.cantGoFurther");
				  }		  
			  }
		  }
		  
		  public void decreaseYear_onClick(FormActionContext ctx) throws Exception {
			  TimeMonthViewForm form = (TimeMonthViewForm) ctx.form();
			  if(form.getYear() != null && !form.getYear().isEmpty()){
				  Calendar cal = Calendar.getInstance();
				  cal.set(Calendar.YEAR, Integer.valueOf(form.getYear()) -1);
				  List<Integer> years = timeDao.getYears();
				  int low = years.get(years.size() -1);
				  if(cal.get(Calendar.YEAR) >= low){
					  form.setYear(String.valueOf(cal.get(Calendar.YEAR)));
				  }else{
					  ctx.addGlobalMessage("time.cantGoFurther");
				  }	
			  }

		  }

		@Override
		public void deleteObject(ControlActionContext ctx, String key)
				throws Exception {
			// TODO Auto-generated method stub
			
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
}
