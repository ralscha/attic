package gtf.util;

public class DiffSearchBooking {

	private com.ibm.math.BigDecimal amount = null;
	private String account = null;
	private String valueDate = null;
	private String bookDate = null;

	private static final COM.stevesoft.pat.Regex diffRx = new COM.stevesoft.pat.Regex("[ 1](\\d{4}-[ ]*\\d{2,7}-\\d{2}(-\\d{1,3})?)\\s+Bu:(\\d{8})\\s+Val:(\\d{8})\\s+([-\\d.]+)");

	public static DiffSearchBooking createObject(String line) {
		diffRx.search(line);
		if (diffRx.didMatch()) {	
			DiffSearchBooking newObject = new DiffSearchBooking();
			newObject.setAccount(diffRx.stringMatched(1));
			newObject.setBookDate(diffRx.stringMatched(3));
			newObject.setValueDate(diffRx.stringMatched(4));
			newObject.setAmount(new com.ibm.math.BigDecimal(diffRx.stringMatched(5)));
	
			return newObject;
		} else {
			return null;
		}
	}
	
	public com.ibm.math.BigDecimal getAbsolutAmount() {
		return amount.abs();
	}
	
	public String getAccount() {
		return account;
	}
	
	public com.ibm.math.BigDecimal getAmount() {
		return amount;
	}
	
	public String getBookDate() {
		return bookDate;
	}
	
	public String getValueDate() {
		return valueDate;
	}
	
	public void setAccount(String newValue) {
		this.account = newValue;
	}
	
	public void setAmount(com.ibm.math.BigDecimal newValue) {
		this.amount = newValue;
	}
	
	public void setBookDate(String newValue) {
		this.bookDate = newValue;
	}
	
	public void setValueDate(String newValue) {
		this.valueDate = newValue;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(ViolinStrings.Strings.leftJustify(getAccount(),19));
		sb.append("  Bu:");
		sb.append(getBookDate());
		sb.append("  Val:");
		sb.append(getValueDate());
		sb.append(ViolinStrings.Strings.rightJustify(getAmount().toString(),20));
		return sb.toString();
	}
}