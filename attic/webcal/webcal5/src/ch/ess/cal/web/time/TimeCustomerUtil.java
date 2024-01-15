package ch.ess.cal.web.time;

import ch.ess.base.service.AppConfig;
import ch.ess.base.service.Config;
import ch.ess.cal.model.TimeCustomer;
import ch.ess.cal.model.TimeProject;

public class TimeCustomerUtil {
	
	public static String GetCustomerNumber(TimeCustomer customer, Config appConfig){
		String customerNumber = "";
		
		if(customer.getCustomerNumber() != null){
			customerNumber = appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG,	"<")
	        		  +customer.getCustomerNumber()+appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">")
	        		  +" "+customer.getName();
		} else {
			customerNumber = customer.getName();
		}
	     
		return customerNumber;
	}
	
	public static String GetCustomerNumber(String customerName, String customerNumber, Config appConfig){
		
		if(customerNumber != null){
			customerName = appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG,	"<")
	        		  +customerNumber+appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">")
	        		  +" "+customerName;
		}
	     
		return customerName;
	}
	
	public static String GetProjectNumber(TimeProject project, Config appConfig){
		String projectNumber = "";
		
		if(project.getProjectNumber() != null){
			projectNumber = appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG,	"<")
	        		  +project.getProjectNumber()+appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">")
	        		  +" "+project.getName();
		} else {
			projectNumber = project.getName();
		}
	     
		return projectNumber;
	}
	
	public static String GetProjectNumber(String projectName, String projectNumber, Config appConfig){
		
		if(projectNumber != null){
			projectName = appConfig.getStringProperty(AppConfig.OPEN_CUSTOMER_TAG,	"<")
	        		  +projectNumber+appConfig.getStringProperty(AppConfig.CLOSE_CUSTOMER_TAG, ">")
	        		  +" "+projectName;
		}
	     
		return projectName;
	}

	
}