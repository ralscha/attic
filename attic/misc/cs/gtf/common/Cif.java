package gtf.common;

import ViolinStrings.*;

public class Cif {
	public String ibbb;
	public String stamm;
	public String p;

	public Cif() {
		ibbb = stamm = p = null;
	}

	public Cif(String intNo) {
		if (CifFormat.isCif(intNo) == CifFormat.INTERN) {
			ibbb = intNo.substring(0, 4);
			stamm = intNo.substring(4, 11);
			p = intNo.substring(11, 12);
		} else {
			throw new IllegalArgumentException("wrong intNo");
		} 
	}

	public boolean equals(Object obj) {
		try {
			Cif cif = (Cif)obj;
			return getInternString().equals(cif.getInternString());
		} catch (ClassCastException cce) {
			return false;
		} 
	} 

	public String getExternString() {
		return CifFormat.getExternString(ibbb, stamm, p);
	} 

	public String getIbbb() {
		return ibbb;
	} 

	public String getInternString() {
		return CifFormat.getInternString(ibbb, stamm, p);
	} 

	public String getP() {
		return p;
	} 

	public String getStamm() {
		return stamm;
	} 

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
		this.stamm = Strings.rightJustify(newValue, 7, '0');
	} 

	public String toString() {
		return ("#Cif (" + getInternString() + ")");
	} 

}