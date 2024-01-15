
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;


public class SWIFTHeaderDriver 
{
	private DbAccess dba = null;
	private PreparedStatement psInsert = null;
	private PreparedStatement psSelectDates = null;
    private PreparedStatement psSelectDatesBranch = null;
    private PreparedStatement psSelectTOSN  = null;

	private final static String selectHeaderWithDatesSQL =
                        "SELECT TOSN,Send_Date,Address_Sender,"+
                        "Session_Number,Sequence_Number,Address_Receiver,Proc_Center"+
                        ",Message_Type,Duplicate,Priority,Receive_Date,Receive_Time "+
                        "from SWIFTHeader "+
                        "WHERE Receive_Date >= ? AND Receive_Date <= ? ORDER BY TOSN";

   	private final static String selectHeaderWithDatesBranchSQL =
                        "SELECT TOSN,Send_Date,Address_Sender,"+
                        "Session_Number,Sequence_Number,Address_Receiver,Proc_Center"+
                        ",Message_Type,Duplicate,Priority,Receive_Date,Receive_Time "+
                        "from SWIFTHeader "+
                        "WHERE Receive_Date >= ? AND Receive_Date <= ? AND Proc_Center = ? ORDER BY TOSN";


	private final static String insertSQL = "INSERT INTO SWIFTHeader(TOSN,Send_Date,Address_Sender,"+
                            "Session_Number,Sequence_Number,Address_Receiver,Proc_Center,Message_Type,Duplicate"+
                            ",Priority,Receive_Date,Receive_Time) "+
                            "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    
  	private final static String selectHeaderSQL =
                        "SELECT TOSN,Send_Date,Address_Sender,"+
                        "Session_Number,Sequence_Number,Address_Receiver,Proc_Center,Message_Type,Duplicate"+
                        ",Priority,Receive_Date,Receive_Time FROM SWIFTHeader "+
                        "WHERE TOSN = ?";

	
	public void dbconnect(DbAccess dba) 
	{
		this.dba = dba;
	}
		
	public SWIFTHeaderDriver(DbAccess dba)
	{
		this.dba = dba;
	}

    public void commit() throws SQLException
    {
        dba.commit();
    }
    
	public int insert(SWIFTHeader obj) throws SQLException 
	{
		checkHandles();
		
		if (psInsert == null) 
	    	psInsert = dba.getPreparedStatement(insertSQL);

	    psInsert.setString(1, obj.getTOSN());
        psInsert.setDate(2, obj.getSend_Date());
        psInsert.setString(3, obj.getAddress_Sender());
        psInsert.setString(4, obj.getSession_Number());
        psInsert.setString(5, obj.getSequence_Number());
        psInsert.setString(6, obj.getAddress_Receiver());
        psInsert.setString(7, obj.getProc_Center());
        psInsert.setString(8, obj.getMessage_Type());
        psInsert.setString(9, obj.getDuplicate());        
        psInsert.setString(10, obj.getPriority());       
        psInsert.setDate(11, obj.getReceive_Date());        
        psInsert.setTime(12, obj.getReceive_Time());        
        return(psInsert.executeUpdate());        				
	}


	public ResultSet query(String condition, String orderby)
				throws SQLException 
	{
		checkHandles();
		String sql = "SELECT * FROM SWIFTHeader";
		if (condition != null && condition.length() > 0)
			sql += " WHERE " + condition;

		if (orderby != null && orderby.length() > 0)
			sql += " ORDER BY " + orderby;

		return(dba.executeQuery(sql));
	}


	public SWIFTHeader retrieve(String TOSN) throws SQLException 
	{
		checkHandles();
		if (psSelectTOSN == null)
		    psSelectTOSN = dba.getPreparedStatement(selectHeaderSQL);
		
		psSelectTOSN.setString(1, TOSN);		
		ResultSet rs = dba.executePreparedStatement(psSelectTOSN);
		SWIFTHeader shObj = next(rs);
		if (shObj != null)
    		dba.close(rs);
		return(shObj);
		    
	}
	
    public ResultSet query(java.sql.Date from, java.sql.Date to) throws SQLException
    {
        checkHandles();
   		if (psSelectDates == null) 
	    	psSelectDates = dba.getPreparedStatement(selectHeaderWithDatesSQL);
        
	    psSelectDates.setDate(1, from);
        psSelectDates.setDate(2, to);
        return (dba.executePreparedStatement(psSelectDates));
    }
    
    public ResultSet query(java.sql.Date from, java.sql.Date to, String branch) throws SQLException
    {
        checkHandles();
   		if (psSelectDatesBranch == null) 
	    	psSelectDatesBranch = dba.getPreparedStatement(selectHeaderWithDatesBranchSQL);
        
	    psSelectDatesBranch.setDate(1, from);
        psSelectDatesBranch.setDate(2, to);
        psSelectDatesBranch.setString(3, branch);
        return (dba.executePreparedStatement(psSelectDatesBranch));
    }
	
	public int delete(String condition) throws SQLException
	{
	    checkHandles();
	    String sql = "DELETE FROM SWIFTHeader";	    
		if (condition != null && condition.length() > 0) 
			sql += " WHERE " + condition;
	
	    return (dba.executeUpdate(sql));
	}


    public SWIFTHeader next(ResultSet rs) throws SQLException 
    {
		if (rs == null) 
			return null;		
		else 
		{
			if (rs.next()) 
			{
        		SWIFTHeader obj = new SWIFTHeader();
        		obj.setTOSN(rs.getString(1));
        		obj.setSend_Date(rs.getDate(2));
        		obj.setAddress_Sender(rs.getString(3));
        		obj.setSession_Number(rs.getString(4));
        		obj.setSequence_Number(rs.getString(5));
        		obj.setAddress_Receiver(rs.getString(6));
        		obj.setProc_Center(rs.getString(7));
        		obj.setMessage_Type(rs.getString(8));
        		obj.setDuplicate(rs.getString(9));
        		obj.setPriority(rs.getString(10));
        		obj.setReceive_Date(rs.getDate(11));
        		obj.setReceive_Time(rs.getTime(12));
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
		String sql = "SELECT COUNT(*) FROM SWIFTHeader";
		if (condition != null && condition.length() > 0) 
			sql += " WHERE " + condition;

		ResultSet rs = dba.executeQuery(sql);
		rs.next();
		int count = rs.getInt(1);
		dba.close(rs);
		return count;
	}

	private void checkHandles()	throws SQLException
	{
		if (dba == null) 
			throw new SQLException();
	}
}
