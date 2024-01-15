package gtf.check;

import java.io.*;
import java.util.*;
import java.text.*;

import gtf.check.*;
import gtf.common.*;
import gtf.db.LIABILITY_BOOKING;
import gtf.db.LIABILITY_BOOKINGTable;
import gtf.db.RC_LIABILITY_BOOK;
import gtf.db.RC_LIABILITY_BOOKTable;

import common.util.*;

import com.tce.math.TBigDecimal;

public class LCLiabCheck implements gtf.common.Constants {

	private final static SimpleDateFormat lastUpdateFormat = new SimpleDateFormat("dd.MM.yyyy'&nbsp;'HH:mm:ss.SSS");    
 	private static DecimalFormat form = null;
	static {
		form = new DecimalFormat("#,##0.000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);
	}

	private Map hostItemsMap = new TreeMap();
	private Map gtfItemsMap = new TreeMap();
	private List tableRows = new ArrayList();

	private List dbRowsList = new ArrayList();

	private final static String COMPARE_FILE = "comparelc.ser";
	private Map newCompareMap = new HashMap();
	private Map oldCompareMap;

	private LCLiabCheck() {
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
				
				LIABILITY_BOOKINGTable dlbt = new LIABILITY_BOOKINGTable(sc.getConnection());
				Iterator dbit = dlbt.select();
				while(dbit.hasNext()) {
					LIABILITY_BOOKING obj = (LIABILITY_BOOKING)dbit.next();
					dbRowsList.add(obj);
				}
				
				if (sc.getShortName().equals("ZRH")) {
					RC_LIABILITY_BOOKTable rbt = new RC_LIABILITY_BOOKTable(sc.getConnection());
					dbit = rbt.select();
					while(dbit.hasNext()) {
						dbRowsList.add(getLB((RC_LIABILITY_BOOK)dbit.next()));
					}
				}
			}
			
			Collections.sort(dbRowsList, new Comparator() {
								public int compare(Object o1, Object o2) {
									LIABILITY_BOOKING d1 = (LIABILITY_BOOKING)o1;
									LIABILITY_BOOKING d2 = (LIABILITY_BOOKING)o2;
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
				LIABILITY_BOOKING obj = (LIABILITY_BOOKING)it.next();
				pw.println(obj);
			}
			pw.close();
			
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
					

	}

	private void check(String[] files , List scList, String outputFile) {
		
		try {		
			
			/*
			Iterator it = scList.iterator();
			while(it.hasNext()) {
				ServiceCenter sc = (ServiceCenter)it.next();
				
				LIABILITY_BOOKINGTable dlbt = new LIABILITY_BOOKINGTable(sc.getConnection());
				Iterator dbit = dlbt.select("IS_EFFECTED = 0");
				while(dbit.hasNext()) {
					LIABILITY_BOOKING obj = (LIABILITY_BOOKING)dbit.next();
					dbRowsList.add(obj);
				}
			}
			*/
				
			readHostFiles(files);
		
			Iterator it = scList.iterator();
			while(it.hasNext()) {
				ServiceCenter sc = (ServiceCenter)it.next();
				System.out.println(sc.getName());
				
				LIABILITY_BOOKINGTable dlbt = new LIABILITY_BOOKINGTable(sc.getConnection());
				
				//Iterator dbit = dlbt.select("IS_EFFECTED = 1");
				Iterator dbit = dlbt.selectGroup();
				
				while(dbit.hasNext()) {
					LIABILITY_BOOKING obj = (LIABILITY_BOOKING)dbit.next();
					
					String acctno = obj.getACCT_MGMT_UNIT()+obj.getACCOUNT_NUMBER().trim();
					String iso = obj.getCURRENCY();
					TBigDecimal amount = new TBigDecimal(obj.getAMOUNT());
					//String bu = obj.getBUSINESS_CODE().trim();
					
					AccountItem ai = new AccountItem(acctno, "", iso, amount, "0014");
					
					if (obj.getTYPE().equals("D"))
						addToGtfMap(ai, false);
					else
						addToGtfMap(ai, true);
				}
				
				
				if (sc.getShortName().equals("ZRH")) {
					RC_LIABILITY_BOOKTable rbt = new RC_LIABILITY_BOOKTable(sc.getConnection());
					dbit = rbt.selectGroup();
					while(dbit.hasNext()) {
						
						RC_LIABILITY_BOOK obj = (RC_LIABILITY_BOOK)dbit.next();
												
						String acctno = obj.getACCT_MGMT_UNIT()+obj.getACCOUNT_NUMBER().trim();
						String iso = obj.getCURRENCY();
						TBigDecimal amount = new TBigDecimal(obj.getAMOUNT());
						//String bu = obj.getBUSINESS_CODE().trim();
						
						AccountItem ai = new AccountItem(acctno, "", iso, amount, "0014");
						
						if (obj.getTYPE().equals("D"))
							addToGtfMap(ai, false);
						else
							addToGtfMap(ai, true);

						
					}
				}

				
				
				sc.closeConnection();			
			}	
				

			Set diffAcctSet = new HashSet();
			
			PrintWriter pwacct = new PrintWriter(new FileWriter("LC_Accounts.txt"));

			it = gtfItemsMap.values().iterator();
			while(it.hasNext()) {
				AccountItem gtfAI = (AccountItem)it.next();
				AccountItem hostAI = (AccountItem)hostItemsMap.get(gtfAI.getAccountNo());
				
				if (hostAI != null) {
					if (hostAI.getBalance().compareTo(gtfAI.getBalance()) != 0) {
						if (!isGlobalAccount(hostAI.getAccountType())) {
							diffAcctSet.add(gtfAI.getAccountNo());
							pwacct.println(gtfAI.getAccountNo());
							addRow(AccountFormat.format(gtfAI.getAccountNo(),AccountFormat.EXTERN),hostAI.getAccountType()
									,form.format(gtfAI.getBalance()),form.format(hostAI.getBalance()),
									form.format(gtfAI.getBalance().subtract(hostAI.getBalance())));
					  	}
					} else {
						//System.out.println(gtfAI.getAccountNo() + " gleich");
					}
					hostItemsMap.remove(gtfAI.getAccountNo());
				} else {
					if (!gtfAI.getBalance().isZero()) {
						if (!isGlobalAccount2(gtfAI.getAccountNo())) {
							diffAcctSet.add(gtfAI.getAccountNo());
							pwacct.println(gtfAI.getAccountNo());	
							addRow(AccountFormat.format(gtfAI.getAccountNo(),AccountFormat.EXTERN),""
								,form.format(gtfAI.getBalance()),"0.00",form.format(gtfAI.getBalance()));
					 	}
					}
				}
				
			}
			
			it = hostItemsMap.values().iterator();	
			while(it.hasNext()) {
				AccountItem ai = (AccountItem)it.next();
				if (!isGlobalAccount(ai.getAccountType())) {
					if (!ai.getBalance().isZero()) {
						diffAcctSet.add(ai.getAccountNo());
						pwacct.println(ai.getAccountNo());	
						addRow(AccountFormat.format(ai.getAccountNo(),AccountFormat.EXTERN),ai.getAccountType()
							,"0.00",form.format(ai.getBalance()),form.format(ai.getBalance().negate()));
					}
				}
			}
			
			
			PrintWriter pw = new PrintWriter(new FileWriter(outputFile));
			writeHead(pw);
		
			writeRows(pw);
			
			writeTail(pw);
			pw.close();
			pwacct.close();
	
	
			pw = new PrintWriter(new FileWriter("lc_non_effected.txt"));
			it = dbRowsList.iterator();
			while(it.hasNext()) {
				LIABILITY_BOOKING obj = (LIABILITY_BOOKING)it.next();
				if (diffAcctSet.contains((obj.getACCT_MGMT_UNIT()+obj.getACCOUNT_NUMBER()).trim()))
					pw.println(obj);
			}
			pw.close();
			
			writeCompare();
		
		} catch (Exception e) {
			System.err.println(e);
		}
		
	}

	private boolean isGlobalAccount(String accountType) {
		return (accountType.equals("405") || 
			     accountType.equals("408") );
	}
	
	private boolean isGlobalAccount2(String accountNo) {
		return (accountNo.indexOf("94005", 4) != -1);
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
	private void readHostFiles(String[] files) throws FileNotFoundException, IOException {
		
		for (int i = 0; i < files.length; i++) {
			BufferedReader br = new BufferedReader(new FileReader(files[i]));
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
		}
		
	}

	private void writeHead(PrintWriter pw) {
		pw.println("<html>");
		pw.println("<head>");
		pw.print("<title>");
		pw.print("LC GTF / HOST Compare");
		pw.println("</title>");
		pw.println("<META HTTP-EQUIV=\"Expires\" CONTENT=\"Mon, 06 Jan 1990 00:00:01 GMT\">");
		pw.println("</head>");
		pw.println("<body>");
		pw.print("<FONT SIZE=\"+1\"><b>");
		pw.print("LC GTF / HOST Compare");
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
		pw.println("<th>KAS</th>");
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
	
	private void writeRows(PrintWriter pw) {
		Collections.sort(tableRows);
		Iterator it = tableRows.iterator();
		while(it.hasNext()) {
			pw.println((String)it.next());
		}
	}
	
	private void addRow(String r1, String kas, String r2, String r3, String diff) {
				
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
	
		StringBuffer sb = new StringBuffer();
		sb.append("<tr"+color+">");
		sb.append("<td>"+r1+"</td>");
		sb.append("<td>"+kas+"</td>");
		sb.append("<td align=\"right\">"+r2+"</td>");
		sb.append("<td align=\"right\">"+r3+"</td>");
		sb.append("<td align=\"right\">"+diff+"</td>");
		sb.append("</tr>");
		tableRows.add(sb.toString());
	}

	
	private LIABILITY_BOOKING getLB(RC_LIABILITY_BOOK r) {
		
		return new LIABILITY_BOOKING(r.getRECORDID(),r.getOID(),r.getHOLDER_OID(),
		                             r.getDOSSIER_OID(),r.getSEQUENCE_NUMBER(),
		                             r.getLIABILITY_TYPE(),r.getGTF_NUMBER(),r.getTYPE(),
		                             r.getCURRENCY(),r.getAMOUNT(),r.getACCT_MGMT_UNIT(),
		                             r.getACCOUNT_NUMBER(),r.getVALUE_DATE(),
		                             r.getEXCHANGE_RATE(),r.getPROCESSING_CENTER(),
		                             r.getCUSTOMER_REF(),r.getGTF_TYPE(),r.getBUSINESS_CODE(),
		                             r.getIS_EFFECTED(),r.getUPDATED_BY(),
		                             r.getUPDATE_TS(),r.getHOLDER_CLASS_TYPE()) ;
		
	}

	public static void main(String args[]) throws Exception {
		
		if (args.length == 3) {				
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
			if ("DB".equals(args[2])) {
				String[] files = new String[6];
				files[0] = AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ2_INPUT_FILE);
				files[1] = AppProperties.getStringProperty(P_LIAB_CHECK_K405_RZ3_INPUT_FILE);
				files[2] = AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ2_INPUT_FILE);
				files[3] = AppProperties.getStringProperty(P_LIAB_CHECK_K406_RZ3_INPUT_FILE);
				files[4] = AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ2_INPUT_FILE);
				files[5] = AppProperties.getStringProperty(P_LIAB_CHECK_K407_RZ3_INPUT_FILE);
				new LCLiabCheck().check(files, scList, args[1]);
			}
			else
				new LCLiabCheck().readRows(scList, args[1]);

		} 
	}
	
}