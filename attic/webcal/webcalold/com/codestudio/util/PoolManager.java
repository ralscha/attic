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
import java.io.Serializable;

/**
 * An object that manages several pools of objects.
 * @see SQLManager
 */
public class PoolManager implements Serializable {
    
    protected Hashtable pools;
    protected ObjectPool defaultpool;
    

    public Enumeration getAllPoolnames() {
	return pools.keys();
    }

    public ObjectPool getPool(String name) {
	if (name == null)
	    return this.defaultpool;
	if (pools.containsKey(name)) {
	    try {
		return (ObjectPool) pools.get(name);
	    } catch (Exception e) {}
	}
	throw new NullPointerException ("ERROR: " + name + " does not exist. " +
					"Please check your poolman.props file.");
    }

    public void addPool(String id, ObjectPool newpool) {
	if (pools.containsKey(id)) {
	    System.out.println("ERROR: A pool identified by the id " +
			       id + " already exists, ignoring it.");
	}
	else {
	    pools.put(id, newpool);
	    if (this.defaultpool == null)
		this.defaultpool = newpool;
	}
    }
    
    public void removePool(String id) {
	if (pools.containsKey(id)) {
	    int th = this.defaultpool.hashCode();
	    int oh = pools.get(id).hashCode();
	    if (th == oh)
		this.defaultpool = null;
	    pools.remove(id);
	}	
    }
    
    public Object requestObject(String classname) {
	try {
	    return defaultpool.checkOut();
	} catch (Exception e) { 
	    System.out.println("ERROR: Could not request object, returning NULL:");
	}
	return null;
    }
    
    public Object requestObject(String classname, String poolname) {
	ObjectPool pool = null;
	try {
	    pool = (ObjectPool) pools.get(poolname);
	} catch (NullPointerException ne) {}
	if (pool != null) {
	    try {
		return pool.checkOut();
	    } catch (Exception e) {
		System.out.println("ERROR: Could not request object, returning NULL:");
	    }
	}
	return null;
    }
    
    public void returnObject(Object o) {
	defaultpool.checkIn(o);
    }
    
    public void returnObject(Object o, String poolname) {
	ObjectPool pool = null;
	try {
	    pool = (ObjectPool) pools.get(poolname);
	} catch (NullPointerException ne) {}
	if (pool != null)
	    pool.checkIn(o);
    }
    
    /*
      class Lifeguard extends Thread {
      
      public void run() {
      try {
      
      } catch (InterruptedException ie) {}
      }
      
      }
    */
    
}
