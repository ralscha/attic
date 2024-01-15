package gtf.util;

import ViolinStrings.*;

public class Cif {
	public String ibbb;
	public String stamm;
	public String p;
	
	public Cif() {
		super();
		ibbb = stamm = p = null;
	}

	public Cif(String intNo) {
		if (CifFormat.isCif(intNo) == CifFormat.INTERN) {
			ibbb  = intNo.substring(0,4);
			stamm = intNo.substring(4,11);
			p 	  = intNo.substring(11,12);
		} else {
			throw new IllegalArgumentException("wrong intNo");
		}
	}

	/**
	 * Compares two objects for equality. Returns a boolean that indicates
	 * whether this object is equivalent to the specified object. This method
	 * is used when an object is stored in a hashtable.
	 * @param obj the Object to compare with
	 * @return true if these Objects are equal; false otherwise.
	 * @see java.util.Hashtable
	 */
	public boolean equals(Object obj) {
		try {		
			Cif cif = (Cif)obj;
			return getInternString().equals(cif.getInternString());
		} catch (ClassCastException cce) {
			return false;
		}
	}
	
	public String getExternString() {
		return CifFormat.getExternString(ibbb,stamm,p);
	}
	
	public String getIbbb() {
		return ibbb;
	}
	
	public String getInternString() {
		return CifFormat.getInternString(ibbb,stamm,p);
	}
	
	public String getP() {
		return p;
	}
	
	public String getStamm() {
		return stamm;
	}
	
	/**
	 * Generates a hash code for the receiver.
	 * This method is supported primarily for
	 * hash tables, such as those provided in java.util.
	 * @return an integer hash code for the receiver
	 * @see java.util.Hashtable
	 */
	public int hashCode() {
		return (getInternString().hashCode());
	}
	
	public void setIbbb(String newValue) {
		this.ibbb = newValue;
	}
	
	public void setP(String newValue) {
		this.p = newValue;
	}
	
	public void setStamm(String newValue) {
		this.stamm = Strings.rightJustify(newValue,7,'0');
	}
	
	/**
	 * Returns a String that represents the value of this object.
	 * @return a string representation of the receiver
	 */
	public String toString() {
		return ("#Cif (" + getInternString() + ")");
	}
}