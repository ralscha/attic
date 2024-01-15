package credoc;

import java.io.*;
import ViolinStrings.*;
import java.text.*;
import java.util.*;
import gtf.util.*;

public class Util {
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private final static SimpleDateFormat dateOutFormat = new SimpleDateFormat("dd.MM.yyyy");

	static {
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
	} 
/**
 * Util constructor comment.
 */
public Util() {
	super();
}
/**
 * This method was created in VisualAge.
 * @param fileName java.lang.String
 */
public static void checkNumbers(String fileName) {
	COM.stevesoft.pat.Regex stammRegex = new COM.stevesoft.pat.Regex("^\\d{2,7}$");
	COM.stevesoft.pat.Regex mitRegex = new COM.stevesoft.pat.Regex("^\\d{10,11}$");
	Set onlyCifSet = new HashSet();
	Set substSet   = new HashSet();
	Set acctCheckSet = new HashSet();
	String subst;
		
	try {
	
		String accounts[] = new String[7];
		String cifs[] = new String[7];
		CredocRecord record;
		CredocRecordStream recordStream = new CredocRecordStream(new BufferedReader(new FileReader(fileName)));
		PrintWriter pw   = new PrintWriter(new BufferedWriter(new FileWriter("subst.dat", true)));
		PrintWriter only = new PrintWriter(new BufferedWriter(new FileWriter("only_cif.dat")));
		PrintWriter acctOut = new PrintWriter(new BufferedWriter(new FileWriter("account_check.dat")));
		PrintWriter badRecordsWriter = new PrintWriter(new BufferedWriter(new FileWriter("badRecords.dat")));		
		PrintWriter newCredocOut = new PrintWriter(new BufferedWriter(new FileWriter("credoc_file_new.dat")));
		
		recordStream.defineAllFieldsRelevant();
		record = recordStream.read();
		
		boolean bad;
		
		while ((record = recordStream.read()) != null) {
			bad = false;
			accounts[0] = record.getField(CredocRecord.A_ACCOUNT_NUMBER);
			accounts[1] = record.getField(CredocRecord.CB_ACCOUNT_NUMBER);
			accounts[2] = record.getField(CredocRecord.NB_ACCOUNT_NUMBER);
			accounts[3] = record.getField(CredocRecord.IB_ACCOUNT_NUMBER);
			accounts[4] = record.getField(CredocRecord.RF_ACCOUNT_NUMBER);
			accounts[5] = record.getField(CredocRecord.B_ACCOUNT_NUMBER);
			accounts[6] = record.getField(CredocRecord.RB_ACCOUNT_NUMBER);
		
			cifs[0] = record.getField(CredocRecord.A_CIF_NUMBER);
			cifs[1] = record.getField(CredocRecord.CB_CIF_NUMBER);
			cifs[2] = record.getField(CredocRecord.NB_CIF_NUMBER);
			cifs[3] = record.getField(CredocRecord.IB_CIF_NUMBER);
			cifs[4] = record.getField(CredocRecord.RF_CIF_NUMBER);
			cifs[5] = record.getField(CredocRecord.B_CIF_NUMBER);
			cifs[6] = record.getField(CredocRecord.RB_CIF_NUMBER);
		
			for (int i = 0; i < 7; i++) {
				if (cifs[i] != null) {
					cifs[i] = cifs[i].trim();
					stammRegex.search(cifs[i]);
					if (stammRegex.didMatch()) {
						if (accounts[i] != null) {
							accounts[i] = accounts[i].trim();
							Account acct = AccountFormat.makeAccount(accounts[i]);
							if (acct != null) {

								Cif c = new Cif();
								c.setIbbb(acct.getIbbb());
								c.setStamm(cifs[i]);
																
								String hc = Strings.rightJustify(cifs[i], 7, '0');								
								if (acct.getStamm().equals(hc)) 						
									c.setP(acct.getP().substring(0,1));

										
								if (CheckSumCalculator.check(c.getInternString())) {
									long key = Long.parseLong(cifs[i].trim());
									subst = Substitution.getInstance().getSubst(String.valueOf(key));									
									if (subst == null) {																
										substSet.add(Strings.leftJustify(String.valueOf(key),20) + c.getExternString());
									}
										
									acctCheckSet.add(acct.getInternString() + " " + c.getInternString() + " " + Strings.rightJustify(cifs[i],7,'0'));
								}
								else {
									System.out.println(cifs[i] + " != " + accounts[i] + "  falsche Prüfziffer ");
									bad = true;
								}
									
							} else {
								System.out.println(cifs[i] + "    " + accounts[i] + " keine Kontonummer ");
								bad = true;
							}
						} else {
							long key = Long.parseLong(cifs[i].trim());
							subst = Substitution.getInstance().getSubst(String.valueOf(key));									
							if (subst == null)
								onlyCifSet.add(cifs[i]);
						}
					} else {
						if (CifFormat.isCif(cifs[i]) == CifFormat.WRONG) {
							mitRegex.search(cifs[i]);
							if (!mitRegex.didMatch()) {							
								System.out.println(cifs[i] + " falsches Format ");									
								bad = true;
							}
						}
					}
				}
			}
			
			if (bad) 
				badRecordsWriter.write(record.toString());
			else
				newCredocOut.write(record.toString());	
		}
		
		badRecordsWriter.close();
		newCredocOut.close();
		
		Iterator it = substSet.iterator();
		while(it.hasNext()) {
			pw.println((String)it.next());
		}		
		pw.close();

		it = acctCheckSet.iterator();
		while(it.hasNext()) {
			acctOut.println((String)it.next());
		}
		acctOut.close();
		
		it = onlyCifSet.iterator();
		while(it.hasNext()) {
			only.println((String)it.next());
		}
		only.close();
	} catch (Exception e) {
		System.err.println(e);
		return;
	}
}

public static void cifFile2substFile(String fileName) {
	COM.stevesoft.pat.Regex cifRegex = new COM.stevesoft.pat.Regex("^([0]*)(\\d{1,7}) (\\d{12})");
			
	try {	
	
		BufferedReader br = new BufferedReader(new FileReader(fileName));		
		PrintWriter pw   = new PrintWriter(new BufferedWriter(new FileWriter("subst.dat", true)));
		String line, subst;
			
		while ((line = br.readLine()) != null) {
			cifRegex.search(line);
			if (cifRegex.didMatch()) {

				String stamm = cifRegex.stringMatched(2);
				String cifno = cifRegex.stringMatched(3);

				long key = Long.parseLong(stamm.trim());
				subst = Substitution.getInstance().getSubst(String.valueOf(key));									
				if (subst == null) {
					Cif c = new Cif(cifno);
					pw.println(Strings.leftJustify(stamm,20) + c.getExternString());
				} else {
					System.out.println(stamm + " exist");
				}
			} 
		}
		br.close();
		pw.close();

	} catch (Exception e) {
		System.err.println(e);
		return;
	}
}


public static void splitFile(String inFile, String fileOpenDossiers, String fileLiquidatedDossiers) {
	try {
		CredocRecord record;
		CredocRecordStream recordStream = 	new CredocRecordStream(new BufferedReader(new FileReader(inFile)));
		recordStream.defineAllFieldsRelevant();
			
		BufferedWriter writerForOpenDossiers = new BufferedWriter(new FileWriter(fileOpenDossiers));
		BufferedWriter writerForLiquidatedDossiers = new BufferedWriter(new FileWriter(fileLiquidatedDossiers));

		while((record = recordStream.read()) != null) {	
			if (record.getField(CredocRecord.LIQUIDATION_DATE) == null) {
				writerForOpenDossiers.write(record.toString());
			} else {
				writerForLiquidatedDossiers.write(record.toString());
			}
			
		}
		writerForLiquidatedDossiers.close();
		writerForOpenDossiers.close();	
		
	} catch (Exception e) {
		System.err.println(e);
	}
}

public static void createBigFile(String fileName, int size) {
	try {
		CredocRecord record;
		CredocRecordStream recordStream = new CredocRecordStream(
							new BufferedReader(new FileReader(fileName)));
		
		recordStream.defineAllFieldsRelevant();

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName+"_BIG"));
		
