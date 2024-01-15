package ch.ess.calendar.db;

import java.sql.*;
import java.util.*;


public class CategoriesTable {

	private final static String deleteSQL = "DELETE FROM Categories";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT categoryid,description,color FROM Categories";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO Categories(description,color) VALUES(?,?)";

  private final static String insertUnloadSQL = 
    "INSERT INTO Categories(categoryid, description,color) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE Categories SET description=?, color=? WHERE categoryid=?";


	public int delete() throws SQLException {
		return (delete(null));
	}


	public boolean canDelete(int categoryid) throws SQLException {
		AppointmentsTable at = new AppointmentsTable();
		Iterator it = at.select("categoryid = " + categoryid);
		if (!it.hasNext()) {
			return true;
		} else {		
			return false;
		}
	}
	
	public int delete(String whereClause) throws SQLException {

    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {      
		  Statement stmt = conn.createStatement();
		  if (whereClause == null)
			  //return (stmt.executeUpdate(deleteSQL));
			  return 1;
		  else {
			  return (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));
		  }
    } finally {
      conn.close();
    }
	}

	public Iterator select() throws SQLException {
		return select(null, null);
	}

	public Iterator select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

	public Iterator select(String whereClause, String orderClause) throws SQLException {

    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
		  StringBuffer sb = new StringBuffer(selectSQL);

		  if (whereClause != null)
			  sb.append(whereSQL).append(whereClause);
		  if (orderClause != null)
			  sb.append(orderSQL).append(orderClause);

		  Statement stmt = conn.createStatement();
		  ResultSet rs = stmt.executeQuery(sb.toString());

		  List tmpList = new ArrayList();
		  while(rs.next()) {
		    tmpList.add(makeObject(rs));
		  }
		  return tmpList.iterator();
    } finally {
      conn.close();
    }
	}

	public int insert(Categories categories) throws SQLException {
    
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {      
			PreparedStatement insertPS = conn.prepareStatement(insertSQL);
		  prepareInsertStatement(insertPS, categories);
		  return insertPS.executeUpdate();
	  } finally {
      conn.close();
    }
	}

  public int insertFromUnload(Categories categories) throws SQLException {
    
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {      
      PreparedStatement insertPS = conn.prepareStatement(insertUnloadSQL);
      prepareInsertUnloadStatement(insertPS, categories);
      return insertPS.executeUpdate();
    } finally {
      conn.close();
    }
  }

	public int update(Categories categories) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman"); 
    try {
      
			PreparedStatement updatePS = conn.prepareStatement(updateSQL);
		  prepareUpdateStatement(updatePS, categories);		  
		  int rowCount = updatePS.executeUpdate();
		  if (rowCount <= 0) {
			  return insert(categories);
		  } else {
			  return rowCount;
		  }
    } finally {
      conn.close();
    }
	}

	public static Categories makeObject(ResultSet rs) throws SQLException {
		return new Categories(rs.getInt(1), rs.getString(2), rs.getString(3));
	}

	private void prepareInsertStatement(PreparedStatement ps, Categories categories) throws SQLException {
		ps.setString(1, categories.getDescription());
		ps.setString(2, categories.getColor());
	}
	
  private void prepareInsertUnloadStatement(PreparedStatement ps, Categories categories) throws SQLException {
    ps.setInt(1, categories.getCategoryid());
    ps.setString(2, categories.getDescription());
    ps.setString(3, categories.getColor());
  }
  

	private void prepareUpdateStatement(PreparedStatement ps, Categories categories) throws SQLException {
		ps.setString(1, categories.getDescription());
		ps.setString(2, categories.getColor());
		ps.setInt(3, categories.getCategoryid());
	}
}
