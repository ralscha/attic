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

import java.io.Serializable;

/**
 * An object aware of its pool, lifespan, and possibly other resources.
 */
public interface SmartObject extends Serializable {
    
    public void clean();
    public ObjectPool getPool();
    public void closeAllResources();
    public void setState(int i);
    public int getState();

    public static final int UNLOCKED = 0;
    public static final int LOCKED = 1;
    public static final int FAIL = -1;
}

