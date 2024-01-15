package gtf.ttf;

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.*;
import common.util.*;
import common.log.*;
  
/**
* collateral updater for ttf
* @author  Ralph Schaer, CIAL31
* @version 1.0
*/

public class CollateralUpdate {
	
	private final COM.stevesoft.pat.Regex exRegex = 
		new COM.stevesoft.pat.Regex("^(\\d{12}).(\\d{3}).([A-Z]{3}).[0]*([\\d.,]+)([+-]).[0]*([\\d.,]+)([+-])");
	
			
	public static void main(java.lang.String[] args) {				
		
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
		Log.setLogger(new WeeklyDiskLogger(AppProperties11.getStringProperty("log.path")));	
		
		String serviceCenter;
		if (args.length == 1) 
			serviceCenter = args[0].toUpperCase();
		else 
			serviceCenter = "TEST";
		
		String serviceCenters = AppProperties11.getStringProperty("service.centers");
		if (serviceCenters.indexOf(serviceCenter) != -1)		
			new CollateralUpdate().update(serviceCenter);
		else 
			Log.log(serviceCenter + ":CollateralUpdate: service Center " + serviceCenter + " does not exists");		
		
		Log.cleanUp();	
	}
		
	public void update(String serviceCenter) {
		try {
		
			Connection conn = TtfUtil.connect(serviceCenter);
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM GTFLC.GP_COLLATERAL");
			stmt.close();
			
			String fileName = AppProperties11.getStringProperty(serviceCenter+".collateral.file");
			File f = new File(fileName);
			
			if (!f.exists()) {
				Log.log(serviceCenter + ":CollateralUpdate: cannot find collateral.file " + fileName);	
				return;
			}
			
			String line;		
			int count = 0;
			
			BufferedReader dis = new BufferedReader(new FileReader(f));
			while ((line = dis.readLine()) != null) {
				GP_COLLATERAL ex = createCollateralObject(line);
				if (ex != null) {
					count++;
					ex.update(conn);
				}
			}
			dis.close();
			conn.close();
			
			Log.log(serviceCenter + ":CollateralUpdate: update succesful");
			Log.log(serviceCenter + ":CollateralUpdate: "+ count + " rows updated/inserted");
			
		} catch (Exception e) {			
			Log.log("CollateralUpdate: " + e.toString());	
			Log.cleanUp();
		}
	}
		
	private GP_COLLATERAL createCollateralObject(String line) {
		exRegex.search(line);
		if (exRegex.didMatch()) {
		
			GP_COLLATERAL coll = new GP_COLLATERAL();		
							
			coll.setCIF_NUMBER(exRegex.stringMatched(1));
			coll.setCATEGORY(exRegex.stringMatched(2));
			coll.setDEBIT_AMOUNT(TtfUtil.makeBigDecimal(exRegex.stringMatched(4),
											  exRegex.stringMatched(5)));
			coll.setCURRENCY_ISO(exRegex.stringMatched(3));
			coll.setCREDIT_AMOUNT(TtfUtil.makeBigDecimal(exRegex.stringMatched(6),
											  exRegex.stringMatched(7)));			
			return coll;
		} else {
			return null;
		}
	}	
}