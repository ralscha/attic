/*
 * $Header: c:/cvs/webcal2/test/ch/ess/ct/tests/DatabaseChecker.java,v 1.1 2003/03/31 06:34:46 sr Exp $
 * $Revision: 1.1 $
 * $Date: 2003/03/31 06:34:46 $
 */

// ===========================================================================
// CONTENT  : CLASS DatabaseChecker
// AUTHOR   : Manfred Duchrow
// VERSION  : 1.0 - 14/06/2001
// HISTORY  :
//  14/06/2001  duma  CREATED
//
// Copyright (c) 2001, by Daedalos Consulting GmbH. All rights reserved.
// ===========================================================================
package ch.ess.ct.tests;


// ===========================================================================
// IMPORTS
// ===========================================================================
import java.sql.*;


/**
 * A simple interface to check data in a database.
 * It provides easy to use methods to retrieve single column values
 * or row counts of database tables without fiddling around with
 * JDBC and SQL.
 *
 * @author Manfred Duchrow
 * @version 1.0
 */
public class DatabaseChecker {

  private Statement stmt = null;

  // =========================================================================
  // CONSTANTS
  // =========================================================================
  // =========================================================================
  // INSTANCE VARIABLES
  // =========================================================================

  public static DatabaseChecker createDatabaseChecker(String dbDriver, String dbUrl, String dbUser, String dbPassword) {

    try {

      Class.forName(dbDriver);
      Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

      return new DatabaseChecker(conn);
    } catch (SQLException sqle) {
      System.err.println(sqle);
    } catch (ClassNotFoundException e) {
      System.err.println(e);
    }

    return null;
  }

  private Connection connection = null;

  public Connection getConnection() {

    return connection;
  }

  public void setConnection(Connection newValue) {
    connection = newValue;
  }

  // =========================================================================
  // CLASS METHODS
  // =========================================================================
  // =========================================================================
  // CONSTRUCTORS
  // =========================================================================

  /**
   * Initialize the new instance with connection, that will be used
   * for all database access.
   */
  public DatabaseChecker(Connection conn) {
    super();
    this.setConnection(conn);
  } // DatabaseChecker()

  // =========================================================================
  // PUBLIC INSTANCE METHODS
  // =========================================================================
  public void closeConnection() {
    closeStatement();

    try {
      connection.close();
    } catch (SQLException sqle) {
      System.err.println(sqle);
    }
  }

  public void closeStatement() {

    try {

      if (stmt != null) {
        stmt.close();
        stmt = null;
      }
    } catch (SQLException sqle) {
      System.err.println(sqle);
    }
  }

  /**
   * Returns whether or not the row that matches the given where clause
   * exists in the specified table.
   * The method returns true, if there is exactly one row that matches.
   * Zero or more than one row returns false.
   */
  public boolean rowExists(String tableName, String whereClause) throws SQLException {

    return (this.rowCount(tableName, whereClause) == 1L);
  } // rowExists()

  // -------------------------------------------------------------------------

  /**
   * Returns whether or not one or more rows exist that match the given
   * where clause in the specified table.
   * The method returns true, if there is any row that matches.
   * Zero rows returns false.
   */
  public boolean rowsExist(String tableName, String whereClause) throws SQLException {

    return (this.rowCount(tableName, whereClause) > 0);
  } // rowsExist()

  // -------------------------------------------------------------------------

  /**
   * Returns the current number of rows in the specified table
   */
  public long rowCount(String tableName) throws SQLException {

    return this.rowCount(tableName, null);
  } // rowCount()

  // -------------------------------------------------------------------------

  /**
   * Returns the current number of rows in the specified table
   * that match the conditions in the given where clause.
   */
  public long rowCount(String tableName, String whereClause) throws SQLException {

    String sqlCmd = null;
    ResultSet result = null;
    long count = -1;
    sqlCmd = "SELECT count(*) FROM " + tableName;
    result = this.executeQuery(sqlCmd, whereClause);

    if (result.next()) {
      count = result.getLong(1);
    }

    result.close();
    stmt.close();
    stmt = null;

    return count;
  } // rowCount()

