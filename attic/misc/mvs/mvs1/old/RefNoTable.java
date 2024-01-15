import java.sql.*;
import java.util.Iterator;

public class RefNoTable {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT refno FROM RefNo";
	private final static String orderSQL  = " ORDER BY ";
	private final static String updateSQL = "UPDATE RefNo SET refno = ?";

	
	private final static String insertSQL = 
		"INSERT INTO RefNo(refno) VALUES(?)";

	public RefNoTable(Connection conn) {
		this.conn = conn;
		insertPS = null;
	}

	public Iterator select() throws SQLException {
		return select(null, null);
	}

	public Iterator select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

	public Iterator select(String whereClause, String orderClause) throws SQLException {
		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery(sb.toString());

		return new Iterator() {
			public boolean hasNext() {
				try {
					return rs.next();
				} catch (SQLException sqle) {
					System.err.println(sqle);
					return false;
				}
			}
			public Object next() {
				try {
					return makeObject(rs);

				} catch (SQLException sqle) {
					System.err.println(sqle);
					return null;
				}
			} 
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}

	public int update(RefNo refno) throws SQLException {
		if (updatePS == null)
			updatePS = conn.prepareStatement(updateSQL);

		prepareUpdateStatement(updatePS, refno);
		return updatePS.executeUpdate();
	}
	
	
	public int insert(RefNo refno) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, refno);
		return insertPS.executeUpdate();
	}
	
	public static RefNo makeObject(ResultSet rs) throws SQLException {
		return new RefNo(rs.getInt(1));
	}

	private void prepareInsertStatement(PreparedStatement ps, RefNo refno) throws SQLException {
		ps.setInt(1, refno.getRefno());
	}
	
	private void prepareUpdateStatement(PreparedStatement ps, RefNo refno) throws SQLException {
		ps.setInt(1, refno.getRefno());
	}	
	
}
