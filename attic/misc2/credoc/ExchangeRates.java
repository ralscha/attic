package credoc;

import java.util.*;
import java.io.*;

public class ExchangeRates {
	private final static ExchangeRates instance = new ExchangeRates();
	private Map rates;
	
	protected ExchangeRates() {
		rates = new HashMap();
	
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("rates.dat"));
			while ((line = br.readLine()) != null) {
				rates.put(line.substring(0, 4), line.substring(5).trim());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public static ExchangeRates getInstance() {
		return instance;		
	}
	
	public String getRate(String code) {
		return (String)rates.get(code);	
	}
}