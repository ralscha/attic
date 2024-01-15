package ch.ess.cal.web.time;

import org.apache.commons.lang.StringUtils;

import ch.ess.base.dao.UserConfigurationDao;
import ch.ess.base.model.User;
import ch.ess.base.service.Config;
import ch.ess.base.service.UserConfig;
import ch.ess.base.web.MapForm;
import ch.ess.cal.web.TimeMonthViewForm;

public class UserHelper {
	public static TimeListForm loadAttributes(User user, Config userConfig, TimeListForm form){
		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_ACTIVITY))){
			form.setSearchWithActivity("true");
		}else{
			form.setSearchWithActivity("false");
		}

		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_CHARGES))){
			form.setSearchWithCharges("true");
		}else{
			form.setSearchWithCharges("false");
		}

		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_COMMENT))){
			form.setSearchWithComment("true");
		}else{
			form.setSearchWithComment("false");
		}

		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_INACTIVE))){
			form.setSearchWithInactive("true");
		}else{
			form.setSearchWithInactive("false");
		}
		
		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_HOUR))){
			form.setSearchWithHour("true");
		}else{
			form.setSearchWithHour("false");
		}
		
		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_COST))){
			form.setSearchWithCost("true");
		}else{
			form.setSearchWithCost("false");
		}
		
		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_BUDGET))){
			form.setSearchWithBudget("true");
		}else{
			form.setSearchWithBudget("false");
		}
		
		return form;
	}
	
	public static TimeListForm saveAttributes(User user, Config userConfig, TimeListForm form, UserConfigurationDao userConfigurationDao){
		userConfig.setProperty(UserConfig.ATTRIBUTE_ACTIVITY, StringUtils.equals("on", form.getSearchWithActivity()) ? "true" : "false");
		userConfig.setProperty(UserConfig.ATTRIBUTE_CHARGES, StringUtils.equals("on", form.getSearchWithCharges()) ? "true" : "false");
		userConfig.setProperty(UserConfig.ATTRIBUTE_COMMENT, StringUtils.equals("on", form.getSearchWithComment()) ? "true" : "false");
		userConfig.setProperty(UserConfig.ATTRIBUTE_INACTIVE, StringUtils.equals("on", form.getSearchWithInactive()) ? "true" : "false");
		userConfig.setProperty(UserConfig.ATTRIBUTE_HOUR, StringUtils.equals("on", form.getSearchWithHour()) ? "true" : "false");
		userConfig.setProperty(UserConfig.ATTRIBUTE_COST, StringUtils.equals("on", form.getSearchWithCost()) ? "true" : "false");
		userConfig.setProperty(UserConfig.ATTRIBUTE_BUDGET, StringUtils.equals("on", form.getSearchWithBudget()) ? "true" : "false");

		userConfigurationDao.save(user, userConfig);
		
		return form;
	}

	
	public static MapForm loadAttributes(User user, Config userConfig, MapForm form){
		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_INACTIVE))){
			form.setSearchWithInactive("true");
		}else{
			form.setSearchWithInactive("false");
		}
		
		return form;
	}
	
	public static MapForm saveAttributes(User user, Config userConfig, MapForm form, UserConfigurationDao userConfigurationDao){
		userConfig.setProperty(UserConfig.ATTRIBUTE_INACTIVE, StringUtils.equals("on",form.getSearchWithInactive()) ? "true" : "false");
		userConfigurationDao.save(user, userConfig);
		
		return form;
	}
	
	public static TimeMonthViewForm loadAttributes(User user, Config userConfig, TimeMonthViewForm form){
		if(StringUtils.equals("true", userConfig.getStringProperty(UserConfig.ATTRIBUTE_INACTIVE))){
			form.setSearchWithInactive("true");
		}else{
			form.setSearchWithInactive("false");
		}
		
		return form;
	}
	
	public static TimeMonthViewForm saveAttributes(User user, Config userConfig, TimeMonthViewForm form, UserConfigurationDao userConfigurationDao){
		userConfig.setProperty(UserConfig.ATTRIBUTE_INACTIVE, StringUtils.equals("on",form.getSearchWithInactive()) ? "true" : "false");
		userConfigurationDao.save(user, userConfig);
		
		return form;
	}
}

