package gtf.swift.input;

import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.text.*;

public class SWIFTHeader {

	private String _TOSN;
	private java.sql.Timestamp _SendDate;
	private String _SenderAddress;
	private String _SessionNumber;
	private String _SequenceNumber;
	private String _ReceiverAddress;
	private String _ProcCenter;
	private String _MessageType;
	private String _Duplicate;
	private String _Priority;
	private java.sql.Timestamp _ReceiveTS;


	private final static String insertSQL =
		"INSERT INTO SWIFTHeader(TOSN,SendDate,SenderAddress,"+ "SessionNumber,SequenceNumber,ReceiverAddress,ProcCenter,MessageType,Duplicate"+
		",Priority,ReceiveTS) "+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
	private static PreparedStatement ps = null;

	public SWIFTHeader() {
		clear();
	}

	public SWIFTHeader(String _TOSN, java.sql.Timestamp _SendDate, String _SenderAddress,
                   	String _SessionNumber, String _SequenceNumber, String _ReceiverAddress,
                   	String _ProcCenter, String _MessageType, String _Duplicate, String _Priority,
                   	java.sql.Timestamp _ReceiveTS) {
		this._TOSN = _TOSN;
		this._SendDate = _SendDate;
		this._SenderAddress = _SenderAddress;
		this._SessionNumber = _SessionNumber;
		this._SequenceNumber = _SequenceNumber;
		this._ReceiverAddress = _ReceiverAddress;
		this._ProcCenter = _ProcCenter;
		this._MessageType = _MessageType;
		this._Duplicate = _Duplicate;
		this._Priority = _Priority;
		this._ReceiveTS = _ReceiveTS;
	}

	public void clear() {
		_TOSN = null;
		_SendDate = null;
		_SenderAddress = null;
		_SessionNumber = null;
		_SequenceNumber = null;
		_ReceiverAddress = null;
		_ProcCenter = null;
		_MessageType = null;
		_Duplicate = null;
		_Priority = null;
		_ReceiveTS = null;
	}
	public String getDuplicate() {
		return _Duplicate;
	}
	public String getMessageType() {
		return _MessageType;
	}

	public String getPriority() {
		return _Priority;
	}

	public String getProcCenter() {
		return _ProcCenter;
	}

	public String getReceiverAddress() {
		return _ReceiverAddress;
	}

	public java.sql.Timestamp getReceiveTS() {
		return _ReceiveTS;
	}

	public java.sql.Timestamp getSendDate() {
		return _SendDate;
	}

	public String getSenderAddress() {
		return _SenderAddress;
	}

	public String getSequenceNumber() {
		return _SequenceNumber;
	}

	public String getSessionNumber() {
		return _SessionNumber;
	}

	public String getTableName() {
		return "SWIFTHeader";
	}

	public String getTOSN() {
		return _TOSN;
	}

	public int insert(Connection con) throws SQLException {

		if (ps == null)
			prepareStatement(con);


		ps.setString(1, getTOSN());
		ps.setTimestamp(2, getSendDate());
		ps.setString(3, getSenderAddress());
		ps.setString(4, getSessionNumber());
		ps.setString(5, getSequenceNumber());
		ps.setString(6, getReceiverAddress());
		ps.setString(7, getProcCenter());
		ps.setString(8, getMessageType());
		ps.setString(9, getDuplicate());
		ps.setString(10, getPriority());
		ps.setTimestamp(11, getReceiveTS());

		try {
			return(ps.executeUpdate());
		} catch (SQLException sqle) {
			if (!sqle.getSQLState().equals("23505")) {
				throw(sqle);
			}
		}
		return 0;

	}
	public static SWIFTHeader makeObject(ResultSet rs) throws SQLException {
		return new SWIFTHeader(rs.getString(1), rs.getTimestamp(2), rs.getString(3),
                       		rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                       		rs.getString(8), rs.getString(9), rs.getString(10), rs.getTimestamp(11));
	}
	private void prepareStatement(Connection con) throws SQLException {
		ps = con.prepareStatement(insertSQL);
	}
	public void setDuplicate(String _Duplicate) {
		this._Duplicate = _Duplicate;
	}
	public void setMessageType(String _MessageType) {
		this._MessageType = _MessageType;
	}
	public void setPriority(String _Priority) {
		this._Priority = _Priority;
	}
	public void setProcCenter(String _ProcCenter) {
		this._ProcCenter = _ProcCenter;
	}
	public void setReceiverAddress(String _ReceiverAddress) {
		this._ReceiverAddress = _ReceiverAddress;
	}
	public void setReceiveTS(java.sql.Timestamp _ReceiveTS) {
		this._ReceiveTS = _ReceiveTS;
	}
	public void setSendDate(java.sql.Timestamp _SendDate) {
		this._SendDate = _SendDate;
	}
	public void setSenderAddress(String _SenderAddress) {
		this._SenderAddress = _SenderAddress;
	}

	public void setSequenceNumber(String _SequenceNumber) {
		this._SequenceNumber = _SequenceNumber;
	}
	public void setSessionNumber(String _SessionNumber) {
		this._SessionNumber = _SessionNumber;
	}
	public void setTOSN(String _TOSN) {
		this._TOSN = _TOSN;
	}

	/**
	   * Return a printable representation of the SWIFTHeader
	   * object.
	   */
	public String toString() {
		return "#(" + _TOSN + " " + _SendDate + " " + _SenderAddress + " " +
       		_SessionNumber + " " + _SequenceNumber + " " + _ReceiverAddress + " " +
       		_ProcCenter + " " + _MessageType + " " + _Duplicate + " " + _Priority +
       		" " + _ReceiveTS + ")";
	}
}