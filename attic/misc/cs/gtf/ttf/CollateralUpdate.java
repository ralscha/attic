package gtf.ttf;

import java.sql.*;
import java.text.*;
import java.io.*;
import java.util.*;

import gtf.db.GP_COLLATERAL;
import gtf.db.GP_COLLATERALTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

/**
 * collateral updater for ttf
 * @author  Ralph Schaer, CIAL31
 * @version 1.0
 */
public class CollateralUpdate implements gtf.common.Constants {
	private final COM.stevesoft.pat.Regex exRegex = 
		new COM.stevesoft.pat.Regex("^(\\d{12}).(\\d{3}).([A-Z]{3}).[0]*([\\d.,]+)([+-]).[0]*([\\d.,]+)([+-])");

	public static void main(String[] args) {
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
			new CollateralUpdate().update(sc);
		} catch (ServiceCenterNotFoundException scnfe) {
			Log.log("CollateralUpdate: service Center " + sc.getShortName() + " does not exists");
		} finally {
			Log.cleanUp();
		}
	} 


	public void update(ServiceCenter sc) {
		try {
			GP_COLLATERALTable gct = new GP_COLLATERALTable(sc.getConnection());
			gct.delete();
			
			String fileName = AppProperties.getStringProperty(sc.getShortName() + P_TTF_COLLATERAL_FILE);
			if (fileName == null) {
				Log.log(sc.getShortName() + ":CollateralUpdate: no P_TTF_COLLATERAL_FILE property");
				return;
			}
			
			File f = new File(fileName);
			if (!f.exists()) {
				Log.log(sc.getShortName() + ":CollateralUpdate: cannot find collateral.file " + fileName);
				return;
			} 

			String line;
			int count = 0;
			BufferedReader dis = new BufferedReader(new FileReader(f));

			while ((line = dis.readLine()) != null) {
				GP_COLLATERAL ex = createCollateralObject(line);

				if (ex != null) {
					count++;
					gct.insert(ex);
				} 
			} 

			dis.close();
			sc.closeConnection();
			Log.log(sc.getShortName() + ":CollateralUpdate: update successful");
			Log.log(sc.getShortName() + ":CollateralUpdate: " + count + " rows updated/inserted");
		} catch (Exception e) {
			Log.log("CollateralUpdate: " + e.toString());
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