package gtf.control;

import java.io.*;
import java.util.*;
import java.text.*;
import common.util.*;
import com.ibm.math.BigDecimal;

public class LiabCheck {

	private Map buMap = new TreeMap();

	public void check477() {
		check(AppProperties.getStringProperty("kas477.check.rz2.input.file"),
			  AppProperties.getStringProperty("kas477.check.rz2.output.file"),
			  AppProperties.getStringProperty("kas477.check.rz2.title"),true);

		check(AppProperties.getStringProperty("kas477.check.rz3.input.file"),
			  AppProperties.getStringProperty("kas477.check.rz3.output.file"),
			  AppProperties.getStringProperty("kas477.check.rz3.title"),true);
	}
	
	public void check407() {
		check(AppProperties.getStringProperty("kas407.check.rz2.input.file"),
			  AppProperties.getStringProperty("kas407.check.rz2.output.file"),
			  AppProperties.getStringProperty("kas407.check.rz2.title"),false);

		check(AppProperties.getStringProperty("kas407.check.rz3.input.file"),
			  AppProperties.getStringProperty("kas407.check.rz3.output.file"),
			  AppProperties.getStringProperty("kas407.check.rz3.title"),false);
	}
	
	public void check406() {
		check(AppProperties.getStringProperty("kas406.check.rz2.input.file"),
			  AppProperties.getStringProperty("kas406.check.rz2.output.file"),
			  AppProperties.getStringProperty("kas406.check.rz2.title"),false);

		check(AppProperties.getStringProperty("kas406.check.rz3.input.file"),
			  AppProperties.getStringProperty("kas406.check.rz3.output.file"),
			  AppProperties.getStringProperty("kas406.check.rz3.title"),false);
	
	}
	
	public void check405() {
		
		check(AppProperties.getStringProperty("kas405.check.rz2.input.file"),
			  AppProperties.getStringProperty("kas405.check.rz2.output.file"),
			  AppProperties.getStringProperty("kas405.check.rz2.title"),false);
		
		check(AppProperties.getStringProperty("kas405.check.rz3.input.file"),
			  AppProperties.getStringProperty("kas405.check.rz3.output.file"),
			  AppProperties.getStringProperty("kas405.check.rz3.title"),false);
	}
	
	
	private void check(String inputFileName, String outputFileName, String title, boolean newCheck) {
						
		buMap.clear();
		String line;
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(inputFileName));
			while((line = br.readLine()) != null) {
				if (line.length() >= 50) {
					String acctno = line.substring(0, 16);
					String kas = line.substring(17,20);
					String iso = line.substring(26,32).trim();
					BigDecimal amount = new BigDecimal(line.substring(33,50).trim());
					String bu = line.substring(51,52);
					
					AccountItem ai = new AccountItem(acctno, kas, iso, amount, bu);
					
					addToMap(ai);
				}				
			}
			br.close();
			

			PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));
			LiabCheckHTMLPage page = new LiabCheckHTMLPage(pw, title);
			
			
			page.writeOverviewTableHead();
			
			Iterator it = buMap.values().iterator();
			while(it.hasNext()) {
				SummaryItem si = (SummaryItem)it.next();
				page.writeOverviewItem(si.getBuCode(), si.getIsoCurrency(),
										  si.getTotalSingle(), si.getTotalTotal(),
										  si.getDifference());
				si.summarize();
			}
			page.writeTableTail();
			page.writeSeparator();
			

			it = buMap.values().iterator();
			while(it.hasNext()) {
				SummaryItem si = (SummaryItem)it.next();
				if (si.getDifference().compareTo(BigDecimal.ZERO) != 0) {
					page.writeCurrencyTableHead(si.getIsoCurrency());
					
					if (newCheck) {
						writeItems(page, si.getSingleAccountMap(), false, null);
						writeItems(page, si.getTotalAccountMap(), true, si.getDifference());
					} else {
						writeItemsOld(page, si.getSingleAccountMap(), si.getTotalAccountMap());
					}
					
					page.writeTableTail();
				}
			}
			
			
			page.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	private void writeItemsOld(LiabCheckHTMLPage page, Map singleMap, Map totalMap) {

		Iterator it = singleMap.keySet().iterator();
								
		while(it.hasNext()) {

			String bkst = (String)it.next();
			BigDecimal singleTotal = (BigDecimal)singleMap.get(bkst);
			BigDecimal totalTotal = (BigDecimal)totalMap.get(bkst);

			if (totalTotal == null)
				totalTotal = BigDecimal.ZERO;
			
			BigDecimal diff = singleTotal.add(totalTotal);
			if (diff.compareTo(BigDecimal.ZERO) != 0) {
				page.writeCurrencyItem(bkst, singleTotal, totalTotal, diff);	
			}
		}

		it = totalMap.keySet().iterator();
								
		while(it.hasNext()) {

			String bkst = (String)it.next();
			BigDecimal singleTotal = (BigDecimal)singleMap.get(bkst);
			BigDecimal totalTotal = (BigDecimal)totalMap.get(bkst);

			if (singleTotal == null) {
				singleTotal = BigDecimal.ZERO;
				
				BigDecimal diff = totalTotal;
				if (diff.compareTo(BigDecimal.ZERO) != 0) {
					page.writeCurrencyItem(bkst, singleTotal, totalTotal, diff);	
				}
			}
		}

		
	}

	private void writeItems(LiabCheckHTMLPage page, Map m, boolean isTotal, BigDecimal diff) {
								
		Iterator it = m.keySet().iterator();
								
		while(it.hasNext()) {

			String bkst = (String)it.next();
			BigDecimal total = (BigDecimal)m.get(bkst);
			
			if ((diff != null) && (!it.hasNext()))
				page.writeCurrencyItem(bkst, total, isTotal, diff);
			else
				page.writeCurrencyItem(bkst, total, isTotal);
		}

	}

	private void addToMap(AccountItem ai) {
		SummaryItem si = (SummaryItem)buMap.get(ai.getBuCode()+ai.getIsoCurrency());
		if (si == null) {
			si = new SummaryItem(ai.getBuCode(), ai.getIsoCurrency());
			buMap.put(ai.getBuCode()+ai.getIsoCurrency(), si);
		}
		si.add(ai);
	}

	public static void main(String args[]) throws Exception {
		new LiabCheck().check405();
	}
	
}