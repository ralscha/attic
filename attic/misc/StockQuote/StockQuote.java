import java.io.*;
import wit.html.*;
import wit.http.*;

public class StockQuote
{
	public String title;
	public String name;
	public String date;
	public String time;
	public String last;
	public String change;
	public String todayhigh;
	public String prevclose;
	public String todaylow;
	public String bid;
	public String todayopen;
	public String ask;
	public String volume;
	public String peratio;
	public String week52high;
	public String earnings;
	public String week52low;
	public String divpershare;

	public StockQuote (String symbols) throws IOException, WITException, WITServiceException
	{
		String args[] = { symbols };
		WitContext w = new WitContext();
		w.invokeService("qs", "stockQuote", args);
		getValues(w);
	}

	public StockQuote(WitContext w,	String symbols)
		throws IOException, WITException, WITServiceException
    {

		String args[] = { symbols };
		if (w.getDefaultInterface() == null
		 || w.getDefaultInterface().equals("default"))
			w.setDefaultInterface("qs");
		w.invokeService("stockQuote", args);
		getValues(w);
	}


	private void getValues(WitContext w) throws WITException
	{
		title = w.getValue("title");
		name = w.getValue("name");
		date = w.getValue("date");
		time = w.getValue("time");
		last = w.getValue("last");
		change = w.getValue("change");
		todayhigh = w.getValue("todayhigh");
		prevclose = w.getValue("prevclose");
		todaylow = w.getValue("todaylow");
		bid = w.getValue("bid");
		todayopen = w.getValue("todayopen");
		ask = w.getValue("ask");
		volume = w.getValue("volume");
		peratio = w.getValue("peratio");
		week52high = w.getValue("week52high");
		earnings = w.getValue("earnings");
		week52low = w.getValue("week52low");
		divpershare = w.getValue("divpershare");
	}
}
