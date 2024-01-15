package gtf.tools;

import java.io.*;
import java.util.*;
import java.sql.*;
import gtf.common.*;
import gtf.db.LEGAL_ENTITY;
import gtf.db.LEGAL_ENTITYTable;

public class SCCifUpdate {
	private Set cifSet;
	

	private SCCifUpdate() {
		cifSet = new HashSet();
		String line;
		InputStream is = getClass().getResourceAsStream("scaccounts.dat");
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));

			while ((line = buf.readLine()) != null) {
				cifSet.add(line.substring(0, 12));
			} 
		} catch (FileNotFoundException fnfe) {
			System.err.println(fnfe);
		} catch (IOException ioe) {
			System.err.println(ioe);
		} 
	}
	
	private void startUpdate(List scList) {
		try {
			Iterator it = scList.iterator();
			ServiceCenter sc = null;
			while (it.hasNext()) {
				try {
					sc = (ServiceCenter)it.next();
					update(sc);
				} catch (SQLException sqle) {
					System.err.println(sqle);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	private void update(ServiceCenter sc) throws SQLException {

		LEGAL_ENTITYTable let = new LEGAL_ENTITYTable(sc.getConnection());
		Iterator dbit = let.select();
		while(dbit.hasNext()) {
			LEGAL_ENTITY obj = (LEGAL_ENTITY)dbit.next();
			if (cifSet.contains(obj.getCIF_NUMBER())) {
				System.out.println("update : "+obj.getCIF_NUMBER());
				obj.setBUSINESS_UNIT("0011");
				let.updateBU(obj);
			}
		}
		sc.closeConnection();	
	}
	
	public static void main(String args[]) {
		if (args.length == 1) {
				
			ServiceCenters scs = new ServiceCenters();
			StringTokenizer st = new StringTokenizer(args[0], ",");
			
			List scList = new ArrayList();	
			
			while(st.hasMoreTokens()) {
				String center = st.nextToken();
				if (scs.exists(center)) {
					ServiceCenter sc = scs.getServiceCenter(center);
					scList.add(sc);
				}
			}
			if (!scList.isEmpty())
				new SCCifUpdate().startUpdate(scList);
			else
				System.err.println("no service center");
		} else
			System.err.println("SCCifUpdate <serviceCenter, serviceCenter,...>"); 
		
	}
}