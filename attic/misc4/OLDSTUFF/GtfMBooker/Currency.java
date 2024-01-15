
public class Currency {
	private int csCurrNumber;
	private String isoCode;
	private java.math.BigDecimal internalRate;
	
	
	public Currency() {
		csCurrNumber = 0;
		isoCode = null;
		internalRate = null;
	}
	
	public Currency(int csCurrNumber, String isoCode, java.math.BigDecimal internalRate) {
		this.csCurrNumber = csCurrNumber;
		this.isoCode = isoCode;
		this.internalRate = internalRate;
	}
	
	private void setCSCurrNumber(int newValue) {
		csCurrNumber = newValue;
	}
	
	private void setISOCode(String newValue) {
		isoCode = newValue;
	}
	
	private void setInternalRate(java.math.BigDecimal newValue) {
		internalRate = newValue;
	}

	public int getCSCurrNumber() {
		return csCurrNumber;
	}
	
	public String getISOCode() {
		return isoCode;
	}
	
	public java.math.BigDecimal getInternalRate() {
		return internalRate;
	}

	public String toString() {
		return "Currency (" + csCurrNumber + " " + isoCode + " " + internalRate + ")";
	}
	
	public boolean equals(Object obj) {
		try {
			Currency curr = (Currency)obj;
			return ((csCurrNumber == curr.getCSCurrNumber()) && 
					 (isoCode.equals(curr.getISOCode())) && 
					 (internalRate.equals(curr.getInternalRate())));			
		} catch (ClassCastException cce) {
			return false;
		}
	}
}