package ch.sr.lotto.util;

public class PairEntry {
	private int value1;
	private int value2;
	private int data;
	
	public PairEntry(int v1, int v2, int d) {
		value1 = v1;
		value2 = v2;
		data = d;
	}
	
	public void setValue1(int v1) {
		value1 = v1;
	}
	
	public void setvalue2(int z2) {
		value2 = z2;
	}
	
	
	public void setData(int d) {
		data = d;
	}
	
	public int getValue1() {
		return value1;
	}
	
	public int getValue2() {
		return value2;
	}
	
	public int getData() {
		return data;
	}
	
	public boolean equals(Object object) {
		try { 
			PairEntry e = (PairEntry)object;
			return ((data == e.getData()) && (value1 == e.getValue1()) && (value2 == e.getValue2()));
		} catch (ClassCastException e) {
      //no action  
    }
		
		return false;
	}

	public String toString() {
	  return (new String(value1+" + "+value2+" / "+data));
	}
}