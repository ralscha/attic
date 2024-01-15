package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.EX_PAYMENT;
import gtf.db.EX_PAYMENTTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class CSBS_PaymentExporter implements gtf.common.Constants {

	private List paymentList;

	private void deleteContents(ServiceCenter sc) {
		System.out.println("DELETE");
		try {
			EX_PAYMENTTable ptt = new EX_PAYMENTTable(sc.getConnection());
			Log.log(sc.getShortName() + " delete EX_PAYMENT rows : " + ptt.delete());
		} catch (SQLException sqle) {
			Log.log(sc.getShortName() + sqle.toString());
		}
	}

	private boolean export(ServiceCenter sc) throws SQLException, IOException {
		System.out.println("EXPORT");
		String fileName = AppProperties.getStringProperty(sc.getShortName() + P_PAYMENT_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_PAYMENT_FILE property not found");
			return false;
		}

		String datapath = AppProperties.getStringProperty(sc.getShortName() + P_PAYMENT_PATH);
		if (datapath == null) {
			Log.log(sc.getShortName() + " P_PAYMENT_PATH property not found");
			datapath = "";
		}

		File dataFile =
  		new File(datapath + fileName + fileDateFormat.format(new java.util.Date()));
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
			Log.setLogger(
  			new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));

			if (args.length == 1) {

				ServiceCenters scs = new ServiceCenters();
				java.util.StringTokenizer st = new java.util.StringTokenizer(args[0], ",");

				List scList = new ArrayList();

				while (st.hasMoreTokens()) {
					String center = st.nextToken();
					if (scs.exists(center)) {
						ServiceCenter sc = scs.getServiceCenter(center);
						scList.add(sc);
					}
				}
				if (!scList.isEmpty())
					if (new CSBS_PaymentExporter().start(scList))
						rc = 0;
					else
						rc = 1;
				else
					Log.log("payment exporter: no service centers specified");
			} else
				Log.log("payment exporter: wrong argument");
		}
		finally { 
			Log.cleanUp();
          	System.exit(rc);
       }
	}


	private void readData(Connection conn, PrintWriter out) throws SQLException {
		System.out.println("readData");
		EX_PAYMENTTable ptt = new EX_PAYMENTTable(conn);
		StringBuffer sb = new StringBuffer();

		Iterator it = ptt.select();
		System.out.println(it);
		while (it.hasNext()) {
			EX_PAYMENT obj = (EX_PAYMENT) it.next();
			System.out.println(obj);
			
			sb.setLength(0);

			System.out.println("0");
			System.out.println(obj.getKEY());
			System.out.println("Test ob Denis das Jar kopiert hat: ja");
			
			sb.append(Strings.leftJustify(String.valueOf(obj.getKEY()), 3));
			sb.append(";");
			System.out.println("1");
			if (obj.getGTF_NUMBER() != null)
				sb.append(Strings.rightJustify(obj.getGTF_NUMBER().trim(), 7, '0'));
			else
				sb.append(Strings.rightJustify("", 7, '0'));	
			sb.append(";");
			if (obj.getGTF_TYPE() != null) 
				sb.append(Strings.leftJustify(obj.getGTF_TYPE().trim(), 20));
			else
				sb.append(Strings.leftJustify("", 20));	
				
			sb.append(";");
			sb.append(Strings.leftJustify(String.valueOf(obj.getSEQUENCE_NUMBER()), 3));
			sb.append(";");
			if (obj.getTYPE() != null) 
				sb.append(Strings.leftJustify(obj.getTYPE().trim(), 3));
			else
				sb.append(Strings.leftJustify("", 3));	
			sb.append(";");
			
			if (obj.getTX_AMOUNT() != null) 
				sb.append(Strings.rightJustify(obj.getTX_AMOUNT().toString(), 18));
			else
				sb.append(Strings.rightJustify("", 18));
				
			sb.append(";");
			
			if (obj.getACCT_AMOUNT() != null)
				sb.append(Strings.rightJustify(obj.getACCT_AMOUNT().toString(), 18));
			else
				sb.append(Strings.rightJustify("", 18));		
			sb.append(";");
			if (obj.getACCOUNT_NUMBER() != null) 
				sb.append( Strings.leftJustify(obj.getACCT_MGMT_UNIT() +
   	                            			obj.getACCOUNT_NUMBER().trim(), 24));
			else
				sb.append( Strings.leftJustify("", 24));
					
			sb.append(";");

			if (obj.getVALUE_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getVALUE_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			if (obj.getORDER_ENTRY_DATE() != null)
				sb.append(
  				Strings.leftJustify(dateFormat.format(obj.getORDER_ENTRY_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			if (obj.getORDER_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getORDER_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");

			if (obj.getEXCHANGE_RATE() != null)
				sb.append(Strings.rightJustify(obj.getEXCHANGE_RATE().toString(), 18));
			else
				sb.append(Strings.rightJustify("", 18));
			sb.append(";");
		
			if (obj.getTX_CURRENCY_ISO() != null)	
				sb.append(Strings.leftJustify(obj.getTX_CURRENCY_ISO().trim(), 3));
			else
				sb.append(Strings.leftJustify("", 3));
				
			sb.append(";");
			sb.append(Strings.leftJustify(String.valueOf(obj.getTX_CURRENCY_PROP()), 4));
			sb.append(";");
			
			if (obj.getACCT_CURRENCY() != null)
				sb.append(Strings.leftJustify(obj.getACCT_CURRENCY().trim(), 3));
			else
				sb.append(Strings.leftJustify("", 3));
				
			sb.append(";");
		
			if (obj.getTICKET_NUMBER() != null)	
				sb.append(Strings.leftJustify(obj.getTICKET_NUMBER().trim(), 7));
			else
				sb.append(Strings.leftJustify("", 7));
					
			sb.append(";");
		
			if (obj.getINTERNAL_RATE() != null)	
				sb.append(Strings.rightJustify(obj.getINTERNAL_RATE().toString(), 18));
			else
				sb.append(Strings.rightJustify("", 18));
				
			sb.append(";");
			
			if (obj.getPRIORITY() != null)
				sb.append(Strings.leftJustify(obj.getPRIORITY().trim(), 1));
			else
				sb.append(Strings.leftJustify("", 1));
				
			sb.append(";");
		
			if (obj.getGTF_PROC_CENTER() != null)	
				sb.append(Strings.leftJustify(obj.getGTF_PROC_CENTER().trim(), 4));
			else
				sb.append(Strings.leftJustify("", 4));
				
			sb.append(";");
		
			if (obj.getREFERENCE_NUMBER() != null) 	
				sb.append(Strings.leftJustify(obj.getREFERENCE_NUMBER().trim(), 35));
			else
				sb.append(Strings.leftJustify("", 35));	
				
			sb.append(";");
		
			if (obj.getRELATED_REF_NUMBER() != null) 	
				sb.append(Strings.leftJustify(obj.getRELATED_REF_NUMBER().trim(), 16));
			else
				sb.append(Strings.leftJustify("", 16));
				
			sb.append(";");
			
			if (obj.getANNOUNCE_NUMBER() != null) 
				sb.append(Strings.leftJustify(obj.getANNOUNCE_NUMBER().trim(), 9));
			else
				sb.append(Strings.leftJustify("", 9));
				
			sb.append(";");

			if (obj.getFIXING_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getFIXING_DATE()), 10));
			else
				sb.append("          ");
			sb.append(";");
			
			sb.append(Strings.leftJustify(String.valueOf(obj.getFIXING_NUMBER()), 10));
			sb.append(";");
			if (obj.getBU_CODE() != null)
				sb.append(Strings.leftJustify(obj.getBU_CODE().trim(), 4));
			else
				sb.append(Strings.leftJustify("", 4));	
			sb.append(";");
			if (obj.getBC_NUMBER() != null) 
				sb.append(Strings.leftJustify(obj.getBC_NUMBER().trim(), 11));
			else
				sb.append(Strings.leftJustify("", 11));	
			sb.append(";");
			if (obj.getCIF_NUMBER() != null) 
				sb.append(Strings.leftJustify(obj.getCIF_NUMBER().trim(), 12));
			else
				sb.append(Strings.leftJustify("", 12));		
			sb.append(";");
			if (obj.getPAY() != null)
				sb.append(Strings.leftJustify(obj.getPAY().trim(), 1));
			else
				sb.append(Strings.leftJustify("", 1));	
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_1(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_2(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_3(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_4(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_5(), 35));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getFIELD72_6(), 35));

			System.out.println("2");

			paymentList.add(sb.toString());
			System.out.println("before write");
			System.out.println();
			out.println(sb.toString());
		}
		System.out.println("END");
		
	}
	protected boolean start(List scList) {

		System.out.println("START");
		paymentList = new ArrayList();
		try {

			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
			
				System.out.println("SC gefunden");
				try {
					sc = (ServiceCenter)it.next();
					if (export(sc))
						Log.log(sc.getShortName() + " payment export successful");
					else
						Log.log(sc.getShortName() + " payment export not successful");
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());
				}
			}


			if (!paymentList.isEmpty()) {
				String cdpath = AppProperties.getStringProperty(P_CD_PATH);
				if (cdpath == null) {
					Log.log(sc.getShortName() + " P_CD_PATH property not found");
					return false;
				}

				String fileName = AppProperties.getStringProperty(P_PAYMENT_TRAN_FILE);
				if (fileName == null) {
					Log.log(sc.getShortName() + " P_PAYMENT_TRAN_FILE property not found");
					return false;
				}

				File tranFile = new File(cdpath + fileName);
				if (tranFile.exists())
					tranFile.delete();

				PrintWriter br = new PrintWriter(new BufferedWriter(new FileWriter(tranFile)));
				Iterator its = paymentList.iterator();
				while (its.hasNext()) {
					br.println((String) its.next());
				}
				br.close();
				return true;
			} else {
				//empty
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.log(e.toString());
			return false;
		}
	}
}