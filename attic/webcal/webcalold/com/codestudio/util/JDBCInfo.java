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

/**
 * JDBCInfo maintains useful information that describes
 * a JDBCPool. It contains info such as the pool name,
 * the connection parameters, and the number of available
 * connections.
 */
public class JDBCInfo {

    protected String poolname, drivername, url, username, password;

    public JDBCInfo() {}

    public JDBCInfo(String poolname, String drivername, String url,
		    String username, String password) {
	setPoolname(poolname);
	setDrivername(drivername);
	setUrl(url);
	setUsername(username);
	setPassword(password);
    }

    public String getPoolname() {
	return this.poolname;
    }

    public void setPoolname(String name) {
	this.poolname = name;
    }

    public String getDrivername() {
	return this.drivername;
    }
    
    public void setDrivername (String drivername) {
	this.drivername = drivername;
    }
    
    public String getUrl() {
	return this.url;
    }
    
    public void setUrl (String url) {
	this.url = url;
    }
    
    public String getUsername() {
	return this.username;
    }
    
    public void setUsername (String username) {
	this.username = username;
    }
    
    public String getPassword() {
	return this.password;
    }
    
    public void setPassword (String password) {
	this.password = password;
    }

}
