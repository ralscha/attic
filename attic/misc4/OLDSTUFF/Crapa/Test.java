
import java.sql.*;
import java.util.*;
import common.util.AppProperties;

public class Test {

	private final static String dropTableSQL = "DROP TABLE T1";
	private final static String createTableSQL = "CREATE TABLE T1 (year INTEGER, month INTEGER, cif CHAR(12), branch CHAR(4), "+
					"kst CHAR(3), legalEntity CHAR(4), crapaCode INTEGER, amount DOUBLE)";

	private Connection conn;
	
	private Test() { 
		try {
			String driver = AppProperties.getStringProperty("crapa.db.driver");
			Class.forName(driver);

			String url = AppProperties.getStringProperty("crapa.db.url");
			String user = AppProperties.getStringProperty("crapa.db.user");
			String pw = AppProperties.getStringProperty("crapa.db.pw");
			conn = DriverManager.getConnection(url, user, pw);


		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	private void create() {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(dropTableSQL);
			stmt.executeUpdate(createTableSQL);
			stmt.close();
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}

	public static void main(String args[]) {
		new Test().create();
		new Test().go();
	}

	private void go() {
		try {
			DatabaseMetaData dmb = conn.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs = dmb.getTables (null,null,"%",types);
			while(rs.next()) {
				System.out.println(rs.getString("TABLE_NAME"));
			}
			
			rs = dmb.getColumns(null,null,"T1","%");
			while(rs.next()) {
				String schema = rs.getString("TABLE_SCHEM");
				String tableName = rs.getString("TABLE_NAME");
				String columnName = rs.getString("COLUMN_NAME");	
				short type = rs.getShort("DATA_TYPE");
				
				int column_size = rs.getInt("COLUMN_SIZE");	
			 	
			 	System.out.println(schema + ", " + tableName + ", " + columnName + ", " + types(type) + ", " + column_size);
		
			}
		} catch (SQLException sqle) {
			System.err.println(sqle);
		} finally {
			try {
				conn.close();
			} catch(SQLException sqle) {}
		}
	}
	
	private String types(short type) {
		switch(type) {
			case Types.BIT : return("BIT"); 
			case Types.TINYINT : return("TINYINT"); 
			case Types.SMALLINT : return("SMALLINT"); 
			case Types.INTEGER : return("INTEGER"); 
			case Types.BIGINT : return("BIGINT"); 

			case Types.FLOAT : return("FLOAT"); 
			case Types.REAL : return("REAL"); 
			case Types.DOUBLE : return("DOUBLE"); 

			case Types.NUMERIC : return("NUMERIC"); 
			case Types.DECIMAL : return("DECIMAL"); 

			case Types.CHAR : return("CHAR"); 
			case Types.VARCHAR : return("VARCHAR"); 
			case Types.LONGVARCHAR : return("LONGVARCHAR"); 

			case Types.DATE : return("DATE"); 
			case Types.TIME : return("TIME"); 
			case Types.TIMESTAMP : return("TIMESTAMP"); 

			case Types.BINARY : return("BINARY"); 
			case Types.VARBINARY : return("VARBINARY"); 
			case Types.LONGVARBINARY : return("LONGVARBINARY"); 
			default: return(String.valueOf(type));
			
			
			case -1: 
        case 1: // '\001'
        case 12: // '\f'
            return "String";

        case -4: 
        case -3: 
        case -2: 
            return "byte[]";

        case 2: // '\002'
        case 3: // '\003'
            return "java.math.BigDecimal";

        case -7: 
            return "boolean";

        case -6: 
            return "byte";

        case 5: // '\005'
            return "short";

        case 4: // '\004'
            return "int";

        case -5: 
            return "long";

        case 7: // '\007'
            return "float";

        case 6: // '\006'
        case 8: // '\b'
            return "double";

        case 91: // '['
            return "java.sql.Date";

        case 92: // '\\'
            return "java.sql.Time";

        case 93: // ']'
            return "java.sql.Timestamp";

        case 1111: 
            return "Object";

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