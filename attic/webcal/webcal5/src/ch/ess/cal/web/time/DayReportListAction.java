package ch.ess.cal.web.time;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.ActionScope;
import ch.ess.base.annotation.struts.StrutsAction;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.dao.TimeDao;
import ch.ess.cal.dao.TimeProjectDao;
import ch.ess.cal.dao.TimeTaskDao;
import ch.ess.cal.model.Time;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTask;
import ch.ess.cal.model.UserTimeCustomer;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ListControl;
import com.cc.framework.ui.model.ListDataModel;

@StrutsAction(path = "/dayReportList", form = TimeListForm.class, input = "/dayreportlist.jsp", scope = ActionScope.SESSION, roles = "$timeadmin;$time", validate = true)
public class DayReportListAction extends AbstractListAction {
	
  private TimeCustomerDao timeCustomerDao;
  private UserDao userDao;
  private TimeDao timeDao;
  private TimeTaskDao timeTaskDao;
  private TimeProjectDao timeProjectDao;
  private Config appConfig;
  private Boolean save = false;
  private Boolean increaseDate = false;
  private Boolean decreaseDate = false;
  
  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
	  this.timeCustomerDao = timeCustomerDao;
  }
  
  public void setUserDao(UserDao userDao) {
	  this.userDao = userDao;
  }
  
  public void setTimeDao(TimeDao timeDao) {
	  this.timeDao = timeDao;
  }
  
  public void setTimeTaskDao(TimeTaskDao timeTaskDao) {
	  this.timeTaskDao = timeTaskDao;
  }
  
  public void setTimeProjectDao(TimeProjectDao timeProjectDao) {
	  this.timeProjectDao = timeProjectDao;
  }
  
  public void setAppConfig(final Config appConfig) {
	this.appConfig = appConfig;
  }  

	@Override
	public void doExecute(ActionContext ctx) throws Exception {
		TimeListForm form = (TimeListForm)ctx.form();
		String callFromMenu = ctx.request().getParameter("startCrumb");

		if(callFromMenu == null) {
			if(form.getUserId() == null || form.getUserId().isEmpty()){
				ctx.addGlobalError("time.noUser");
				ctx.forwardToInput();
				
			}else{
				super.doExecute(ctx);
			}
		}else{
			super.doExecute(ctx);
		}
	}

  @Override
  public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

	  	TimeListForm form = (TimeListForm) ctx.form();
	  	String date;
		  	// saveModul zurücksetzen
		  if(this.save || this.increaseDate || this.decreaseDate){
			  date = form.getDateDailyReport();
			  this.save = false;
			  this.increaseDate = false;
			  this.decreaseDate = false;
		  }
		  else {
			  	date = form.getFrom();
		  }

	  	if(!TimeHelper.checkIsDateValid(date)){
	  		date = null; // Wenn Datum ungültig, dann auf NULL setzen --> aktueller Tag wird gesetzt.
	  	}
	  	if(date == null) {
	  		date = TimeHelper.getActualDay(); // wird weiter unten noch verwendet!
	  		form.setDateDailyReport(date);
	  		form.setDateActualWeek(TimeHelper.getActualWeekNumber());
	  	}
	  	else {
	  		form.setDateDailyReport(date);
	  		form.setDateActualWeek(TimeHelper.getWeekNumber(date));
	  	}
	  	form.setFrom(""); // Eingabefeld auf leer setzten.
	  	
	    getResources(ctx.request());

	    SimpleListDataModel dataModel = new SimpleListDataModel();
	    
	    List<Object[]> list = timeCustomerDao.findWithActiveTasks();
	    /*
	     * [0]: TimeCustomer
	     * [1]: TimeProject
	     * [2]: TimeTask
	     */

	    User user = new User();
	    UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
	    if(userPrincipal.hasRight("timeadmin")){
	    	if(form.getUserId() == null){
	    		form.setUserLoggedIn(user.getFirstName()+" "+user.getName());
			    form.setUserId(userPrincipal.getUserId());	
	    	}
	    		user = userDao.findById(form.getUserId());
	    }else{
	    	user = userDao.findById(userPrincipal.getUserId());
		    form.setUserLoggedIn(user.getFirstName()+" "+user.getName());
		    form.setUserId(userPrincipal.getUserId());	    
	    }
	   
	    List<Time> times = timeCustomerDao.findWithTasksUserDate(user, TimeHelper.getDBQueryDate(date));
	    Float dailySum = 0f;
	    Set<Integer> projectIds = new HashSet<Integer>();
	    Integer idDummy = -1;
	    Boolean hasRight = false;
	    for (Object[] object : list) {
	    	hasRight = false;
	    	TimeCustomer cust = (TimeCustomer) object[0];
	    	TimeProject proj = (TimeProject) object[1];
	    	TimeTask task = (TimeTask) object[2];

			for (UserTimeCustomer userTimeCustomer : user.getUserTimeCustomers()) {
				if(cust.getId() == userTimeCustomer.getTimeCustomer().getId()){
					hasRight = true;
				}
			}
	    	
	    	TimeDaylistDisplay timeDisplay;
	    	boolean taskIsActive;
	    	if(task == null){
	    		taskIsActive = true;
	    	}else{
	    		taskIsActive = task.isActive();
	    	}
	  
	        timeDisplay = new TimeDaylistDisplay();
	        if(cust.isActive() && proj.isActive() && taskIsActive && hasRight){
		    	//Task/Tätigkeit
		    	if(task != null){
		    		timeDisplay.setTask(task.getName());
		    		//ID für Liste
		    		timeDisplay.setId(task.getId().toString());
		    	}
		    	else { 
		    		timeDisplay.setTask("-");
	    	    	timeDisplay.setId(idDummy.toString());
	    	    	idDummy--;
		    	}
		    	//Kunde
		    	if(cust != null){
		    		timeDisplay.setCustomerNumber(cust.getCustomerNumber());
		    		timeDisplay.setCustomer(cust.getName());
		    	}
		    	//Projekt/Auftrag
		    	if(proj != null){
		    		timeDisplay.setProjectId(proj.getId().toString());
		    		timeDisplay.setProject(proj.getName());
		    	}
	
		    	//Zeit setzen
		    	//Zeiteintrag vorhanden
		    	int i = 0;
		    	for(Time timeExist : times) {
		    		if((task == null && timeExist.getTimeProject().getId() == proj.getId()) || (task != null && timeExist.getTimeTask() != null && timeExist.getTimeTask().getId() == task.getId())){
		    			i++; //Time-Eintrag nummerieren
		    			if(i>1){
		    				dataModel.add(timeDisplay);
		    				timeDisplay = new TimeDaylistDisplay();
		    	    		if(task == null){
		    		    		timeDisplay.setTask("-");
		    		    		timeDisplay.setId("-1");
		    	    		}else{
		    	    			timeDisplay.setTask(timeExist.getTimeTask().getName());
			    	    		timeDisplay.setId(timeExist.getTimeTask().getId().toString());
		    	    		}
	
		    	    		timeDisplay.setCustomerNumber(cust.getCustomerNumber());
		    	    		timeDisplay.setCustomer(cust.getName());
		    	    		timeDisplay.setProjectId(proj.getId().toString());
		    	    		timeDisplay.setProject(proj.getName());
		    			}
		    			if(timeExist.getWorkInHour() != null){
			    			timeDisplay.setWorkinhour(timeExist.getWorkInHour().setScale(2, BigDecimal.ROUND_DOWN).toString());		    				
		    			}
		    			timeDisplay.setActivity(timeExist.getActivity());		
		    			timeDisplay.setComment(timeExist.getComment( ));	  		
		    			if(timeExist.getAmount() != null)timeDisplay.setAmount(Constants.TWO_DECIMAL_FORMAT.format(timeExist.getAmount()));	 		
		    			timeDisplay.setChargesStyle(timeExist.getChargesStyle());
		    			timeDisplay.setTimeId(timeExist.getId().toString());
		    			
		    			//Zeittotal aufsummieren.
		    			if(timeExist.getWorkInHour() != null){
					    	dailySum = dailySum + timeExist.getWorkInHour().floatValue();
		    			}
		    		}
		    	}

		        dataModel.add(timeDisplay);
	        }
	    	
	    }
	    
	    form.setDailySum(dailySum.toString());
	    
	    dataModel.sort("customerNumber", SortOrder.ASCENDING);

	    return dataModel;
	  }
	
	@Override
	public void deleteObject(ControlActionContext ctx, String key) throws Exception {
		// Unused: Auto-generated method stub		
	}
	
	public void save_onClick(final FormActionContext ctx) throws Exception {
		//Save-Modus einschalten
		this.save = true;
		ListControl ctrl = (ListControl) ctx.session().getAttribute("listControl");
		SimpleListDataModel model = (SimpleListDataModel) ctrl.getDataModel();
		String defaultHourRate;
    	BigDecimal rate = null;
		for (Object obj : model.getObjectList() ) {
			TimeDaylistDisplay timeDisplay = (TimeDaylistDisplay) obj;
			//Testem ob korrekte Stunden eingetragen wurden...
			if(timeDisplay.getWorkinhour() != null && !timeDisplay.getWorkinhour().isEmpty()){
				try {
					BigDecimal big = new BigDecimal(timeDisplay.getWorkinhour());
				} catch (NumberFormatException e) {
					continue;
				}
			}
			
			//existiernede Time löschen
			if(timeDisplay.getWorkinhour() != null && timeDisplay.getWorkinhour().isEmpty() && timeDisplay.getAmount() != null && timeDisplay.getAmount().isEmpty() && timeDisplay.getTimeId() != null){
				timeDao.delete(timeDisplay.getTimeId());
			}
			//TIME nur speichern, wenn Arbeitszeit || Spesenbetrag eingetragen sind
			//Update Time
			else if((timeDisplay.getWorkinhour() != null && !timeDisplay.getWorkinhour().isEmpty() && timeDisplay.getTimeId() != null && !timeDisplay.getTimeId().isEmpty())){
				Time time = timeDao.findById(timeDisplay.getTimeId());
				if(timeDisplay.getWorkinhour() != null && !timeDisplay.getWorkinhour().isEmpty()){
					time.setWorkInHour(new BigDecimal(timeDisplay.getWorkinhour()));
					BigDecimal hour = BigDecimal.valueOf(Double.valueOf(timeDisplay.getWorkinhour()));
					if(time.getHourRate() != null){
						//Time mit bestehendem Wert verrechnen
						time.setCost(hour.multiply(time.getHourRate()));
						rate = time.getHourRate();
					}else if(time.getTimeTask() != null && time.getTimeTask().getHourRate() != null ){
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
						defaultHourRate = appConfig.getStringProperty(AppConfig.DEFAULT_HOUR_RATE);
						time.setCost(hour.multiply(BigDecimal.valueOf(Double.valueOf(defaultHourRate))));
						rate = BigDecimal.valueOf(Double.valueOf(defaultHourRate));
					}
			      
			      time.setHourRate(rate);

				}else{
					time.setWorkInHour(null);
				}

				time.setActivity(timeDisplay.getActivity());
				time.setComment(timeDisplay.getComment());
				if(timeDisplay.getAmount() != null && !timeDisplay.getAmount().isEmpty()){
					time.setAmount(new BigDecimal(timeDisplay.getAmount()));
				}else{
					time.setAmount(null);
				}
				time.setChargesStyle(timeDisplay.getChargesStyle());
			
				if(timeDisplay.getId().startsWith("-")){	
					time.setTimeTask(null);
				}else{
					time.setTimeTask(timeTaskDao.findById(timeDisplay.getId()));
				}	
				timeDao.save(time);
			}
			//New Time
			else if(timeDisplay.getWorkinhour() != null && !timeDisplay.getWorkinhour().isEmpty()) {
			  	TimeListForm form = (TimeListForm) ctx.session().getAttribute("TimeListForm");
			  	Date date = TimeHelper.getDateFromString(form.getDateDailyReport());			  	
			  	User user = userDao.findById(form.getUserId());			  	
			  	TimeTask task = null;
			  	if(timeDisplay.getId() != null && timeDisplay.getId() != "-1"){
			  		 task = timeTaskDao.findById(timeDisplay.getId());
			  	}
				TimeProject project = timeProjectDao.findById(timeDisplay.getProjectId());
				Time time = new Time();
				if(task != null && !task.getId().toString().startsWith("-")){
					time.setTimeTask(task);
				}else{
					time.setTimeTask(null);
				}
				time.setTimeProject(project);
				if(!timeDisplay.getWorkinhour().isEmpty()){
					time.setWorkInHour(new BigDecimal(timeDisplay.getWorkinhour()));
					BigDecimal hour = BigDecimal.valueOf(Double.valueOf(timeDisplay.getWorkinhour()));
					if(time.getHourRate() != null){
						//Time mit bestehendem Wert verrechnen
						time.setCost(hour.multiply(time.getHourRate()));
						rate = time.getHourRate();
					}else if(time.getTimeTask() != null && time.getTimeTask().getHourRate() != null ){
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
						defaultHourRate = appConfig.getStringProperty(AppConfig.DEFAULT_HOUR_RATE);
						time.setCost(hour.multiply(BigDecimal.valueOf(Double.valueOf(defaultHourRate))));
						rate = BigDecimal.valueOf(Double.valueOf(defaultHourRate));
					}
			      
			      time.setHourRate(rate);

				}
				time.setActivity(timeDisplay.getActivity());
				time.setComment(timeDisplay.getComment());
				if(timeDisplay.getAmount() != null && !timeDisplay.getAmount().isEmpty()){
					time.setAmount(new BigDecimal(timeDisplay.getAmount()));
				}else{
					time.setAmount(null);
				}
				time.setChargesStyle(timeDisplay.getChargesStyle());
				time.setTaskTimeDate(date);
				time.setUser(user);
				
				timeDao.save(time);
			}
		}
		
		ctx.addGlobalMessage("common.updateOK");
	}
	
	public void increaseDate_onClick(FormActionContext ctx) throws Exception {
		  increaseDate = true;
		  TimeListForm form = (TimeListForm) ctx.form();
		  String date;  
		  Date d = new Date();
 
		  date = form.getDateDailyReport();
		  if(date == null) {
		  		date = TimeHelper.getActualDay(); // wird weiter unten noch verwendet!
		  		form.setDateDailyReport(date);
		  		form.setDateActualWeek(TimeHelper.getActualWeekNumber());
		  }
		  d = TimeHelper.getDateFromString(date);
		  d.setTime(d.getTime() + 1 * 24 * 60 * 60 * 1000);
		  date = TimeHelper.getStringFromDate(d);
		  form.setDateDailyReport(date);
	  }
	  
	  public void decreaseDate_onClick(FormActionContext ctx) throws Exception {
		  decreaseDate = true;
		  TimeListForm form = (TimeListForm) ctx.form();
		  String date;  
		  Date d = new Date();
		  
		  date = form.getDateDailyReport();
		  if(date == null) {
		  		date = TimeHelper.getActualDay(); // wird weiter unten noch verwendet!
		  		form.setDateDailyReport(date);
		  		form.setDateActualWeek(TimeHelper.getActualWeekNumber());
		  }
		  d = TimeHelper.getDateFromString(date);
		  d.setTime(d.getTime() - 1 * 24 * 60 * 60 * 1000);
		  date = TimeHelper.getStringFromDate(d);
		  form.setDateDailyReport(date);
	  }
}
