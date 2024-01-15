package gtf.recon;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;

import gtf.common.*;
import gtf.db.LEGAL_ENTITY;
import gtf.db.LEGAL_ENTITYTable;
import gtf.db.ADDRESS;
import gtf.db.ADDRESSTable;
import gtf.db.COUNTRY;
import gtf.db.COUNTRYTable;

import common.util.*;
import common.util.log.*;

public class CIFUpdater implements gtf.common.Constants {

	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS");
	private final static String NOT_FOUND = "NOTFOUND";
	private final static String HEADER = "GTC0007";
	private final static String ADDRESS = "GTC0008";

	public static void main(String[] args) {
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
			
			if (args.length == 1) {
				ServiceCenters scs = new ServiceCenters();
				if (scs.exists(args[0])) {
					ServiceCenter sc = scs.getServiceCenter(args[0]);
					new CIFUpdater().start(sc);
				} else 
					Log.log("cif update: service center " + args[0] + "does not exists");
			} else
				Log.log("cif update: wrong argument");	
		} finally {
			Log.cleanUp();
		}

	}	
	
	private void start(ServiceCenter sc) {
		try {
			
			String fileName = AppProperties.getStringProperty(sc.getShortName() + P_CIF_OUT_FILE);
			if (fileName == null) {
				Log.log(sc.getShortName() + " P_CIF_OUT_FILE property not found");
				return;
			}
			
			update(sc.getConnection(), fileName);
			sc.closeConnection();
			Log.log(sc.getShortName() + " cif update successful");

		} catch (Exception sqle) {
			Log.log(sc.getShortName() +  " cif update: " + sqle.toString());
		} 
	}
	
	private String getIsoCountryCode(COUNTRYTable ct, String csCode) throws SQLException {
		Iterator it = ct.select("INT_BANK_NUMBER = '"+csCode+"'");
		if (it.hasNext()) {
			COUNTRY country = (COUNTRY)it.next();
			return country.getISO_CODE_ALPHA();
		} else {
			return null;
		}
	}
	
	
	private void update(Connection conn, String fileName) throws SQLException, FileNotFoundException, IOException {
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
	
		LEGAL_ENTITYTable let = new LEGAL_ENTITYTable(conn);
		ADDRESSTable at = new ADDRESSTable(conn);
		COUNTRYTable ct = new COUNTRYTable(conn);
		
		LEGAL_ENTITY memLe = null;
		
		while ((line = reader.readLine()) != null) {
   			String id = line.substring(0, 8);
   			
   			if (id.trim().equalsIgnoreCase(NOT_FOUND)) {
   				
   				int end = (line.length() < 21) ? line.length() : 21;
   				String cif = line.substring(9, end);
   				
   				
   				if (!cif.trim().equals("")) {
   					LEGAL_ENTITY le = new LEGAL_ENTITY();
   					le.setCIF_NUMBER(cif);
   					let.updateInactiv(le);
   				}   				
   			} else if (id.trim().equalsIgnoreCase(HEADER)) {
   				String cif = line.substring(9,21);
				
				
   				memLe = null;

   				Iterator it = let.select("CIF_NUMBER = '"+cif+"'");
   				if (it.hasNext()) {
   					LEGAL_ENTITY le = (LEGAL_ENTITY)it.next();
   					memLe = le;
   					
   					String lang = line.substring(22,25).trim();

   					if (lang.equalsIgnoreCase("D") || lang.equalsIgnoreCase("G"))
   						le.setLANGUAGE("DE");
   					else if (lang.equalsIgnoreCase("E"))
   						le.setLANGUAGE("EN");
   					else if (lang.equalsIgnoreCase("I"))
   						le.setLANGUAGE("IT");
   					else if (lang.equalsIgnoreCase("F"))
   						le.setLANGUAGE("FR");
   					else
   						le.setLANGUAGE("EN");
   					
					String c = getIsoCountryCode(ct, line.substring(26,29).trim());
					if (c != null) 
						le.setDOMICILE(c);
						
					c = getIsoCountryCode(ct, line.substring(30,33).trim());
					if (c != null) 
						le.setNATIONALITY(c);
					
					le.setCUSTOMER_SEGMENT(line.substring(34, 37).trim());
					le.setINDUSTRY_TYPE(line.substring(38,42).trim());
					le.setCATEGORY(line.substring(45,47).trim());
					le.setCUSTOMER_TYPE(line.substring(48,51).trim());
					le.setCREDIT_RESP(line.substring(63,70).trim());
					le.setCUSTOMER_RESP(line.substring(71,78).trim());
					le.setBRANCH(line.substring(79,83).trim());
					le.setCLEARING_NUMBER(line.substring(84,95).trim());
					le.setGROUP_ID(line.substring(96,108).trim());
					le.setBUSINESS_UNIT(line.substring(109,113).trim());
					le.setUPDATED_BY("CIFRECON");
					
					String ts = line.substring(237, 260);
					ParsePosition pos = new ParsePosition(0);
					java.util.Date updateDate = sdf.parse(ts, pos);
			
					le.setUPDATE_TS(new java.sql.Timestamp(updateDate.getTime()));
					le.setINACTIV("A");
					le.setSIC_PARTICIPANT(line.substring(266,267).trim());
					le.setSEC_PARTICIPANT(line.substring(268,269).trim());
					
					let.update(le);
   				} else {
   					Log.log(fileName + " CIF " + cif +" not found");
   				}
   				
   			} else if (id.trim().equalsIgnoreCase(ADDRESS)) {
   				if (memLe != null) {
   					
   					
   					String instr = line.substring(9, 15);
   					Iterator it = at.select("LE_OID = "+memLe.getOID()+" AND ADDRESS_INSTRCTION = '"+instr+"'");
   					if (it.hasNext()) {
   						ADDRESS addr = (ADDRESS)it.next();
   						
   						
						addr.setLINE1(line.substring(16,51).trim());
						addr.setLINE2(line.substring(52,87).trim());
						addr.setLINE3(line.substring(88,123).trim());
						addr.setLINE4(line.substring(124,159).trim());
						addr.setLINE5(line.substring(160,195).trim());
						
						addr.setPHONE_COUNTRY(line.substring(196,203).trim());
						addr.setPHONE_CITY(line.substring(204,209).trim());
						addr.setPHONE_LOCAL(line.substring(210,225).trim());
						addr.setFAX_COUNTRY(line.substring(226,233).trim());
						addr.setFAX_CITY(line.substring(234,239).trim());
						addr.setFAX_LOCAL(line.substring(240,255).trim());
						addr.setTELEX_COUNTRY(line.substring(256,263).trim());
						addr.setTELEX_LOCAL(line.substring(264,284).trim());
						addr.setTELEX_MV_ADDRESS(line.substring(285,293).trim());
						addr.setSWIFT_BANK_CODE(line.substring(294,298).trim());
						addr.setSWIFT_COUNTRY_CODE(line.substring(299,301).trim());
						addr.setSWIFT_LOCATION(line.substring(302,304).trim());
						addr.setSWIFT_BRANCH_NUM(line.substring(305,310).trim());
						addr.setEDIFACT_ID(line.substring(311,346).trim());

						if (line.length() < 351) 
							addr.setEDIFACT_ID_CODE("");
						else 
							addr.setEDIFACT_ID_CODE(line.substring(347,351).trim());
							
						addr.setUPDATED_BY(memLe.getUPDATED_BY());
						addr.setUPDATE_TS(memLe.getUPDATE_TS());

   						
   						at.update(addr);
   					}
   					
   				} else {
   					//Log.log(fileName + " memCif == null ");
   				}
   				
   			} else {
   				Log.log(id + " undefined ID");
   			}
   					
   					
		
		}
		reader.close();
	}
}