
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class SWIFTLinesDriver 
{
	private DbAccess dba = null;
	private PreparedStatement psInsert = null;
	private PreparedStatement psSelect = null;
	private PreparedStatement psDelete = null;
	
    private final static String selectLinesSQL =
                        "SELECT TOSN,Lineno,Field_Tag,Msg_Line FROM SWIFTLines WHERE TOSN = ? " +
                        "ORDER BY lineno ASC";

    private final static String insertSQL = "INSERT INTO SWIFTLines(TOSN,Lineno,Field_Tag,Msg_Line) VALUES(?,?,?,?)";
	private final static String deleteSQL = "DELETE FROM SWIFTLines WHERE TOSN = ?";
	
	public void dbconnect(DbAccess dba) 
	{
		this.dba = dba;
	}
	
	public SWIFTLinesDriver(DbAccess dba)
	{
		this.dba = dba;		
	}

    public void commit() throws SQLException
    {
        dba.commit();
    }

	
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


	public ResultSet query( String condition, String orderby )
				throws SQLException 
	{
		checkHandles();
		String sql = "SELECT * FROM SWIFTLines";
		if (condition != null && condition.length() > 0) {
			sql += " WHERE " + condition;
		}
		if (orderby != null && orderby.length() > 0) {
			sql += " ORDER BY " + orderby;
		}
		return(dba.executeQuery(sql));
	}
	
	public ResultSet query(String TOSN) throws SQLException
	{
	    checkHandles();
   		if (psSelect == null) 
	    	psSelect = dba.getPreparedStatement(selectLinesSQL);
        
	    psSelect.setString(1, TOSN);
        return (dba.executePreparedStatement(psSelect));
	}
	
	
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
	}
	
    public SWIFTLines next(ResultSet rs) throws SQLException 
    {
    	if (rs == null) 
			return null;		
		else 
		{
			if (rs.next()) 
			{
            	SWIFTLines obj = new SWIFTLines();
    		    obj.setTOSN(rs.getString(1));
    			obj.setLineno(rs.getInt(2));
    			obj.setField_Tag(rs.getString(3));
    			obj.setMsg_Line(rs.getString(4));
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
		String sql = "SELECT COUNT(*) FROM SWIFTLines";
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
