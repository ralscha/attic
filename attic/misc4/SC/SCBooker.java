package gtf.tools;

import java.util.*;
import java.text.*;
import java.io.*;

import gtf.mbooker.*;
import gtf.common.*;

public class SCBooker {
		
	private static final DecimalFormat df = new DecimalFormat("000");
	
	private static DecimalFormat form = null;
	static {
		form = new DecimalFormat("#,##0.000");
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		dfs.setGroupingSeparator('\'');
		form.setDecimalFormatSymbols(dfs);
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
	}
	
	
	private final static COM.stevesoft.pat.Regex exRegex = 
		new COM.stevesoft.pat.Regex("^\\s*[A-Z]{2}\\s*[A-Z]{2}\\s*(CAH[AB] \\d{2,3})\\s*[-](\\d+)\\s*(\\d{4})-(\\d{12})\\s*(\\d{2}).(\\d{2}).(\\d{4})\\s*([\\d.,-]+)\\s*([A-Z]*)\\s*(\\d*)");
	
	private void createFirmBookings(String fileName) {
		BufferedReader dis;
		String line;
		String fileLine;
		int seqno = 700;
		List bookingItemsList = new ArrayList();
		
		try {
			String outputFileName = "firm_book.txt";
				
			PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((fileLine = br.readLine()) != null) {
				String global = null;
				BufferedReader bookFile = new BufferedReader(new FileReader(fileLine));
					
				while((line = bookFile.readLine()) != null) {
					exRegex.search(line);
					if (exRegex.didMatch()) {
						String gtfNo = exRegex.stringMatched(2);
						String amu = exRegex.stringMatched(3);
						String acct = exRegex.stringMatched(4);
						String day = exRegex.stringMatched(5);
						String month = exRegex.stringMatched(6);
						String year = exRegex.stringMatched(7); 
						String amountstr = exRegex.stringMatched(8);
						String currency = exRegex.stringMatched(9);
						String caha = exRegex.stringMatched(1);
						String holderOID = exRegex.stringMatched(10);
						if (SCAccounts.isSC(amu+acct)) {
							
							SCFBookItem sbi = new SCFBookItem();
							sbi.setGtfNo(gtfNo);
							sbi.setValueDate(day+"."+month+"."+year);
							sbi.setCurrency(currency);
							sbi.setCaha(caha);
							sbi.setHolderOID(holderOID);

							FirmLiability fl = new FirmLiability();
							fl.setSequence_number(seqno++);
							fl.setGtf_number(gtfNo);
							
							fl.setCurrency(currency);
							
							double amount = Double.parseDouble(amountstr);
							fl.setAmount(Math.abs(amount));
							sbi.setAmount(form.format(fl.getAmount()));
							sbi.setTAmount(fl.getAmount());
							fl.setValue_date(day+month+year); // ddMMyyyy

							Currency cu = CurrencyMap.getCurrency(currency);
							fl.setExchange_rate(cu.getInternalRate().doubleValue());

							if ((amu+acct).startsWith("9400", 4))
								global = amu+acct;
								
							if (amount < 0)
								sbi.setAccountNoDebit(AccountFormat.format(amu+acct,AccountFormat.EXTERN));
							else
								sbi.setAccountNoCredit(AccountFormat.format(amu+acct,AccountFormat.EXTERN));

							System.out.println(amu+acct);
							String line2 = searchOtherSide(fileLine, holderOID,amu+acct);
							exRegex.search(line2);
							if (exRegex.didMatch()) {
								amountstr = exRegex.stringMatched(8);
								amount = Double.parseDouble(amountstr);
								amu = exRegex.stringMatched(3);
								acct = exRegex.stringMatched(4);

								if ((amu+acct).startsWith("9400", 4))
									global = amu+acct;

								if (amount < 0)
									sbi.setAccountNoDebit(AccountFormat.format(amu+acct,AccountFormat.EXTERN));
								else
									sbi.setAccountNoCredit(AccountFormat.format(amu+acct,AccountFormat.EXTERN));
							}
								

							bookingItemsList.add(sbi);
							
							fl.setGtf_proc_center(getIBBB(caha));
							fl.setCustomer_ref("");
							
							fl.setGtf_type("importLc");
							
							fl.setDossier_item("");
							fl.setBu_code("1411");
							
							System.out.println(global);
							int rz = Branches.getRZ(global.substring(0,4));
							
							int csno = cu.getCSCurrNumber();
							String csstr = df.format(csno);
						
							String stamm = global.substring(4,11);
							if (rz == 2) {
								fl.setDeb_acct_number(global);
								int pz = gtf.common.CheckSumCalculator.calc("0892"+stamm);
								fl.setCre_acct_number("0892"+stamm+pz+"8"+csstr);
							} else {
								fl.setDeb_acct_number(global);
								int pz = gtf.common.CheckSumCalculator.calc("0896"+stamm);
								fl.setCre_acct_number("0896"+stamm+pz+"8"+csstr);
							}
							
							sbi.setDebit(AccountFormat.format(fl.getDeb_acct_number(),AccountFormat.EXTERN));
							sbi.setCredit(AccountFormat.format(fl.getCre_acct_number(),AccountFormat.EXTERN));
							
							
							pw.print(fl.getBookString());
											
						}
					} else {
						System.out.println(line);
					}
				}
				bookFile.close();
			}
			br.close();
			pw.close();
			
			
			//Summary
			Collections.sort(bookingItemsList);
			
			outputFileName = "firm_book_summary.html";				
			
			pw = new PrintWriter(new FileWriter(outputFileName));
			
			SCFBookItem.writeHead(pw);
			
			Iterator it = bookingItemsList.iterator();
			String mcurr = null;
			String macct = null;
			double currtotal = 0.0;
			double accttotal = 0.0;
			
			while(it.hasNext()) {
				SCFBookItem sbi = (SCFBookItem)it.next();
				
				
				String c1 = sbi.getAccountNoDebit();
				
				if (c1.startsWith("9400", 4))
					c1 = sbi.getAccountNoCredit();
				
				if (c1.equals(macct)) {
					accttotal += sbi.getTAmount();
				} else {
					if (macct != null)
						SCFBookItem.writeTotal(pw, macct, form.format(accttotal));
					macct = c1;
					accttotal = sbi.getTAmount();
				}
				
				if (sbi.getCurrency().equals(mcurr)) {
					currtotal += sbi.getTAmount();
				} else {
					if (mcurr != null)
						SCFBookItem.writeTotal(pw, mcurr, form.format(currtotal));
					mcurr = sbi.getCurrency();
					currtotal = sbi.getTAmount();
				}
				
				sbi.writeRow(pw);
			}
			if (macct != null)
				SCFBookItem.writeTotal(pw, macct, form.format(accttotal));
			
			if (mcurr != null)
				SCFBookItem.writeTotal(pw, mcurr, form.format(currtotal));

			SCFBookItem.writeTail(pw);
			
			pw.close();
			
		} catch (IOException ioe) {
			System.err.println(ioe);
		}				

	}
		
		
	private String searchOtherSide(String fileName, String OID, String account) throws IOException {
	
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line;			
		while((line = reader.readLine()) != null) {
			exRegex.search(line);
			if (exRegex.didMatch()) {
				String holderOID = exRegex.stringMatched(10);
				String amu = exRegex.stringMatched(3);
				String acct = exRegex.stringMatched(4);

				if (OID.equals(holderOID)) {
					if (!account.equals(amu+acct)) {
						reader.close();
						return line;
					}
				}
			}
		}
		reader.close();
		return null;
	}	
		
			
	private void createContBookings(String fileName, boolean lc) {
		BufferedReader dis;
		String line;
		int seqno = 300;
		List bookingItemsList = new ArrayList();
		
		try {
			String outputFileName = "cont_book.txt";
			if (lc)
				outputFileName = "LC_"+outputFileName;
			else
				outputFileName = "DC_"+outputFileName;
				
			PrintWriter pw = new PrintWriter(new FileWriter(outputFileName));
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((line = br.readLine()) != null) {
				
				BufferedReader bookFile = new BufferedReader(new FileReader(line));
				while((line = bookFile.readLine()) != null) {
					exRegex.search(line);
					if (exRegex.didMatch()) {
						String gtfNo = exRegex.stringMatched(2);
						String amu = exRegex.stringMatched(3);
						String acct = exRegex.stringMatched(4);
						String day = exRegex.stringMatched(5);
						String month = exRegex.stringMatched(6);
						String year = exRegex.stringMatched(7); 
						String amountstr = exRegex.stringMatched(8);
						String currency = exRegex.stringMatched(9);
						String caha = exRegex.stringMatched(1);
						String holderOID = exRegex.stringMatched(10);
						if (SCAccounts.isSC(amu+acct)) {
							
							SCBookItem sbi = new SCBookItem();
							sbi.setGtfNo(gtfNo);
							sbi.setAccountNo(AccountFormat.format(amu+acct,AccountFormat.EXTERN));
							sbi.setValueDate(day+"."+month+"."+year);
							
							sbi.setCurrency(currency);
							sbi.setCaha(caha);
							sbi.setHolderOID(holderOID);
							
							FirmLiability fl = new FirmLiability();
							fl.setSequence_number(seqno++);
							fl.setGtf_number(gtfNo);
							
							fl.setCurrency(currency);
							
							double amount = Double.parseDouble(amountstr);
							if (amount > 0) {
								System.out.println(line);
								continue;
							}
								
							fl.setAmount(Math.abs(amount));
							sbi.setAmount(form.format(fl.getAmount()));
							sbi.setTAmount(fl.getAmount());
							
							fl.setValue_date(day+month+year); // ddMMyyyy
							
							Currency cu = CurrencyMap.getCurrency(currency);
							fl.setExchange_rate(cu.getInternalRate().doubleValue());
							
							int rz = Branches.getRZ(amu);
							
							int csno = cu.getCSCurrNumber();
							String csstr = df.format(csno);
							
							if (lc) {
								String debit = amu+"9400510";
								int pz = gtf.common.CheckSumCalculator.calc(debit);
								
								if (rz == 2) {
									fl.setDeb_acct_number(debit+pz+"8"+csstr);
									fl.setCre_acct_number("0892940051038"+csstr);
								} else {
									fl.setDeb_acct_number(debit+pz+"8"+csstr);
									fl.setCre_acct_number("0896940051028"+csstr);
								}
							} else {
								
								if (rz == 2) {
									fl.setDeb_acct_number("0895940052888"+csstr);
									fl.setCre_acct_number("0892940052868"+csstr);
								} else {
									fl.setDeb_acct_number("0899940052878"+csstr);
									fl.setCre_acct_number("0896940052858"+csstr);
								}
							}
							sbi.setDebit(AccountFormat.format(fl.getDeb_acct_number(),AccountFormat.EXTERN));
							sbi.setCredit(AccountFormat.format(fl.getCre_acct_number(),AccountFormat.EXTERN));
							bookingItemsList.add(sbi);
							
							fl.setGtf_proc_center(getIBBB(caha));
							fl.setCustomer_ref("");
							
							if (lc) 
								fl.setGtf_type("importLc");
							else
								fl.setGtf_type("importDc");
							
							fl.setDossier_item("");
							fl.setBu_code("1411");
							pw.print(fl.getBookString());
											
						}
					} else {
						System.out.println(line);
					}
				}
				bookFile.close();
			}
			br.close();
			pw.close();
			
			
			//Summary
			Collections.sort(bookingItemsList);
			
			outputFileName = "cont_book_summary.html";
			if (lc)
				outputFileName = "LC_"+outputFileName;
			else
				outputFileName = "DC_"+outputFileName;
				
			
			pw = new PrintWriter(new FileWriter(outputFileName));
			
			SCBookItem.writeHead(pw);
			
			Iterator it = bookingItemsList.iterator();
			String mcurr = null;
			String macct = null;
			double currtotal = 0.0;
			double accttotal = 0.0;
			
			while(it.hasNext()) {
				SCBookItem sbi = (SCBookItem)it.next();
				
				if (sbi.getAccountNo().equals(macct)) {
					accttotal += sbi.getTAmount();
				} else {
					if (macct != null)
						SCBookItem.writeTotal(pw, macct, form.format(accttotal));
					macct = sbi.getAccountNo();
					accttotal = sbi.getTAmount();
				}
				
				if (sbi.getCurrency().equals(mcurr)) {
					currtotal += sbi.getTAmount();
				} else {
					if (mcurr != null)
						SCBookItem.writeTotal(pw, mcurr, form.format(currtotal));
					mcurr = sbi.getCurrency();
					currtotal = sbi.getTAmount();
				}
				
				sbi.writeRow(pw);
			}
			if (macct != null)
				SCBookItem.writeTotal(pw, macct, form.format(accttotal));
			
			if (mcurr != null)
				SCBookItem.writeTotal(pw, mcurr, form.format(currtotal));

			SCBookItem.writeTail(pw);
			
			pw.close();
			
		} catch (IOException ioe) {
			System.err.println(ioe);
		}				
	}
	
