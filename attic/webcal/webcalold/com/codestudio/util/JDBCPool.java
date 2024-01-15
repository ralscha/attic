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
package com.codestudio.util;

// Code Studio
import com.codestudio.sql.*;

// The JDK
import java.sql.*;
import java.util.*;


/**
 * JDBCPool is an ObjectPool of JDBC connection objects.
 */
public class JDBCPool extends ObjectPool {
    
    protected JDBCInfo info;
    protected SQLCache sqlcache;

    
    /**
     * Create a JDBCPool with full control of all parameters.
     * @param drivername the name of the sql driver
     * @param url the url for the db connection
     * @param username the user of the db connections
     * @param password the paswer for the db user
     * @param timeout the timout for a given connection
     * @param sleep the amount of time between skim cycles.
     * @param limit is the maximum number of open connections allowed.
     */
    public JDBCPool(String poolname, String drivername, String url,
		    String username, String password, int limit,
		    long expiration, long sleep, long usertimeout) {
	
	super(limit, expiration, sleep, usertimeout);
	this.info = new JDBCInfo(poolname, drivername, url,
				 username, password);
	this.sqlcache = null;
    }

    /** Associates a SQLCache with this pool. */
    public void setCache(SQLCache cache) {
	this.sqlcache = cache;
    }

    /** @return SQLCache The SQLCache associated with this pool. */
    public SQLCache getCache() {
	return this.sqlcache;
    }

    public boolean usingCache() {
	if (this.sqlcache == null)
	    return false;
	return true;
    }
    
    /** Creates a new JDBC connection */
    protected Object create() 
	throws SQLException {
	
	if (info.getDrivername() == null || info.getUrl() == null)
	    return null;
	
	try {

	    // using the .newInstance() method to work around 
	    // known problems with certain VM's (per mm.mysql)

	    Driver d = (Driver) (Class.forName(info.getDrivername()).newInstance());
	    Properties p = new Properties();
	    p.put("user", info.getUsername());
	    p.put("password", info.getPassword());
	    Connection con = d.connect(info.getUrl(), p);

	    /* OLD CODE
	    Class.forName(info.getDrivername()).newInstance();
	    Connection con = DriverManager.getConnection(info.getUrl(),
							info.getUsername(),
							info.getPassword());
	    */

	    SmartConnection smartcon = new SmartConnection(con, this);
	    return smartcon;
	}
	catch (ClassNotFoundException cnfex) {
	    System.out.println("Looks like the driver was not found...");
	    System.out.println("Be sure it is in your CLASSPATH and listed properly in the properties file.");
	}
	catch (SQLException sqlex)	{
	    throw new SQLException("SQLException occurred in JDBCPool: " + sqlex.toString() + 
				   "\nparams: " + info.getDrivername() + ", " +
				   info.getUrl() + ". Please check your username, password " +
				   "and other connectivity info.");
	}
	catch(RuntimeException e) {
	    System.out.println(e);
	    //e.printStackTrace();
	}
	catch(Exception e) {
	    System.out.println(e);
	    //e.printStackTrace();
	}
	catch(Throwable t) {
	    System.out.println(t);
	    //t.printStackTrace();
	}
	return null;
    }
    
    /** Checks to see if the current connection is valid. */
    protected boolean validate(Object o) {
	Connection conn= (Connection) o;
	try {
	    return !conn.isClosed();
	} catch(SQLException e) {
	    System.out.println(e);
	}
	return false;
    }
    
    /** Closes a database connection. */
    protected void expire(Object o) {
	if (o instanceof com.codestudio.sql.SmartConnection) {
	    SmartConnection scon = (SmartConnection) o;
	    scon.closeAllResources();
    	}
	o = null;
    }
    
    /** Retrieves a connection. */
    public Connection requestConnection() 
	throws SQLException {
	try {
	    return (Connection) super.checkOut();
	} catch (SQLException sqle) {
	    throw new SQLException(sqle.getMessage());
	} catch(Exception e) {
	    System.out.print("A non-SQL error occurred when requesting a connection:");
	    System.out.println(e);
	    //e.printStackTrace();
	}
	return null;
    }
    
    /** Returns a connection to the pool. */
    public void returnConnection(Connection conn) {
	super.checkIn(conn);
    }

    public JDBCInfo getInfo() {
	return this.info;
    }

    public void setInfo(JDBCInfo i) {
	this.info = i;
    }
    
    /** Static method that closes the statement and result sets in one place; 
     * this is here as a convenience to shorten the finally block 
     * in statements.  Both arguments may be null;
     * @param statement the statement to be closed
     * @param resultSet the resultSet to be closed
     */
    public static void closeResources(Statement statement, ResultSet resultSet) {
	closeResultSet(resultSet);
	closeStatement(statement);
    }
    
    /** Closes the given statement.  It is here to get rid of
     * the extra try block in finally blocks that need to close statements
     * @param statement the statement to be closed. may be null
     */
    public static void closeStatement(Statement statement) {
	try {
	    if(statement!=null)
		statement.close();
      	} catch(SQLException e) {}
    }
    
    /** This method closes the given resultset.  It is here to get rid of 
     * the extra try block in finally blocks.
     * @param rs the ResultSet to be closed.  may be null
     */
    public static void closeResultSet(ResultSet rs) {
	try {
	    if (rs!=null)
		rs.close();
	} catch(SQLException e) {}
    }
    
    /**
     * Close all resources in the pool.
     */
    public void closeAllResources() {
    	for (Enumeration enum=this.unlocked.keys(); enum.hasMoreElements(); ) {
	    Object o = enum.nextElement();
	    expire(o);
	    o=null;
	}
    }
    
    public void finalize() {
	closeAllResources();
    }
    
}


