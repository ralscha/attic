
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class StateDriver 
{
	private DbAccess dba = null;
	private PreparedStatement psSelect1 = null;
	private PreparedStatement psSelect2 = null;
	
    private final static String selectAllSQL =
                        "SELECT Proc_Center,Order_Number,Gtf_Number, State_Num,State_Tran,"+
                        "Message_Type,State_Date,State_Time "+
                        "from State ORDER BY State_Date DESC, State_Time DESC";
    private final static String selectGTFSQL =
                        "SELECT Proc_Center,Order_Number,Gtf_Number, State_Num,State_Tran,"+
                        "Message_Type,State_Date,State_Time "+
                        "from State "+
                        "WHERE Gtf_Number = ? "+
                        "ORDER BY State_Date DESC, State_Time DESC";
	
	public void dbconnect(DbAccess dba) 
	{
		this.dba = dba;
	}
	
	public StateDriver(DbAccess dba)
	{
		this.dba = dba;		
	}

	/*
	public int insert(SWIFTLines obj) throws SQLException 
	{
		checkHandles();
		if (psInsert == null) 
	    	psInsert = dba.getPreparedStatement(insertSQL);
	
	    psInsert.setString(1, obj.getTOSN());
        psInsert.setInt(2, obj.getLineno());
        psInsert.setString(3, obj.getField_Tag());
        psInsert.setString(4, obj.getMsg_Line());
        return(psInsert.executeUpdate());        		
	}
*/

	public ResultSet query(String condition, String orderby)
				throws SQLException 
	{
		checkHandles();
		String sql = "SELECT * FROM State";
		if (condition != null && condition.length() > 0) {
			sql += " WHERE " + condition;
		}
		if (orderby != null && orderby.length() > 0) {
			sql += " ORDER BY " + orderby;
		}
		return(dba.executeQuery(sql));
	}

	public ResultSet query() throws SQLException
	{
	    checkHandles();
   		if (psSelect1 == null) 
	    	psSelect1 = dba.getPreparedStatement(selectAllSQL);
        
        return (dba.executePreparedStatement(psSelect1));
	}
	
	public ResultSet query(int gtf_Number) throws SQLException
	{
	    checkHandles();
   		if (psSelect2 == null) 
	    	psSelect2 = dba.getPreparedStatement(selectGTFSQL);
        
        psSelect2.setInt(1, gtf_Number);
        return (dba.executePreparedStatement(psSelect2));	    
	}

	/*
    public int delete(String condition) throws SQLException
	{
	    checkHandles();
	    String sql = "DELETE FROM SWIFTLines";	    
		if (condition != null && condition.length() > 0) 
			sql += " WHERE " + condition;

	    return dba.executeUpdate(sql);
	}
	
    public int deleteTOSN(String TOSN) throws SQLException
	{
	    checkHandles();
        if (psDelete == null)
            psDelete = dba.getPreparedStatement(deleteSQL);
        
        psDelete.setString(1, TOSN);
        return (psDelete.executeUpdate());
	}*/
	
    public State next(ResultSet rs) throws SQLException 
    {
    	if (rs == null) 
			return null;		
		else 
		{
			if (rs.next()) 
			{			    
            	State obj = new State();
    		    obj.setProc_Center(rs.getString(1));
    			obj.setOrder_Number(rs.getString(2));
    			obj.setGtf_Number(rs.getInt(3));
    			obj.setState_Num(rs.getString(4));
    			obj.setState_Tran(rs.getString(5));
    			obj.setMessage_Type(rs.getString(6));
    			obj.setState_Date(rs.getDate(7));
    			obj.setState_Time(rs.getTime(8));
    			
    			return obj;
			}
			else
			{
    			dba.close(rs);
	    		return null;
	    	}
		}
	}

	public int countRows (String condition)
				throws SQLException 
	{
		checkHandles();
		String sql = "SELECT COUNT(*) FROM State";
		if (condition != null && condition.length() > 0) {
			sql += " WHERE " + condition;
		}
		ResultSet rs = dba.executeQuery(sql);
		rs.next();
		int count = rs.getInt(1);
		dba.close(rs);
		return count;
	}

	private void checkHandles()
				throws SQLException
	{
		if (dba == null)
			throw new SQLException();
	}
}
