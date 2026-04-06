package ch.rasc.springwebsocket;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.msgpack.MessagePack;
import org.springframework.scheduling.annotation.Scheduled;

public class QuoteService {

	private static final MathContext mathContext = new MathContext(2);

	private final Random random = new Random();

	private final Map<String, String> prices = new ConcurrentHashMap<>();

	private final QuoteHandler quoteHandler;

	private final MessagePack msgpack;

	public QuoteService(QuoteHandler quoteHandler) {
		this.quoteHandler = quoteHandler;

		this.msgpack = new MessagePack();
		this.msgpack.register(Quote.class);

		this.prices.put("CTXS", "24.30");
		this.prices.put("DELL", "13.03");
		this.prices.put("EMC", "24.13");
		this.prices.put("GOOG", "893.49");
		this.prices.put("MSFT", "34.21");
		this.prices.put("ORCL", "31.22");
		this.prices.put("RHT", "48.30");
		this.prices.put("VMW", "66.98");
	}

	private Set<Quote> generateQuotes() {
		Set<Quote> quotes = new HashSet<>();
		for (String ticker : this.prices.keySet()) {
			Quote q = new Quote();
			q.ticker = ticker;
			q.price = getPrice(ticker);
			quotes.add(q);
		}
		return quotes;
	}

	private BigDecimal getPrice(String ticker) {
		BigDecimal seedPrice = new BigDecimal(this.prices.get(ticker), mathContext);
		double range = seedPrice.multiply(new BigDecimal("0.02")).doubleValue();
		BigDecimal priceChange = new BigDecimal(
				String.valueOf(this.random.nextDouble() * range), mathContext);
		return seedPrice.add(priceChange);
	}

	@Scheduled(fixedDelay = 1000)
	public void sendQuotes() throws IOException {
		this.quoteHandler.sendToAll(this.msgpack.write(generateQuotes()));
	}

}
