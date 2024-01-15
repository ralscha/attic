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
import javax.sql.*;
import java.util.*;

/**
 * A connection object that has a time value, so it knows how long it has to live,
 * and it knows its pool, so it will automatically return to its pool if it isn't
 * explicitly returned. It also knows its state -- whether it is in a pool or in use.
 *<p>
 * It encapsulates a true Connection implementation, which handles all the 
 * required JDBC methods by delegation.
 *
 */
public class SmartConnection implements Connection, SmartObject, PooledConnection {
    
    private JDBCPool mypool;
    private Connection con;
    private Vector openstatements;
    public int state;
    private Vector listeners;


    public SmartConnection(Connection con, ObjectPool pool) {
	this.con = con;
	this.mypool = (JDBCPool) pool;
	this.openstatements = new Vector(1,1);
	this.state = SmartObject.UNLOCKED;
    }
    
    /** Close the underlying Connection instead of returning it to the pool. */
    public synchronized void closeAllResources() {
	try {
	    this.con.close();
	} catch (SQLException sqle) {}
    }

    /**
     * Close all resources (any lingering Statements and ResutSets).
     */
    public synchronized void clean() {

	for (int i=0; i<openstatements.size(); i++) {
	    SmartStatement sst = (SmartStatement) openstatements.elementAt(i);
	    sst.clean();
	    JDBCPool.closeStatement(sst);
      openstatements.removeElement(sst);
	}
	System.gc();
    }
    
    public ObjectPool getPool() {
	return this.mypool;
    }
    
    public void setState(int i) {
	this.state = i;
    }
    
    public int getState() {
	return this.state;
    }

    /*
      POOLEDCONNECTION METHODS
    */

    public Connection getConnection()
	throws SQLException {
	return this.con;
    }

    public void addConnectionEventListener(ConnectionEventListener listener) {
	if (null == this.listeners)
	    this.listeners = new Vector(1,1);
	this.listeners.addElement(listener);
    }

    public void removeConnectionEventListener(ConnectionEventListener listener) {
	try {
	    this.listeners.remove(listener);
	} catch (Exception e) {}
    }
    
    public Statement createStatement() throws SQLException {
	Statement s = this.con.createStatement();
	SmartStatement sst = new SmartStatement(s, this.mypool);
	sst.setState(SmartObject.LOCKED);
	openstatements.addElement(sst);
	return s;
    }
    
    public PreparedStatement prepareStatement(String sql)
	throws SQLException {
	PreparedStatement s = this.con.prepareStatement(sql);
	SmartStatement sst = new SmartStatement(s, this.mypool);
	sst.setState(SmartObject.LOCKED);
	openstatements.addElement(sst);
	return s;
    }
    
    public CallableStatement prepareCall(String sql) throws SQLException {
	CallableStatement s = this.con.prepareCall(sql);
	SmartStatement sst = new SmartStatement(s, this.mypool);
	sst.setState(SmartObject.LOCKED);
	openstatements.addElement(sst);
	return s;
    }
    
    public String nativeSQL(String sql) throws SQLException { 
	String s = this.con.nativeSQL(sql);
	return s;
    }
    
    public void setAutoCommit(boolean autoCommit) throws SQLException {
	this.con.setAutoCommit(autoCommit);
    }
    
    public boolean getAutoCommit() throws SQLException {
	return this.con.getAutoCommit();
    }
    
    public void commit() throws SQLException {
	this.con.commit();
    }
    
    public void rollback() throws SQLException {
	this.con.rollback();
    }
    
    public void close() throws SQLException {
	mypool.returnConnection(this);
    }
    
    public boolean isClosed() throws SQLException {
	return this.con.isClosed();
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
	return this.con.getMetaData();
    }
    
    public void setReadOnly(boolean readOnly) throws SQLException {
	this.con.setReadOnly(readOnly);
    }
    
    public boolean isReadOnly() throws SQLException {
	return this.con.isReadOnly();
    }
    
    public void setCatalog(String catalog) throws SQLException {
	this.con.setCatalog(catalog);
    }
    
    public String getCatalog() throws SQLException {
	return this.con.getCatalog();
    }
    
    public void setTransactionIsolation(int level) throws SQLException {
	this.con.setTransactionIsolation(level);
    }
    
    public int getTransactionIsolation() throws SQLException {
	return this.con.getTransactionIsolation();
    }
    
    public SQLWarning getWarnings() throws SQLException {
	return this.con.getWarnings();
    }
    
    public void clearWarnings() throws SQLException {
	this.con.clearWarnings();
    }
    
    
    public Statement createStatement(int resultSetType, int resultSetConcurrency) 
	throws SQLException {
	Statement s = this.con.createStatement(resultSetType, resultSetConcurrency);
	SmartStatement sst = new SmartStatement(s, this.mypool);
	sst.setState(SmartObject.LOCKED);
	openstatements.addElement(sst);
	return s;
    }
    
    public PreparedStatement prepareStatement(String sql, int resultSetType, 
					      int resultSetConcurrency)
	throws SQLException {
	PreparedStatement s = this.con.prepareStatement(sql, resultSetType,
							resultSetConcurrency);
	SmartStatement sst = new SmartStatement(s, this.mypool);
	sst.setState(SmartObject.LOCKED);
	openstatements.addElement(sst);
	return s;
    }
    
    public CallableStatement prepareCall(String sql, int resultSetType, 
					 int resultSetConcurrency) throws SQLException {
	CallableStatement s = this.con.prepareCall(sql, resultSetType,
						   resultSetConcurrency);
	SmartStatement sst = new SmartStatement(s, this.mypool);
	sst.setState(SmartObject.LOCKED);
	openstatements.addElement(sst);
	return s;
    }
    
    public java.util.Map getTypeMap() throws SQLException {
	return this.con.getTypeMap();
    }
    
    public void setTypeMap(java.util.Map map) throws SQLException {
	this.con.setTypeMap(map);
    }
    
    
    
    
}	
