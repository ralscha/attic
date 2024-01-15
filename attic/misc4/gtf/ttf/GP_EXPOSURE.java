package gtf.ttf;
 
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class GP_EXPOSURE {

	private String _RECORDID;
	private String _CIF_NUMBER;
	private java.math.BigDecimal _LIMIT_AMOUNT;
	private String _CURRENCY_ISO;
	private java.math.BigDecimal _UTILIZATION_AMOUNT;
	private java.math.BigDecimal _CREDIT_RISK;
	private java.sql.Date _FIX_DATE;
	private java.sql.Date _END_DATE;
	private String _PRODUCT_CODE;
	
	private final static String insertSQL = 
		"INSERT INTO GTFLC.GP_EXPOSURE(RECORDID,CIF_NUMBER,LIMIT_AMOUNT,CURRENCY_ISO,UTILIZATION_AMOUNT,"+
		"CREDIT_RISK,FIX_DATE,END_DATE,PRODUCT_CODE) " + 
		"VALUES(?,?,?,?,?,?,?,?,?)";
		   
	private static PreparedStatement insertPS = null;
	private static Connection con = null;

	
	public GP_EXPOSURE() {
		this._RECORDID = "30052";
		this._CIF_NUMBER = null;
		this._LIMIT_AMOUNT = null;
		this._CURRENCY_ISO = null;
		this._UTILIZATION_AMOUNT = null;
		this._CREDIT_RISK = null;
		this._FIX_DATE = null;
		this._END_DATE = null;
		this._PRODUCT_CODE = null;	
	}
	
	public GP_EXPOSURE(String _RECORDID, String _CIF_NUMBER, java.math.BigDecimal _LIMIT_AMOUNT, 
	                   String _CURRENCY_ISO, java.math.BigDecimal _UTILIZATION_AMOUNT, 
	                   java.math.BigDecimal _CREDIT_RISK, java.sql.Date _FIX_DATE, 
	                   java.sql.Date _END_DATE, String _PRODUCT_CODE) {
		this._RECORDID = "30052";
		this._CIF_NUMBER = _CIF_NUMBER;
		this._LIMIT_AMOUNT = _LIMIT_AMOUNT;
		this._CURRENCY_ISO = _CURRENCY_ISO;
		this._UTILIZATION_AMOUNT = _UTILIZATION_AMOUNT;
		this._CREDIT_RISK = _CREDIT_RISK;
		this._FIX_DATE = _FIX_DATE;
		this._END_DATE = _END_DATE;
		this._PRODUCT_CODE = _PRODUCT_CODE;
	}

	public String getRECORDID() {
		return _RECORDID;
	}

	public String getCIF_NUMBER() {
		return _CIF_NUMBER;
	}

	public void setCIF_NUMBER(String _CIF_NUMBER) {
		this._CIF_NUMBER = _CIF_NUMBER;
	}

	public java.math.BigDecimal getLIMIT_AMOUNT() {
		return _LIMIT_AMOUNT;
	}

	public void setLIMIT_AMOUNT(java.math.BigDecimal _LIMIT_AMOUNT) {
		this._LIMIT_AMOUNT = _LIMIT_AMOUNT;
	}

	public String getCURRENCY_ISO() {
		return _CURRENCY_ISO;
	}

	public void setCURRENCY_ISO(String _CURRENCY_ISO) {
		this._CURRENCY_ISO = _CURRENCY_ISO;
	}

	public java.math.BigDecimal getUTILIZATION_AMOUNT() {
		return _UTILIZATION_AMOUNT;
	}

	public void setUTILIZATION_AMOUNT(java.math.BigDecimal _UTILIZATION_AMOUNT) {
		this._UTILIZATION_AMOUNT = _UTILIZATION_AMOUNT;
	}

	public java.math.BigDecimal getCREDIT_RISK() {
		return _CREDIT_RISK;
	}

	public void setCREDIT_RISK(java.math.BigDecimal _CREDIT_RISK) {
		this._CREDIT_RISK = _CREDIT_RISK;
	}

	public java.sql.Date getFIX_DATE() {
		return _FIX_DATE;
	}

	public void setFIX_DATE(java.sql.Date _FIX_DATE) {
		this._FIX_DATE = _FIX_DATE;
	}

	public java.sql.Date getEND_DATE() {
		return _END_DATE;
	}

	public void setEND_DATE(java.sql.Date _END_DATE) {
		this._END_DATE = _END_DATE;
	}

	public String getPRODUCT_CODE() {
		return _PRODUCT_CODE;
	}

	public void setPRODUCT_CODE(String _PRODUCT_CODE) {
		this._PRODUCT_CODE = _PRODUCT_CODE;
	}

	public String toString() {
		return "#(" + _RECORDID + " " + _CIF_NUMBER + " " + _LIMIT_AMOUNT + " " + _CURRENCY_ISO + " " + _UTILIZATION_AMOUNT + " " + _CREDIT_RISK + " " + _FIX_DATE + " " + _END_DATE + " " + _PRODUCT_CODE + ")";
	}

	public int update(Connection con) throws SQLException {
		
		if ((this.con == null) || (this.con != con))
			prepareStatements(con);
				

		insertPS.setString(1, getRECORDID());
		insertPS.setString(2, getCIF_NUMBER());
		insertPS.setBigDecimal(3, getLIMIT_AMOUNT());
		insertPS.setString(4, getCURRENCY_ISO());
		insertPS.setBigDecimal(5, getUTILIZATION_AMOUNT());
		insertPS.setBigDecimal(6, getCREDIT_RISK());
		insertPS.setDate(7, getFIX_DATE());
		insertPS.setDate(8, getEND_DATE());
		insertPS.setString(9, getPRODUCT_CODE());
	
		return (insertPS.executeUpdate());
					
	}
	
	private void prepareStatements(Connection con) throws SQLException {
		insertPS = con.prepareStatement(insertSQL);
		this.con = con;
	}
	
}