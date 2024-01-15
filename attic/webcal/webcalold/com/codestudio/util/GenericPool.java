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

import java.lang.reflect.*;

/**
 * GenericPool allows any object to be pooled through the PoolManager.
 * By passing a classname and optionally some constructor params, any
 * Java object can be pooled here and accessed through a PoolManager.
 * 
 */
public class GenericPool extends ObjectPool {
    
    protected Class otype;
    protected Object[] oparams;
    protected Constructor con;
    
    
    /**
     * Create a pool of Objects in which the Objects are created using
     * the default empty constructor.
     */
    public GenericPool(String classname) {
	this(classname, MAX_POOLSIZE, DEFAULT_TIMEOUT, DEFAULT_SLEEPTIME);
    }
    
    /**
     * Create a pool of Objects in which the Objects are created using
     * a specific constructor with the specified params.
     */
    public GenericPool(String classname, Object[] params) {
	this(classname, params, MAX_POOLSIZE, DEFAULT_TIMEOUT, DEFAULT_SLEEPTIME);
    }
    
    public GenericPool(String classname, int objectLimit,
		       long expirationTime, long sleepTime) {
	
	super(objectLimit, expirationTime, sleepTime, DEFAULT_TIMEOUT);
	try {
	    this.otype = Class.forName(classname);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException("ERROR: Class " + classname + " not found:");
	}
	this.oparams = null;
	this.con = null;
    }
    
    public GenericPool(String classname, Object[] params, int objectLimit,
		       long expirationTime, long sleepTime) {
	
	super(objectLimit, expirationTime, sleepTime, DEFAULT_TIMEOUT);
	try {
	    this.otype = Class.forName(classname);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException("ERROR: Class " + classname + " not found:");
	}
	this.oparams = params;
	this.con = figureConstructor();
    }
    
    /**
     * Set the constructor's params
     */
    public synchronized void setParams(Object[] p) {
	this.oparams = p;
	this.con = figureConstructor();
    }
    
    /**
     * Get the constructor's params
     */
    public synchronized Object[] getParams() {
	return this.oparams;
    }
    
    protected Constructor figureConstructor() {
	
	if (this.oparams == null)
	    return null;
	
	Class[] paramscl = new Class[this.oparams.length];
	for (int i=0; i<oparams.length; i++) {
	    paramscl[i] = oparams[i].getClass();
	}
	try {
	    return otype.getConstructor(paramscl);
	} catch (NoSuchMethodException ne) {
	    System.err.println("ERROR: In GenericPool: No such constructor with the specified params:");
	} catch (SecurityException se) {
	    System.err.println("ERROR: GenericPool: Security Exception:");
	}
	return null;
    }
    
    protected Object create() throws Exception {
	
	if (otype == null)
	    throw new ClassNotFoundException();
	
	Object o = null;
	
	if (this.con == null) {
	    o = otype.newInstance();
	    return o;
	}
	
	else {
	    o = this.con.newInstance(this.oparams);
	}
	
	return o;
    }
    
    public Object requestObject() {
	try {
            return super.checkOut();
        } catch (Exception e) {
	    System.out.println(e);
	    return null;
        }
    }

    public void returnObject(Object o) {
	super.checkIn(o);
    }

    protected boolean validate(Object o) {
	return (o != null);
    }
    
    protected void expire(Object o) {
	o = null;
    }
    
}

