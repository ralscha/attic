package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.CRAPA;
import gtf.db.CRAPATable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class CrapaExporter implements gtf.common.Constants {


	public static void main(String[] args) {
		int rc = 1;
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
	
			if (args.length == 1) {
				ServiceCenters scs = new ServiceCenters();
				if (scs.exists(args[0])) {
					ServiceCenter sc = scs.getServiceCenter(args[0]);
					if (new CrapaExporter().start(sc))
						rc = 0;
					else
						rc = 1;
				} else
					Log.log("crapa export: service center " + args[0] + "does not exists");
			} else
				Log.log("crapa export: wrong argument");
		} finally {
			Log.cleanUp();
			System.exit(rc);
		}
	}


	private boolean start(ServiceCenter sc) {
		try {
			if (export(sc)) {
				Log.log(sc.getShortName() + " crapa export successful");
				return true;
			} else {
				Log.log(sc.getShortName() + " crapa export not successful");
				return false;
			}
		} catch (Exception sqle) {
			Log.log(sc.getShortName() +  " crapa export: " + sqle.toString());
			return false;
		} 
	}

	private boolean export(ServiceCenter sc) throws SQLException, IOException {
			
		Connection conn = sc.getConnection();

		String fileName = AppProperties.getStringProperty(sc.getShortName() + P_CRAPA_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_CRAPA_FILE property not found"); 
			return false;
		}

		String cdpath = AppProperties.getStringProperty(P_CD_PATH);
		if (cdpath == null) {
			Log.log(sc.getShortName() + " P_CD_PATH property not found");
			return false;
		}

		String datapath = AppProperties.getStringProperty(sc.getShortName() + P_CRAPA_PATH);
		if (datapath == null) {
			Log.log(sc.getShortName() + " P_CRAPA_PATH property not found");			
			return false;
		}

		File dataFile = new File(datapath + fileName + fileDateFormat.format(new java.util.Date()));
		File tranFile = new File(cdpath + fileName);
		PrintWriter br = new PrintWriter(new FileWriter(dataFile));

		readData(conn, br);
		br.close();

		if (tranFile.exists())
			tranFile.delete();

		FileUtil.copy(dataFile, tranFile);

		if (!TEST)
			updateTable(sc.getShortName(), conn);
		sc.closeConnection();
		
		return true;
	}

	private void readData(Connection conn,
                      	PrintWriter out) throws SQLException, IOException {
                      		
		CRAPATable ct = new CRAPATable(conn);
		StringBuffer sb = new StringBuffer();

		out.println(getTitle1());
		out.println(getTitle2());

		Iterator it = ct.select("uploaded = 'N'", "GTF_NUMBER");
		while(it.hasNext()) {
			CRAPA obj = (CRAPA)it.next();

			//fix                      		
			if ("0835".equals(obj.getGTF_PROC_CENTER())) {
				if ("CAHA".equals(obj.getBU_CODE().trim())) {
					obj.setGTF_COST_CENTER("291");
				} else if ("CAHB".equals(obj.getBU_CODE().trim())) {
					obj.setGTF_COST_CENTER("061");				
				} else if ("CAHK".equals(obj.getBU_CODE().trim())) {
					obj.setGTF_COST_CENTER("154");				
				}
			}

			sb.setLength(0);
			sb.append(Strings.leftJustify(obj.getRECORDID().trim(), 8));
			sb.append(" ");
			sb.append(Strings.rightJustify(obj.getGTF_NUMBER().trim(), 7));
			sb.append("    ");
			sb.append(Strings.leftJustify(obj.getGTF_PROC_CENTER().trim(), 15));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getGTF_COST_CENTER().trim(), 15));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getGTF_TYPE().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getDOSSIER_ITEM().trim(), 20));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getCOMM_TYPE().trim(), 30));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getCUST_CIF_NUMB().trim(), 13));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getCORR_BK_CIF_NUMB().trim(), 16));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getCURRENCY().trim(), 8));
			sb.append(" ");
			sb.append(Strings.rightJustify(obj.getCOMM_AMT().toString(), 17));
			sb.append(" ");
			sb.append(Strings.rightJustify(obj.getCOMM_AMT_DEF_CURR().toString(), 17));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getBU_CODE().trim(), 7));
			sb.append(" ");
			sb.append(Strings.leftJustify(obj.getUPLOADED().trim(), 8));
			sb.append(" ");
			sb.append(Strings.rightJustify(String.valueOf(obj.getDOSSIER_ITEM_KEY()), 16));
			out.println(sb.toString());
		}
	}
	
	private void updateTable(String center, Connection conn) throws SQLException {
		CRAPATable ct = new CRAPATable(conn);
						
		Log.log(center + " Delete Uploaded = 'B' ROWS: " + 
			ct.delete("UPLOADED = 'B'"));
		
		Statement stmt = conn.createStatement();
		Log.log(center + " Update Uploaded = 'B' WHERE Uploaded = 'S' ROWS : " 
			+ stmt.executeUpdate("update gtflc.crapa set uploaded = 'B' where uploaded = 'S'"));		
		
		Log.log(center + " Update Uploaded = 'S' WHERE Uploaded = 'N' ROWS : " +
          + stmt.executeUpdate("update gtflc.crapa set uploaded = 'S' where uploaded = 'N'"));
	}
	
	private String getTitle1() {
		return "RECORDID GTF_NUMBER GTF_PROC_CENTER GTF_COST_CENTER GTF_TYPE             DOSSIER_ITEM         COMM_TYPE                      CUST_CIF_NUMB CORR_BK_CIF_NUMB CURRENCY COMM_AMT          COMM_AMT_DEF_CURR BU_CODE UPLOADED DOSSIER_ITEM_KEY";
	}

	private String getTitle2() {
		return "-------- ---------- --------------- --------------- -------------------- -------------------- ------------------------------ ------------- ---------------- -------- ----------------- ----------------- ------- -------- ----------------";
	}

	
}