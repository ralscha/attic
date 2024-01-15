import java.sql.*;
import java.util.Iterator;

public class Word3Table {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT ref,word3,hits FROM Word3";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO Word3(ref,word3,hits) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE Word3 SET hits=? WHERE ref=? AND word3=?";

	public Word3Table(Connection conn) {
		this.conn = conn;
		insertPS = null;
		updatePS = null;
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

	public int insert(Word3 word3) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, word3);
		return insertPS.executeUpdate();
	}

	public int update(Word3 word3) throws SQLException {
		if (updatePS == null)
			updatePS = conn.prepareStatement(updateSQL);

		prepareUpdateStatement(updatePS, word3);
		int rowCount = updatePS.executeUpdate();
		if (rowCount <= 0) {
			return insert(word3);
		} else {
			return rowCount;
		}
	}

	public static Word3 makeObject(ResultSet rs) throws SQLException {
		return new Word3(rs.getInt(1), rs.getInt(2), rs.getInt(3));
	}

	private void prepareInsertStatement(PreparedStatement ps, Word3 word3) throws SQLException {
		ps.setInt(1, word3.getRef());
		ps.setInt(2, word3.getWord3());
		ps.setInt(3, word3.getHits());
	}

	private void prepareUpdateStatement(PreparedStatement ps, Word3 word3) throws SQLException {
		ps.setInt(1, word3.getHits());
		ps.setInt(2, word3.getRef());
		ps.setInt(3, word3.getWord3());
	}
}
