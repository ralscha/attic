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

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 *<br>
 * A singleton object that manages connection pools to any number of databases.
 * Call it by passing in the name of a properties file or by using the default 
 * <code>SQLManager.getInstance()</code> method, which will access the 
 * "poolman.props" file for the necessary parameters. This properties file MUST
 * be in the system classpath.
 *<p>
 * See the Sample.java file in the "samples" directory of the distribution for 
 * more specific usage and code examples.
 */
public class SQLManager extends PoolManager {

    private static SQLManager myself;

    // The default config file for PoolMan is called 'poolman.props'
    public static final String DEFAULT_PROPSFILE = "poolman.props";

    // Old PoolMan releases used a config file too vaguely named 'pool.props' 
    public static final String OLD_PROPSFILE = "pool.props";

    
    public static SQLManager getInstance() {
	if (myself == null) {
	    synchronized(SQLManager.class) {
		myself = new SQLManager(DEFAULT_PROPSFILE);
	    }
	}
	return myself;
    }
    
    public static SQLManager getInstance(String propsfilename) {
	if (myself == null) {
	    synchronized(SQLManager.class) {
		myself = new SQLManager(propsfilename);
	    }
	}
	return myself;
    }
    
    public static SQLManager getInstance(Properties p) {
	if (myself == null) {
	    synchronized(SQLManager.class) {
		myself = new SQLManager(p);
	    }
	}
	return myself;
    }

    private SQLManager(String propsfilename) {
	
	InputStream is = null;
	Properties p = null;

	try {
	    // read from top of any classpath entry
	    is = getClass().getResourceAsStream("/" + propsfilename);
	    p = new Properties();
	    p.load(is);
	} catch (Exception e) {
	    if (propsfilename.equals(DEFAULT_PROPSFILE)) {
		System.out.print("Could not find 'poolman.props' -- now attempting to read deprecated file name 'pool.props'...");
		try {
		    is = getClass().getResourceAsStream("/" + OLD_PROPSFILE);
		    p = new Properties();
		    p.load(is);
		} catch (Exception e2) {
		    System.out.print(" failed.\n\n");
		    throw new PoolPropsException("\nERROR: Unable to find and read a valid PoolMan properties file. " +
						 "Please ensure that '" + DEFAULT_PROPSFILE + "' is in a directory that is in your CLASSPATH.\n");
		}
		System.out.print(" succeeded.\n");
		System.out.println("\nPLEASE NOTE: You should rename the 'pool.props' file to 'poolman.props'\n");
	    }
	    else {
		throw new PoolPropsException("ERROR: Unable to find and read a valid PoolMan properties file.\n" +
					     "Please ensure that " + propsfilename + " is in a directory that is in your CLASSPATH.\n");
	    }
	}
	
	this.pools = new Hashtable(1);
	this.defaultpool = null;
	init(p);
    }
    
    private SQLManager(Properties p) {
	this.pools = new Hashtable(1);
	this.defaultpool = null;
	init(p);		
    }
    
    /**
     * Use the provided properties to establish the necessary JDBCPool objects
     * There'll be one JDBCPool per database connection pool. If only one
     * database is in use, for example, only one JDBCPool will be created.
     * This method will call createPool with the params it parses.
     */
    private void init(Properties p) {

	for (Enumeration enum=p.keys(); enum.hasMoreElements(); ) {
	    String key = (String) enum.nextElement();

	    if (key.startsWith("db_name")) {
		
		// not all properties necessarily need to be configured
		int limit = -1;
		long timeout = -1;
		long sleep = -1;
		long usertimeout = -1;
		boolean use_cache = false;
		int cache_size = SQLCache.DEFAULT_SIZE;
		long cache_refresh = ObjectPool.DEFAULT_SLEEPTIME;
		
		String dbname = p.getProperty(key);
		String number = "1";
		try {
		    number = key.substring(key.indexOf('.'), key.length());
		} catch (StringIndexOutOfBoundsException sbe) {
		    throw new PoolPropsException("No Number Found in Props File For " +
						 dbname + " Treating It As Database 1");
		} 
		String driver = p.getProperty("db_driver" + number);
		String url = p.getProperty("db_url" + number);
		String username = p.getProperty("db_username" + number);
		String password = p.getProperty("db_password" + number);
		
		if (p.containsKey("maximumsize" + number)) {
		    try {
			limit = Integer.parseInt(p.getProperty("maximumsize" + number));
		    } catch (Exception e) {
			throw new PoolPropsException("ERROR: Unable to use the specified maximum size");
		    }
		}
		
		if (p.containsKey("connection_timeout" + number)) {
		    try {
			timeout = Long.parseLong(p.getProperty("connection_timeout" + number));
		    } catch (Exception e) {
			throw new PoolPropsException("ERROR: Unable to use the specified connection timeout");
		    }
		}
		
		if (p.containsKey("checkfrequency" + number)) {
		    try {
			sleep = Long.parseLong(p.getProperty("checkfrequency" + number));
		    } catch (Exception e) {
			throw new PoolPropsException("ERROR: Unable to use the specified check frequency");
		    }
		}

		if (p.containsKey("usertimeout" + number)) {
		    try {
			usertimeout = Long.parseLong(p.getProperty("usertimeout" + number));
		    } catch (Exception e) {
			throw new PoolPropsException("ERROR: Unable to use the specified usertimeout");
		    }
		}

		if (p.containsKey("enableCache" + number)) {
		    String bu = p.getProperty("enableCache" + number);
		    bu = bu.toLowerCase();
		    use_cache = false;
		    if (bu.startsWith("true"))
			use_cache = true;
		    else
			use_cache = false;
		}

		if (use_cache) {
		    if (p.containsKey("cacheSize" + number)) {
			try {
			    cache_size = Integer.parseInt(p.getProperty("cacheSize" + number));
			} catch (Exception e) {
			    throw new PoolPropsException("ERROR: Unable to use the specified cache size:");
			}
		    }
		    if (p.containsKey("cacheRefresh" + number)) {
			try {
			    cache_refresh = Integer.parseInt(p.getProperty("cacheRefresh" + number));
			} catch (Exception e) {
			    throw new PoolPropsException("ERROR: Unable to use the specified cache refresh rate:");
			}
		    }
		}
		
		JDBCPool pool = createPool(dbname, driver, url, username, password, 
					   limit, timeout, sleep, usertimeout);
		
		if (use_cache) {
		    SQLCache sqlc = new SQLCache(pool, cache_size, cache_refresh);
		    pool.setCache(sqlc);
		}
		
		if (pool != null)
		    addPool(dbname, pool);
		else
		    throw new PoolPropsException("Unable to create pool: Driver: " + 
						 driver + " URL: " + url + " Username: " 
						 + username + " Password: " + password);
	    }
	}	
    }
    
