package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.LEGAL_ENTITY;
import gtf.db.LEGAL_ENTITYTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class CIFExporter implements gtf.common.Constants {
	
	private final static String whereClause = "CIF_NUMBER <> '' AND INACTIV <> 'I'";
	private final static String orderClause = "CIF_NUMBER";

	private boolean export(ServiceCenter sc) throws SQLException, IOException {
		Connection conn = sc.getConnection();

		String fileName = AppProperties.getStringProperty(sc.getShortName() + P_CIF_IN_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_CIF_IN_FILE property not found"); 
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
		
		br.println(dateFormat.format(new java.util.Date()));

		String outFileName = AppProperties.getStringProperty(sc.getShortName() + P_CIF_OUT_FILE);
		if (outFileName == null) {
			Log.log(sc.getShortName() + " P_CIF_OUT_FILE property not found"); 
			return false;
		}
		br.println(outFileName.substring(outFileName.lastIndexOf("/")+1));

		br.println(sc.getShortName());

		readData(conn, br);
		br.close();

		return true;
	}

	private void readData(Connection conn, PrintWriter out) throws SQLException {
		LEGAL_ENTITYTable at = new LEGAL_ENTITYTable(conn);
		Iterator it = at.select(whereClause, orderClause);
		while(it.hasNext()) {
			LEGAL_ENTITY obj = (LEGAL_ENTITY)it.next();
			out.println(obj.getCIF_NUMBER());
		}
	}
	
	private boolean start(ServiceCenter sc) {
		try {
			if (export(sc)) {
				sc.closeConnection();
				Log.log(sc.getShortName() + " cif export successful");
				return true;
			} else {
				Log.log(sc.getShortName() + " cif export not successful");
				return false;
			}
		} catch (Exception sqle) {
			Log.log(sc.getShortName() +  " cif export: " + sqle.toString());
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
					if (new CIFExporter().start(sc))
						rc = 0;
					else
						rc = 1;
				} else 
					Log.log("cif export: service center " + args[0] + "does not exists");
			} else
				Log.log("cif export: wrong argument");
		} finally {
			Log.cleanUp();
			System.exit(rc);
		}
	}
	
}