		while((record = recordStream.read()) != null) {
			
			for (int i = 0; i < size; i++)
				writer.write(record.toString());			
			
		}
		writer.close();
		
	} catch (Exception e) {
		System.err.println(e);
	}
}
/**
 * This method was created in VisualAge.
 * @param in java.lang.String
 * @param out java.lang.String
 */
public static void createBookings(String in, String out, String type) {
	try {
		CredocRecord record;
		CredocRecordStream recordStream = new CredocRecordStream(
							new BufferedReader(new FileReader(in)));
		
		recordStream.defineAllFieldsRelevant();
		PrintWriter writer = new PrintWriter(new FileWriter(out));
		String seqno = Strings.leftJustify("777", 6)+";";

		StringBuffer sb = new StringBuffer(150);
		
		while((record = recordStream.read()) != null) {
			if (record.getField(CredocRecord.LIQUIDATION_DATE)==null) {
				
				sb.setLength(0);

				sb.append(seqno);
				sb.append(Strings.rightJustify(record.getField(CredocRecord.DOSSIER_NUMBER), 7));
				sb.append(";");
				sb.append(type); sb.append(";");
				sb.append(Strings.rightJustify(record.getField(CredocRecord.AMOUNT_CURRENCY).trim(), 4));
				sb.append(";");
				sb.append(Strings.rightJustify(record.getField(CredocRecord.AMOUNT), 18));
				sb.append(";");

				String account = record.getField(CredocRecord.RF_ACCOUNT_NUMBER);
				Account acctObj;
				
				if ((acctObj = AccountFormat.makeAccount(account)) != null) {
					String extern = acctObj.getExternString();
					sb.append(extern.substring(0,4));
					sb.append(";");
					sb.append(extern.substring(5));
					sb.append(";");
				} else {
					sb.append(Strings.leftJustify(" ", 4));
					sb.append(";");
					sb.append(Strings.leftJustify(" ", 12));
					sb.append(";");
				} 

				String cdate = record.getField(CredocRecord.EXPIRY_DATE);
				Date date = dateFormat.parse(cdate);
				if (date != null)
					sb.append(dateOutFormat.format(date));
				else
					sb.append("  .  .    ");
					
				sb.append(";");
				String fx = ExchangeRates.getInstance().getRate(record.getField(CredocRecord.AMOUNT_CURRENCY));
				if (fx != null)
					sb.append(Strings.rightJustify(fx,18));
				else
					sb.append(Strings.rightJustify(" ", 18));
				sb.append(";");

				int t = Integer.parseInt(record.getField(CredocRecord.LC_TYPE));
				if (t == 1) 
					sb.append("importLC;");
				else if (t == 2) 
					sb.append("exportLC;");
				else
					sb.append("        ;");
					
				sb.append("0014");

				writer.println(sb.toString());
				
			}
		}
		writer.close();
		
	} catch (Exception e) {
		System.err.println(e);
	}
}

