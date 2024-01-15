package gtf.ttf;

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.*;

import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class TraderExport implements gtf.common.Constants {
	private final static String exportSQLString = 
		"SELECT CIF_NUMBER FROM GTFLC.LEGAL_ENTITY " 
		+ "WHERE OID IN (SELECT LE_OID FROM GTFLC.TRADER)";

	public static void main(java.lang.String[] args) {
		Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_TTF_LOG_PATH), true));
		
		String scString;
		if (args.length == 1) {
			scString = args[0].toUpperCase();
		} else {
			scString = "TEST";
		} 
		
		boolean ok = false;
		ServiceCenter sc = null;
		try {
			sc = new ServiceCenter(scString);
			ok = new TraderExport().export(sc);
		} catch (ServiceCenterNotFoundException scnfe) {
			Log.log("TraderExport: service Center " + sc.getShortName()
					  + " does not exists");
		} finally {
			Log.cleanUp();
		}
		
		if (ok)
			System.exit(0);
		else
			System.exit(1);

	} 

	public boolean export(ServiceCenter sc) {
		try {
			Connection conn = sc.getConnection();
			String fileName = AppProperties.getStringProperty(sc.getShortName() + P_TTF_TRADER_CIF);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(exportSQLString);
			PrintWriter pw = new PrintWriter(new FileWriter(fileName));

			while (rs.next()) {
				pw.println(rs.getString(1));
			} 

			pw.close();
			rs.close();
			stmt.close();
			conn.close();

			return true;
		} catch (Exception e) {
			Log.log("TraderExport: " + e.toString());
			return false;
		} 
	} 


}