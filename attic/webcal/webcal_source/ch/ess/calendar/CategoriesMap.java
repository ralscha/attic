
package ch.ess.calendar;

import java.sql.*;
import java.util.*;
import ch.ess.calendar.db.*;

import java.sql.SQLException;
import ch.ess.calendar.db.*;

public class CategoriesMap {

	private Map categoriesmap;

	public CategoriesMap() {
		categoriesmap = new HashMap();
	}

	public void init() throws SQLException {
		
		categoriesmap.clear();
		
		CategoriesTable catTable = new CategoriesTable();
		Iterator it = catTable.select();
		while (it.hasNext()) {
			Categories category = (Categories)it.next();
			categoriesmap.put(new Integer(category.getCategoryid()), category);
		}		
	}
	
	public String getDescription(Integer id) {
		Categories category = (Categories)categoriesmap.get(id);
		if (category != null)
			return category.getDescription();
		return null;	
	}
		
	public String getDescription(int id) {
		return getDescription(new Integer(id));
	}
	
	public String getColor(Integer id) {
		Categories category = (Categories)categoriesmap.get(id);
		if (category != null)
			return category.getColor();		
		return null;		
	}
	
	public int getSize() {
		if (categoriesmap != null)
			return categoriesmap.size();
		else
			return 0;	
	}
	
	public String getColor(int id) {
		return getColor(new Integer(id));
	}
	
  public Categories getCategory(int id) {
    return getCategory(new Integer(id));
  }

	public Categories getCategory(Integer id) {
		return (Categories)categoriesmap.get(id);
	}
		
	public Integer[] getKeys() {
		return (Integer[])categoriesmap.keySet().toArray(new Integer[categoriesmap.size()]);
	}
}
