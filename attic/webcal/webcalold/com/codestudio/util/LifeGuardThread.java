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


/**
 * Asks SmartObjects to manage themselves and clean up if necessary.
 * For JDBCPool objects, this means asking hanging connections to 
 * clear themselves and either return to their respective pools or
 * to dispose of themselves.
 *<p>
 * LifeGuardThread attempts to salvage objects that may still be of use;
 * PoolSkimmerThread also monitors ObjectPool instances, but 
 * PoolSkimmerThread is a little harsher and mechanical -- it simply 
 * disposes of objects that are already dead or useless.
 *<br>
 *@see PoolSkimmerThread
 */
public class LifeGuardThread implements Runnable {
    
    /** The ObjectPool whose SmartObjects this Thread is monitoring. */
    private ObjectPool pool;

    /** The standard user timeout for each SmartObject, used to time checks. */
    private long timeout;

    public LifeGuardThread(long timeout, ObjectPool p) {
	this.pool = p;
	this.timeout = timeout;
    }
    
    public void run() {

	for (;;) {

	    synchronized(this) {

		// do nothing until something is checked out
		while (this.pool.getLockedObjectCount() <= 0) {
		    try {
			wait(this.timeout);
		    } catch (InterruptedException ie) {}
		}
		
		// when something is checked out, wait the usertimeout period then act
		try {
		    wait(this.timeout);
		} catch (InterruptedException iee){}
	    }

	    this.pool.checkTimeout();

	}
	
    }

}
