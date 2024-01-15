/*
 *  PoolMan Java Object Pooling and Caching Library
 *  Copyright (C) 2000 The Code Studio
 *  
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2 of the License, or (at your option) any later version.
 *  
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *  
 *  The full license is located at the root of this distribution
 *  in the LICENSE file.
 */
package com.codestudio.sql;

// Code Studio Library
import com.codestudio.util.*;

// The JDK
import java.sql.*;
import java.util.*;


/**
 * A SQL Statement that is aware of its Connection and resources.
 *<p>
 * It encapsulates a true driver-specific Statement that handles
 * all the necessary JDBC methods by delegation.
 */
public class SmartStatement implements Statement, SmartObject {

    private Statement statement;
    private Vector openres;
    private JDBCPool mypool;
    private int state;

    public SmartStatement(Statement s, ObjectPool p) {
	this.statement = s;
	this.mypool = (JDBCPool) p;
	this.openres = new Vector(1,1);
	this.state = SmartObject.UNLOCKED;
    }

    public void closeAllResources() {
	try {
	    this.statement.close();
	} catch (SQLException sqle) {}
    }
    
    /** Close any lingering ResultSets. */
    public void clean() {
    

	for (int i=0; i<openres.size(); i++) {
	    JDBCPool.closeResultSet((ResultSet) openres.elementAt(i));
	}
    }
    
    public ObjectPool getPool() {
	return this.mypool;
    }
    
    public int getState() {
	return this.state;
    }
    
    public void setState(int i) {
	this.state = i;
    }
    
    public ResultSet executeQuery(String sql) throws SQLException {
	ResultSet r = this.statement.executeQuery(sql);
	openres.addElement(r);
	return r;
    }
    
    public int executeUpdate(String sql) throws SQLException {
	return this.statement.executeUpdate(sql);
    }
    
    
    public void close() throws SQLException {
	this.statement.close();
    }
    
    
    public int getMaxFieldSize() throws SQLException {
	return this.statement.getMaxFieldSize();
    }
    
    
    public void setMaxFieldSize(int max) throws SQLException {
	this.statement.setMaxFieldSize(max);
    }
    
    
    public int getMaxRows() throws SQLException {
	return this.statement.getMaxRows();
    }
    
    public void setMaxRows(int max) throws SQLException {
	this.statement.setMaxRows(max);
    }
    
    public void setEscapeProcessing(boolean enable) throws SQLException {
	this.statement.setEscapeProcessing(enable);
    }
    
    public int getQueryTimeout() throws SQLException {
	return this.statement.getQueryTimeout();
    }
    
    public void setQueryTimeout(int seconds) throws SQLException {
	this.statement.setQueryTimeout(seconds);
    }
    
    public void cancel() throws SQLException {
	this.statement.cancel();
    }
    
    public SQLWarning getWarnings() throws SQLException {
	return this.statement.getWarnings();
    }
    
    public void clearWarnings() throws SQLException {
	this.statement.clearWarnings();
    }
    
    public void setCursorName(String name) throws SQLException {
	this.statement.setCursorName(name);
    }
    
    public boolean execute(String sql) throws SQLException {
	return this.statement.execute(sql);
    }
    
    public ResultSet getResultSet() throws SQLException {
	return this.statement.getResultSet();
    }

    public int getUpdateCount() throws SQLException {
	return this.statement.getUpdateCount();
    }

    public boolean getMoreResults() throws SQLException {
	return this.statement.getMoreResults();
    }
 
    public void setFetchDirection(int direction) throws SQLException {
	this.statement.setFetchDirection(direction);
    }

    public int getFetchDirection() throws SQLException {
	return this.statement.getFetchDirection();
    }
	
    public void setFetchSize(int rows) throws SQLException {
	this.statement.setFetchSize(rows);
    }
  
    public int getFetchSize() throws SQLException {
	return this.statement.getFetchSize();
    }

    public int getResultSetConcurrency() throws SQLException {
	return this.statement.getResultSetConcurrency();
    }

    public int getResultSetType()  throws SQLException {
	return this.statement.getResultSetType();
    }

    public void addBatch(String sql) throws SQLException {
	this.statement.addBatch(sql);
    }

    public void clearBatch() throws SQLException {
	this.statement.clearBatch();
    }

    public int[] executeBatch() throws SQLException {
	return this.statement.executeBatch();
    }
  
    public Connection getConnection()  throws SQLException {
	return this.statement.getConnection();
    }
    
}	




