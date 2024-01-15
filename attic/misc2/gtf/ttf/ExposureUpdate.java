package gtf.ttf;

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.*;
import common.util.*;
import common.log.*;

/**
* expsoure updater for ttf
* @author  Ralph Schaer
* @version 1.0
*/

public class ExposureUpdate {
	
	private final COM.stevesoft.pat.Regex exRegex = 
		new COM.stevesoft.pat.Regex("^(\\d{12}).(\\d*)[ ]*.[ ]*([\\d.,]+)([+-]).([A-Z ]{4}).[ ]*([\\d.,]+)([+-]).[ ]*([\\d.,]+)([+-]).(\\d{2}[.]\\d{2}[.]\\d{4}).(\\d{2}[.]\\d{2}[.]\\d{4}).");
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				
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
			new ExposureUpdate().update(serviceCenter);
		else 
			Log.log(serviceCenter + ":ExposureUpdate: service Center " + serviceCenter + " does not exists");		
		
		Log.cleanUp();
	}
		
	
	public void update(String serviceCenter) {
		try {
			
			Connection conn = TtfUtil.connect(serviceCenter);
			
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM GTFLC.GP_EXPOSURE");
			stmt.close();
			
			
			String fileName = AppProperties11.getStringProperty(serviceCenter+".exposure.file");
			File f = new File(fileName);
			
			if (!f.exists()) {
				Log.log(serviceCenter + ":ExposureUpdate: cannot find exposure.file :" + fileName);	
				Log.cleanUp();
				return;
			}
			
			BufferedReader dis;
			String line;		
			int count = 0;
			
			dis = new BufferedReader(new FileReader(f));
			while ((line = dis.readLine()) != null) {
				GP_EXPOSURE ex = createExposureObject(line);
				if (ex != null) {
					count++;
					ex.update(conn);
				}
			}
			
			dis.close();			
			conn.close();
						
			Log.log(serviceCenter + ": ExposureUpdate: update succesful");
			Log.log(serviceCenter + ": ExposureUpdate: "+ count + " rows updated/inserted");
			
		} catch (Exception e) {
			Log.log("ExposureUpdate: " + e.toString());	
			Log.cleanUp();
		}
	}
	
	
	private GP_EXPOSURE createExposureObject(String line) {
		exRegex.search(line);
		if (exRegex.didMatch()) {
		
			GP_EXPOSURE ex = new GP_EXPOSURE();		
			ex.setCIF_NUMBER(exRegex.stringMatched(1));
			ex.setLIMIT_AMOUNT(TtfUtil.makeBigDecimal(exRegex.stringMatched(3), 
											exRegex.stringMatched(4)));
			ex.setCURRENCY_ISO(exRegex.stringMatched(5));
			ex.setUTILIZATION_AMOUNT(TtfUtil.makeBigDecimal(exRegex.stringMatched(6),
											exRegex.stringMatched(7)));
			ex.setCREDIT_RISK(TtfUtil.makeBigDecimal(exRegex.stringMatched(8),
											 exRegex.stringMatched(9)));
			
			ParsePosition pos = new ParsePosition(0);
			java.util.Date fixDate = dateFormat.parse(exRegex.stringMatched(10), pos);						
			ex.setFIX_DATE(new java.sql.Date(fixDate.getTime()));
				
			pos = new ParsePosition(0);
			java.util.Date endDate = dateFormat.parse(exRegex.stringMatched(11), pos);
			ex.setEND_DATE(new java.sql.Date(endDate.getTime()));									
			ex.setPRODUCT_CODE(exRegex.stringMatched(2));
			
			return ex;
		} else {
			return null;
		}
	}	
}