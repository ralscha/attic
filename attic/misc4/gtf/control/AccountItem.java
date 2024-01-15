
package gtf.control;

import com.ibm.math.BigDecimal;
import common.util.AppProperties;
import java.util.List;

public class AccountItem implements Comparable {
	private String accountNo;
	private String accountType;
	private String isoCurrency;
	private BigDecimal balance;
	private String buCode;

	private final static String[] totalType;
	
	
	static {
		List li = AppProperties.getStringArrayProperty("liab.total.accounttype");
		totalType = (String[])li.toArray(new String[li.size()]);
	}
	
	public AccountItem() {
		accountNo = null;
		accountType = null;
		isoCurrency = null;
		balance = null;
		buCode = null;
	}
	
	public AccountItem(String accountNo, String accountType, String isoCurrency, BigDecimal balance, String buCode) {
		this.accountNo = accountNo;
		this.accountType = accountType;
		this.isoCurrency = isoCurrency;
		this.balance = balance;
		setBuCode(buCode);
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	
	public boolean isTotalAccount() {
				
		for (int i = 0; i < totalType.length; i++) {
			if (accountType.equals(totalType[i].trim())) {
				return true;
			}
		}
		return false;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setIsoCurrency(String isoCurrency) {
		this.isoCurrency = isoCurrency;
	}

	public String getIsoCurrency() {
		return isoCurrency;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBuCode(String buCode) {
		if ("B".equals(buCode))
			this.buCode = "0011";
		else if ("C".equals(buCode))
			this.buCode = "0012";
		else if ("D".equals(buCode))
			this.buCode = "0013";
		else if ("E".equals(buCode))
			this.buCode = "0014";
		else if ("F".equals(buCode))
			this.buCode = "0015";
		else
			this.buCode = buCode;
	}
	
	public String getBuCode() {
		return buCode;
	}

	
	public boolean equals(Object o) {
		try {
			AccountItem ai = (AccountItem)o;
			
			return ((accountNo.equals(ai.getAccountNo())) && (accountType.equals(ai.getAccountType())) 
						&& (balance.equals(ai.getBalance())) && (buCode.equals(ai.getBuCode()))
						&& (isoCurrency.equals(ai.getIsoCurrency())));

		} catch (ClassCastException cce) {
			return false;
		}
	}

	public int compareTo(Object o) {
		int r;
		AccountItem other = (AccountItem)o;

		r = buCode.compareTo(other.getBuCode());
		if (r == 0) {
			r = isoCurrency.compareTo(other.getIsoCurrency());
			if (r == 0) {
				r = accountNo.compareTo(other.getAccountNo());
			}
		}
		return r;
	}
	
}