package gtf.ttf;
 
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class GP_COLLATERAL {

	private String _RECORDID;
	private String _CIF_NUMBER;
	private String _CATEGORY;
	private java.math.BigDecimal _DEBIT_AMOUNT;
	private String _CURRENCY_ISO;
	private java.math.BigDecimal _CREDIT_AMOUNT;
	
	private final static String insertSQL = 
		"INSERT INTO GTFLC.GP_COLLATERAL(RECORDID,CIF_NUMBER,CATEGORY,DEBIT_AMOUNT,CURRENCY_ISO,CREDIT_AMOUNT) " +
	   "VALUES(?,?,?,?,?,?)";
	   
	private static PreparedStatement insertPS = null;
	private static Connection con = null;
	
	
	public GP_COLLATERAL()	{
		this._RECORDID = "30053";
		this._CIF_NUMBER = null;
		this._CATEGORY = null;
		this._DEBIT_AMOUNT = null;
		this._CURRENCY_ISO = null;
		this._CREDIT_AMOUNT = null;		
	}
	
	public GP_COLLATERAL(String _CIF_NUMBER, String _CATEGORY, 
								 java.math.BigDecimal _DEBIT_AMOUNT, String _CURRENCY_ISO, 
								 java.math.BigDecimal _CREDIT_AMOUNT) {

		this._RECORDID = "30053";
		this._CIF_NUMBER = _CIF_NUMBER;
		this._CATEGORY = _CATEGORY;
		this._DEBIT_AMOUNT = _DEBIT_AMOUNT;
		this._CURRENCY_ISO = _CURRENCY_ISO;
		this._CREDIT_AMOUNT = _CREDIT_AMOUNT;
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

	public String getCATEGORY() {
		return _CATEGORY;
	}

	public void setCATEGORY(String _CATEGORY) {
		this._CATEGORY = _CATEGORY;
	}

	public java.math.BigDecimal getDEBIT_AMOUNT()	{
		return _DEBIT_AMOUNT;
	}

	public void setDEBIT_AMOUNT(java.math.BigDecimal _DEBIT_AMOUNT) {
		this._DEBIT_AMOUNT = _DEBIT_AMOUNT;
	}

	public String getCURRENCY_ISO() {
		return _CURRENCY_ISO;
	}

	public void setCURRENCY_ISO(String _CURRENCY_ISO) {
		this._CURRENCY_ISO = _CURRENCY_ISO;
	}

	public java.math.BigDecimal getCREDIT_AMOUNT() {
		return _CREDIT_AMOUNT;
	}

	public void setCREDIT_AMOUNT(java.math.BigDecimal _CREDIT_AMOUNT) {
		this._CREDIT_AMOUNT = _CREDIT_AMOUNT;
	}

	public String toString()	{
		return "#(" + _RECORDID + " " + _CIF_NUMBER + " " + _CATEGORY + " " + _DEBIT_AMOUNT + " " + _CURRENCY_ISO + " " + _CREDIT_AMOUNT + ")";
	}
	
	public int update(Connection con) throws SQLException {
			
		if ((this.con == null) || (this.con != con))
			prepareStatements(con);
				

		insertPS.setString(1, getRECORDID());
		insertPS.setString(2, getCIF_NUMBER());
		insertPS.setString(3, getCATEGORY());			
		insertPS.setBigDecimal(4, getDEBIT_AMOUNT());			
		insertPS.setString(5, getCURRENCY_ISO());
		insertPS.setBigDecimal(6, getCREDIT_AMOUNT());				
		return (insertPS.executeUpdate());
			
	}
	
	private void prepareStatements(Connection con) throws SQLException {
		insertPS = con.prepareStatement(insertSQL);
		this.con = con;
	}
}
	