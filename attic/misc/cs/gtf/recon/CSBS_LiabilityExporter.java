package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.BOOK_CONT_LIAB;
import gtf.db.BOOK_FIRM_LIAB;
import gtf.db.BOOK_CONT_LIABTable;
import gtf.db.BOOK_FIRM_LIABTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;


public class CSBS_LiabilityExporter implements gtf.common.Constants {

	private List firmList, contList;

	private void deleteContents(ServiceCenter sc) {
		try {
			BOOK_CONT_LIABTable cltt = new BOOK_CONT_LIABTable(sc.getConnection());
			BOOK_FIRM_LIABTable fltt = new BOOK_FIRM_LIABTable(sc.getConnection());
			Log.log(sc.getShortName() + " delete BOOK_CONT_LIAB rows : " + cltt.delete());
			Log.log(sc.getShortName() + " delete BOOK_FIRM_LIAB rows : " + fltt.delete());
		} catch (SQLException sqle) {
			Log.log(sc.getShortName() + sqle.toString());
		}
	}
	
	private boolean export(ServiceCenter sc) throws SQLException, IOException {
		String fileName = AppProperties.getStringProperty(sc.getShortName()+P_LIAB_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_LIAB_FILE property not found");
			return false;
		}

		String datapath = AppProperties.getStringProperty(sc.getShortName()+P_LIAB_PATH);
		if (datapath == null) {
			Log.log(sc.getShortName() + " P_LIAB_PATH property not found");
			datapath = "";
		}

		File dataFile = new File(datapath + fileName + fileDateFormat.format(new java.util.Date()));
		PrintWriter br = new PrintWriter(new FileWriter(dataFile));
		readData(sc.getConnection(), br);
		br.close();

		if (!TEST)
			deleteContents(sc);
		
		sc.closeConnection();
		return true;
	}

