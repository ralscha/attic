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

/** The Thread that manages refreshes of the SQL query cache. */
public class CacheThread implements Runnable {

    private JDBCPool pool;
    private SQLCache cache;
    private Hashtable rawcache;
    private long sleeptime;
    

    public CacheThread(JDBCPool pool, SQLCache cache, long sleep) {
	this.pool = pool;
	this.cache = cache;
	this.rawcache = cache.getRawCache();
	this.sleeptime = sleep;
    }

    /** 
     * The Thread which manages the cache refreshes operates 
     * in this run method. */
    public void run() {
	for (;;) {
	    try {
		Thread.sleep(this.sleeptime);
		//System.out.println("woke up, sleeptime is " + this.sleeptime);
		for (Enumeration enum=rawcache.keys(); enum.hasMoreElements(); ) {
		    String sql = (String) enum.nextElement();
		    try {
			JDBCInfo info = pool.getInfo();			
			Hashtable[] res = SQLUtil.getInstance().doJDBC(info.getPoolname(), sql);
			cache.cacheResult(sql, res);
		    } catch (SQLException sqle) {
			cache.removeResult(sql);
		    }
		}
		System.gc();
	    } catch (InterruptedException ie) { 
		System.out.println("ERROR: Cache Checker Died:");
	    } 
	}
    }
}
