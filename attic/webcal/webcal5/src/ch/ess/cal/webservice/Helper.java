package ch.ess.cal.webservice;

import java.util.List;

import ch.ess.base.dao.UserDao;
import ch.ess.base.model.TranslationText;
import ch.ess.base.model.User;
import ch.ess.cal.dao.CategoryDao;
import ch.ess.cal.dao.EventDao;
import ch.ess.cal.model.Category;
import ch.ess.cal.model.Event;

/**
 * Helper-Class of CalService
 * @author  ESS Development AG
 * @version  1.0
 */
public class Helper {
	/**
	 * @uml.property  name="u"
	 * @uml.associationEnd  
	 */
	User u;
	/**
	 * @uml.property  name="ud"
	 * @uml.associationEnd  
	 */
	UserDao ud;
	/**
	 * @uml.property  name="ed"
	 * @uml.associationEnd  
	 */
	EventDao ed;
	
	/**
	 * Creates and returns a user object
	 * @param userDao UserDao-Instance
	 * @param username username to check
	 * @param password password to check
	 * @return created user object
	 */
	public User getUserObj(UserDao userDao, String username, String password){
		this.ud = userDao;
	    if(ud.findByNameAndToken(username, password) != null){
	    	u = ud.findByNameAndToken(username, password);	    
	    }
		return u;
	}

	/**
	 * Selects and returns all normal WebCal events
	 * @param eventDao EventDao instance
	 * @param username username to check
	 * @param password password to check
	 * @param userDao UserDao instance
	 * @return List<Event> List of all found events
	 */
	public List<Event> getNormalEvents(EventDao eventDao, String username, String password, UserDao userDao){
		this.ed = eventDao;
		this.ud = userDao;
		u = getUserObj(ud, username, password);		
		return ed.getNormalEventsByUser(u);
	}
	
	/**
	 * Selects and returns all recurring WebCal-events
	 * @param eventDao EventDao instance
	 * @param username username to check
	 * @param password password to check
	 * @param userDao UserDao instance
	 * @return List of all found recurrences with the matching event
	 */
	public List<Object[]> getRecurEvents(EventDao eventDao, String username, String password, UserDao userDao){
		this.ed = eventDao;
		this.ud = userDao;
		u = getUserObj(ud, username, password);
		return ed.getRecurEventsByUser(u);
	}
	
	/**
	 * Builds and returns a xml-file with all WebCal-categorys
	 * @param categoryDao CategoryDao instance
	 * @return string holding the xml data
	 * @see #buildCatXML(List)
	 */
	public String getCatgories(CategoryDao categoryDao){
		return buildCatXML(categoryDao.findAll());
	}
	
	/**
	 * Builds an xml from c
	 * @param c
	 * @return string representing an xml
	 */
	private String buildCatXML(List<Category> c){
		String xml = "<SynchroCal.Categorys assembly=\"SynchroCal\"><c>";
		
		for (Category category : c) {
			xml += "<SynchroCal.Category assembly=\"SynchroCal\">";
			xml += "<id>"+category.getId()+"</id>";
			xml += "<translations>";
			for (TranslationText t : category.getTranslation().getTranslationTexts()) {
				xml += "<string lang=\""+t.getLocale()+"\">"+t.getText()+"</string>";
			}
			xml += "</translations>";
			xml += "</SynchroCal.Category>";
		}
		xml += "</c></SynchroCal.Categorys>";
		return xml;
	}
	
	public static String replace(String toReplace){
		String result = toReplace;
		result = result.replace("/", "REPLACECODE:01");
		result = result.replace("\\", "REPLACECODE:02");
		result = result.replace("&", "REPLACECODE:03");
		result = result.replace("<", "REPLACECODE:04");
		result = result.replace(">", "REPLACECODE:05");
		result = result.replace("{", "REPLACECODE:06");
		result = result.replace("}", "REPLACECODE:07");
		result = result.replace("$", "REPLACECODE:08");
		return result;
	}
}
