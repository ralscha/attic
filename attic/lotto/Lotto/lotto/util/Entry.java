package lotto.util;

import java.util.*;

public class Entry implements Comparable {
	private int value;
	private int data;
	
	public Entry(int v, int d) {
		value = v;
		data = d;
	}
	
	public void setValue(int v) {
		value = v;
	}
	
	public void setData(int d) {
		data = d;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getData() {
		return data;
	}
	
	public boolean equals(Object object) {
		try { 
		   Entry e = (Entry)object;
		   return ((data == e.getData()) && (value == e.getValue()));
		} catch(ClassCastException e) { }
		return false;
	}
	
	public int compareTo(Object o) {
		try {
			Entry e = (Entry)o;
			
		if (e.getData() == data)
			return (value - e.getValue());
		else
			return (e.getData() - data);
		} catch (ClassCastException cce) {
				return 1;
		}
	}
	
	public String toString() {
		return (new String(value+" / "+data));
	}
}