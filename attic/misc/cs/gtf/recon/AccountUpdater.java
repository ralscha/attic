package gtf.recon;

import java.io.*;
import java.sql.*;
import java.util.*;

import gtf.common.*;
import gtf.db.ACCOUNT;
import gtf.db.ACCOUNTTable;

import common.util.*;
import common.util.log.*;

public class AccountUpdater implements gtf.common.Constants {

	public static void main(String[] args) {
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
			
			if (args.length == 1) {
				ServiceCenters scs = new ServiceCenters();
				if (scs.exists(args[0])) {
					ServiceCenter sc = scs.getServiceCenter(args[0]);
					new AccountUpdater().start(sc);
				} else 
					Log.log("account update: service center " + args[0] + "does not exists");
			} else
				Log.log("account update: wrong argument");	
		} finally {
			Log.cleanUp();
		}

	}	
	
	private void start(ServiceCenter sc) {
		try {
			
			String fileName = AppProperties.getStringProperty(sc.getShortName() + P_ACCOUNT_OUT_FILE);
			if (fileName == null) {
				Log.log(sc.getShortName() + " P_ACCOUNT_OUT_FILE property not found");
				return;
			}
			
			update(sc.getConnection(), fileName);
			sc.closeConnection();
			Log.log(sc.getShortName() + " account update successful");

		} catch (Exception sqle) {
			Log.log(sc.getShortName() +  " account update: " + sqle.toString());
		} 
	}
	
	private void update(Connection conn, String fileName) throws SQLException, FileNotFoundException, IOException {
		String line;
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
	
		ACCOUNTTable at = new ACCOUNTTable(conn);
		
		while ((line = reader.readLine()) != null) {
			if (line.length() >= 25) {
				ACCOUNT acct = new ACCOUNT();
				acct.setACCT_MGMT_UNIT(line.substring(0, 4));
				acct.setACCOUNT_NUMBER(line.substring(4, 16));
				acct.setINACTIV(line.substring(17, 18));
				acct.setSALDIERT(line.substring(19, 20));
				acct.setBUSINESS_UNIT(line.substring(21, 25));
				acct.setACCOUNT_TYPE(line.substring(26,29));

				if (line.length() >= 33) 
					acct.setCURRENCY(line.substring(30,33));
				else {
					System.out.println(line);
					acct.setCURRENCY("");
				}

				Calendar today = Calendar.getInstance();
				today.add(Calendar.YEAR, +11);
				today.set(Calendar.DAY_OF_MONTH, 31);
				today.set(Calendar.MONTH, Calendar.DECEMBER);
				today.set(Calendar.MINUTE, 59);
				today.set(Calendar.SECOND, 59);
				today.set(Calendar.HOUR_OF_DAY, 23);
				java.sql.Timestamp ts = new java.sql.Timestamp(today.getTime().getTime());
				acct.setCACHE_EXPIRES_TS(ts);
				
				at.update(acct);
			}
		}
		reader.close();
	}
}