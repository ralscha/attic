package gtf.tools;

import com.tce.math.TBigDecimal;

public class DiffSearchBooking {
	private TBigDecimal amount = null;
	private String account = null;
	private String valueDate = null;
	private String bookDate = null;
	private static final COM.stevesoft.pat.Regex diffRx = 
		new COM.stevesoft.pat.Regex("[ 1](\\d{4}-[ ]*\\d{2,7}-\\d{2}(-\\d{1,3})?)\\s+Bu:(\\d{8})\\s+Val:(\\d{8})\\s+([-\\d.]+)");

	public static DiffSearchBooking createObject(String line) {
		diffRx.search(line);

		if (diffRx.didMatch()) {
			DiffSearchBooking newObject = new DiffSearchBooking();

			newObject.setAccount(diffRx.stringMatched(1));
			newObject.setBookDate(diffRx.stringMatched(3));
			newObject.setValueDate(diffRx.stringMatched(4));
			newObject.setAmount(new TBigDecimal(diffRx.stringMatched(5)));

			return newObject;
		} else {
			return null;
		} 
	} 

	public TBigDecimal getAbsolutAmount() {
		return amount.abs();
	} 

	public String getAccount() {
		return account;
	} 

	public TBigDecimal getAmount() {
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

	public void setAmount(TBigDecimal newValue) {
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

		sb.append(ViolinStrings.Strings.leftJustify(getAccount(), 19));
		sb.append("  Bu:");
		sb.append(getBookDate());
		sb.append("  Val:");
		sb.append(getValueDate());
		sb.append(ViolinStrings.Strings.rightJustify(getAmount().toString(), 20));

		return sb.toString();
	} 

}