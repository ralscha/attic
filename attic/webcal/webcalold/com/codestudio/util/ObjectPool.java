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
 * ObjectPool provides a mechanism for pooling objects and resources.
 * It must be subclassed to acocmplish specific pools; GenericPool 
 * and JDBCPool are examples of such subclasses.
 *<br>
 *@see GenericPool
 *@see JDBCPool
 */
public abstract class ObjectPool implements Serializable {
    
    protected int limit;		// total number of allowed objects
    protected int count;		// current number of created objects
    protected long expirationTime;	// time objects have to live
    protected long objectTimeout;       // time SmartObjects have before being returned
    protected Hashtable locked;		// objects checked out
    protected Hashtable unlocked;	// objects available for checkout
    protected Thread skimmer;		// thread to clean up dead objects
    protected Thread lifeguard;		// thread to protect objects from hazard
    protected boolean debug = false; 	// whether or not to print diagnostic data
    
    // some default values
    public static final long DEFAULT_EXPIRATION = 600000;	// 10 minutes
    public static final long DEFAULT_SLEEPTIME = 300000;	// 5 minutes
    public static final int MAX_POOLSIZE = Integer.MAX_VALUE;	// System's max
    public static final long DEFAULT_TIMEOUT = 30000;		// 30 seconds
    
    /**
     * Creates an ObjectPool with the default params.
     */
    public ObjectPool() {
    	this(MAX_POOLSIZE, DEFAULT_EXPIRATION, DEFAULT_SLEEPTIME, DEFAULT_TIMEOUT);
    }
    
    /**
     * Create an ObjectPool with no object count limit.
     */
    public ObjectPool(long expirationTime, long sleepTime) {
	this(MAX_POOLSIZE, expirationTime, sleepTime, DEFAULT_TIMEOUT);
    }

    /** 
     * Create an instance of ObjectPool
     * @param expirationTime is the time in milliseconds that each object has to live.
     * @param objectLimit is the total number of objects that are allowed in the pool at any time. If objectLimit is equal to or less than 0, it is assumed that the limit is Integer.MAX_VALUE
     * @param sleepTime is the time in miliseconds that the pool skimming thread sleeps between 'reap' cycles
     */
    public ObjectPool(int objectLimit, long expirationTime, long sleepTime, long timeout) {
	
	this.objectTimeout = timeout;
	this.expirationTime = expirationTime;
	this.limit = objectLimit;

	this.count = 0;
	this.locked = new Hashtable();
	this.unlocked = new Hashtable();
	
	if(this.limit <= 0)
	    this.limit=Integer.MAX_VALUE;
	
	// skimmer removes dead or useless objects
	this.skimmer = new Thread(new PoolSkimmerThread(sleepTime, this));
	skimmer.start();
	
	// lifeguard protects SmartObjects
	this.lifeguard = new Thread(new LifeGuardThread(objectTimeout, this));
	lifeguard.start();
    }

    public long getTimeout() { return this.objectTimeout; }
    
    public boolean isDebugging() {
    	return this.debug;
    }
    
    public void setDebugging(boolean b) {
    	this.debug = b;
    }
    
    abstract protected Object create() throws Exception;
    abstract protected boolean validate( Object o );
    abstract protected void expire( Object o );

    /** @return int The total number of objects in the pool, both locked and unlocked. */
    public int numTotalObjects() {
	return (numUnlockedObjects() + numLockedObjects());
    }

    /** @return int The number of objects currently available. */
    public int numUnlockedObjects() {
	return unlocked.size();
    }

    /** @return int The number of objects currently checked out. */
    public int numLockedObjects() {
	return locked.size();
    }

    public long getExpirationTime() {
	return this.expirationTime;
    }

    public void setExpirationTime(long l) {
	this.expirationTime = l;
    }

    /** 
     * Checkout cycles through the available objects and returns the first valid
     * object it finds.  if there are no valid objects, checkOut will create
     * one, unless the client specified amount of objects have already been
     * created, and then it will block until it finds an appropriate object
     * @returns Object the object this pool holds
     */
    protected synchronized Object checkOut() throws Exception {
	
	long now = System.currentTimeMillis();
	Object o;
	
	if (unlocked.size() > 0)   {
	    
	    Enumeration e = unlocked.keys();
	    
	    while (e.hasMoreElements()) {
		o = e.nextElement();
		if(validate(o))  {
		    unlocked.remove(o);
		    locked.put(o, new Long(now));
		    if (o instanceof com.codestudio.util.SmartObject) {
			com.codestudio.util.SmartObject so = (com.codestudio.util.SmartObject) o;
			so.setState(SmartObject.LOCKED);
		    }
		    notifyAll();
		    return(o);
		}
		else {
		    // object failed validation 
		    --count;
		    expire(o);
		    o = null;
		}
	    }
	}
	
	// no objects available, create a new one
	if(count < limit) {
	    // there is still room for objects
	    o = create();
	    locked.put(o, new Long(now));
	    if (o instanceof com.codestudio.util.SmartObject) {
		com.codestudio.util.SmartObject so = (com.codestudio.util.SmartObject) o;
		so.setState(SmartObject.LOCKED);
	    }
	    ++count;
	    notifyAll();
	    return(o);
	}
	else {
	    //there is no room for objects
	    try {
		wait();
	    } catch(InterruptedException e) {
		System.out.println("ERROR: Failed while waiting for an object:");
		System.out.println(e);
	    }
	    return checkOut();
	}
    }
    
    /** Checks an object back into the pool. */
    protected synchronized void checkIn(Object o) {
    	if (o instanceof com.codestudio.util.SmartObject) {
	    com.codestudio.util.SmartObject so = (com.codestudio.util.SmartObject) o;
	    so.clean();
	    so.setState(SmartObject.UNLOCKED);
	}
	locked.remove(o);
	unlocked.put(o, new Long(System.currentTimeMillis()));
	notifyAll();
    }
    
    /** 
     * Remove unusable objects from the pool, called by PoolSkimmerThread.
     * Check the unlocked objects for expired members.
     */
    protected synchronized void cleanUp() {
	
	long now = System.currentTimeMillis();	
	for (Enumeration enum=unlocked.keys(); enum.hasMoreElements(); ) {
	    Object o = enum.nextElement();
	    long lasttouch = ((Long) unlocked.get(o)).longValue();
	    
	    // Object has expired
	    if ((now - lasttouch) > expirationTime) {
		if (o instanceof com.codestudio.util.SmartObject) {
		    com.codestudio.util.SmartObject so = (com.codestudio.util.SmartObject) o;
		    so.closeAllResources();
		}
		unlocked.remove(o);
		--count;
		expire(o);
		o=null;
	    }
	    
	}
	
	System.gc();
    }
    
    /**
     * Determine whether locked objects have timed out and 
     * should be checked back in. Called by LifeGuardThread.
     */
    protected void checkTimeout() {
	
    	long now = System.currentTimeMillis();
    	for (Enumeration enum=locked.keys(); enum.hasMoreElements(); ) {
	    Object o = enum.nextElement();
	    long lasttouch = ((Long) locked.get(o)).longValue();
	    
	    // User loses the Object, but it is still alive (gets cleaned on checkIn)
	    if ((System.currentTimeMillis() - lasttouch) > this.objectTimeout)
		checkIn(o);
	}	
    }
    
    /** @returns the current number of objects that are locked. */
    public int getLockedObjectCount() {
	return locked.size();    
    }
    
    /** @returns the current number of objects that are unlocked. */
    public int getUnlockedObjectCount() {
	return unlocked.size();
    }
    

    
    
}


