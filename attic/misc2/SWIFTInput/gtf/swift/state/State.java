package gtf.swift.state;

import java.sql.*;

public class State {
	private String procCenter;
	private String orderNo;
	private int gtfNo;
	private String stateNum;
	private String stateTran;
	private String messageType;
	private java.sql.Timestamp stateTS;

	private final static String insertSQL = "INSERT INTO State(ProcCenter,OrderNo,GtfNo,StateNum,StateTran,MessageType,StateTS) VALUES(?,?,?,?,?,?,?)";
	private final static String updateSQL = "Update State SET StateNum=?, StateTran=?, StateTS=? WHERE OrderNo = ?";

	private static PreparedStatement update = null;
	private static PreparedStatement ps = null;

	public State() {
		procCenter = null;
		orderNo = null;
		gtfNo = 0;
		stateNum = null;
		stateTran = null;
		messageType = null;
		stateTS = null;
	}
	public State(String procCenter, String orderNumber, int gtfNumber, String stateNum,
             	String stateTran, String messageType, java.sql.Timestamp ts) {
		this.procCenter = procCenter;
		this.orderNo = orderNumber;
		this.gtfNo = gtfNumber;
		this.stateNum = stateNum;
		this.stateTran = stateTran;
		this.messageType = messageType;
		this.stateTS = ts;

	}
	public int getGtfNo() {
		return(gtfNo);
	}
	public String getMessageType() {
		return(messageType);
	}
	public String getOrderNo() {
		return(orderNo);
	}
	public String getProcCenter() {
		return(procCenter);
	}
	public String getStateNum() {
		return(stateNum);
	}
	public String getStateTran() {
		return(stateTran);
	}
	public java.sql.Timestamp getStateTS() {
		return stateTS;
	}
	public int hashCode() {
		return(orderNo.hashCode());
	}
	/**
	   * Insert a State Object
	   * @exception SQLException When a SQL Exception occurred.
	   */
	public int insert(Connection con) throws SQLException {
		if (ps == null)
			prepareStatement(con);


		ps.setString(1, getProcCenter());
		ps.setString(2, getOrderNo());
		ps.setInt(3, getGtfNo());
		ps.setString(4, getStateNum());
		ps.setString(5, getStateTran());
		ps.setString(6, getMessageType());
		ps.setTimestamp(7, getStateTS());

		try {
			int r = ps.executeUpdate();
			return r;
		} catch (SQLException sqle) {

			if (sqle.getSQLState().equals("23505")) {

				update.setString(1, getStateNum());
				update.setString(2, getStateTran());
				update.setTimestamp(3, getStateTS());
				update.setString(4, getOrderNo());
				return (update.executeUpdate());
			}
		}
		return 0;

	}

	/**
	   * Make a new State object.
	   * @exception SQLException When a SQL Exception occurred.
	   */
	public static State makeObject(ResultSet rs) throws SQLException {
		return new State(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4),
                 		rs.getString(5), rs.getString(6), rs.getTimestamp(7));
	}


	private void prepareStatement(Connection con) throws SQLException {
		ps = con.prepareStatement(insertSQL);
		update = con.prepareStatement(updateSQL);
	}
	public void setGtfNo(int gtfNo) {
		this.gtfNo = gtfNo;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public void setProcCenter(String procCenter) {
		this.procCenter = procCenter;
	}
	public void setStateNum(String stateNum) {
		this.stateNum = stateNum;
	}
	public void setStateTran(String stateTran) {
		this.stateTran = stateTran;
	}
	public void setStateTS(java.sql.Timestamp ts) {
		stateTS = ts;
	}
	public String toString() {
		return "#State("+procCenter + " "+orderNo + " "+gtfNo + " "+stateNum + " "+
       		stateTran + " "+messageType + " "+stateTS + ")";

	}
}