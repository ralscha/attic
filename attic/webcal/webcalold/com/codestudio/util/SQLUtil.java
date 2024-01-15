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

// The Codestudio PoolMan Library
import com.codestudio.util.*;

// The JDK
import java.util.*;
import java.sql.*;
import java.io.*;

/**
 * A utility class that is capable of performing any sql operation.
 * All results from queries are returned as Hashtable arrays, where each 
 * Hashtable represents one row (record) of data (key=column_name,
 * value=data).
 *<p>
 * If the sql is an update, insert, or delete, then the 
 * Hashtable array contains only one slice, and the value of that first
 * Hashtable contains a description of the number of rows affected.
 *<p>
 * Results are cached by query that refreshes itself perdiocially, at a
 * configurable interval that defaults to five minutes.
 * <p>
 * If you wish to force the cache to refresh before this interval, you should
 * either update the cacheRefresh parameter in the poolman.props file or
 * invoke the <code>executeSql(String sql, boolean usecache)</code>
 *  method with the second argument set to 'false'. If the second argument 
 * is set to true or ommitted, the cache will be used. The cache updates are
 * handled internally by a separate thread of execution (<code>CacheThread</code>).
 *<p>
 * For specific usage examples, take a look at <code>Sample.java</code>
 * in the <code>samples</code> directory of the distribution.
 */
public class SQLUtil {
    
    private static SQLUtil myself;
    public static final int MAX_ATTEMPTS = 3;
    public String propsfilename;
    
    
    /* TESTING */
    public static void main(String args[]) {
	
	System.out.println("\n");
	if (args.length < 2) {
	    System.out.println("SYNTAX:\njava com.codestudio.SQLUtil " +
			       "\"[name of database as specified in poolman.props]\"" +
			       "\"[SQL statement to be executed]\"\n");
	    System.exit(0);
	}
	
	try {
	    SQLResult res = SQLUtil.getInstance().execute(args[0], args[1]);
	    while (res.hasNext()) {
		Hashtable row = res.next();
		for (Enumeration enum=row.keys(); enum.hasMoreElements(); ) {
		    String key = enum.nextElement().toString();
		    System.out.print(key + ": ");
		    System.out.print(row.get(key).toString() + "\t");
		}
		System.out.print("\n");
	    }
	    if (res.size() < 1)
		System.out.println("SQLUtil: No results were returned for that SQL statement.");
	} catch (Exception e) { e.printStackTrace(); }

	System.out.println("\n");
	System.exit(1);
	
    }
    
    /**
     * The default method used to retrieve an instance of SQLUtil,
     * based on the properties file SQLManager.DEFAULT_PROPSFILE,
     * which must be in your CLASSPATH.
     */
    public static SQLUtil getInstance() {
	if (myself == null) {
	    synchronized(SQLUtil.class) {
		myself = new SQLUtil();
	    }
	}
	return myself;
    }
    
    /**
     * An alternative method to retrieve an instance of SQLUtil,
     * using a specified props file.
     */
    public static SQLUtil getInstance(String propsfilename) {
	if (myself == null) {
	    synchronized(SQLUtil.class) {
		myself = new SQLUtil(propsfilename);
	    }
	}
	return myself;
    }
    
    private SQLUtil() {
	this(SQLManager.DEFAULT_PROPSFILE);
    }
    
    private SQLUtil(String propsfilename) {
	this.propsfilename = propsfilename;

    }
 
    public SQLResult execute(String sql)
	throws SQLException {
	return execute(null, sql);
    }

    public SQLResult execute(String dbname, String sql)
	throws SQLException {
	return makeResult(executeSql(dbname, sql));
    }

    /** Simply executes executeSql(sql, true). */
    public Hashtable[] executeSql(String sql)
	throws SQLException {
	return executeSql(null, sql);
    }
    
    /** Wraps an array of Hashtables into a more
     * convenient data structure, called a SQLResult. */
    protected SQLResult makeResult(Hashtable[] h) 
	throws SQLException {
	return new SQLResult(h);
    }

    /** Reference the SQLManager, which contains all available JDBCPools. */
    private SQLManager getSQLManager() {
	SQLManager datab = null;
	try {
	    datab = SQLManager.getInstance(this.propsfilename);
	} catch (PoolPropsException pe) {
	    throw new RuntimeException("Couldn't get a reference to the SQLManager: " +
				       pe.toString());
	}
	return datab;
    }

    /** 
     * Get a Vector containing all the names of the database 
     * pools currently in use.
     */
    public Enumeration getAllPoolnames() {
	SQLManager datab = getSQLManager();
	if (datab == null)
	    return null;
	return datab.getAllPoolnames();
    }

    /** @return JDBCPool The pool requested by name. */
    public JDBCPool getPool(String dbname) {
	SQLManager datab = getSQLManager();
	return (JDBCPool) datab.getPool(dbname);
    }