/**
 * This method was created in VisualAge.
 * @param fileName java.lang.String
 * @param size int
 */
public static void joinCifFile(String rz2file, String rz3file) {
	try {

		Set bkstSet = new HashSet();

		File lbkstFile = new File("lugano_bkst.dat");
		if (lbkstFile.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(lbkstFile));
			String l;
			while((l = br.readLine()) != null) {
				bkstSet.add(l.trim());		
			}
			br.close();
			
		}		
		
		Set tempSet  = new HashSet();
		Set tempSet2 = new HashSet();
			
		BufferedReader br2 = new BufferedReader(new FileReader(rz2file));
		BufferedReader br3 = new BufferedReader(new FileReader(rz3file));

		PrintWriter pwNot = new PrintWriter(new FileWriter("stamm_not_found.dat"));
		PrintWriter pwFound = new PrintWriter(new FileWriter("stamm_cif.dat"));
		PrintWriter pwFound1 = new PrintWriter(new FileWriter("stamm_cif_one.dat"));
			
		String line2, line3, mstamm;
		line2 = br2.readLine();
		line3 = br3.readLine();
			
		while(line2 != null) {
			mstamm = line2.substring(0,7);
						
			while((line2 != null) && (line2.substring(0,7).equals(mstamm))) {
				tempSet.add(line2);
				line2 = br2.readLine();
			}
			
			while((line3 != null) && (line3.substring(0,7).equals(mstamm))) {
				tempSet.add(line3);
				line3 = br3.readLine();
			}

			
			tempSet2.addAll(tempSet);
			Iterator itb = tempSet2.iterator();
			while(itb.hasNext()) {
				String s = (String)itb.next();
				if (!bkstSet.contains(s.substring(8,12))) {
					itb.remove();		
				}
			}
			if (tempSet2.size() > 0) {
				tempSet.clear();
				tempSet.addAll(tempSet2);	
			}
			
			
			boolean blank = true;
			int setSize = tempSet.size();

			Iterator it = tempSet.iterator();
			while(it.hasNext()) {				
				String s = (String)it.next();
				if (s.substring(8,20).equals("000000000000")) {
					if (setSize == 1) {
						pwNot.println(mstamm);
					} 
				} else {
					if (setSize == 1) {
						pwFound1.println(s);
					} else {
						if (blank) {
							pwFound.println();
							blank = false;
						}
						pwFound.println(s);
					}
				}
				
			}			
			
			tempSet.clear();
			tempSet2.clear();
		}

		pwNot.close();
		pwFound.close();
		pwFound1.close();
								
	} catch (Exception e) {
		System.err.println(e);
	}
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main(java.lang.String[] args) {

	long startTime = System.currentTimeMillis();
	
	if (args.length >= 1) {
		if (args[0].equals("testfile")) {
			if (args.length == 3) 
				Util.createBigFile(args[1], Integer.parseInt(args[2]));
			else 
				printHelpText();			
		} else if ("booking".equals(args[0])) {
			if (args.length == 4) 
				Util.createBookings(args[1], args[2], args[3]);
			else
				printHelpText();
		} else if ("check".equals(args[0])) {
			if (args.length == 2) 
				Util.checkNumbers(args[1]);
			else
				printHelpText();
		} else if ("subst".equals(args[0])) {
			if (args.length == 3)
				Util.substNumbers(args[1], args[2]);
			else
				printHelpText();
		} else if ("join".equals(args[0])) {
			if (args.length == 3) {
				Util.joinCifFile(args[1], args[2]);
			}
		} else if ("cif2subst".equals(args[0])) {
			if (args.length == 2) {
				Util.cifFile2substFile(args[1]);
			}
		} else if ("split".equals(args[0])) {
			if (args.length == 4) {
				Util.splitFile(args[1], args[2], args[3]);
			}
		} else {
			printHelpText();
		}
	} else {
		printHelpText();
	}

	System.out.println("Millis : " + (System.currentTimeMillis() - startTime));
}
/**
 * This method was created in VisualAge.
 */
