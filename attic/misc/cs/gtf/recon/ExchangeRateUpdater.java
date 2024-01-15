package gtf.recon;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.db.EXCHANGE_RATE;
import gtf.db.EXCHANGE_RATETable;
import gtf.common.*;

import common.util.*;
import common.util.log.*;

public class ExchangeRateUpdater implements gtf.common.Constants {

	private static final String deleteOldRatesSQLString = "delete from gtflc.exchange_rate where rate_fixing_date < ?";

	private static final int DEL_DAYS = 14;
	private String saveFileName = null;
	private String fileName;

	private final SimpleDateFormat backupDateFormat = new SimpleDateFormat("yyyyMMdd");

	static {
		COM.stevesoft.pat.Key.registeredTo("Gamma.Pleides/-407061011");
	}

	private void deleteOldRates(ServiceCenter sc) {
		try {
			Connection conn = sc.getConnection();
			PreparedStatement ps = conn.prepareStatement(deleteOldRatesSQLString);
			Calendar today = new GregorianCalendar();
			today.add(Calendar.DATE, -DEL_DAYS);

			ps.setDate(1, new java.sql.Date(today.getTime().getTime()));

			Log.log(sc.getShortName() + " delete old exchange rates : "+ ps.executeUpdate());

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			Log.log(sc.getShortName() + sqle.toString());
		}
	}


	public static void main(String[] args) {
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_RECON_LOG_PATH), true));
	
			if (args.length == 1) {
	
				ServiceCenters scs = new ServiceCenters();
				java.util.StringTokenizer st = new java.util.StringTokenizer(args[0], ",");
	
				List scList = new ArrayList();
	
				while (st.hasMoreTokens()) {
					String center = st.nextToken();
					if (scs.exists(center)) {
						ServiceCenter sc = scs.getServiceCenter(center);
						scList.add(sc);
					}
				}
				if (!scList.isEmpty())
					new ExchangeRateUpdater().start(scList);
				else
					Log.log("exchange_rate update: no service centers specified");
			} else
				Log.log("exchange_rate update: wrong argument");
		} finally {
			Log.cleanUp();
		}

	}

	private void update(ServiceCenter sc) throws SQLException, IOException{
		deleteOldRates(sc);
		updateFX(sc.getConnection(), fileName);
		sc.closeConnection();
	}

	private void makeBackup() throws IOException {
		String backupPath = AppProperties.getStringProperty(P_EX_BACKUP_PATH);
		if (backupPath == null)
			backupPath = "";

		if (saveFileName != null) {
			File backupFile = new File(backupPath + saveFileName);
			if (!backupFile.exists()) {
				FileUtil.copy(fileName, backupPath + saveFileName);
			}
		}
	}

	private void start(List scList) {
		try {
			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			
			fileName = AppProperties.getStringProperty(P_EX_FILE);
			if (fileName == null) {
				Log.log("P_EX_FILE property not found");
				return;
			}

			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					update(sc);
					Log.log(sc.getShortName() + " update exchange_rate successful");
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());
				}
			}

			makeBackup();

		} catch (Exception e) {
			e.printStackTrace();
			Log.log(e.toString());
		}
	}

	private void updateFX(Connection conn, String fileName) throws SQLException,FileNotFoundException, IOException {
		BufferedReader dis;
		String line;

		EXCHANGE_RATETable ert = new EXCHANGE_RATETable(conn);

		dis = new BufferedReader(new FileReader(fileName));
		while ((line = dis.readLine()) != null) {
			EXCHANGE_RATE fx = ExchangeRateFactory.createObject(line);
			if (fx != null) {
				if (saveFileName == null) {
					saveFileName = "FX" + backupDateFormat.format(fx.getRATE_FIXING_DATE()) +
               					"_" + fx.getRATE_FIXING_NUMBER().trim() + ".CSV";
				}
				try {
					ert.insert(fx);
				} catch (SQLException sqle) {
					Log.log(sqle.toString());
				}
			}
		}
		dis.close();
	}
}