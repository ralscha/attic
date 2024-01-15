import java.sql.*;
import java.util.Iterator;

public class Test {

	public static void main(String args[]) {
		
		if (args.length == 4) {
			new Test(args[0], args[1], args[2], args[3]);
		}
	}

	
	private Test(String dbDriver, String dbURL, String user, String password) {
		try {
			Class.forName(dbDriver);
			Connection conn = DriverManager.getConnection(dbURL, user, password);
			System.out.println("connected");
			
			CRAPATable ct = new CRAPATable(conn);

			Iterator it = ct.select("crapaCode = 320");
			while(it.hasNext()) {
				System.out.println(it.next());
			}
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}