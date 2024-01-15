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

import java.sql.*;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * SQLResult is a simplified means of keeping track of 
 * results returned from SQL queries. It encapsulates
 * the Hashtable array returned from a query made by SQLUtil.
 */
public class SQLResult {

    protected Hashtable[] records;
    private int index;


    /**
     * Build a SQLResult object from an array of Hashtables
     * returned from a SQLUtil query.
     */
    public SQLResult(Hashtable[] h) {
	this.records = h;
	this.index = 0;
    }

    public boolean hasNext() {
	if (null == this.records)
	    return false;
	if (this.index < this.records.length)
	    return true;
	return false;
    }

    public Hashtable next() {
	this.index++;
	return records[(this.index -1)];
    }

    public int size() {
	if (null == this.records)
	    return 0;
	return records.length;
    }

    public Object getObject(String name) {
	Hashtable h = records[index];
	if (h.containsKey(name)) {
	    return h.get(name);
	}
	else {
	    for (Enumeration enum=h.keys(); enum.hasMoreElements(); ) {
		String colname = enum.nextElement().toString();
		if (colname.toLowerCase().equals(name.toLowerCase()))
		    return h.get(colname);
	    }
	}
	return null;
    }

    public String getString(String name) {
	Object o = getObject(name);
	if (o != null) {
	    if (o instanceof java.lang.String)
		return (String) o;
	    else
		throw new ClassCastException();
	}
	return null;
    }
    
    public String toString() {
	
	if (records == null) 
	    return "SQLResult [0]";
	
	StringBuffer out = new StringBuffer();
	out.append("SQLResult ["+records.length+"]\n");
	if (records.length > 0){
	    out.append("[");
	    Hashtable h = records[0];
	    Enumeration keys = h.keys();
	    while (keys.hasMoreElements()){
		String key = (String) keys.nextElement();
		out.append(key);
		if(keys.hasMoreElements())
		    out.append(", ");
	    }
	    out.append("]\n");
	    out.append("[");
	    keys = h.keys();
	    while (keys.hasMoreElements()){
		String key = (String) keys.nextElement();
		Object o = h.get(key);
		String ClassName = o.getClass().getName();
		
		out.append(ClassName);
		if(keys.hasMoreElements())
		    out.append(", ");
	    }
	    out.append("]\n\n");
	    
	    for (int n =0; n < records.length;n++){
		h = records[n];
		out.append("[");
		keys = h.keys();
		while (keys.hasMoreElements()){
		    String key = (String) keys.nextElement();
		    Object o = h.get(key);
		    
		    out.append(o.toString());
		    if(keys.hasMoreElements())
			out.append(", ");
		}
		out.append("]\n");
	    }
	    
	}
	return out.toString();
    }
    
    public String toHtml() {

	if (records == null)
	    return "<h3>SQLResult [0]</h3>";

	StringBuffer out = new StringBuffer();
	//out.append("<h3>SQLResult ["+records.length+"]</h3>");
	out.append("<table bgcolor='#505050' cellspacing='1' cellpadding='1'>");
	if (records.length > 0){
	    out.append("<tr>");
	    Hashtable h = records[0];
	    Enumeration keys = h.keys();
	    while (keys.hasMoreElements()){
		String key = (String) keys.nextElement();
		out.append("<td bgcolor='#C0C0C0' align='center'><b><font size=3>");
		out.append(key);
		out.append("</font></b></td>");
	    }
	    out.append("</tr>");
	    out.append("<tr>");
	    keys = h.keys();
	    while (keys.hasMoreElements()){
		String key = (String) keys.nextElement();
		Object o = h.get(key);
		String ClassName = o.getClass().getName();
		
		out.append("<td bgcolor='#C0C0C0' align='center'>");
		out.append("<font size=1>Java Type:<br>");
		out.append(ClassName);
		out.append("</font></td>");
	    }
	    out.append("</tr>");
	    
	    for (int n =0; n < records.length;n++){
		h = records[n];
		out.append("<tr>");
		keys = h.keys();
		while (keys.hasMoreElements()){
		    String key = (String) keys.nextElement();
		    Object o = h.get(key);
		    
		    out.append("<td bgcolor='#E0E0E0'><font size=2>");
		    out.append(o.toString());
		    out.append("</font></td>");
		}
		out.append("</tr>");
	    }
	    out.append("</table>");
	}
	return out.toString();
    }

}
