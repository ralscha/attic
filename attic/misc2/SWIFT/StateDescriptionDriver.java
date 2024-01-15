
import java.sql.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class StateDescriptionDriver
{
	private DbAccess dba = null;

	public void dbconnect(DbAccess dba)
	{
		this.dba = dba;
	}

	public StateDescriptionDriver(DbAccess dba)
	{
		this.dba = dba;
	}


	public ResultSet query()
				throws SQLException
	{
		checkHandles();
		String sql = "SELECT Code, Colour, Description from StateDescription";
		return(dba.executeQuery(sql));
	}


    public StateDescription next(ResultSet rs) throws SQLException
    {
    	if (rs == null)
			return null;
		else
		{
			if (rs.next())
			{
            	StateDescription obj = new StateDescription();
    		    obj.setCode(rs.getString(1));
    			obj.setColour(rs.getInt(2));
    			obj.setDescription(rs.getString(3));
    			return obj;
			}
			else
			{
    			dba.close(rs);
	    		return null;
	    	}
		}
	}

	private void checkHandles()
				throws SQLException
	{
		if (dba == null)
			throw new SQLException();
	}
}