public static void printHelpText() {
	System.out.println("java Util testfile  <input> <mult>");
	System.out.println("          booking   <input> <bookings> <type>");
	System.out.println("          check     <input>");
	System.out.println("          subst     <input> <output>");
	System.out.println("          join      <rz2File> <rz3File>");
	System.out.println("          cif2subst <file>");
	System.out.println("          split     <input> <active> <inactive>");
}
/**
 * This method was created in VisualAge.
 * @param fileName java.lang.String
 */
public static void substNumbers(String fileName, String outFileName) {
	try {
		int fields[] = new int[14];
		
		CredocRecord record;
		CredocRecordStream recordStream = new CredocRecordStream(new BufferedReader(new FileReader(fileName)));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outFileName)));
		recordStream.defineAllFieldsRelevant();
		record = recordStream.read();
		String subst, number;
		
		fields[0] = CredocRecord.A_ACCOUNT_NUMBER;
		fields[1] = CredocRecord.CB_ACCOUNT_NUMBER;
		fields[2] = CredocRecord.NB_ACCOUNT_NUMBER;
		fields[3] = CredocRecord.IB_ACCOUNT_NUMBER;
		fields[4] = CredocRecord.RF_ACCOUNT_NUMBER;
		fields[5] = CredocRecord.B_ACCOUNT_NUMBER;
		fields[6] = CredocRecord.RB_ACCOUNT_NUMBER;
		fields[7] = CredocRecord.A_CIF_NUMBER;
		fields[8] = CredocRecord.CB_CIF_NUMBER;
		fields[9] = CredocRecord.NB_CIF_NUMBER;
		fields[10] = CredocRecord.IB_CIF_NUMBER;
		fields[11] = CredocRecord.RF_CIF_NUMBER;
		fields[12] = CredocRecord.B_CIF_NUMBER;
		fields[13] = CredocRecord.RB_CIF_NUMBER;
		
		while ((record = recordStream.read()) != null) {
			for (int i = 0; i < fields.length; i++) {
				
				number = record.getField(fields[i]);				
				if (number != null) {
					String keyStr = null;
					try {
						long key = Long.parseLong(number.trim());
						keyStr  = String.valueOf(key);
					} catch (NumberFormatException nfe) {
						keyStr = number.trim();
					}
					
					if ((subst = Substitution.getInstance().getSubst(keyStr)) != null)
						record.setField(fields[i], subst);
				}
			}
			pw.write(record.toString());
		}
		pw.close();
	} catch (Exception e) {
		System.err.println(e);
		return;
	}
}
}