    private JDBCPool createPool(String dbname, String driver, String url, String username,
				String password, int limit, long expiration, long sleep, long usertimeout) {
	JDBCPool m = null;
	try {
	    if (limit < 1)
		limit = ObjectPool.MAX_POOLSIZE;
	    if (expiration < 1)
		expiration = ObjectPool.DEFAULT_EXPIRATION;
	    if (sleep < 1)
		sleep = ObjectPool.DEFAULT_SLEEPTIME;
	    if (usertimeout < 1)
		usertimeout = ObjectPool.DEFAULT_TIMEOUT;
	    m = new JDBCPool(dbname, driver, url, username, password, limit, expiration, sleep, usertimeout);
	} catch (Exception e) {
	    throw new RuntimeException("ERROR: SQLManager: Couldn't create connection pool:");
	}
	return m;
    }
    
    /**
     * Get a connection from the first (default) database connection pool.
     */
    public Connection requestConnection() 
	throws SQLException {
	return ((JDBCPool)defaultpool).requestConnection();
    }
    
    /**
     * Return a connection to the default pool.
     */
    public void returnConnection(Connection con) {
	((JDBCPool)defaultpool).returnConnection(con);
    }
    
    /**
     * Get a connection from a particular database pool.
     */
    public Connection requestConnection(String dbname)
	throws SQLException {
	JDBCPool pool = null;
	try {
	    pool = (JDBCPool) pools.get(dbname);
	} catch (NullPointerException ne) {
	    throw new RuntimeException("No connection pool available for " + dbname);
	}
	if (pool != null)
	    return pool.requestConnection();
	return null;
    }
    
    /**
     * Return a connection to a particular database pool.
     */
    public void returnConnection(String dbname, Connection con) {
	JDBCPool pool = null;
	try {
	    pool = (JDBCPool) pools.get(dbname);
	} catch (NullPointerException ne) {
	    throw new RuntimeException("No connection pool available for " + dbname + ", cannot return connection");
	}
	if (pool != null)
	    pool.returnConnection(con);
    }
    
    private void closeAllResources() {
	for (Enumeration enum=pools.keys(); enum.hasMoreElements(); ) {
	    String key = (String) enum.nextElement();
	    JDBCPool m = (JDBCPool) pools.get(key);
	    m.closeAllResources();
	    pools.remove(key);
	    m = null;
	}
	pools = null;
	System.gc();
    }
    
    public void finalize() {
	closeAllResources();
    }
    
    /** Static method that closes the statement and result sets in one place; 
     * this is here as a convenience to shorten the finally block 
     * in statements.  Both arguments may be null.
     * @param statement the statement to be closed
     * @param resultSet the resultSet to be closed
     */
    public static void closeResources(Statement statement, ResultSet resultSet) {
	closeResultSet(resultSet);
	closeStatement(statement);
    }
    
    public void collectResources(Statement s, ResultSet r) {
	SQLManager.closeResources(s,r);
    }
    
    /** Closes the given statement.  It is here to get rid of
     * the extra try block in finally blocks that need to close statements
     * @param statement the statement to be closed. May be null.
     */
    public static void closeStatement(Statement statement) {
	try {
	    if(statement!=null) 
		statement.close();
	    statement = null;
	} catch(SQLException e) {}
    }
    
    /** This method closes the given resultset.  It is here to get 
     * rid of the extra try block in finally blocks.
     * @param rs the ResultSet to be closed. May be null.
     */
    public static void closeResultSet(ResultSet rs) {
	try {
	    if(rs!=null)
		rs.close();
	    rs = null;
	} catch(SQLException e) {}
    }
}


