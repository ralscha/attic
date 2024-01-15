package gtf.check;

import java.io.*;
import java.util.*;
import java.text.*;

import gtf.common.AccountFormat;

import common.util.*;
import com.tce.math.TBigDecimal;

public class LiabCheck implements gtf.common.Constants {

	private Map buMap = new TreeMap();
	private List itemsList = new ArrayList();

	public void check477() {
		check(AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ2_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ2_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ2_TITLE),true);

		check(AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ3_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ3_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ3_TITLE),true);
	}
	
	public void check407() {
		check(AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ2_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ2_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ2_TITLE),true);

		check(AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ3_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ3_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ3_TITLE),true);
	}
	
	public void check406() {
		check(AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ2_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ2_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ2_TITLE),true);

		check(AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ3_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ3_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ3_TITLE),true);
	
	}
	
	public void check405() {
		
		check(AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ2_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ2_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ2_TITLE),true);
		
		check(AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ3_INPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ3_OUTPUT_FILE),
			  AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ3_TITLE),true);
	}
	
	
	private void check(String inputFileName, String outputFileName, String title, boolean newCheck) {
						
		buMap.clear();
		itemsList.clear();
		String line;
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(inputFileName));
			while((line = br.readLine()) != null) {
				if (line.length() >= 50) {
					String acctno = line.substring(0, 16);
					String kas = line.substring(17,20);
					if (!"410".equals(kas)) {
						String iso = line.substring(26,32).trim();
						TBigDecimal amount = new TBigDecimal(line.substring(33,50).trim());
						if (!amount.isZero()) {
							String bu = line.substring(51,52);
							
							AccountItem ai = new AccountItem(acctno, kas, iso, amount, bu);
							itemsList.add(ai);
							addToMap(ai);
						}
					}
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
				if (!si.getDifference().isZero()) {
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
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void writeItemsOld(LiabCheckHTMLPage page, Map singleMap, Map totalMap) {

		Iterator it = singleMap.keySet().iterator();
								
		while(it.hasNext()) {

			String bkst = (String)it.next();
			TBigDecimal singleTotal = (TBigDecimal)singleMap.get(bkst);
			TBigDecimal totalTotal = (TBigDecimal)totalMap.get(bkst);

			if (totalTotal == null)
				totalTotal = TBigDecimal.ZERO;
			
			TBigDecimal diff = singleTotal.add(totalTotal);
			if (!diff.isZero()) {
				page.writeCurrencyItem(bkst, singleTotal, totalTotal, diff);	
				AccountItem ai = searchDifference(diff);
				if (ai != null) {
					page.writeAccountWithDiff(AccountFormat.format(ai.getAccountNo(), AccountFormat.EXTERN));
				}

			}
		}

		it = totalMap.keySet().iterator();
								
		while(it.hasNext()) {

			String bkst = (String)it.next();
			TBigDecimal singleTotal = (TBigDecimal)singleMap.get(bkst);
			TBigDecimal totalTotal = (TBigDecimal)totalMap.get(bkst);

			if (singleTotal == null) {
				singleTotal = TBigDecimal.ZERO;
				
				TBigDecimal diff = totalTotal;
				if (!diff.isZero()) {
					page.writeCurrencyItem(bkst, singleTotal, totalTotal, diff);	
					AccountItem ai = searchDifference(diff);
					if (ai != null) {
						page.writeAccountWithDiff(AccountFormat.format(ai.getAccountNo(), AccountFormat.EXTERN));
					}

				}
			}
		}

		
	}

	private void writeItems(LiabCheckHTMLPage page, Map m, boolean isTotal, TBigDecimal diff) {
								
		Iterator it = m.keySet().iterator();
								
		while(it.hasNext()) {

			String bkst = (String)it.next();
			TBigDecimal total = (TBigDecimal)m.get(bkst);
			
			if ((diff != null) && (!it.hasNext())) {
				page.writeCurrencyItem(bkst, total, isTotal, diff);
				AccountItem ai = searchDifference(diff);
				if (ai != null) {
					page.writeAccountWithDiff(AccountFormat.format(ai.getAccountNo(), AccountFormat.EXTERN));
				}
			}
			else
				page.writeCurrencyItem(bkst, total, isTotal);
		}

	}

	private AccountItem searchDifference(TBigDecimal diff) {
		Iterator it = itemsList.iterator();
		while (it.hasNext()) {
			AccountItem ai = (AccountItem)it.next();
			if (diff.compareTo(ai.getBalance()) == 0)
				return ai;	
		}	
		return null;
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
		//Nur Test, wird durch PollService angestossen
		new LiabCheck().check477();
		new LiabCheck().check406();
		new LiabCheck().check405();
	}
	
}