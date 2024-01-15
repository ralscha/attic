package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.SEND_SWIFT;
import gtf.db.SEND_SWIFTTable;
import gtf.db.SEND_TELEX;
import gtf.db.SEND_TELEXTable;

import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class CSBS_SWIFTTelexExporter implements gtf.common.Constants {

	private List stList;
	
	private void deleteContents(ServiceCenter sc) {
		try {
			SEND_SWIFTTable sst = new SEND_SWIFTTable(sc.getConnection());
			Log.log(sc.getShortName() + " delete SEND_SWIFT rows : " + sst.delete());
			
			SEND_TELEXTable stt = new SEND_TELEXTable(sc.getConnection());
			Log.log(sc.getShortName() + " delete SEND_TELEX rows : " + stt.delete());
			
		} catch (SQLException sqle) {
			Log.log(sc.getShortName() + sqle.toString());
		}
	}
	
	private boolean export(ServiceCenter sc) throws SQLException, IOException {
	
		String fileName = AppProperties.getStringProperty(sc.getShortName() + P_SWIFTTELEX_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_SWIFTTELEX_FILE property not found");
			return false;
		}	
			
		String datapath = AppProperties.getStringProperty(sc.getShortName() + P_SWIFTTELEX_PATH);
		if (datapath == null) {
			Log.log(sc.getShortName() + " P_SWIFTTELEX_PATH property not found");
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
					if (new CSBS_SWIFTTelexExporter().start(scList))
						rc = 0;
					else
						rc = 1;
				else
					Log.log("swift/telex exporter: no service centers specified");
			} else
				Log.log("swift/telex exporter: wrong argument");
		}
		finally { 
			Log.cleanUp();
			System.exit(rc);
		}
	}

	
	
	private void readData(Connection conn, PrintWriter out) throws SQLException {
		
		StringBuffer sb = new StringBuffer();
		SEND_SWIFTTable sst = new SEND_SWIFTTable(conn);
		Iterator it = sst.select(null, "ORDER_NUMBER");
		
		while (it.hasNext()) {
			SEND_SWIFT obj = (SEND_SWIFT)it.next();
			
			sb.setLength(0);

			sb.append("S;");
			sb.append(Strings.leftJustify(obj.getMESSAGE_TYPE(), 3));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getREF_NUMBER(), 16));
			sb.append(";");
			sb.append(Strings.leftJustify("DC"+obj.getORDER_NUMBER(), 37));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getADDRESS_SENDER(), 11));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getPROC_CENTER(), 4));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getORG_UNIT(), 7));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getADDRESS_RECEIVER(), 24));
			sb.append(";");

			if (obj.getCREATE_TIME() != null)
				sb.append(Strings.leftJustify(timeFormat.format(obj.getCREATE_TIME()), 4));
			else
				sb.append("    ");

			sb.append(";");

			if (obj.getCREATE_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(obj.getCREATE_DATE()), 10));
			else
				sb.append("          ");


			stList.add(sb.toString());
			out.println(sb.toString());
		}


		SEND_TELEXTable stt = new SEND_TELEXTable(conn);
		it = stt.select(null, "ORDER_NUMBER");
		
		while (it.hasNext()) {
			SEND_TELEX objT = (SEND_TELEX)it.next();

			sb.setLength(0);

			sb.append("T;");
			sb.append(Strings.leftJustify(objT.getMESSAGE_TYPE(), 3));
			sb.append(";");
			sb.append(Strings.leftJustify(objT.getREF_NUMBER(), 16));
			sb.append(";");
			sb.append(Strings.leftJustify("DC"+objT.getORDER_NUMBER(), 37));
			sb.append(";");
			sb.append(Strings.leftJustify(objT.getADDRESS_SENDER(), 11));
			sb.append(";");
			sb.append(Strings.leftJustify(objT.getPROC_CENTER(), 4));
			sb.append(";");
			sb.append(Strings.leftJustify(objT.getORG_UNIT(), 7));
			sb.append(";");
			sb.append(Strings.leftJustify(objT.getADDRESS_RECEIVER(), 24));
			sb.append(";");

			if (objT.getCREATE_TIME() != null)
				sb.append(Strings.leftJustify(timeFormat.format(objT.getCREATE_TIME()), 4));
			else
				sb.append("    ");

			sb.append(";");

			if (objT.getCREATE_DATE() != null)
				sb.append(Strings.leftJustify(dateFormat.format(objT.getCREATE_DATE()), 10));
			else
				sb.append("          ");


			stList.add(sb.toString());
			out.println(sb.toString());
		}
	}
	
	
	protected boolean start(List scList) {
		stList = new ArrayList();
		try {

			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					if (export(sc))
						Log.log(sc.getShortName() + " swift/telex export successful");
					else
						Log.log(sc.getShortName() + " swift/telex export not successful");
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());				
				}
			}

			if (!stList.isEmpty()) {
				String cdpath = AppProperties.getStringProperty(P_CD_PATH);
				if (cdpath == null) {
					Log.log(sc.getShortName() + " P_CD_PATH property not found");
					return false;
				}

				String fileName = AppProperties.getStringProperty(P_SWIFTTELEX_TRAN_FILE);
				if (fileName == null) {
					Log.log(sc.getShortName() + " P_SWIFTTELEX_TRAN_FILE property not found");
					return false;
				}

				File tranFile = new File(cdpath + fileName);
				if (tranFile.exists())
					tranFile.delete();

				PrintWriter br = new PrintWriter(new BufferedWriter(new FileWriter(tranFile)));
				Iterator its = stList.iterator();
				while (its.hasNext()) {
					br.println((String) its.next());
				}
				br.close();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			Log.log(e.toString());
			return false;
		}
	}
}