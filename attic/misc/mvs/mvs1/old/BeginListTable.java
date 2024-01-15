import java.sql.*;
import java.util.Iterator;

public class BeginListTable {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT word1,word2,total FROM BeginList";
	private final static String orderSQL  = " ORDER BY ";

	private final static String totalSQL = 
		"SELECT SUM(total) FROM BeginList";
	
	private final static String insertSQL = 
		"INSERT INTO BeginList(word1,word2,total) VALUES(?,?,?)";

	private final static String updateSQL = 
		"UPDATE BeginList SET total=? WHERE word1=? AND word2=?";

	public BeginListTable(Connection conn) {
		this.conn = conn;
		insertPS = null;
		updatePS = null;
	}

	public int getTotal() throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(totalSQL);
		if (rs.next()) {
			int total = rs.getInt(1);
			rs.close();
			stmt.close();			
			return total;
		} else {
			return 0;
		}

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

	public int insert(BeginList beginlist) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, beginlist);
		return insertPS.executeUpdate();
	}

	public int update(BeginList beginlist) throws SQLException {
		if (updatePS == null)
			updatePS = conn.prepareStatement(updateSQL);

		prepareUpdateStatement(updatePS, beginlist);
		int rowCount = updatePS.executeUpdate();
		if (rowCount <= 0) {
			return insert(beginlist);
		} else {
			return rowCount;
		}
	}

	public static BeginList makeObject(ResultSet rs) throws SQLException {
		return new BeginList(rs.getInt(1), rs.getInt(2), rs.getInt(3));
	}

	private void prepareInsertStatement(PreparedStatement ps, BeginList beginlist) throws SQLException {
		ps.setInt(1, beginlist.getWord1());
		ps.setInt(2, beginlist.getWord2());
		ps.setInt(3, beginlist.getTotal());
	}

	private void prepareUpdateStatement(PreparedStatement ps, BeginList beginlist) throws SQLException {
		ps.setInt(1, beginlist.getTotal());
		ps.setInt(2, beginlist.getWord1());
		ps.setInt(3, beginlist.getWord2());
	}
}
