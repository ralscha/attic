package gtf.crapa;

import java.sql.*;
import java.util.*;
import common.util.AppProperties;

public class CrapaDbManager implements gtf.common.Constants {

	private final static String dropTableSQL = "DROP TABLE GTFLC.CRAPA";
	private final static String createTableSQL = "CREATE TABLE GTFLC.CRAPA (year INTEGER, month INTEGER, cif CHAR(12), branch CHAR(4), "+
					"kst CHAR(3), legalEntity CHAR(4), crapaCode INTEGER, amount DECIMAL(15,3))";
	private final static String createIxSQL = "CREATE INDEX CRAPA_IX ON GTFLC.CRAPA (branch asc, year asc, month asc)";

	private final static String insertSQL = "INSERT INTO GTFLC.CRAPA VALUES(?,?,?,?,?,?,?,?)";
	private final static String selectSQL = "SELECT * FROM GTFLC.CRAPA WHERE branch = ? AND year = ? AND month = ? "
													+ "ORDER BY branch, kst, legalEntity, crapaCode";
	
	private Connection con;
	private static CrapaDbManager instance = new CrapaDbManager();
	private PreparedStatement prep = null;
	
	private CrapaDbManager() { 
		try {
			Class.forName(AppProperties.getStringProperty(P_CRAPA_DB_DRIVER));
			if (DEBUG)
				java.sql.DriverManager.setLogStream(System.out);

		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	public static void createCrapaTable() throws SQLException {
		openDb();
		Statement stmt = instance.con.createStatement ();
		try {
			stmt.executeUpdate(dropTableSQL);
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
		
		stmt.executeUpdate(createTableSQL);
		stmt.executeUpdate(createIxSQL);
		closeDb();
	}

	static ResultSet selectCrapaItems(String branch, int year, int month) throws SQLException {
		if (instance.prep != null)
			instance.prep.close();
			
		instance.prep = instance.con.prepareStatement(selectSQL);
		instance.prep.setString(1, branch);
		instance.prep.setInt(2, year);
		instance.prep.setInt(3, month);
		
		return instance.prep.executeQuery();
	}

	static void insertCrapaItem(CrapaItem item) throws SQLException {
		if (instance.prep == null) {
			if (instance.con != null)
				instance.prep = instance.con.prepareStatement(insertSQL);
			else {
				System.err.println("con == null");
				return;
			}
		} 
		
		instance.prep.setInt(1, item.getYear());
		instance.prep.setInt(2, item.getMonth());
		instance.prep.setString(3, item.getCif());
		instance.prep.setString(4, item.getBranch());
		instance.prep.setString(5, item.getKst());
		instance.prep.setString(6, item.getLegalEntity());
		instance.prep.setInt(7, item.getCrapaCode());
		instance.prep.setDouble(8, item.getAmount().doubleValue());
		
		instance.prep.execute();
	} 

	public static void execSQLs(String[] sqls) throws SQLException {
		Statement stmt = instance.con.createStatement ();

		for (int i=0; i < sqls.length; i++) {
			stmt.execute(sqls[i]);
			ResultSet rs = stmt.getResultSet();
			if (rs != null) {
				dispResultSet(rs);
				rs.close();
			} 
		}
		stmt.close(); 
	} 

	public static void openDb() throws SQLException {		
		String url = AppProperties.getStringProperty(P_CRAPA_DB_URL);
		String user = AppProperties.getStringProperty(P_CRAPA_DB_USER);
		String pw = AppProperties.getStringProperty(P_CRAPA_DB_PASSWORD);
		instance.con = DriverManager.getConnection(url, user, pw);
	} 

	public static Connection getConnection() {
		return instance.con;
	}

	public static void closeDb() throws SQLException {

		if (instance.prep != null)	
			instance.prep.close();

		if (instance.con != null)  {
			instance.con.close();
			instance.con = null;
		}

	} 

	private static void dispResultSet (ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData ();
		int numCols = rsmd.getColumnCount ();

		for (int i=1; i<=numCols; i++) {
			if (i > 1) System.out.print(",");
			System.out.print(rsmd.getColumnLabel(i));
		}
		System.out.println("");
		
		while (rs.next ()) {
			for (int i=1; i<=numCols; i++) {
				if (i > 1) System.out.print(",");
				System.out.print(rs.getString(i));
			}
			System.out.println("");
		}
	}

} 