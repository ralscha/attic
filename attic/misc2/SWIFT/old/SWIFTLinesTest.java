
import java.sql.*;

public class SWIFTLinesTest {
	public static void main(String args[]) {
		try {
		  String drv = "sun.jdbc.odbc.JdbcOdbcDriver";
		  String url = "jdbc:odbc:SWIFTInput";
		  DbAccess dba = new DbAccess(drv, url, "", "");
		  SWIFTLinesDriver driver = new SWIFTLinesDriver(dba);
		  if (args[0].equalsIgnoreCase("insert")) {
		    insert(driver, args);
		  } else if (args[0].equalsIgnoreCase("delete")) {
		    delete(driver, args);
		  } else if (args[0].equalsIgnoreCase("list")) {
		    list(driver, args);
		  }
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}

	private static void insert(SWIFTLinesDriver driver, String args[])
	  throws SQLException
	{
	  SWIFTLines obj = new SWIFTLines(args[1], Integer.parseInt(args[2]), args[3], args[4]	);
	  driver.insert(obj);
	}

	private static void delete(SWIFTLinesDriver driver, String args[])
	  throws SQLException
	{
	  SWIFTLines obj = new SWIFTLines(args[1], Integer.parseInt(args[2])	);
	  driver.delete(obj);
	}

	private static void list(SWIFTLinesDriver driver, String args[])
	  throws SQLException
	{
	  driver.query(args[1], args[2]);
	  SWIFTLines obj;
	  int i = 0;
	  while ((obj = driver.next()) != null) {
	    System.out.println((++i) + "." + obj.toString());
	  }
	}

	private static float parseFloat(String s)
	{
	  float f = (float) 0.0;
	  try {
	    f = Float.valueOf(s).floatValue();
	  } catch (NumberFormatException e) {
	    e.printStackTrace();
	  }
	  return f;
	}

	private static double parseDouble(String s)
	{
	  double f = 0.0;
	  try {
	    f = Double.valueOf(s).doubleValue();
	  } catch (NumberFormatException e) {
	    e.printStackTrace();
	  }
	  return f;
	}

}