	public static void main(String[] args) {
		int rc = 1;
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
	
			if (args.length == 1) {
				
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
				if (!scList.isEmpty())
					if (new CSBS_LiabilityExporter().start(scList))
						rc = 0;
					else
						rc = 1;
				else
					Log.log("liability exporter: no service centers specified");
			} else
				Log.log("liability exporter: wrong argument");
		} finally {
			Log.cleanUp();
			System.exit(rc);
		}

	}


	private void readData(Connection conn,PrintWriter out) throws SQLException, IOException {
			
		BOOK_CONT_LIABTable cltt = new BOOK_CONT_LIABTable(conn);
		BOOK_FIRM_LIABTable fltt = new BOOK_FIRM_LIABTable(conn);

		StringBuffer sb = new StringBuffer();
		out.println(getContTitle1());
		out.println(getContTitle2());

		Iterator it = cltt.select(null, "GTF_NUMBER");
		while(it.hasNext()) {
			BOOK_CONT_LIAB contObj = (BOOK_CONT_LIAB)it.next();
			
			sb.setLength(0);
			sb.append(Strings.rightJustify(String.valueOf(contObj.getSEQUENCE_NUMBER()), 15));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getTYPE().trim(), 4));
			sb.append(" ");
			sb.append(Strings.rightJustify(contObj.getGTF_NUMBER().trim(), 7));
			sb.append("    ");
			sb.append(Strings.leftJustify(contObj.getCURRENCY().trim(), 8));
			sb.append(" ");
			sb.append(Strings.rightJustify(contObj.getAMOUNT().toString(), 17));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getACCT_MGMT_UNIT().trim(), 14));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getACCOUNT_NUMBER().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(dateFormat.format(contObj.getEXPIRY_DATE()), 11));
			sb.append(" ");
			sb.append(Strings.rightJustify(contObj.getEXCHANGE_RATE().toString(), 17));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getGTF_PROC_CENTER().trim(), 15));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getCUSTOMER_REF().trim(), 35));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getGTF_TYPE().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getDOSSIER_ITEM().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(contObj.getBU_CODE().trim(), 7));
			String tmp = sb.toString();
			contList.add(tmp);
			out.println(tmp);
		}
		out.println(" record");

		out.println(getFirmTitle1());
		out.println(getFirmTitle2());

		it = fltt.select(null, "GTF_NUMBER");
		while(it.hasNext()) {
			BOOK_FIRM_LIAB firmObj = (BOOK_FIRM_LIAB)it.next();

			sb.setLength(0);
			sb.append(Strings.rightJustify(String.valueOf(firmObj.getSEQUENCE_NUMBER()), 15));
			sb.append(" ");
			sb.append(Strings.rightJustify(firmObj.getGTF_NUMBER().trim(), 7));
			sb.append("    ");
			sb.append(Strings.leftJustify(firmObj.getCURRENCY().trim(), 8));
			sb.append(" ");
			sb.append(Strings.rightJustify(firmObj.getAMOUNT().toString(), 17));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getDEB_ACCT_MGMT_UNIT().trim(), 18));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getDEB_ACCOUNT_NUMBER().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getCRE_ACCT_MGMT_UNIT().trim(), 18));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getCRE_ACCOUNT_NUMBER().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(dateFormat.format(firmObj.getVALUE_DATE()), 10));
			sb.append(" ");
			sb.append(Strings.rightJustify(firmObj.getEXCHANGE_RATE().toString(), 17));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getGTF_PROC_CENTER().trim(), 15));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getCUSTOMER_REF().trim(), 35));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getGTF_TYPE().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getDOSSIER_ITEM().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(firmObj.getBU_CODE().trim(), 7));
			String tmp = sb.toString();
			firmList.add(tmp);
			out.println(tmp);
		}
		out.println(" record");
	}

	
	private boolean start(List scList) {
		try {

			contList = new ArrayList();
			firmList = new ArrayList();

			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					if (export(sc))
						Log.log(sc.getShortName() + " liability export successful");
					else
						Log.log(sc.getShortName() + " liability export not successful");
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());
				}
			}

			if (!contList.isEmpty() || !firmList.isEmpty()) {
				
				String cdpath = AppProperties.getStringProperty(P_CD_PATH);
				if (cdpath == null) {
					Log.log(sc.getShortName() + " P_CD_PATH property not found");
					return false;
				}
		
				String fileName = AppProperties.getStringProperty(P_LIAB_TRAN_FILE);
				if (fileName == null) {
					Log.log(sc.getShortName() + " P_LIAB_TRAN_FILE property not found");
					return false;
				}

				File tranFile = new File(cdpath + fileName);
				if (tranFile.exists())
					tranFile.delete();

				PrintWriter br = new PrintWriter(new BufferedWriter(new FileWriter(tranFile)));
				br.println(getContTitle1());
				br.println(getContTitle2());
				Iterator its = contList.iterator();
				while (its.hasNext()) {
					br.println((String) its.next());
				}
				br.println(" record");

				br.println(getFirmTitle1());
				br.println(getFirmTitle2());
				its = firmList.iterator();
				while (its.hasNext()) {
					br.println((String) its.next());
				}
				br.println(" record");
				br.close();
				return true;

			} else {
				// empty 
				return false;
			}
		} catch (Exception e) {
			Log.log(e.toString());
			return false;
		}
	}


	String getContTitle1() {
		return ("SEQUENCE_NUMBER TYPE GTF_NUMBER CURRENCY AMOUNT            ACCT_MGMT_UNIT ACCOUNT_NUMBER       EXPIRY_DATE EXCHANGE_RATE     GTF_PROC_CENTER CUSTOMER_REF                        GTF_TYPE             DOSSIER_ITEM         BU_CODE");
	}
	String getContTitle2() {
		return ("--------------- ---- ---------- -------- ----------------- -------------- -------------------- ----------- ----------------- --------------- ----------------------------------- -------------------- -------------------- -------");
	}
	String getFirmTitle1() {
		return ("SEQUENCE_NUMBER GTF_NUMBER CURRENCY AMOUNT            DEB_ACCT_MGMT_UNIT DEB_ACCOUNT_NUMBER   CRE_ACCT_MGMT_UNIT CRE_ACCOUNT_NUMBER   VALUE_DATE EXCHANGE_RATE     GTF_PROC_CENTER CUSTOMER_REF                        GTF_TYPE             DOSSIER_ITEM         BU_CODE");
	}
	String getFirmTitle2() {
		return ("--------------- ---------- -------- ----------------- ------------------ -------------------- ------------------ -------------------- ---------- ----------------- --------------- ----------------------------------- -------------------- -------------------- -------");
	}


}