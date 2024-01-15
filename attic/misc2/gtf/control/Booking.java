package gtf.control;

public class Booking {
	private String orderno;
	private String orderDate;
	private String text;
	private String valueDate;
	private com.ibm.math.BigDecimal amount;
	private String key;
	private String account;

	public Booking() {
	    this(null, null, null, null);
	}
	
	public Booking(String orderno, String text, String valueDate, com.ibm.math.BigDecimal amount) {
		this.orderno = orderno;
		if (orderno != null) {			
			orderDate = orderno.substring(5,11);
			if (orderDate.substring(0,2).equals("99") || orderDate.substring(0,2).equals("98"))
				orderDate = "19" + orderDate;
			else
				orderDate = "20" + orderDate;
		}
		else
		    orderDate = null;
		this.text = text;
		this.valueDate = valueDate;
		this.amount = amount;
		this.key = text;
	}
	
	public boolean equals(Booking b) {
		return (text.equals(b.getText()));
	}
	public String getAccount() {
		return account;
	}
	public com.ibm.math.BigDecimal getAmount() {
	    return amount;
	}
	public String getKey() {
	    return text + orderDate;
	}
	public String getKeyII() {
		return key;
	}
	public String getOrderDate() {
	    return orderDate;
	}
	public String getOrderNo() {
		return orderno;
	}
	public String getText() {
		return text;
	}
	public String getValueDate() {
	    return valueDate;
	}
	public void setAccount(String account) {
			this.account = account;
	}
	public void setAmount(com.ibm.math.BigDecimal amount) {
	    this.amount = amount;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setOrderNo(String orderno) {
		this.orderno = orderno;
		orderDate = orderno.substring(5,11);
		
			if (orderDate.substring(0,2).equals("99") || orderDate.substring(0,2).equals("98"))
			orderDate = "19" + orderDate;
		else
			orderDate = "20" + orderDate;		
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setValueDate(String date) {
	    this.valueDate = date;
	}
	
	public String toString() {
		return account + "," + orderno + "," + text + "," + valueDate + "," + amount;
	}
	
}