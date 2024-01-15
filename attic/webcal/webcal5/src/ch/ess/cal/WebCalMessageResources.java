package ch.ess.cal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;
import ch.ess.base.service.AppConfig;

public class WebCalMessageResources extends PropertyMessageResources {

	private static final long serialVersionUID = 1L;

	public WebCalMessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {
		super(factory, config, returnNull);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected synchronized void loadLocale(String localeKey) {
		boolean alreadyLoaded = (locales.get(localeKey) != null);
		String c_de = "", c_en = "", p_de = "", p_en ="", t_de="", t_en="";
		super.loadLocale(localeKey);
		if (!alreadyLoaded) {
			try {
		      Context ctx = new InitialContext();
		    
		    
		      DataSource db = (DataSource)ctx.lookup("java:comp/env/jdbc/cal");
				Connection con = db.getConnection();				
				
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT name, prop_value FROM cal_configuration");
							
				while(rs.next()){
					String name = rs.getString(1), prop_value = rs.getString(2);
					if(name.equals(AppConfig.CUSTOMER_DE)){
						c_de = prop_value;
					}else if(name.equals(AppConfig.CUSTOMER_EN)){
						c_en = prop_value;
					}else if(name.equals(AppConfig.PROJECT_DE)){
						p_de = prop_value;
					}else if(name.equals(AppConfig.PROJECT_EN)){
						p_en = prop_value;
					}else if(name.equals(AppConfig.TASK_DE)){
						t_de = prop_value;
					}else if(name.equals(AppConfig.TASK_EN)){
						t_en = prop_value;
					}
				}				
				rs.close();
				stmt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NameNotFoundException e) {
				e.printStackTrace();
		    } catch (NamingException e) {
				e.printStackTrace();
			}  
		    
			Map<String, String> newKeys = new HashMap<String, String>();

			for (Object keyObject : this.messages.keySet()) {
				String key = (String) keyObject;
				String oldValue;
				
				
				oldValue = (String) this.messages.get(key);
				String newValue = oldValue;

				if(oldValue.contains("Kunde")){
					newValue = newValue.replaceFirst("Kunde", c_de);
		
				}
				if(oldValue.contains("Projekt")){
					newValue = newValue.replaceFirst("Projekt", p_de);
					
				}
				if(oldValue.contains("Tätigkeit")){
					newValue = newValue.replaceFirst("Tätigkeit", t_de);
					
				}
				if(oldValue.contains("Customer")){
					newValue = newValue.replaceFirst("Customer", c_en);
					
				}
				if(oldValue.contains("Project")){
					newValue = newValue.replaceFirst("Project", p_en);
					
				}
				if(oldValue.contains("Task")){
					newValue = newValue.replaceFirst("Task", t_en);
					
				}
				newKeys.put(key, newValue);
			}
			this.messages.putAll(newKeys);
		}
	}
	
	 public synchronized void reload() {
	    locales.clear();
	    messages.clear();
	    formats.clear();
	}
		 
}


