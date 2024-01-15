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

import java.util.*;
import java.sql.*;

/**
 * PoolManBean is a JavaBean that interfaces with SQLUtil 
 * to perform database operations. 
 */
public class PoolManBean {
    
    private String query, poolname;
    private int queryNumber;
    
    public PoolManBean() {
    	this.queryNumber = 0;
    }
    
    public String getQuery() {
	return this.query;
    }
    
    public void setQuery(String s) {
    	this.query = s;
	if ((s != null) && (s.length() > 0))
	    setQueryNumber(++this.queryNumber);
    }
    
    public int getQueryNumber() {
	return this.queryNumber;
    }
    
    public void setQueryNumber(int i) {
	this.queryNumber = i;
    }
    
    public Hashtable[] getResults() 
	throws SQLException {
	
	String sql = getQuery();
	if (sql.length() < 1)
	    throw new SQLException ("Empty SQL String");
	Hashtable[] results = null;
	results = SQLUtil.getInstance().executeSql(getCurrentPoolname(), sql);
	return results;
	
    }

    public SQLResult getSQLResult() 
	throws SQLException {

	String sql = getQuery();
	if (sql.length() < 1)
	    throw new SQLException ("Empty SQL String");
	SQLResult results = null;
	
	SQLUtil poolman = SQLUtil.getInstance();
	results = poolman.execute(getCurrentPoolname(), sql);	
	return results;
	
    }

    public String getCurrentPoolname() {
	return this.poolname;
    }

    public void setCurrentPoolname(String name) {
	this.poolname = name;
    }

    public Enumeration getAllPoolnames() {
	return SQLUtil.getInstance().getAllPoolnames(); 
    }

    public JDBCPool getCurrentPool() {
	return SQLUtil.getInstance().getPool(getCurrentPoolname());
    }
    
}


