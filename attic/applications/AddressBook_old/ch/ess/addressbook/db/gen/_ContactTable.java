package ch.ess.addressbook.db.gen;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import ch.ess.addressbook.db.support.*;
import ch.ess.addressbook.db.*;

public class _ContactTable {

	private PreparedStatement insertPS;
	private PreparedStatement updatePS;
	private PreparedStatement deletePS;
	private final static String deleteSQL = "DELETE FROM contact";
	private final static String whereSQL  = " WHERE ";
	private final static String selectSQL = "SELECT id,firstname,name,company,address,plz,city,country,email,mobil,businessphone,privatephone,key1,value1,key2,value2,key3,value3 FROM contact";
	private final static String countSQL = "SELECT count(*) FROM contact";
	private final static String orderSQL  = " ORDER BY ";

	private final static IDKeyGenerator idGen = new TableIDKeyGenerator();

	private final static String insertSQL = 
		"INSERT INTO contact(id,firstname,name,company,address,plz,city,country,email,mobil,businessphone,privatephone,key1,value1,key2,value2,key3,value3) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private final static String updateSQL = 
		"UPDATE contact SET firstname=?, name=?, company=?, address=?, plz=?, city=?, country=?, email=?, mobil=?, businessphone=?, privatephone=?, key1=?, value1=?, key2=?, value2=?, key3=?, value3=? WHERE id=?";

	private final static String deleteSQL2 = 
		deleteSQL + " WHERE id=?";

	public _ContactTable() {
		insertPS = null;
		updatePS = null;
		deletePS = null;
	}

	public int delete(Contact contact) throws SQLException {
		Connection conn = null;
		try {
			if (deletePS == null) {
				conn = ConnectionPool.getConnection();
				deletePS = conn.prepareStatement(deleteSQL2);
			}

			synchronized (deletePS) {
				prepareDeleteStatement(deletePS, contact);
				return deletePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				ConnectionPool.freeConnection(conn);
		}
	}

	public int delete() throws SQLException {
		return (delete((String)null));
	}

	public int delete(String whereClause) throws SQLException {
		Connection conn = null;
		try {
			conn = ConnectionPool.getConnection();
			Statement stmt = conn.createStatement();

			if (whereClause == null)
				return (stmt.executeUpdate(deleteSQL));
			else
				return (stmt.executeUpdate(deleteSQL+whereSQL+whereClause));
		} finally {
			ConnectionPool.freeConnection(conn);
		}
	}

	public int count() throws SQLException {
		return count(null);
	}

	public int count(String whereClause) throws SQLException {
		StringBuffer sb = new StringBuffer(countSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);

		Connection conn = ConnectionPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			int count = -1;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			stmt.close();

			return count;

		} finally {
			ConnectionPool.freeConnection(conn);
		}
	}

	public Contact selectOne(String whereClause) throws SQLException {
		List resultList = select(whereClause, null);
		if (resultList.size() > 0) {
			return (Contact)resultList.get(0);
		} else {
			return null;
		}
	}

	public List select() throws SQLException {
		return select(null, null);
	}

	public List select(String whereClause) throws SQLException {
		return select(whereClause, null);
	}