  // -------------------------------------------------------------------------

  /**
   * Returns whether or not the specified table exists in the database
   */
  public boolean tableExists(String tableName) {

    boolean exists = true;

    try {
      this.rowCount(tableName);
    } catch (SQLException ex) {
      exists = false;
    }

    return exists;
  } // tableExists()

  // -------------------------------------------------------------------------

  /**
   * Returns the value of the specified column in the first row
   * that matches the given where clause in the specified table .
   * If the column is not of type VARCHAR an exception will be thrown.
   * <br>
   * Example:<br>
   * <ul>
   * String value = dbChecker.getStringColumn( "BOOKS", "AUTHOR", "AUTHOR_ID='8729'" );
   * </ul>
   * @throws SQLException - if a database access error occurs
   */
  public String getStringColumn(String tableName, String columnName, String whereClause)
                         throws SQLException {

    ResultSet result = null;
    String columnValue = null;
    result = this.queryColumn(tableName, columnName, whereClause);
    columnValue = result.getString(columnName);
    result.close();

    return columnValue;
  } // getStringColumn()

  // -------------------------------------------------------------------------

  /**
   * Returns the value of the specified column in the first row
   * that matches the given where clause in the specified table .
   * If the column is not of type INTEGER an exception will be thrown.
   * <br>
   * Example:<br>
   * <ul>
   * int value = dbChecker.getIntColumn( "BOOKS", "IN_STOCK", "ISBN='0-261-10357-1'" );
   * </ul>
   * @throws SQLException - if a database access error occurs
   */
  public int getIntColumn(String tableName, String columnName, String whereClause)
                   throws SQLException {

    ResultSet result = null;
    int columnValue = 0;
    result = this.queryColumn(tableName, columnName, whereClause);
    columnValue = result.getInt(columnName);
    result.close();

    return columnValue;
  } // getIntColumn()

  // -------------------------------------------------------------------------

  public ResultSet queryColumn(String tableName, String columnName, String whereClause)
                           throws SQLException {

    String sqlCmd = null;
    ResultSet result = null;
    sqlCmd = "SELECT " + columnName + " FROM " + tableName;
    result = this.executeQuery(sqlCmd, whereClause);
    result.next();

    return result;
  } // queryColumn()

  // -------------------------------------------------------------------------
  public int delete(String tablename, String whereClause) throws SQLException {

    String sqlCmd = null;
    Statement statement = null;
    sqlCmd = "DELETE FROM " + tablename + " WHERE " + whereClause;
    statement = this.getConnection().createStatement();

    int ret = statement.executeUpdate(sqlCmd);
    statement.close();

    return ret;
  }

  public int update(String sqlCmd) throws SQLException {

    Statement statement = null;
    statement = this.getConnection().createStatement();

    int ret = statement.executeUpdate(sqlCmd);
    statement.close();

    return ret;
  }

  public ResultSet executeQuery(String select, String whereClause) throws SQLException {

    String sqlCmd = null;

    if (whereClause == null) {
      sqlCmd = select;
    } else {
      sqlCmd = select + " WHERE " + whereClause;
    }

    return this.executeQuery(sqlCmd);
  } // executeQuery()

  // -------------------------------------------------------------------------
  public ResultSet executeQuery(String sqlCmd) throws SQLException {

    if (stmt != null) {
      stmt.close();
      stmt = null;
    }

    stmt = this.getConnection().createStatement();
    this.logSql(sqlCmd);

    return stmt.executeQuery(sqlCmd);
  } // executeQuery()

  // -------------------------------------------------------------------------
  public void logSql(String sqlCmd) {

    // TBD
  } // logSql()

  // -------------------------------------------------------------------------
}