package ch.ess.calendar.db;

import java.sql.*;
import java.util.*;

public class UsersTable {

  private final static String deleteSQL = "DELETE FROM Users";
  private final static String whereSQL = " WHERE ";
  private final static String selectSQL = "SELECT userid,firstname,name,password,administrator,email FROM Users";
  private final static String orderSQL = " ORDER BY ";

  private int limit;

  private final static String insertSQL = "INSERT INTO Users(userid,firstname,name,password,administrator,email) VALUES(?,?,?,?,?,?)";

  private final static String updateSQL = "UPDATE Users SET firstname=?, name=?, password=?, administrator=?, email=? WHERE userid=?";

  public UsersTable() {

    limit = 2;
    if (!ch.ess.calendar.util.CheckLicense.isDemo()) {
      limit = ch.ess.calendar.util.CheckLicense.getLimit();
    }
  }

  public int delete() throws SQLException {
    return (delete(null));
  }

  public int delete(String whereClause) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman");
    try {
      if (!ch.ess.calendar.util.CheckLicense.isDemo()) {
        Statement stmt = conn.createStatement();
        if (whereClause == null) {
          return (stmt.executeUpdate(deleteSQL));
        }

        return (stmt.executeUpdate(deleteSQL + whereSQL + whereClause));

      }
      return 1;

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
      return select(whereClause, orderClause, conn);
    } finally {
      conn.close();
    }
  }

  public Iterator select(String whereClause, String orderClause, Connection conn) throws SQLException {
    StringBuffer sb = new StringBuffer(selectSQL);

    if (whereClause != null)
      sb.append(whereSQL).append(whereClause);
    if (orderClause != null)
      sb.append(orderSQL).append(orderClause);

    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sb.toString());

    int count = 0;
    List tmpList = new ArrayList();
    while (rs.next() && (count < limit)) {
      tmpList.add(makeObject(rs));
      count++;
    }
    return tmpList.iterator();
  }

  public int insert(Users users) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman");
    try {
      if (!ch.ess.calendar.util.CheckLicense.isDemo()) {
        PreparedStatement insertPS = conn.prepareStatement(insertSQL);
        prepareInsertStatement(insertPS, users);
        return insertPS.executeUpdate();
      } 
      return 1;
      

    } finally {
      conn.close();
    }
  }

  public int update(Users users) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:poolman");
    try {
      PreparedStatement updatePS = conn.prepareStatement(updateSQL);
      prepareUpdateStatement(updatePS, users);
      int rowCount = updatePS.executeUpdate();
      if (rowCount <= 0) {
        return insert(users);
      } 
      return rowCount;
      
    } finally {
      conn.close();
    }
  }

  public static Users makeObject(ResultSet rs) throws SQLException {
    return new Users(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs
        .getString(6));
  }

  private void prepareInsertStatement(PreparedStatement ps, Users users) throws SQLException {
    ps.setString(1, users.getUserid());
    ps.setString(2, users.getFirstname());
    ps.setString(3, users.getName());
    ps.setString(4, users.getPassword());
    ps.setString(5, users.getAdministrator());
    ps.setString(6, users.getEmail());
  }

  private void prepareUpdateStatement(PreparedStatement ps, Users users) throws SQLException {
    ps.setString(1, users.getFirstname());
    ps.setString(2, users.getName());
    ps.setString(3, users.getPassword());
    ps.setString(4, users.getAdministrator());
    ps.setString(5, users.getEmail());
    ps.setString(6, users.getUserid());
  }
}
