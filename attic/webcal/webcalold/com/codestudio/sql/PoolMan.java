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

// Codestudio PoolMan Library
import com.codestudio.util.*;

// The JDK
import java.sql.*;
import javax.sql.*;
import java.io.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * The PoolMan class is the core Driver implementation.
 * It can be loaded via the DriverManager, and is alos accessed 
 * through the PoolManDataSource.
 */
public class PoolMan implements Driver {


    public static String PROTOCOL = "jdbc:poolman:";

    public static final int VERSIONID = 2;
    public static final int RELEASEID = 0;


    public static void main(String args[]) {
	if ((null == args) || (args.length < 1)) {
	    System.out.println("\nUSAGE: java com.codestudio.sql.PoolMan \"[db_name]\"\n" +
			       "Where the [db_name] parameter corresponds to a db_name " +
			       "specified in your poolman.props file.\n");
	    System.exit(1);
	}

	System.out.print("Attempting to connect to " + args[0] + "... ");
	Connection con = null;
	try {
	    Class.forName("com.codestudio.sql.PoolMan").newInstance();
	    con = DriverManager.getConnection("jdbc:poolman://" + args[0]);
	    System.out.print("CONNECTED.\nTest passed, exiting.\n\n");
	} catch (Exception e) {
	    System.out.print("FAILED.\nTest Failed with the following error:\n");
	    e.printStackTrace();
	    System.out.print("\n");
	} finally {
	    try { con.close(); } catch (SQLException se) {}
	}
	System.exit(1);
    }

    public PoolMan() {
	registerDriver();
    }
    
    /** Registers the Driver with the DriverManager. */
    private void registerDriver() {
	try {
	    DriverManager.registerDriver(this);
	} catch (SQLException se) {
	    throw new RuntimeException("PoolMan Driver Failed to Load and Register with DriverManager");
	}
    }

    /** Determine whether the dbname is valid for this PoolMan instance. */
    public static boolean nameIsValid(String dbname) {
	SQLManager manager = SQLManager.getInstance();
	for (Enumeration enum=manager.getAllPoolnames(); enum.hasMoreElements(); ) {
	    String name = enum.nextElement().toString();
	    if (dbname.equals(name))
		return true;
	}
	return false;
    }

    /*
      DRIVER METHODS
    */

    public boolean acceptsURL(String url)
	throws SQLException {
	if (url.startsWith("jdbc:poolman"))
	    return true;
	return false;
    }

    public Connection connect(String url, Properties info)
	throws SQLException {

	try {

	    SQLManager manager = SQLManager.getInstance();

	    String dbname = null;

	    if (url.indexOf("//") != -1) {
		dbname = url.substring((url.lastIndexOf("/")+1), url.length());
	    }
	    
	    // Properties override the URL. Don't know if this is wise or not.
	    if ((null != info) && (info.containsKey("dbname")))
		dbname = info.getProperty("dbname");
	    
	    if (dbname == null) {
		// return a SmartConnection to the last numbered pool in pool.props
		return manager.requestConnection();
	    }
	    else {
		// return a SmartConnection to pool named in pool.props that matches the driver URL
		try {
		    return manager.requestConnection(dbname);
		} catch (Exception e) {
		    throw new SQLException(e.getMessage());
		}
	    }
	} catch (PoolPropsException pe) {
	    throw new SQLException(pe.getMessage());
	}

    }

    public static Connection connect(String url)
	throws SQLException {

	try {

	    SQLManager manager = SQLManager.getInstance();
	    String dbname = null;

	    if (url.indexOf("//") != -1) {
		dbname = url.substring((url.lastIndexOf("/")+1), url.length());
	    }
	    
	    if (dbname == null) {
		// return a SmartConnection to the last numbered pool in pool.props
		return manager.requestConnection();
	    }
	    else {
		// return a SmartConnection to pool named in pool.props that matches the driver URL
		try {
		    return manager.requestConnection(dbname);
		} catch (Exception e) {
		    throw new SQLException(e.getMessage());
		}
		
	    }
	    
	} catch (PoolPropsException pe) {
	    throw new SQLException(pe.getMessage());
	}

    }

    public int getMajorVersion() {
	return VERSIONID;
    }

    public int getMinorVersion() {
	return RELEASEID;
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) 
	throws SQLException {
	return null;
    }

    public boolean jdbcCompliant() {
	return false;
    }

}








