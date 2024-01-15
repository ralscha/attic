package gtf.recon;


import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.CURRENCY;
import gtf.db.CURRENCYTable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class CurrencyUpdater implements gtf.common.Constants {

	static {
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
	}

	public static void main(String[] args) {
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
	
			if (args.length == 1) {
				
				ServiceCenters scs = new ServiceCenters();
				java.util.StringTokenizer st = new java.util.StringTokenizer(args[0], ",");
				
				List scList = new ArrayList();	
				
				while(st.hasMoreTokens()) {
					String center = st.nextToken();
					if (scs.exists(center)) {
						ServiceCenter sc = scs.getServiceCenter(center);
						scList.add(sc);
					}
				}
				if (!scList.isEmpty())
					new CurrencyUpdater().start(scList);
				else
					Log.log("currency update: no service centers specified");
			} else
				Log.log("currency update: wrong argument");
		} finally {
			Log.cleanUp();
		}
	}
	
	private void start(List scList) {
		try {
			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					update(sc);
					Log.log(sc.getShortName() + " update currency successful");
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());
				}
			}
		} catch (Exception e) {
			Log.log(e.toString());
		}
	}
	
	private void update(ServiceCenter sc) throws SQLException, IOException {

		String fileName = AppProperties.getStringProperty(P_CURRENCY_FILE);
		if (fileName == null) {
			Log.log(sc.getShortName() + " P_CURRENCY_FILE property not found"); 
			return;
		}
		
		updateCurrency(sc.getConnection(), fileName);
		sc.closeConnection();	
	}

	private void updateCurrency(Connection conn, String fileName) throws FileNotFoundException, IOException, SQLException {

		String line;
		BufferedReader dis = new BufferedReader(new FileReader(fileName));

		CURRENCYTable ct = new CURRENCYTable(conn);
		
		while ((line = dis.readLine()) != null) {
			CURRENCY curr = CurrencyFactory.createObject(line);
			if (curr != null) {
				ct.update(curr);
			}
		}
		dis.close();
	}
}