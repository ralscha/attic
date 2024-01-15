import java.sql.*;
import java.util.Iterator;

public class WordListTable {

	private Connection conn;
	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT hash,word FROM WordList";
	private final static String orderSQL  = " ORDER BY ";

	private final static String insertSQL = 
		"INSERT INTO WordList(hash,word) VALUES(?,?)";

	private final static String updateSQL = 
		"UPDATE WordList SET word=? WHERE hash=?";

	public WordListTable(Connection conn) {
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

	public int insert(WordList wordlist) throws SQLException {
		if (insertPS == null)
			insertPS = conn.prepareStatement(insertSQL);

		prepareInsertStatement(insertPS, wordlist);
		return insertPS.executeUpdate();
	}

	public int update(WordList wordlist) throws SQLException {
		if (updatePS == null)
			updatePS = conn.prepareStatement(updateSQL);

		prepareUpdateStatement(updatePS, wordlist);
		int rowCount = updatePS.executeUpdate();
		if (rowCount <= 0) {
			return insert(wordlist);
		} else {
			return rowCount;
		}
	}

	public static WordList makeObject(ResultSet rs) throws SQLException {
		return new WordList(rs.getInt(1), rs.getString(2));
	}

	private void prepareInsertStatement(PreparedStatement ps, WordList wordlist) throws SQLException {
		ps.setInt(1, wordlist.getHash());
		ps.setString(2, wordlist.getWord());
	}

	private void prepareUpdateStatement(PreparedStatement ps, WordList wordlist) throws SQLException {
		ps.setString(1, wordlist.getWord());
		ps.setInt(2, wordlist.getHash());
	}
}
