package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.ACCOUNT;
import gtf.db.ACCOUNTTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class AccountExporter implements gtf.common.Constants {
	
	private final static String whereClause = "ACCOUNT_TYPE >= '000' AND ACCOUNT_TYPE <= '999' AND INACTIV <> 'I' AND ACCOUNT_TYPE <> '401'";
	private final static String whereInternalClause = "ACCOUNT_TYPE >= '000' AND ACCOUNT_TYPE <= '999' AND INACTIV <> 'I'";

	private final static String orderClause = "ACCT_MGMT_UNIT ASC, ACCOUNT_NUMBER ASC";

	private boolean export(ServiceCenter sc) throws SQLException, IOException {
		Connection conn = sc.getConnection();

		String fileName = AppProperties.getStringProperty(sc.getShortName() + P_ACCOUNT_IN_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_ACCOUNT_IN_FILE property not found"); 
			return false;
		}

		String cdpath = AppProperties.getStringProperty(P_CD_PATH);
		if (cdpath == null) {
			Log.log(sc.getShortName() + " P_CD_PATH property not found");
			return false;
		}
			
		File tranFile = new File(cdpath + fileName);
		PrintWriter br = new PrintWriter(new BufferedWriter(new FileWriter(tranFile)));
		
		String lu = AppProperties.getStringProperty(P_LU);
		if (lu == null) {
			Log.log("P_LU property not found"); 
			return false;
		}
		br.println(lu);

		String outFileName = AppProperties.getStringProperty(sc.getShortName() + P_ACCOUNT_OUT_FILE);
		if (outFileName == null) {
			Log.log(sc.getShortName() + " P_ACCOUNT_OUT_FILE property not found"); 
			return false;
		}
		
		br.println(outFileName.substring(outFileName.lastIndexOf("/")+1));

		br.println(sc.getShortName());
	
		String updatecmd = AppProperties.getStringProperty(sc.getShortName() + P_ACCOUNT_UPDATE_CMD);
		if (updatecmd == null) {
			Log.log(sc.getShortName() + " P_ACCOUNT_UPDATE_CMD property not found"); 
			return false;
		}
		br.println(updatecmd);
		
		String dsn;
		if (TEST)
			dsn = AppProperties.getStringProperty(P_ACCOUNT_DSN_TEST);
		else
			dsn = AppProperties.getStringProperty(P_ACCOUNT_DSN);
			
		if (dsn == null) {
			Log.log("P_ACCOUNT_DSN property not found"); 
			return false;
		}	
		br.println(dsn);

		readData(conn, br, false);
		readData(conn, br, true);
		br.close();

		return true;
	}

	private void readData(Connection conn, PrintWriter out, boolean internal) throws SQLException {
		
		ACCOUNTTable at = new ACCOUNTTable(conn);
		StringBuffer sb = new StringBuffer();

		Iterator it;
		if (internal)
			it = at.selectInternal(whereInternalClause, orderClause);
		else
			it = at.select(whereClause, orderClause);
		while(it.hasNext()) {
			ACCOUNT obj = (ACCOUNT)it.next();
			
			sb.setLength(0);
			sb.append(Strings.leftJustify(obj.getACCT_MGMT_UNIT().trim(), 4));
			sb.append(Strings.leftJustify(obj.getACCOUNT_NUMBER().trim(), 12));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getINACTIV().trim(), 1));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getSALDIERT().trim(), 1));
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getBUSINESS_UNIT().trim(), 4));
			sb.append(";");
			sb.append(obj.getACCOUNT_TYPE().trim());
			sb.append(";");
			sb.append(Strings.leftJustify(obj.getCURRENCY().trim(), 3));

			out.println(sb.toString());
		}
	}
	
	private boolean start(ServiceCenter sc) {
		try {
			if (export(sc)) {
				sc.closeConnection();
				Log.log(sc.getShortName() + " account export successful");
				return true;
			} else {
				Log.log(sc.getShortName() + " account export not successful");
				return false;
			}
		} catch (Exception sqle) {
			Log.log(sc.getShortName() +  " account export: " + sqle.toString());
			return false;
		} 
	}
	
	public static void main(String[] args) {
		int rc = 1;
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
			
			if (args.length == 1) {
				ServiceCenters scs = new ServiceCenters();
				if (scs.exists(args[0])) {
					ServiceCenter sc = scs.getServiceCenter(args[0]);
					if (new AccountExporter().start(sc))
						rc = 0;
					else
						rc = 1;
				} else 
					Log.log("account export: service center " + args[0] + "does not exists");
			} else
				Log.log("account export: wrong argument");
		} finally {
			Log.cleanUp();
			System.exit(rc);
		}
	}
	
}