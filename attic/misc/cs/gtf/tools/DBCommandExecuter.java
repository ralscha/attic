package gtf.tools;

import java.io.*;
import java.util.*;
import java.text.*;
import java.sql.*;

import gtf.common.*;

/**
 * DBCommandExecuter
 *
 * @author  Ralph Schaer
 * @version 1.0 02.07.1999
 */
public class DBCommandExecuter {

	/**
	 * Reads the commands from the file and executes the sql statements
	 * @param fileName Input File with the commands. First line specifies the service center
	 */
	private void execute(String fileName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();
			Connection conn = null;
			if (line != null) {
				ServiceCenter sc = new ServiceCenter(line.trim());
				conn = sc.getConnection();
			} else {
				System.err.println("file empty");
				return;
			}

			if (conn != null) {
				Statement stmt = conn.createStatement();

				while ((line = br.readLine()) != null) {
					int r = stmt.executeUpdate(line.trim());

					if (r != 1) {
						System.out.println(r + " | " + line.trim());
					}
				}
				conn.commit();
				br.close();
				stmt.close();
				conn.close();
			} else {
				System.out.println("Wrong ServiceCenter : " + line);
			}
		} catch (ServiceCenterNotFoundException scnf) {
			System.err.println(scnf);
		} catch (Exception e) {
			System.err.println(e);
		}
	}


	public static void main(String[] args) {
		if (args.length == 1) {
			new DBCommandExecuter().execute(args[0]);
		} else {
			System.out.println("DBCommandExecuter <fileName>");
		}
	}

}