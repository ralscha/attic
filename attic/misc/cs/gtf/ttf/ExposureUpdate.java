package gtf.ttf;

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.*;

import gtf.common.*;
import gtf.db.GP_EXPOSURE;
import gtf.db.GP_EXPOSURETable;

import common.util.*;
import common.util.log.*;

/**
 * expsoure updater for ttf
 * @author  Ralph Schaer
 * @version 1.1
 */
public class ExposureUpdate implements gtf.common.Constants {
	private final COM.stevesoft.pat.Regex exRegex = 
		new COM.stevesoft.pat.Regex("^(\\d{12}).(\\d*)[ ]*.[ ]*([\\d.,]+)([+-]).([A-Z ]{4}).[ ]*([\\d.,]+)([+-]).[ ]*([\\d.,]+)([+-]).(\\d{2}[.]\\d{2}[.]\\d{4}).(\\d{2}[.]\\d{2}[.]\\d{4}).");
	private final static SimpleDateFormat dateFormat = 
		new SimpleDateFormat("dd.MM.yyyy");

	public static void main(java.lang.String[] args) {
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
		Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_TTF_LOG_PATH), true));

		String scString;
		if (args.length == 1) {
			scString = args[0].toUpperCase();
		} else {
			scString = "TEST";
		} 

		ServiceCenter sc = null;
		try {
			sc = new ServiceCenter(scString);
			new ExposureUpdate().update(sc);
		} catch (ServiceCenterNotFoundException scnfe) {
			Log.log("ExposureUpdate: service Center " + sc.getShortName() + " does not exists");							  
		} catch (Exception sqle) {
			Log.log("ExposureUpdate: " + sqle.toString());		
		} finally {
			Log.cleanUp();
		}
	}


	public void update(ServiceCenter sc) throws SQLException, IOException {
		
		GP_EXPOSURETable get = new GP_EXPOSURETable(sc.getConnection());
		get.delete();
		
		String fileName = AppProperties.getStringProperty(sc.getShortName() + P_TTF_EXPOSURE_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + ":ExposureUpdate: no P_TTF_EXPOSURE_FILE property");
			return;
		}

		
		File f = new File(fileName);
		if (!f.exists()) {
			Log.log(sc.getShortName() + ":ExposureUpdate: cannot find exposure.file :" + fileName);
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

				get.insert(ex);
			} 
		} 

		dis.close();
		sc.closeConnection();
		Log.log(sc.getShortName() + ": ExposureUpdate: update succesful");
		Log.log(sc.getShortName() + ": ExposureUpdate: " + count 
				  + " rows updated/inserted");

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
			java.util.Date fixDate = dateFormat.parse(exRegex.stringMatched(10), 
																	pos);

			ex.setFIX_DATE(new java.sql.Date(fixDate.getTime()));

			pos = new ParsePosition(0);

			java.util.Date endDate = dateFormat.parse(exRegex.stringMatched(11), 
																	pos);

			ex.setEND_DATE(new java.sql.Date(endDate.getTime()));
			ex.setPRODUCT_CODE(exRegex.stringMatched(2));

			return ex;
		} else {
			return null;
		} 
	} 

}