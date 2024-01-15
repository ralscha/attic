package ch.ess.cal.web.timetaskbudget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang.StringUtils;

import ch.ess.base.Constants;
import ch.ess.base.annotation.struts.Role;
import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.dao.UserDao;
import ch.ess.base.model.User;
import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.AbstractListAction;
import ch.ess.base.web.MapForm;
import ch.ess.base.web.SimpleListDataModel;
import ch.ess.base.web.UserPrincipal;
import ch.ess.cal.dao.TimeTaskBudgetDao;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;
import ch.ess.cal.model.TimeTaskBudget;
import ch.ess.cal.web.time.UserHelper;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.model.ListDataModel;

@Role("$time")
public class TimeTaskBudgetListAction extends AbstractListAction {

	private UserDao userDao;
	private UserConfigurationDao userConfigurationDao;
	private TimeTaskBudgetDao timeTaskBudgetDao;
	private Config appConfig;

	public void setTimeTaskBudgetDao(final TimeTaskBudgetDao timeTaskBudgetDao) {
		this.timeTaskBudgetDao = timeTaskBudgetDao;
	}

	public void setAppConfig(final Config appConfig) {
		this.appConfig = appConfig;
	}  
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUserConfigurationDao(UserConfigurationDao userConfigurationDao) {
		this.userConfigurationDao = userConfigurationDao;
	}
	
	
	@Override
	public void doExecute(ActionContext ctx) throws Exception {

		String callFromMenu = ctx.request().getParameter("startCrumb");
		MapForm searchForm = (MapForm)ctx.form();
		UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
		User user = userDao.findById(userPrincipal.getUserId());
		Config userConfig = userConfigurationDao.getUserConfig(user);

		//Suchmasken setzen
		if(callFromMenu == null) {
			searchForm = UserHelper.saveAttributes(user, userConfig, searchForm, userConfigurationDao);
		}

		searchForm = UserHelper.loadAttributes(user, userConfig, searchForm);	
		
		super.doExecute(ctx);
	}

	@Override
	public ListDataModel getDataModel(final ActionContext ctx) throws Exception {

		SimpleListDataModel dataModel = new SimpleListDataModel();

		MapForm searchForm = (MapForm)ctx.form();

		List<TimeTaskBudget> timeTaskBudgets = null;
		if (searchForm != null) {
			SimpleDateFormat format = new SimpleDateFormat(Constants.getParseDateFormatPattern());
			format.setLenient(false);
			format.setTimeZone(Constants.UTC_TZ);

			Date from = null;
			Date to = null;

			String fromStr = searchForm.getStringValue("from");
			String toStr = searchForm.getStringValue("to");

			if (StringUtils.isNotBlank(fromStr)) {
				try {
					from = format.parse(fromStr);
				} catch (ParseException pe) {
					searchForm.setValue("from", null);
				}
			}
			if (StringUtils.isNotBlank(toStr)) {
				try {
					to = format.parse(toStr);
				} catch (ParseException pe) {
					searchForm.setValue("to", null);
				}
			}
			UserPrincipal userPrincipal = (UserPrincipal)SecurityUtil.getPrincipal(ctx.session());
			User user = userDao.findById(userPrincipal.getUserId());
			Config userConfig = userConfigurationDao.getUserConfig(user);
			String searchWithInactive = userConfig.getStringProperty(UserConfig.ATTRIBUTE_INACTIVE);
			
			timeTaskBudgets = timeTaskBudgetDao.find(from, to, searchForm.getStringValue("timeCustomerId"), 
					searchForm.getStringValue("timeProjectId"), searchForm.getStringValue("timeTaskId"), Boolean.parseBoolean(searchWithInactive));
		} else {
			timeTaskBudgets = timeTaskBudgetDao.findAll();
		}

		SimpleDateFormat format = new SimpleDateFormat(Constants.getDateFormatPattern());

		for (TimeTaskBudget timeTaskBudget : timeTaskBudgets) {

			DynaBean dynaBean = new LazyDynaBean("timeTaskBudgetList");

			dynaBean.set("id", timeTaskBudget.getId().toString());

			if (timeTaskBudget.getTimeTask() != null) {
				dynaBean.set("timeTask", timeTaskBudget.getTimeTask().getName());
				setProjectString(dynaBean, timeTaskBudget.getTimeTask().getTimeProject());
				setCustomerString(dynaBean, timeTaskBudget.getTimeTask().getTimeProject().getTimeCustomer());
			} else {
				dynaBean.set("timeTask", null);
				setProjectString(dynaBean, timeTaskBudget.getTimeProject());
				setCustomerString(dynaBean, timeTaskBudget.getTimeProject().getTimeCustomer());    
			}
			dynaBean.set("date", timeTaskBudget.getDate());
			dynaBean.set("dateStr", format.format(timeTaskBudget.getDate()));
			dynaBean.set("amount", timeTaskBudget.getAmount());

			dynaBean.set("deletable", timeTaskBudgetDao.canDelete(timeTaskBudget));

			dataModel.add(dynaBean);

		}

		return dataModel;
	}

	@Override
	public void deleteObject(final ControlActionContext ctx, final String key) throws Exception {
		timeTaskBudgetDao.delete(key);
	}

	@Override
	public String getTitle(String id, ActionContext ctx) {
		if (StringUtils.isNotBlank(id)) {
			TimeTaskBudget timeTaskBudget = timeTaskBudgetDao.findById(id);
			if (timeTaskBudget != null && timeTaskBudget.getTimeTask() != null) {
				return timeTaskBudget.getTimeTask().getName();
			} else if (timeTaskBudget != null && timeTaskBudget.getTimeProject() != null) {
				return timeTaskBudget.getTimeProject().getName();
			}
		}
		return null;
	}

	private void setCustomerString(DynaBean dynaBean, TimeCustomer customer) {
		if(StringUtils.isNotBlank(customer.getCustomerNumber())){
			dynaBean.set("timeCustomer", 
					appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<") +
					customer.getCustomerNumber() +
					appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">") + " " +
					customer.getName()
					);
		} else {
			dynaBean.set("timeCustomer", customer.getName());
		}
	}

	private void setProjectString(DynaBean dynaBean, TimeProject project) {
		if(StringUtils.isNotBlank(project.getProjectNumber())){
			dynaBean.set("timeProject", 
					appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG, "<") +
					project.getProjectNumber() +
					appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">") + " " +
					project.getName()
					);
		} else {
			dynaBean.set("timeProject", project.getName());
		}
	}
}
