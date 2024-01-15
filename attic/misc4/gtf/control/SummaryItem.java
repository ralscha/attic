
package gtf.control;

import com.ibm.math.BigDecimal;
import java.util.*;

public class SummaryItem implements Comparable {
	private String buCode;
	private String isoCurrency;
	private List singleAccountList;
	private List totalAccountList;
	private BigDecimal totalSingle;
	private BigDecimal totalTotal;
	
	private Map singleAccountMap;
	private Map totalAccountMap;
	
	public SummaryItem(String buCode, String isoCode) {
		this.buCode = buCode;
		this.isoCurrency = isoCode;
		
		singleAccountList = new ArrayList();
		totalAccountList = new ArrayList();	
		totalSingle = BigDecimal.ZERO;
		totalTotal = BigDecimal.ZERO;
		
		singleAccountMap = new TreeMap();
		totalAccountMap = new TreeMap();
	}
	
	public void summarize() {
		Collections.sort(singleAccountList);
		Collections.sort(totalAccountList);
	
		summarizeList(singleAccountList.iterator(), singleAccountMap);
		summarizeList(totalAccountList.iterator(), totalAccountMap);	
	}
	
	public Map getSingleAccountMap() {
		return singleAccountMap;
	}
	
	public Map getTotalAccountMap() {
		return totalAccountMap;
	}
	
	public BigDecimal getTotal(String bkst, boolean fromTotal) {
		if (fromTotal) {
			return (BigDecimal)totalAccountMap.get(bkst);			
		} else {
			return (BigDecimal)singleAccountMap.get(bkst);
		}
	}
	
	private void summarizeList(Iterator it, Map m) {
		String mbkst = null;
		BigDecimal total = BigDecimal.ZERO;
								
		while(it.hasNext()) {

			AccountItem ai = (AccountItem)it.next();
			
			if (mbkst == null)
				mbkst = ai.getAccountNo().substring(0,4);	
				
			if (mbkst.equals(ai.getAccountNo().substring(0,4))) {
				total = total.add(ai.getBalance());
			} else {
				m.put(mbkst, total);
				
				total = ai.getBalance();
				mbkst = ai.getAccountNo().substring(0,4); 
			}	
		}
		if (total.compareTo(BigDecimal.ZERO) != 0) {
			m.put(mbkst, total);
		}
	}
	
	
	public void add(AccountItem ai) {
		if (ai.isTotalAccount()) {
			totalAccountList.add(ai);
			totalTotal = totalTotal.add(ai.getBalance());
		} else {
			singleAccountList.add(ai);
			totalSingle = totalSingle.add(ai.getBalance());
		}
		
	}
	
	public List getSingleAccountList() {
		return singleAccountList;
	}
	
	public List getTotalAccountList() {
		return totalAccountList;
	}
	
	public BigDecimal getTotalSingle() {
		return totalSingle;
	}
	
	public BigDecimal getTotalTotal() {
		return totalTotal;
	}

	public BigDecimal getDifference() {
		return (totalSingle.add(totalTotal));
	}

	public String getIsoCurrency() {
		return isoCurrency;
	}

	public void setIsoCurrency(String iso) {
		isoCurrency = iso;
	}
	
	public String getBuCode() {
		return buCode;
	}	
	
	public void setBuCode(String buCode) {
		this.buCode = buCode;
	}

	public int compareTo(Object o) {
		SummaryItem other = (SummaryItem)o;
		return (isoCurrency.compareTo(other.getIsoCurrency()));
	}
	
}