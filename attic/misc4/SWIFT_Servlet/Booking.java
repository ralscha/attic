import java.math.*;

public class Booking implements java.io.Serializable {
	private String orderno;
	private String orderDate;
	private String text;
	private String valueDate;
	private double amount;
    private String account;

	public Booking() {
	    this(null, null, null, null, null);
	}
		
	public Booking(String account, String orderno, String text, String valueDate, BigDecimal amount) {
	    this.account = account;
		this.orderno = orderno;
		if (orderno != null)
    		orderDate = orderno.substring(5,11);
    	else
    	    orderDate = null;
		this.text = text;
		this.valueDate = valueDate;
		if (amount == null)
    		this.amount = 0.0d;
        else
            this.amount = amount.doubleValue();
	}
	
	public String getKey() {
	    return text + orderDate;
	}
	
	public String getOrderDate() {
	    return orderDate;
	}
	
    public boolean equals(Booking b) {
        return (text.equals(b.getText()));
    }

    public void setAccount(String account) {
        this.account = account;
    }
    
    public String getAccount() {
        return account;
    }

	public void setOrderno(String orderno) {
		this.orderno = orderno;
		orderDate = orderno.substring(5,11);
	}
	
	public String getOrderno() {
		return orderno;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	
	public void setValueDate(String date) {
	    this.valueDate = date;
	}
	
	public String getValueDate() {
	    return valueDate;
	}
	
	public void setAmount(BigDecimal amount) {
	    this.amount = amount.doubleValue();
	}
	
	public BigDecimal getAmount() {
	    return new BigDecimal(amount);
	}
	    
	public String toString() {
		return orderno + "," + text + "," + valueDate + "," + amount;
	}
} 
