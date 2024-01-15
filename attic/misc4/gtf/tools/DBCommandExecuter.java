package gtf.tools;

import java.io.*;
import java.util.*;
import common.util.*;
import java.text.*;
import java.sql.*;

public class DBCommandExecuter {
	
	
	private Connection connect(String center) throws ClassNotFoundException, SQLException {
		String dbDriver = AppProperties.getStringProperty("db.driver").trim();
		Class.forName(dbDriver);
			
		String user = AppProperties.getStringProperty(center+".db.user").trim();
		if (user == null) return null;
		
		String pw = AppProperties.getStringProperty(center+".db.password").trim();
		String dbURL = AppProperties.getStringProperty(center+".db.url").trim();		
		
		return(DriverManager.getConnection(dbURL, user, pw));
	}

	private void execute(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			
			Connection conn = connect(line.trim());
			if (conn != null) {
					Statement stmt = conn.createStatement();
					
					while((line = br.readLine()) != null) {
						int r = stmt.executeUpdate(line.trim());
						if (r != 1) 
							System.out.println(r + " | " + line.trim());
						conn.commit();
					}
					br.close();
					stmt.close();
					conn.close();				
			} else {
				System.out.println("Wrong ServiceCenter : " + line);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
		

	public static void main(String[] args) {
		if (args.length == 1) 
			new DBCommandExecuter().execute(args[0]);
		else
			System.out.println("java DBCommandExecuter <fileName>");
	}

}

    		
    		