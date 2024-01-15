
package ch.ess.calendar.tools;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.math.*;
 
public class CreateTables {
	private Statement statement;
	private Connection con;
	
  public static void main(String[] args) {
		try {
			if (args.length == 3) {
				CreateTables ct = new CreateTables();
				ct.openDb(args[1], args[2]);
				if ("DROP".equalsIgnoreCase(args[0]))
					ct.dropTables();
				else if ("CREATE".equalsIgnoreCase(args[0]))
					ct.createTables();
				else if ("INSERT".equalsIgnoreCase(args[0]))
					ct.insertData();
				ct.shutDown();
			}
		} catch (Exception sqle) {
			System.err.println(sqle);
		}
	}

	void shutDown() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (con != null) {
   				con.commit();
				con.close();
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
		}
	}
	
	
	public void openDb(String driver, String dburl) throws SQLException {		
	  try {
		  Class.forName(driver).newInstance();
	  } catch (Exception e) {
		  System.err.println(e);
	  } 

		String user = "";
		String pw = "";
		con = DriverManager.getConnection(dburl, user, pw);
		statement = con.createStatement();
		

	}  

	
	private void createTables() {
		
		try {
			System.out.println(statement.executeUpdate("CREATE TABLE Users("+
					"userid			CHAR(20) NOT NULL Primary Key,"+
					"firstname     CHAR(100),"+
					"name          CHAR(100),"+
					"password      CHAR(50) NOT NULL,"+
					"administrator CHAR(1),"+
          "email CHAR(100))"));

			System.out.println(statement.executeUpdate("CREATE TABLE Categories("+
					"categoryid				INT NOT NULL AUTO INCREMENT Primary Key,"+
					"description         CHAR(50) NOT NULL,"+
					"color               CHAR(50) NOT NULL)"));

			System.out.println(statement.executeUpdate("CREATE TABLE Appointments("+
					"appointmentid			INT NOT NULL Primary Key,"+
					"userid              CHAR(20) NOT NULL,"+
					"categoryid          INT NOT NULL,"+
					"startdate           DATE NOT NULL,"+
					"enddate             DATE NOT NULL,"+
					"body                CHAR(200),"+
					"location            CHAR(50),"+
					"subject             CHAR(50),"+
					"alldayevent         CHAR(1),"+
					"importance          INT,"+
					"private             CHAR(1))"));

			System.out.println(statement.executeUpdate("CREATE TABLE Reminders("+
			      "reminderid          INT NOT NULL AUTO INCREMENT Primary Key,"+
					"appointmentid			INT NOT NULL,"+
					"minutesbefore       INT NOT NULL,"+
					"email               CHAR(100) NOT NULL)"));	

			System.out.println(statement.executeUpdate("CREATE TABLE Repeaters("+
			      "repeaterid          INT NOT NULL AUTO INCREMENT Primary Key,"+
					"appointmentid			INT NOT NULL,"+
					"until               DATE,"+
					"every               INT,"+
					"everyperiod         INT,"+
					"repeaton            INT,"+
					"repeatonweekday     INT,"+
					"repeatonperiod      INT)"));
			
			System.out.println(statement.executeUpdate("CREATE INDEX ReminderIx on Reminders (appointmentid)"));												
			System.out.println("Create Table successful");
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void insertData() {
		try {
			System.out.println(statement.executeUpdate("INSERT INTO Categories("+
					"categoryid,description,color) VALUES ("+
					"1, \"customer\", \"FF9900\")"));
			System.out.println(statement.executeUpdate("INSERT INTO Categories("+
					"categoryid,description,color) VALUES ("+
					"2, \"private\", \"0000FF\")"));
			System.out.println(statement.executeUpdate("INSERT INTO Categories("+
					"categoryid,description,color) VALUES ("+
					"3, \"holiday\", \"FFFF00\")"));
			System.out.println(statement.executeUpdate("INSERT INTO Categories("+
					"categoryid,description,color) VALUES ("+
					"4, \"military\", \"66CC00\")"));
			System.out.println(statement.executeUpdate("INSERT INTO Categories("+
					"categoryid,description,color) VALUES ("+
					"5, \"meeting\", \"FF0000\")"));
			System.out.println(statement.executeUpdate("INSERT INTO Categories("+
					"categoryid,description,color) VALUES ("+
					"6, \"education\", \"FF00FF\")"));																				
		
			System.out.println(statement.executeUpdate("INSERT INTO Users("+
					"userid,firstname,name,password,administrator) VALUES ("+
					"\"fh\", \"Fritz\", \"Hugentobler\",\"hugentobler\",\"Y\")"));																				
			
			System.out.println(statement.executeUpdate("INSERT INTO Users("+
					"userid,firstname,name,password,administrator) VALUES ("+
					"\"fx\", \"Felix\", \"Muster\",\"muster\",\"N\")"));																				
			
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

	public void dropTables() {
		try {
		
			statement.executeUpdate("DROP INDEX ReminderIx");
			statement.executeUpdate("DROP TABLE Users");
			statement.executeUpdate("DROP TABLE Categories");
			statement.executeUpdate("DROP TABLE Appointments");
			statement.executeUpdate("DROP TABLE Reminders");
			statement.executeUpdate("DROP TABLE Repeaters");
						
			System.out.println("Drop Table successful");
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	
		


}