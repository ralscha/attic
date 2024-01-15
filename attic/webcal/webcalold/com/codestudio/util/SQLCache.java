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
 *
 */
package com.codestudio.util;

import java.util.*;
import java.sql.SQLException;

public class SQLCache {

    private Hashtable cache;
    private JDBCPool mypool;
    public static final int DEFAULT_SIZE = 5;
    protected long sleeptime;
    protected int max_size;
    protected Thread cachechecker;


    public SQLCache(JDBCPool pool) {
	this(pool, DEFAULT_SIZE, ObjectPool.DEFAULT_SLEEPTIME);
    }

    public SQLCache(JDBCPool pool, int size, long sleeptime) {
	this.cache = new Hashtable(1);
	this.mypool = pool;	
	this.max_size = findCacheSize(size);
	this.cachechecker = new Thread(new CacheThread(mypool, this, sleeptime));
	cachechecker.start();
    }

    /** Return the current cache as a Hashtable. */
    public synchronized Hashtable getRawCache() {
	return this.cache;
    }

    /** Set the maximum size of the cache based on the property
     * set in the props file. If it is not found there, the 
     * DEFAULT_SIZE will be used instead.
     * @returns int The maximum number of cached sql queries and responses.
     */
    private int findCacheSize(int size) {
	if (size > 0)
	    return size;
	return DEFAULT_SIZE;
    }

    /** Retrieve the JDBCPool to which this SQLCache corresponds. */
    public JDBCPool getPool() {
	return this.mypool;
    }

    /** Establish the JDBCPool to which this SQLCache corresponds. */
    public void setPool(JDBCPool pool) {
	this.mypool = pool;
    }

    /** Get a cached SQL Result.
     * @return Hashtable[] A Hashtable array of results
     */
    public Hashtable[] getResult(String sql) {
	if (this.cache.containsKey(sql))
	    return (Hashtable[]) cache.get(sql);
	return null;
    }
    
    /**
     * Remove a cached SQLResult.
     * @return boolean Whether or not the operation succeeded.
     */
    public boolean removeResult(String sql) {
	try {
	    this.cache.remove(sql);
	    return true;
	} catch (Exception e) {}
	return false;
    }

    /** Cache a result. */
    public synchronized boolean cacheResult(String sql, Hashtable[] results) {
	String _sql = sql.toLowerCase();
	if (_sql.startsWith("select")) {
	    if (cache.size() < this.max_size) {
		if (cache.containsKey(sql))
		    cache.remove(sql);
		cache.put(sql, results);
		return true;
	    }
	}
	return false;
    }

    /** Return the size of the cache as an integer. */
    public int size() {
	return this.cache.size();
    }

    /** Return the maximum possible size of the cache. */
    public int getMaxSize() {
	return this.max_size;
    }
}
