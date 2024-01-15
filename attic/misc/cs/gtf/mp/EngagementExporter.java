package gtf.mp;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.text.*;
import ViolinStrings.*;

import gtf.common.*;

import common.util.*;
import common.util.log.*;


public class EngagementExporter implements gtf.common.Constants {


 	private final static DecimalFormat form = new DecimalFormat("###0.000");
	
	public static void main(String[] args) {
		int rc = 1;
		try {
			Log.setLogger(new WeeklyDiskLogger(AppProperties.getStringProperty(P_MP_LOG_PATH), true));
	
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
					if (new EngagementExporter().start(scList))
						rc = 0;
					else
						rc = 1;
				else
					Log.log("engagement exporter: no service centers specified");
			} else
				Log.log("engagement exporter: wrong argument");
		} finally {
			Log.cleanUp();
			System.exit(rc);
		}

	}


	private void readData(Connection conn, PrintWriter pw) throws SQLException{
	
		Statement stmt = null;
		ResultSet rs = null;
			
		try {	
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQLStatement.ENGAGEMENT_SELECT_SQL_STR);
		
			StringBuffer sb = new StringBuffer();
		
			while(rs.next()) {				
				sb.setLength(0);
				
				// SELECT  
		      // b.gtf_number, b.acct_mgmt_unit, b.account_number, a.cif_number,
 		      // b.value_date, b.currency, b.amount, engagement_type 
				
				sb.append(Strings.rightJustify(rs.getString(1).trim(), 7, '0'));
				sb.append(" ");
				sb.append(Strings.leftJustify(rs.getString(2).trim() + rs.getString(3).trim(), 16));
				sb.append(" ");
				sb.append(Strings.leftJustify(rs.getString(4).trim(), 12));								
				sb.append(" ");
				
				sb.append(dateFormat.format(rs.getDate(5)));
											
				sb.append(" ");
				sb.append(Strings.leftJustify(rs.getString(6).trim(), 3));								
				sb.append(" ");
				sb.append(Strings.rightJustify(form.format(rs.getBigDecimal(7,3)), 20));
				sb.append(" ");
				sb.append(rs.getString(8).trim());								
				
				pw.println(sb.toString());
			}
			
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)	
				stmt.close();			
		}
	}

	
	private boolean start(List scList) {
		try {
	
			String fileName = AppProperties.getStringProperty(P_MP_TRAN_FILE);
			if (fileName == null) {
				Log.log("P_MP_TRAN_FILE property not found");
				return false;
			}

			File tranFile = new File(fileName);
			if (tranFile.exists())
				tranFile.delete();

			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(tranFile)));
			
			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					readData(sc.getConnection(), pw);
					sc.closeConnection();		
					Log.log(sc.getShortName() + " export engagment successful");			
				} catch (SQLException sqle) {
					Log.log(sc.getShortName() + sqle.toString());
				}
			}

			pw.close();
			return true;

		} catch (Exception e) {
			Log.log(e.toString());
			return false;
		}
	}



}