    /**
     * Begins the actual database operation by preparing resources.
     * It calls doJDBC() to perform the actual operation.
     */
    public Hashtable[] executeSql(String dbname, String sql)
	throws SQLException {
	
	Hashtable[] results = null;
	Connection con = null;
	ResultSet res = null;
	Statement s = null;
	
	SQLManager datab = getSQLManager();
	
	JDBCPool jp = null;
	jp = getPool(dbname);
	if (jp.usingCache()) {
	    SQLCache jc = jp.getCache();
	    try {
		results = jc.getResult(sql);
	    } catch (Exception cee) { jc.removeResult(sql); }
	    if (results != null)
		return results;
	}
	
	try {
	    if (dbname == null)
	        con = datab.requestConnection();
            else
                con = datab.requestConnection(dbname);
	    results = doJDBC(dbname, sql, con);
	} catch (Exception e) { 
	    throw new SQLException(e.getMessage());
	}
	
	finally {
	    if (dbname == null)
		datab.returnConnection(con);
	    else
		datab.returnConnection(dbname, con);
	}

	// cache it if it was a query and this pool is using a cache
	try {
	    if ((jp.usingCache()) && (sql.toLowerCase().startsWith("select"))) {
		SQLCache jc = jp.getCache();
		try {
		    jc.cacheResult(sql, results);
		} catch (Exception cee) { jc.removeResult(sql); }
	    }
	} catch (Exception ce) {}
	
	return results;
	
    }
    
    /** This method is called by the cache thread in SQLCache. */
    public Hashtable[] doJDBC(String dbname, String sql)
	throws SQLException {
	
        SQLManager datab = getSQLManager();
        if (datab == null)
            throw new SQLException("Unable to initialize PoolMan's SQLManager");

        Connection con = null;
        if (dbname == null)
            con = datab.requestConnection();
        else
            con = datab.requestConnection(dbname);

       return doJDBC(dbname, sql, con);  
    }

    /** Executes a statement and returns results in the form of a Hashtable[]. */
    protected Hashtable[] doJDBC(String dbname, String sql, Connection con)
	throws SQLException {

	Hashtable[] results = null;
	ResultSet res = null;
	Statement s = null;
	
	// build the query
	s = con.createStatement();
	
	// See if this was a select statement
	try {
	    if (s.execute(sql)) {
		results = new Hashtable[10];
		res = s.getResultSet();
		ResultSetMetaData meta = res.getMetaData();
		int cols = meta.getColumnCount();
		int rowcount = 0;
		
		while (res.next()) {
		    
		    if (rowcount == results.length) {
			Hashtable[] temp = new Hashtable[ results.length + 10 ];
			for (int t=0; t<results.length; t++) {
			    temp[t] = results[t];
			}
			results = temp;
		    }
		    
		    Hashtable record = new Hashtable(1);
		    for (int i=1; i <= cols; i++) {
			Object value = null;
			try {
			    switch(meta.getColumnType(i)) {
				//case Types.LONGVARBINARY:
			    case Types.CHAR:
				try {
				    // not sure about this fix, so I'm being overly cautious
				    value = new String(res.getBytes(i));
				} catch (Exception _e) { value = res.getObject(i); }
				break;
			    default:
	                    	value = res.getObject(i);
	                    	break;
			    }
			} catch (Exception ee) {}
			if (value == null)
			    value = new String("");
			record.put(meta.getColumnLabel(i), value);
		    }
		    
		    results[rowcount] = record;
		    rowcount++;
		    
		}
		
		meta = null;
		
		if (results[0] == null) {
		    JDBCPool.closeResources(s,res);
		    throw new SQLException("Query Valid, But Empty ResultSet Returned");
		}
		
		Hashtable[] temp = null;
		for (int i=0; i<results.length; i++) {
		    if (results[i] == null) {
			temp = new Hashtable[i];
			break;
		    }
		    else {
			temp = new Hashtable[ results.length ];
		    }
		}
		for (int i=0; i<temp.length; i++) {
		    temp[i] = results[i];
		}
		results = temp;
		temp = null;
		
	    }
	    
	    else {
		results = new Hashtable[ 1 ];
		results[0] = new Hashtable(1);
		int num;
		String str = "Rows Affected";
		switch(num = s.getUpdateCount()) {
		case 0:
		    results[0].put(str, "No rows affected");
		    break;
		case 1:
		    results[0].put(str, "1 row affected");
		    break;
		default:
		    results[0].put(str, num + " rows affected");
		}
	    }

	} catch (SQLException sqle) { throw sqle; }
	finally {
	    JDBCPool.closeResources(s, res);
	    s = null; res = null;
	}
	
	return results;
	
    }
    

}