	private static String getIBBB(String caha) {
		caha = caha.toUpperCase();
		if (caha.startsWith("CAHA 1"))
			return("0835");
		else if (caha.startsWith("CAHB"))
			return("0835");
		else if (caha.startsWith("CAHA 2"))
			return("0060");
		else if (caha.startsWith("CAHA 3"))
			return("0094");
		else if (caha.startsWith("CAHA 4"))
			return("0251");
		else if (caha.startsWith("CAHA 5"))
			return("0425");
		else if (caha.startsWith("CAHA 6"))
			return("0456");
		else if (caha.startsWith("CAHA 7"))
			return("0637");
		else if (caha.startsWith("CAHA 8"))
			return("0823");		
		else
			return(caha);
	}
	
	public static void main(String[] args) {
		if (args.length == 2) {
			if (args[1].equalsIgnoreCase("FIRM"))
				new SCBooker().createFirmBookings(args[0]);
			else if (args[1].equalsIgnoreCase("DCCONT"))
				new SCBooker().createContBookings(args[0], false);
			else if (args[1].equalsIgnoreCase("LCCONT"))
				new SCBooker().createContBookings(args[0], true);
			else
				System.out.println("SCBooker <filesList> <FIRM | DCCONT | LCCONT>");
			
		} else 
			System.out.println("SCBooker <filesList> <FIRM | DCCONT | LCCONT>");

			
		
	}

}