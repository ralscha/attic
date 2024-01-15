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
 * PoolSkimmer manages the clean up of dead and expired objects
 * floating around the pool.
 *<p>
 * Another Thread, LifeGuardThread, also monitors ObjectPool 
 * instances. LifeGuardThread is more humane; it attempts to
 * send troubled objects back to their pools. PoolSkimmerThread
 * simply disposes of already dead or useless objects.
 *<br>
 * @see LifeGuardThread
 */
public class PoolSkimmerThread implements Runnable {
    
    private long timeout;
    private ObjectPool pool;

	
    public PoolSkimmerThread(long timeout, ObjectPool pool) {
	this.timeout = timeout;
	this.pool = pool;
    }
    
    public void run() {
	
	for (;;) {
	    
	    try {
		synchronized(this) {
		    wait(this.timeout);
		}
	    } catch(InterruptedException e) {}
	    
	    this.pool.cleanUp();
	    
	    //print out current number of objects.
	    if (this.pool.isDebugging()) {
		int locked = this.pool.getLockedObjectCount();
		int unlocked = this.pool.getUnlockedObjectCount();
		System.out.println("PoolMan Stats>> Locked: "+
				   locked+
				   " Unlocked: "+
				   unlocked+
				   " Total: "+
				   (unlocked+locked)+
				   " Date: "+ new Date());
	    }
	}
    }
    
}
    
