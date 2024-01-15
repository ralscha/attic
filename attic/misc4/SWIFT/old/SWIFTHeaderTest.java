
import java.sql.*;

public class SWIFTHeaderTest 
{
	public static void main(String args[]) 
	{
		try 
		{
		  String drv = "sun.jdbc.odbc.JdbcOdbcDriver";
		  String url = "jdbc:odbc:SWIFTInput";
		  DbAccess dba = new DbAccess(drv, url, "", "");
		  
		  SWIFTHeaderDriver driver = new SWIFTHeaderDriver(dba);
		  if (args[0].equalsIgnoreCase("insert")) 
		  {
		    insert(driver, args);
		  } 
		  else if (args[0].equalsIgnoreCase("delete")) 
		  {
		    delete(driver, args);
		  }
		  else if (args[0].equalsIgnoreCase("list")) 
		  {
		    list(driver, args);
		  }
		} 
		catch (Exception e) 
		{
		  e.printStackTrace();
		}
	}

	private static void insert(SWIFTHeaderDriver driver, String args[]) throws SQLException
	{
        SWIFTHeader obj = new SWIFTHeader(args[1], java.sql.Date.valueOf(args[2]), args[3], args[4], args[5], args[6], args[7], args[8], args[9], args[10], java.sql.Date.valueOf(args[11]), java.sql.Time.valueOf(args[12])	);
	    driver.insert(obj);
	}

	private static void delete(SWIFTHeaderDriver driver, String args[])
	  throws SQLException
	{
	    SWIFTHeader obj = new SWIFTHeader(args[1]	);
	    driver.delete(obj);
	}

	private static void list(SWIFTHeaderDriver driver, String args[])
	  throws SQLException
	{
	  driver.query(args[1], args[2]);
	  SWIFTHeader obj;
	  int i = 0;
	  while ((obj = driver.next()) != null) {
	    System.out.println((++i) + "." + obj.toString());
	  }
	}

	

}
