package gtf.ttf;

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.*;
import common.util.*;
import common.log.*;

public class TraderExport {
	
	private final static String exportSQLString = "SELECT CIF_NUMBER FROM GTFLC.LEGAL_ENTITY " +
 																   "WHERE OID IN (SELECT LE_OID FROM GTFLC.TRADER)";
	
	public static void main(java.lang.String[] args) {			
		Log.setLogger(new WeeklyDiskLogger(AppProperties11.getStringProperty("log.path")));	
		
		String serviceCenter;
		if (args.length == 1) 
			serviceCenter = args[0].toUpperCase();
		else 
			serviceCenter = "TEST";
		
		String serviceCenters = AppProperties11.getStringProperty("service.centers");
		if (serviceCenters.indexOf(serviceCenter) != -1)		
			new TraderExport().export(serviceCenter);
		else 
			Log.log("TraderExport: service Center " + serviceCenter + " does not exists");		
	}
		
	public void export(String serviceCenter) {		
		try {		
			Connection conn = TtfUtil.connect(serviceCenter);
			
			String fileName = AppProperties11.getStringProperty(serviceCenter+".tradercif.file");

			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(exportSQLString);
			PrintWriter pw = new PrintWriter(new FileWriter(fileName));			
			
			while(rs.next()) {
				pw.println(rs.getString(1));
			}
			
			pw.close();
			rs.close();
			stmt.close();
			conn.close();
			
			String cdbatch = AppProperties11.getStringProperty(serviceCenter+".tradercif.transfer.batch");			
			String bifName = AppProperties11.getStringProperty(serviceCenter+".tradercif.host.file");
			
			if ((cdbatch != null) && (!cdbatch.trim().equals(""))) {
				Runtime runtime = Runtime.getRuntime();
				System.out.println("cmd /c " + cdbatch + " " + fileName + " " + bifName);
				Process proc = runtime.exec("cmd /c " + cdbatch + " " + fileName + " " + bifName);	
				logOutput(proc.getInputStream());				
				proc.waitFor();
			}
			
		} catch (Exception e) {			
			e.printStackTrace();
			Log.log("TraderExport: " + e.toString());	
			Log.cleanUp();
		}
	}
	
	private void logOutput(InputStream is) throws IOException {
		String line;
		BufferedReader dis = new BufferedReader(new InputStreamReader(is));
		while ((line = dis.readLine()) != null) {
			System.out.println(line);
			Log.log(line);
		}
	}
	
}