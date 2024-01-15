package com.taglib.wdjsp.faqtool;

import java.util.*;
import java.sql.*;

public class FaqRepository {
  private static FaqRepository instance;
    
  private static final String driver = "jdbc.idbDriver";
  private static final String user= "";
  private static final String pass = "";
  private static final String dbURL = "jdbc:idb=d:/javaprojects/private/faq/db.props";
  
  private Connection connection;
  private PreparedStatement getStmt;
  private PreparedStatement putStmt;
  private PreparedStatement remStmt;
  private PreparedStatement getAllStmt;
  private PreparedStatement updStmt;
  
  public static FaqRepository getInstance()
    throws FaqRepositoryException {
    if (instance == null)
      instance = new FaqRepository();
    return instance;
  }

  private FaqRepository() throws FaqRepositoryException {
    String get="SELECT * FROM FAQS WHERE ID=?";
    String put=
      "INSERT INTO FAQS VALUES ( ?, ?, ?, ?)";
    String rem="DELETE FROM FAQS WHERE ID=?";
    String upd=
      "UPDATE FAQS SET QUESTION=?, ANSWER=?, MODIFIED=? WHERE ID=?";
    String all="SELECT * FROM FAQS ORDER BY ID";

    try {
      Class.forName(driver);
      connection = DriverManager.getConnection(dbURL, user, pass);
      getStmt = connection.prepareStatement(get);
      putStmt = connection.prepareStatement(put);
      remStmt = connection.prepareStatement(rem);
      getAllStmt = connection.prepareStatement(all);
      updStmt = connection.prepareStatement(upd);
    }
    catch (ClassNotFoundException e) {
      throw new FaqRepositoryException("No Driver Available!");
    }
    catch (SQLException se) {
      throw new FaqRepositoryException(se.getMessage());
    }
  }
  
  private FaqBean makeFaq(ResultSet results)
    throws FaqRepositoryException {
    try {
      FaqBean faq = new FaqBean();
      faq.setID(results.getInt("ID"));
      faq.setQuestion(results.getString("QUESTION"));
      faq.setAnswer(results.getString("ANSWER"));
      Timestamp t = results.getTimestamp("MODIFIED");
      java.util.Date d;
      d = new java.util.Date(t.getTime() + (t.getNanos()/1000000));
      faq.setLastModified(d);
      return faq;
    }
    catch (SQLException e) {
      throw new FaqRepositoryException(e.getMessage());
    }
  }
  
  public FaqBean getFaq(int id)
    throws UnknownFaqException, FaqRepositoryException {
    try {
      ResultSet results;
      synchronized (getStmt) {
	getStmt.clearParameters();
	getStmt.setInt(1, id);
	results = getStmt.executeQuery();
      }
      if (results.next())
	return makeFaq(results);
      else
	throw new UnknownFaqException("Could not find FAQ# " + id);
    }
    catch (SQLException e) {
      throw new FaqRepositoryException(e.getMessage());
    }
  }
  
  public FaqBean[] getFaqs()
    throws FaqRepositoryException {
    try {
      ResultSet results;
      Collection faqs = new ArrayList();
      synchronized(getAllStmt) {
	results = getAllStmt.executeQuery();
      }
      FaqBean faq;
      while (results.next()) {
	faqs.add(makeFaq(results));
      }
      return (FaqBean[])faqs.toArray(new FaqBean[0]);
    }
    catch (SQLException e) {
      throw new FaqRepositoryException(e.getMessage());
    }
  }

  public void update(FaqBean faq)
    throws UnknownFaqException, FaqRepositoryException {
    try {
      synchronized(updStmt) {
	updStmt.clearParameters();
	updStmt.setString(1, faq.getQuestion());
	updStmt.setString(2, faq.getAnswer());
	Timestamp now;
	now = new Timestamp(faq.getLastModified().getTime());
	updStmt.setTimestamp(3, now);
	updStmt.setInt(4, faq.getID());
	int rowsChanged = updStmt.executeUpdate();
	if (rowsChanged < 1)
	  throw new UnknownFaqException("Could not find FAQ# " +
				      faq.getID());
      }
    }
    catch (SQLException e) {
      throw new FaqRepositoryException(e.getMessage());
    }
  }
  
  public void put(FaqBean faq) throws
    FaqRepositoryException {
    try {
      synchronized(putStmt) {
	putStmt.clearParameters();
	putStmt.setInt(1, 0);
	putStmt.setString(2, faq.getQuestion());
	putStmt.setString(3, faq.getAnswer());
	Timestamp now;
	now = new Timestamp(faq.getLastModified().getTime());
	putStmt.setTimestamp(4, now);
	putStmt.executeUpdate();
      }
    }
    catch (SQLException e) {
      throw new FaqRepositoryException(e.getMessage());
    }
      
  }
  
  public void removeFaq(int id)
    throws FaqRepositoryException {
    try {
      synchronized(remStmt) {
	remStmt.clearParameters();
	remStmt.setInt(1, id);
	int rowsChanged = remStmt.executeUpdate();
	if (rowsChanged < 1)
	  throw new UnknownFaqException("Could not delete FAQ# "+ id);
      }
    }
    catch (SQLException e) {
      throw new FaqRepositoryException(e.getMessage());
    }
  }

  public void destroy() {
    if (connection != null) {
      try { connection.close(); }
      catch (Exception e) { }
    }
  }

}
    
  
