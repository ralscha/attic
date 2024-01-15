package gtf.swift.input;

import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class MessageLine {

	private String _TOSN;
	private short _Lineno;
	private String _FieldTag;
	private String _MsgLine;

	private final static String insertSQL = "INSERT INTO MessageLine(TOSN,Lineno,FieldTag,MsgLine) VALUES(?,?,?,?)";
	private static PreparedStatement ps = null;

	public MessageLine() {
		clear();
	}

	public MessageLine(String _TOSN) {
		this._TOSN = _TOSN;
	}

	public MessageLine(String _TOSN, short _Lineno, String _FieldTag, String _MsgLine) {
		this._TOSN = _TOSN;
		this._Lineno = _Lineno;
		this._FieldTag = _FieldTag;
		this._MsgLine = _MsgLine;
	}

	public void clear() {
		_TOSN = null;
		_Lineno = 0;
		_FieldTag = null;
		_MsgLine = null;
	}

	public String getFieldTag() {
		return _FieldTag;
	}
	public short getLineno() {
		return _Lineno;
	}
	public String getMsgLine() {
		return _MsgLine;
	}
	public String getTableName() {
		return "MessageLine";
	}
	public String getTOSN() {
		return _TOSN;
	}
	public int insert(Connection con) throws SQLException {

		if (ps == null)
			prepareStatement(con);

		ps.setString(1, getTOSN());
		ps.setInt(2, getLineno());
		ps.setString(3, getFieldTag());
		ps.setString(4, getMsgLine());

		try {
			return(ps.executeUpdate());
		} catch (SQLException sqle) {
			if (!sqle.getSQLState().equals("23503") && !sqle.getSQLState().equals("23505")) {
				throw(sqle);
			}
		}
		return 0;

	}

	/**
	  * Make a new MessageLine object.
	  * @exception SQLException When a SQL Exception occurred.
	  */
	public static MessageLine makeObject(ResultSet rs) throws SQLException {
		return new MessageLine(rs.getString(1), rs.getShort(2), rs.getString(3),
                       		rs.getString(4));
	}

	private void prepareStatement(Connection con) throws SQLException {
		ps = con.prepareStatement(insertSQL);
	}

	public void setFieldTag(String _FieldTag) {
		this._FieldTag = _FieldTag;
	}

	public void setLineno(short _Lineno) {
		this._Lineno = _Lineno;
	}

	public void setMsgLine(String _MsgLine) {
		this._MsgLine = _MsgLine;
	}

	public void setTOSN(String _TOSN) {
		this._TOSN = _TOSN;
	}

	public String toString() {
		return "#(" + _TOSN + " " + _Lineno + " " + _FieldTag + " " + _MsgLine + ")";
	}
}