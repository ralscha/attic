import java.sql.*;
import java.util.Iterator;

public class Word12Table {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;	
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT word1,word2,word3ref,total FROM Word12";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO Word12(word1,word2,word3ref,total) VALUES(?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE Word12 SET total=? WHERE word1 = ? AND word2 = ? AND word3ref = ?";
		
		
	public Word12Table(Connection conn) {
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

	public int update(Word12 word12) throws SQLException {
		if (updatePS == null)
			updatePS = conn.prepareStatement(updateSQL);

		prepareUpdateStatement(updatePS, word12);
		int rowCount = updatePS.executeUpdate();
		if (rowCount <= 0) {
			return insert(word12);
		} else {
			return rowCount;
		}
	}	
	
	public int insert(Word12 word12) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, word12);
		return insertPS.executeUpdate();
	}

	public static Word12 makeObject(ResultSet rs) throws SQLException {
		return new Word12(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
	}

	private void prepareInsertStatement(PreparedStatement ps, Word12 word12) throws SQLException {
		ps.setInt(1, word12.getWord1());
		ps.setInt(2, word12.getWord2());
		ps.setInt(3, word12.getWord3ref());
		ps.setInt(4, word12.getTotal());
	}
	
	private void prepareUpdateStatement(PreparedStatement ps, Word12 word12) throws SQLException {
		ps.setInt(1, word12.getTotal());
		ps.setInt(2, word12.getWord1());
		ps.setInt(3, word12.getWord2());
		ps.setInt(4, word12.getWord3ref());
	}	
	
}