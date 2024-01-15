package gtf.ss.common;

import java.math.*;

public class Amount {

	private BigDecimal amount;
	private String isoCode;
	
	private static Currency currency = new Currency();
	
	public Amount(String gtfAmountStr) {
		amount = currency.getDecimal(gtfAmountStr);
		isoCode = currency.getIsoCodeAlpha(gtfAmountStr);		
	}
	
	public String getIsoCode() {
		return isoCode;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
}