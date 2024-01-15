package gtf.tools;

import java.sql.*;
import java.util.*;
import gtf.common.*;

public class DbConnectionChecker {

	private final static String selectSQL = "SELECT * FROM GTFLC.USER";

	public static void main(String args[]) {
		if (args.length == 1) {
			
			List scList = new ArrayList();
			StringTokenizer st = new StringTokenizer(args[0], ",");

			while (st.hasMoreTokens()) {
				try {
					ServiceCenter sc = new ServiceCenter(st.nextToken());
					scList.add(sc);
				} catch (ServiceCenterNotFoundException scnfe) {
					System.err.println(scnfe);
				}
			}
			
			if (!scList.isEmpty()) {
				new DbConnectionChecker().start(scList);
			} 
		} else {
			System.out.println("java DbConnectionChecker SC [,SC,SC,..]");
		} 
	} 

	private void start(List scList) {
		Iterator it = scList.iterator();
		while (it.hasNext()) {
			ServiceCenter sc = (ServiceCenter)it.next();
			check(sc);
		} 
	} 

	private void check(ServiceCenter sc) {
		System.out.print("testing " + sc.getName() + ".... ");

		try {
			Connection conn = sc.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(selectSQL);
			rs.close();
			stmt.close();
			conn.close();
			System.out.println("ok");
		} catch (Exception e) {
			System.err.println(e);
		} 
	} 


}