package gtf.common;

import ViolinStrings.*;

public class Account {
	public String ibbb = null;
	public String stamm = null;
	public String p = null;
	public String last;

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
			Account acct = (Account)obj;

			return getInternString().equals(acct.getInternString());
		} catch (ClassCastException cce) {
			return false;
		} 
	} 

	public String getExternString() {
		return AccountFormat.getExternString(ibbb, stamm, p, last);
	} 

	public String getIbbb() {
		return ibbb;
	} 

	public String getInternString() {
		return AccountFormat.getInternString(ibbb, stamm, p, last);
	} 

	public String getLast() {
		return last;
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

	public void setLast(String newValue) {
		this.last = newValue;
	} 

	public void setP(String newValue) {
		this.p = newValue;
	} 

	public void setStamm(String newValue) {
		this.stamm = Strings.rightJustify(newValue, 7, '0');
	} 

	/**
	 * Returns a String that represents the value of this object.
	 * @return a string representation of the receiver
	 */
	public String toString() {
		return ("#Account (" + getInternString() + ")");
	} 

}