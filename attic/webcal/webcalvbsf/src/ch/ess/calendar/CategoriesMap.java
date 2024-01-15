
package ch.ess.calendar;

import java.sql.*;
import java.util.*;
import ch.ess.calendar.db.*;
import java.sql.SQLException;
import ch.ess.calendar.db.*;
import ch.ess.util.pool.*;
import com.objectmatter.bsf.*;

public class CategoriesMap {

	private Map categoriesmap;

	public CategoriesMap() {
		categoriesmap = new HashMap();
	}

	public void init() throws SQLException {
		
		categoriesmap.clear();
		
		Database db = PoolManager.requestDatabase();
		try {
		  Categories[] cats = (Categories[])db.list(Categories.class);
		  if (cats != null) {
		    for (int i = 0; i < cats.length; i++) {
          categoriesmap.put(cats[i].getCategoryid(), cats[i]);
		    }
		  }  		
  	} finally {
      PoolManager.returnDatabase(db);
    }
	}
	
	public String getDescription(Long id) {
		Categories category = (Categories)categoriesmap.get(id);
		if (category != null)
			return category.getDescription();
		return null;	
	}
		
	public String getDescription(int id) {
		return getDescription(new Long(id));
	}
	
	public String getColor(Long id) {
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
	
	  
	public Categories getCategory(Long id) {
		return (Categories)categoriesmap.get(id);
	}
		
	public Long[] getKeys() {
		return (Long[])categoriesmap.keySet().toArray(new Long[categoriesmap.size()]);
	}
}
