package gtf.check;

import java.io.*;
import java.util.*;
import java.text.*;

import gtf.check.*;
import gtf.common.*;
import gtf.db.DC_LIABILITY_BOOK;
import gtf.db.DC_LIABILITY_BOOKTable;

import common.util.*;

import com.tce.math.TBigDecimal;

public class DCLiabCheck implements gtf.common.Constants {

	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    
 	private static DecimalFormat form = null;
	static {
		form = new DecimalFormat("#,##0.000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);
	}

	private Map hostItemsMap = new HashMap();
	private Map gtfItemsMap = new HashMap();

	private final static String COMPARE_FILE = "comparedc.ser";
	private Map newCompareMap = new HashMap();
	private Map oldCompareMap;

	private List dbRowsList = new ArrayList();

	private DCLiabCheck() {
		try {
			File compareFile = new File(COMPARE_FILE);
			if (compareFile.exists()) {
				ObjectInputStream s = new ObjectInputStream(new FileInputStream(compareFile));
				oldCompareMap = (Map)s.readObject();
				s.close();
			} else {
				oldCompareMap = new HashMap();
			}
		} catch (Exception e) {
			oldCompareMap = new HashMap();
			System.err.println(e);
		}
	}
	
	private void writeCompare() {
		try {
			ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(COMPARE_FILE));
			s.writeObject(newCompareMap);
			s.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void readRows(List scList, String outputFile) {
		try {					
			Iterator it = scList.iterator();
			while(it.hasNext()) {
				ServiceCenter sc = (ServiceCenter)it.next();
				
				DC_LIABILITY_BOOKTable dlbt = new DC_LIABILITY_BOOKTable(sc.getConnection());
				Iterator dbit = dlbt.select();
				while(dbit.hasNext()) {
					DC_LIABILITY_BOOK obj = (DC_LIABILITY_BOOK)dbit.next();
					dbRowsList.add(obj);
				}
			}
			
			Collections.sort(dbRowsList, new Comparator() {
								public int compare(Object o1, Object o2) {
									DC_LIABILITY_BOOK d1 = (DC_LIABILITY_BOOK)o1;
									DC_LIABILITY_BOOK d2 = (DC_LIABILITY_BOOK)o2;
									int r = d1.getACCT_MGMT_UNIT().compareTo(d2.getACCT_MGMT_UNIT());
									if (r == 0) {
										r = d1.getACCOUNT_NUMBER().compareTo(d2.getACCOUNT_NUMBER());
									}
									return r;
								}
								});

			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
			it = dbRowsList.iterator();
			while(it.hasNext()) {
				DC_LIABILITY_BOOK obj = (DC_LIABILITY_BOOK)it.next();
				pw.println(obj);
			}
			pw.close();
			
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
					

	}

	private void check(String inputFileName2, String inputFileName3, List scList, String outputFile) {
		
		try {			
			readHostFiles(inputFileName2, inputFileName3);
		
			Iterator it = scList.iterator();
			while(it.hasNext()) {
				ServiceCenter sc = (ServiceCenter)it.next();
				
				DC_LIABILITY_BOOKTable dlbt = new DC_LIABILITY_BOOKTable(sc.getConnection());
				
				//Iterator dbit = dlbt.select("IS_EFFECTED = 1");
				Iterator dbit = dlbt.selectGroup();
				
				while(dbit.hasNext()) {
					DC_LIABILITY_BOOK obj = (DC_LIABILITY_BOOK)dbit.next();
					
					String acctno = obj.getACCT_MGMT_UNIT()+obj.getACCOUNT_NUMBER().trim();
					String iso = obj.getCURRENCY();
					TBigDecimal amount = new TBigDecimal(obj.getAMOUNT());
					//String bu = obj.getBUSINESS_CODE().trim();
					
					AccountItem ai = new AccountItem(acctno, "477", iso, amount, "0014");
					
					if (obj.getTYPE().equals("D"))
						addToGtfMap(ai, false);
					else
						addToGtfMap(ai, true);
				}
				
				
				sc.closeConnection();			
			}	
			
			
			PrintWriter pwacct = new PrintWriter(new FileWriter("DC_Accounts.txt"));
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
			writeHead(pw);
			
			
			it = gtfItemsMap.values().iterator();
			while(it.hasNext()) {
				AccountItem gtfAI = (AccountItem)it.next();
				AccountItem hostAI = (AccountItem)hostItemsMap.get(gtfAI.getAccountNo());
				
				if (hostAI != null) {
					if (hostAI.getBalance().compareTo(gtfAI.getBalance()) != 0) {
						pwacct.println(gtfAI.getAccountNo());
						writeRow(pw,AccountFormat.format(gtfAI.getAccountNo(),AccountFormat.EXTERN)
								,form.format(gtfAI.getBalance()),form.format(hostAI.getBalance()),
								form.format(gtfAI.getBalance().subtract(hostAI.getBalance())));	
					} else {
						//System.out.println(gtfAI.getAccountNo() + " gleich");
					}
					hostItemsMap.remove(gtfAI.getAccountNo());
				} else {
					if (!gtfAI.getBalance().isZero()) {
						pwacct.println(gtfAI.getAccountNo());
						writeRow(pw,AccountFormat.format(gtfAI.getAccountNo(),AccountFormat.EXTERN)
							,form.format(gtfAI.getBalance()),"no balance",form.format(gtfAI.getBalance()));
					}					
				}
				
			}
			
			it = hostItemsMap.values().iterator();	
			while(it.hasNext()) {
				AccountItem ai = (AccountItem)it.next();
				if (ai.getAccountType().equals("477"))
					if (!ai.getBalance().isZero()) {
						pwacct.println(ai.getAccountNo());
						writeRow(pw, AccountFormat.format(ai.getAccountNo(),AccountFormat.EXTERN)
							,"no balance",form.format(ai.getBalance()),form.format(ai.getBalance().negate()));
					}
			}
			
			
			writeTail(pw);
			pw.close();
			pwacct.close();
	
			writeCompare();
		
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	private void addToGtfMap(AccountItem ai, boolean plus) {			
		AccountItem help = (AccountItem)gtfItemsMap.get(ai.getAccountNo());
		if (help == null) {
			if (!plus) {
				TBigDecimal neg = ai.getBalance().negate();
				ai.setBalance(neg);
			}
			gtfItemsMap.put(ai.getAccountNo(), ai);
				
		} else {
			TBigDecimal total;
			if (plus)
				total = help.getBalance().add(ai.getBalance());
			else
				total = help.getBalance().add(ai.getBalance().negate());
			
			help.setBalance(total);	
		}
	}

	private void check(String inputFileName2, String inputFileName3,
							 String gtfInputFile, String outputFile) {
						
		String line;
		
		try {			
			readHostFiles(inputFileName2, inputFileName3);
			
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
			writeHead(pw);
						
			BufferedReader br = new BufferedReader(new FileReader(gtfInputFile));
			while((line = br.readLine()) != null) {
				if (line.length() >= 16) {
					String acctno = line.substring(0, 16);
					TBigDecimal amount = new TBigDecimal(line.substring(16).trim());		
					
					AccountItem ai = (AccountItem)hostItemsMap.get(acctno);
					if (ai != null) {
						if (ai.getBalance().compareTo(amount) != 0) {
							writeRow(pw,AccountFormat.format(acctno,AccountFormat.EXTERN)
									,form.format(amount),form.format(ai.getBalance()),
										form.format(amount.abs().add(ai.getBalance())));	
						} else {
							//System.out.println(acctno + " gleich");
						}
						hostItemsMap.remove(acctno);
					} else {
						writeRow(pw,AccountFormat.format(acctno,AccountFormat.EXTERN)
							,form.format(amount),"no balance",form.format(amount.abs()));
					}
				}
				
			}
			br.close();
			
			Iterator it = hostItemsMap.values().iterator();	
			while(it.hasNext()) {
				AccountItem ai = (AccountItem)it.next();
				if (ai.getAccountType().equals("477"))
					writeRow(pw, AccountFormat.format(ai.getAccountNo(),AccountFormat.EXTERN)
						,"no balance",form.format(ai.getBalance()),form.format(ai.getBalance()));
			}
			
			
			writeTail(pw);
			pw.close();

			writeCompare();
			
		} catch (FileNotFoundException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void readHostFiles(String inputFileName2, String inputFileName3) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(inputFileName2));
		String line;
		while((line = br.readLine()) != null) {
			if (line.length() >= 50) {
				String acctno = line.substring(0, 16);
				String kas = line.substring(17,20);
				String iso = line.substring(26,32).trim();
				TBigDecimal amount = new TBigDecimal(line.substring(33,50).trim());
				String bu = line.substring(51,52);
				
				AccountItem ai = new AccountItem(acctno, kas, iso, amount, bu);
				hostItemsMap.put(acctno, ai);
			}				
		}
		br.close();
		br = new BufferedReader(new FileReader(inputFileName3));
		while((line = br.readLine()) != null) {
			if (line.length() >= 50) {
				String acctno = line.substring(0, 16);
				String kas = line.substring(17,20);
				String iso = line.substring(26,32).trim();
				TBigDecimal amount = new TBigDecimal(line.substring(33,50).trim());
				String bu = line.substring(51,52);
				
				AccountItem ai = new AccountItem(acctno, kas, iso, amount, bu);
				hostItemsMap.put(acctno, ai);
			}				
		}
		br.close();
	}
	
	private void writeHead(PrintWriter pw) {
		pw.println("<html>");
		pw.println("<head>");
		pw.print("<title>");
		pw.print("DC GTF / HOST Compare");
		pw.println("</title>");
		pw.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"Mon, 06 Jan 1990 00:00:01 GMT\">");
		pw.println("</head>");
		pw.println("<body>");
		pw.print("<FONT SIZE=\"+1\"><b>");
		pw.print("DC GTF / HOST Compare");
		pw.println("</b></FONT>");
		pw.println("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font size=\"-1\">Last Update: ");
		
		Calendar today = Calendar.getInstance();
		pw.print(lastUpdateFormat.format(today.getTime()));
		pw.println("</font>");
		pw.println("<hr>");
		pw.println("<p>");	
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		pw.println("<th>AccountNo</th>");
		pw.println("<th>GTF</th>");
		pw.println("<th>HOST</th>");
		pw.println("<th>DIFF</th>");
		pw.println("</tr>");
	}
	
	private void writeTail(PrintWriter pw) {
		pw.println("</table>");
		pw.println("</body>");
		pw.println("</html>");
	}
	
	private void writeRow(PrintWriter pw, String r1, String r2, String r3, String diff) {
	
		Row r = new Row(r1, r2, r3, diff);
		newCompareMap.put(r1, r);
	
		Row fr = (Row)oldCompareMap.get(r1);		
		String color = "";
		if (fr != null) {
			if (!fr.equals(r)) {
				color = "bgcolor=\"Lime\"";
			} 
		} else {
			color = "bgcolor=\"Lime\"";
		}
	
		pw.println("<tr"+color+">");
		pw.println("<td>"+r1+"</td>");
		pw.println("<td align=\"right\">"+r2+"</td>");
		pw.println("<td align=\"right\">"+r3+"</td>");
		pw.println("<td align=\"right\">"+diff+"</td>");

		pw.println("</tr>	");
	}


	public static void main(String args[]) throws Exception {
		
		if (args.length == 2) {
			new DCLiabCheck().check(AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ2_INPUT_FILE),
																			 AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ3_INPUT_FILE),
																			 args[0], args[1]);
		} else if (args.length == 3) {				
			ServiceCenters scs = new ServiceCenters();
			java.util.StringTokenizer st = new java.util.StringTokenizer(args[0], ",");
			
			List scList = new ArrayList();	
			
			while(st.hasMoreTokens()) {
				String center = st.nextToken();
				if (scs.exists(center)) {
					ServiceCenter sc = scs.getServiceCenter(center);
					scList.add(sc);
				}
			}
			if ("DB".equals(args[2]))
				new DCLiabCheck().check(AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ2_INPUT_FILE),
											AppProperties.getStringProperty(P_LIAB_CHECK_K477_RZ3_INPUT_FILE), scList,args[1]);
			else
				new DCLiabCheck().readRows(scList, args[1]);

		} 
	}
	
}