	public List select(String whereClause, String orderClause) throws SQLException {
		StringBuffer sb = new StringBuffer(selectSQL);

		if (whereClause != null)
			sb.append(whereSQL).append(whereClause);
		if (orderClause != null)
			sb.append(orderSQL).append(orderClause);

		Connection conn = ConnectionPool.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			List resultList = new ArrayList();

			while(rs.next()) {
				resultList.add(makeObject(rs));
			}
			rs.close();
			stmt.close();

			return resultList;

		} finally {
			ConnectionPool.freeConnection(conn);
		}
	}

	public int insert(Contact contact) throws SQLException {
		Connection conn = null; 
		try {
			if (insertPS == null) {
				conn = ConnectionPool.getConnection();
				insertPS = conn.prepareStatement(insertSQL);
			}

			contact.setId(idGen.generate("contact", contact));

			synchronized (insertPS) {
				prepareInsertStatement(insertPS, contact);
				return insertPS.executeUpdate();
			}
		} finally {
			if (conn != null)
				ConnectionPool.freeConnection(conn);
		}
	}

	public int update(Contact contact) throws SQLException {
		Connection conn = null;
		try {
		if (updatePS == null) {
				conn = ConnectionPool.getConnection();
				updatePS = conn.prepareStatement(updateSQL);
			}

			synchronized (updatePS) {
				prepareUpdateStatement(updatePS, contact);
				return updatePS.executeUpdate();
			}
		} finally {
			if (conn != null)
				ConnectionPool.freeConnection(conn);
		}
	}

	public static Contact makeObject(ResultSet rs) throws SQLException {
		return new Contact(rs.getInt("id"), rs.getString("firstname"), rs.getString("name"), rs.getString("company"), rs.getString("address"), rs.getString("plz"), rs.getString("city"), rs.getString("country"), rs.getString("email"), rs.getString("mobil"), rs.getString("businessphone"), rs.getString("privatephone"), rs.getString("key1"), rs.getString("value1"), rs.getString("key2"), rs.getString("value2"), rs.getString("key3"), rs.getString("value3"));
	}

	private void prepareInsertStatement(PreparedStatement ps, Contact contact) throws SQLException {
		ps.setInt(1, contact.getId());

		if (contact.getFirstname() != null)
			ps.setString(2, contact.getFirstname());
		else
			ps.setNull(2, java.sql.Types.VARCHAR);


		if (contact.getName() != null)
			ps.setString(3, contact.getName());
		else
			ps.setNull(3, java.sql.Types.VARCHAR);


		if (contact.getCompany() != null)
			ps.setString(4, contact.getCompany());
		else
			ps.setNull(4, java.sql.Types.VARCHAR);


		if (contact.getAddress() != null)
			ps.setString(5, contact.getAddress());
		else
			ps.setNull(5, java.sql.Types.VARCHAR);


		if (contact.getPlz() != null)
			ps.setString(6, contact.getPlz());
		else
			ps.setNull(6, java.sql.Types.VARCHAR);


		if (contact.getCity() != null)
			ps.setString(7, contact.getCity());
		else
			ps.setNull(7, java.sql.Types.VARCHAR);


		if (contact.getCountry() != null)
			ps.setString(8, contact.getCountry());
		else
			ps.setNull(8, java.sql.Types.VARCHAR);


		if (contact.getEmail() != null)
			ps.setString(9, contact.getEmail());
		else
			ps.setNull(9, java.sql.Types.VARCHAR);


		if (contact.getMobil() != null)
			ps.setString(10, contact.getMobil());
		else
			ps.setNull(10, java.sql.Types.VARCHAR);


		if (contact.getBusinessphone() != null)
			ps.setString(11, contact.getBusinessphone());
		else
			ps.setNull(11, java.sql.Types.VARCHAR);


		if (contact.getPrivatephone() != null)
			ps.setString(12, contact.getPrivatephone());
		else
			ps.setNull(12, java.sql.Types.VARCHAR);


		if (contact.getKey1() != null)
			ps.setString(13, contact.getKey1());
		else
			ps.setNull(13, java.sql.Types.VARCHAR);


		if (contact.getValue1() != null)
			ps.setString(14, contact.getValue1());
		else
			ps.setNull(14, java.sql.Types.VARCHAR);


		if (contact.getKey2() != null)
			ps.setString(15, contact.getKey2());
		else
			ps.setNull(15, java.sql.Types.VARCHAR);


		if (contact.getValue2() != null)
			ps.setString(16, contact.getValue2());
		else
			ps.setNull(16, java.sql.Types.VARCHAR);


		if (contact.getKey3() != null)
			ps.setString(17, contact.getKey3());
		else
			ps.setNull(17, java.sql.Types.VARCHAR);


		if (contact.getValue3() != null)
			ps.setString(18, contact.getValue3());
		else
			ps.setNull(18, java.sql.Types.VARCHAR);

	}

	private void prepareUpdateStatement(PreparedStatement ps, Contact contact) throws SQLException {

		if (contact.getFirstname() != null)
			ps.setString(1, contact.getFirstname());
		else
			ps.setNull(1, java.sql.Types.VARCHAR);


		if (contact.getName() != null)
			ps.setString(2, contact.getName());
		else
			ps.setNull(2, java.sql.Types.VARCHAR);


		if (contact.getCompany() != null)
			ps.setString(3, contact.getCompany());
		else
			ps.setNull(3, java.sql.Types.VARCHAR);


		if (contact.getAddress() != null)
			ps.setString(4, contact.getAddress());
		else
			ps.setNull(4, java.sql.Types.VARCHAR);


		if (contact.getPlz() != null)
			ps.setString(5, contact.getPlz());
		else
			ps.setNull(5, java.sql.Types.VARCHAR);


		if (contact.getCity() != null)
			ps.setString(6, contact.getCity());
		else
			ps.setNull(6, java.sql.Types.VARCHAR);


		if (contact.getCountry() != null)
			ps.setString(7, contact.getCountry());
		else
			ps.setNull(7, java.sql.Types.VARCHAR);


		if (contact.getEmail() != null)
			ps.setString(8, contact.getEmail());
		else
			ps.setNull(8, java.sql.Types.VARCHAR);


		if (contact.getMobil() != null)
			ps.setString(9, contact.getMobil());
		else
			ps.setNull(9, java.sql.Types.VARCHAR);


		if (contact.getBusinessphone() != null)
			ps.setString(10, contact.getBusinessphone());
		else
			ps.setNull(10, java.sql.Types.VARCHAR);


		if (contact.getPrivatephone() != null)
			ps.setString(11, contact.getPrivatephone());
		else
			ps.setNull(11, java.sql.Types.VARCHAR);


		if (contact.getKey1() != null)
			ps.setString(12, contact.getKey1());
		else
			ps.setNull(12, java.sql.Types.VARCHAR);


		if (contact.getValue1() != null)
			ps.setString(13, contact.getValue1());
		else
			ps.setNull(13, java.sql.Types.VARCHAR);


		if (contact.getKey2() != null)
			ps.setString(14, contact.getKey2());
		else
			ps.setNull(14, java.sql.Types.VARCHAR);


		if (contact.getValue2() != null)
			ps.setString(15, contact.getValue2());
		else
			ps.setNull(15, java.sql.Types.VARCHAR);


		if (contact.getKey3() != null)
			ps.setString(16, contact.getKey3());
		else
			ps.setNull(16, java.sql.Types.VARCHAR);


		if (contact.getValue3() != null)
			ps.setString(17, contact.getValue3());
		else
			ps.setNull(17, java.sql.Types.VARCHAR);

		ps.setInt(18, contact.getId());
	}

	private void prepareDeleteStatement(PreparedStatement ps, Contact contact) throws SQLException {
		ps.setInt(1, contact.getId());
	}

}
