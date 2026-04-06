package ch.rasc.springwebsocket.client;

import java.math.BigDecimal;

public class Quote {

	public String ticker;

	public BigDecimal price;

	@Override
	public String toString() {
		return "Quote [ticker=" + this.ticker + ", price=" + this.price + "]";
	}

}
