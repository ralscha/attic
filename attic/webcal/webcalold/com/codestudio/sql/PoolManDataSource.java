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

// The JDK and Extensions
import java.sql.*;
import javax.sql.*;
import java.io.*;
import javax.naming.*;


public class PoolManDataSource extends Reference 
    implements DataSource, ConnectionPoolDataSource {

    private String dbname;
    private String urlstring;
    private String description;
    private PrintWriter logger;
    private int loginTimeout;


    public PoolManDataSource() {
	super("com.codestudio.sql.PoolManDS");
	this.urlstring = PoolMan.PROTOCOL;
    }
    
     /*
       DATASOURCE METHODS
     */

    public Connection getConnection()
	throws SQLException {
	return PoolMan.connect(this.urlstring);
    }

    public Connection getConnection(String user, String password)
	throws SQLException {
	return PoolMan.connect(this.urlstring);
    }

    /*
      CONNECTIONPOOLDATASOURCE METHODS
    */

    public javax.sql.PooledConnection getPooledConnection()
	throws SQLException {
	return (PooledConnection) PoolMan.connect(this.urlstring);
    }
    
    public javax.sql.PooledConnection getPooledConnection(String user, String password)
	throws SQLException {
	return (PooledConnection) PoolMan.connect(this.urlstring);
    }

    /*
      XADATASOURCE METHODS
    */

    public javax.sql.XAConnection getXAConnection() 
	throws SQLException {
	// NOT YET IMPLEMENTED
	return null;
    }

    public javax.sql.XAConnection getXAConnection(String user, String password)
	throws SQLException {
	// NOT YET IMPLEMENTED
	return null;
    }

    /*
      COMMON DATASOURCE METHODS
    */

    public void setLoginTimeout(int seconds)
	throws SQLException {
	this.loginTimeout = seconds;
    }
    
    public int getLoginTimeout()
	throws SQLException {
	return this.loginTimeout;
    }
    
    public void setLogWriter(PrintWriter out)
	throws SQLException {
	this.logger = out;
    }

    public PrintWriter getLogWriter()
	throws SQLException {
	return this.logger;
    }

    /*
      POOLMAN METHODS
    */
    
    public void setDatabaseName(String name) {
	if (name != null) {
	    if (PoolMan.nameIsValid(name)) {
		this.dbname = name;
		this.urlstring = PoolMan.PROTOCOL + "//" + name;
	    }
	    else {
		throw new NullPointerException("PoolMan has no entry for a database named " + name +
					       ". Please check your poolman.props file " +
					       "for valid dbnames.");
	    }
	}
	else
	    this.urlstring = PoolMan.PROTOCOL;
    }

    public String getDatabaseName() {
	return this.dbname;
    }

    public String getName() {
	return this.urlstring;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return this.description;
    }

}
